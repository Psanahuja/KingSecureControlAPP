package com.example.kingsecurecontrolapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.kingsecurecontrolapp.Controlador.CasaController;
import com.example.kingsecurecontrolapp.exceptions.HabitacionConDispositivosException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HabitacionAdapter extends RecyclerView.Adapter<HabitacionAdapter.ViewHolder> {

    private ArrayList<Habitacion> localDataSet;
    private Casa casa;
    private Context context;
    private RequestQueue requestQueue;
    private CasaController casaController;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageButton imgdelete;
        private final ImageButton imgedit;
        private final ImageButton imgadvance;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.tltHabitacion);
            imgdelete = (ImageButton) view.findViewById(R.id.btnDeleteHab);
            imgedit = (ImageButton) view.findViewById(R.id.btnEditHab);
            imgadvance = (ImageButton) view.findViewById(R.id.btnSeeHab);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     *
     * by RecyclerView.
     */
    public HabitacionAdapter(Casa casa, Context context, RequestQueue requestQueue, CasaController casaController) {
        this.casa = casa;
        localDataSet = casa.getHabitaciones();
        this.context = context;
        this.requestQueue=requestQueue;
        this.casaController = casaController;
    }
    public void dataSetChanged() throws InterruptedException {
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_habitacion, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.setText(localDataSet.get(position).getNombre());
        viewHolder.imgdelete.setOnClickListener(v -> {
            try {
                int pos = getPos(viewHolder.textView.getText().toString());
                deleteHabitacion(localDataSet.get(pos).getCodigo(), pos);
            } catch (HabitacionNoExistenteException e) {

            }

        });
        viewHolder.imgedit.setOnClickListener(v -> {
            int pos = getPos(viewHolder.textView.getText().toString());
            editHabitacion(localDataSet.get(pos).getCodigo(), pos);
        });
        viewHolder.imgadvance.setOnClickListener(v -> {
            int pos = getPos(viewHolder.textView.getText().toString());
            advanceHabitacion(localDataSet.get(pos));
        });
    }
    public void addHabitacion(Habitacion habitacion){
        notifyItemInserted(localDataSet.size() - 1);
    }
    public void deleteHabitacion(String codHab, int position) throws HabitacionNoExistenteException {
        MaterialAlertDialogBuilder mADB = new MaterialAlertDialogBuilder(context);
        mADB.setTitle("¿Desea eliminar la habitación?");
        MaterialAlertDialogBuilder habInex = new MaterialAlertDialogBuilder(context);
        mADB.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    casa.removeHabitacion(codHab);
                    requestQueue.add(casaController.deleteHabitacion(codHab));
                    notifyItemRemoved(position);
                } catch (HabitacionNoExistenteException e) {
                    habInex.setTitle("Esta habitacion no existe");
                    habInex.show();
                }
                catch (HabitacionConDispositivosException e){
                    habInex.setTitle("Esta habitacion todavia tiene dispositivos asignados");
                    habInex.show();
                }
            }
        });
        mADB.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        mADB.show();


    }
    public void advanceHabitacion(Habitacion habitacion){
        Intent intent = new Intent(this.context, DispositivosHabitacion.class);
        intent.putExtra("casa", casa);
        intent.putExtra("habitacion", habitacion);
        this.context.startActivity(intent);
    }
    public void editHabitacion(String codHab, int position){
        MaterialAlertDialogBuilder mADB = new MaterialAlertDialogBuilder(context);
        mADB.setTitle("Cambia el nombre de la habitación");
        EditText inputNombre = new EditText(context);
        inputNombre.setText("Nombre");
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.addView(inputNombre);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        mADB.setView(linearLayout);
        MaterialAlertDialogBuilder habYaExiste = new MaterialAlertDialogBuilder(context);
        mADB.setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newNombre = inputNombre.getText().toString().trim();
                try {
                    casa.cambiarNombreHabitacion(codHab, newNombre);
                    requestQueue.add(casaController.editHabitacion(codHab, localDataSet.get(position).getNombre()));
                    notifyItemChanged(position);
                } catch (HabitacionYaExistenteException | JSONException e) {
                    habYaExiste.setTitle("Ese nombre ya existe");
                    habYaExiste.show();
                }
            }
        });
        mADB.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        mADB.show();
    }
    public void setLocalDataSet(ArrayList<Habitacion> habs){
        this.localDataSet = habs;
        notifyDataSetChanged();
    }
    private int getPos(String codHab){
        for (Habitacion hab : localDataSet){
            if (hab.getNombre().equals(codHab)){
                return localDataSet.indexOf(hab);
            }
        }
        return localDataSet.size();
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}