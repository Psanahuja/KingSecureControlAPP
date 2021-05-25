package com.example.kingsecurecontrolapp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class HabitacionAdapter extends RecyclerView.Adapter<HabitacionAdapter.ViewHolder> {

    private ArrayList<Habitacion> localDataSet;
    private Casa casa;
    private Context context;

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
    public HabitacionAdapter(Casa casa, Context context) {
        this.casa = casa;
        localDataSet = casa.getHabitaciones();
        this.context = context;
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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.textView.setText(localDataSet.get(position).getNombre());
        viewHolder.imgdelete.setOnClickListener(v -> {
            try {
                deleteHabitacion(localDataSet.get(position).getCodigo(), position);
            } catch (HabitacionNoExistenteException e) {

            }

        });
    }
    public void addHabitacion(Habitacion habitacion){
        notifyItemInserted(localDataSet.size() - 1);
    }
    public void deleteHabitacion(String codHab, final int position) throws HabitacionNoExistenteException {
        MaterialAlertDialogBuilder mADB = new MaterialAlertDialogBuilder(context);
        mADB.setTitle("¿Desea eliminar la habitación?");
        MaterialAlertDialogBuilder habInex = new MaterialAlertDialogBuilder(context);
        mADB.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    casa.removeHabitacion(codHab);
                    notifyItemRemoved(position);
                } catch (HabitacionNoExistenteException e) {
                    habInex.setTitle("Esta habitacion no existe");
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
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}