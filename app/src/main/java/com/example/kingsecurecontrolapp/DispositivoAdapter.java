package com.example.kingsecurecontrolapp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kingsecurecontrolapp.Controlador.CasaController;
import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.DispositivoNoAsignadoException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionConDispositivosException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Actuador;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Dispositivo;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.Sensor;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;
import com.example.kingsecurecontrolapp.modelo.SensorMovimiento;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;

import java.util.ArrayList;

public class DispositivoAdapter extends RecyclerView.Adapter<DispositivoAdapter.ViewHolder>{
    private ArrayList<Dispositivo> localDataSet;
    private Casa casa;
    private Context context;
    private RequestQueue requestQueue;
    private CasaController casaController;
    private Habitacion habitacion;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView estado;
        private final ImageButton imageButton;
        private String codDisp;



        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.dspTlt);
            estado = (TextView) view.findViewById(R.id.estado);
            imageButton = (ImageButton) view.findViewById(R.id.opciones);
        }

        public TextView getTextView() {
            return textView;
        }
        public TextView getEstado() { return estado; }
        public ImageButton getImageButton() { return imageButton; }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     *
     * by RecyclerView.
     */
    public DispositivoAdapter(Casa casa, Context context, RequestQueue requestQueue, CasaController casaController, Habitacion habitacion) {
        this.casa = casa;
        this.habitacion = habitacion;
        this.context = context;
        this.requestQueue=requestQueue;
        this.casaController = casaController;
        localDataSet = new ArrayList<>();
        localDataSet.addAll(habitacion.getSensores());
        localDataSet.addAll(habitacion.getActuadores());
    }
    public void dataSetChanged() throws InterruptedException {
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DispositivoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_dispositivo, viewGroup, false);

        return new DispositivoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DispositivoAdapter.ViewHolder holder, int position) {
        Dispositivo dispositivo = localDataSet.get(position);
        holder.codDisp = dispositivo.getCodigo();
        holder.textView.setText(dispositivo.getNombre());
        String est = casa.getEstadoDispositivo(dispositivo.getCodigo(), habitacion.getCodigo());
        holder.estado.setText(est);
        int pos = getPos(holder.codDisp);
        holder.imageButton.setOnClickListener(v -> {
            menu(pos, holder.codDisp);
        });
    }
    public int getPos(String codDisp){
        for (Dispositivo dispositivo : localDataSet){
            if (dispositivo.getCodigo().equals(codDisp)){
                return localDataSet.indexOf(dispositivo);
            }
        }
        return 0;
    }
    public void menu(int pos, String codDisp){
        MaterialAlertDialogBuilder madB = new MaterialAlertDialogBuilder(context);
        AlertDialog materialAlertDialogBuilder = madB.create();
        materialAlertDialogBuilder.setTitle("Opciones");
        if (habitacion.getCodigo().equals("000")){
            Button button =  new Button(context);
            button.setText("Añadir a una habitacion");
            button.setOnClickListener(v -> {
                anyadirAHabitacion(pos, codDisp);
                materialAlertDialogBuilder.cancel();
            });
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.addView(button);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            materialAlertDialogBuilder.setView(linearLayout);
            materialAlertDialogBuilder.setCancelable(true);
            materialAlertDialogBuilder.show();
        }
        else {
            Button button =  new Button(context);
            button.setText("Cambiar a una habitacion");
            button.setOnClickListener(v -> {
                cambiarHabDisp(pos, codDisp);
                materialAlertDialogBuilder.cancel();
            });
            Button btnrm = new Button(context);
            btnrm.setText("Desasociar dispositivo");
            btnrm.setOnClickListener(v -> {
                removeDisp(pos, codDisp);
                materialAlertDialogBuilder.cancel();
            });
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.addView(button);
            linearLayout.addView(btnrm);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            materialAlertDialogBuilder.setView(linearLayout);
            materialAlertDialogBuilder.setCancelable(true);
            materialAlertDialogBuilder.show();
        }
    }
    public void anyadirAHabitacion(int pos, String codDisp){
        MaterialAlertDialogBuilder mAterialDB = new MaterialAlertDialogBuilder(context);
        AlertDialog mADB = mAterialDB.create();
        mADB.setTitle("Escoge una habitacion");
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        mADB.setView(linearLayout);
        mADB.setCancelable(true);
        for (Habitacion hab : casa.getHabitaciones()){
            Button btnHab = new Button(context);
            btnHab.setText(hab.getNombre());
            btnHab.setOnClickListener(v ->{
                try {
                    Dispositivo dispositivo = getDisp(codDisp);
                    casa.addDispositivoAHabitacion(hab.getCodigo(), codDisp);
                    JsonObjectRequest jsonObjectRequest;
                    if (dispositivo.getClass().equals(Actuador.class)){
                        jsonObjectRequest = casaController.addActuadorToHabitacion(hab.getCodigo(), codDisp);
                    }
                    else {
                        jsonObjectRequest = casaController.addSensorToHabitacion(hab.getCodigo(), codDisp);
                    }
                    requestQueue.add(jsonObjectRequest);
                    notifyItemRemoved(pos);
                    localDataSet.clear();
                    localDataSet.addAll(habitacion.getSensores());
                    localDataSet.addAll(habitacion.getActuadores());
                    mADB.cancel();

                } catch (DispositivoConHabitacionExpception dispositivoConHabitacionExpception) {
                    dispositivoConHabitacionExpception.printStackTrace();
                } catch (HabitacionNoExistenteException e) {
                    e.printStackTrace();
                }
            });
            linearLayout.addView(btnHab);
        }

        mADB.show();
    }
    public void removeDisp(int pos, String codDisp){
        MaterialAlertDialogBuilder mADB = new MaterialAlertDialogBuilder(context);
        mADB.setTitle("¿Desea desasociar el dispositivo?");
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        mADB.setView(linearLayout);
        mADB.setCancelable(true);
        mADB.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Dispositivo dispositivo = getDisp(codDisp);
                    casa.removeDispositivoDeHabitacion(habitacion.getCodigo(), codDisp);
                    JsonObjectRequest jsonObjectRequest;
                    if (dispositivo.getClass().equals(Actuador.class)){
                        jsonObjectRequest = casaController.removeActFromHab(codDisp);
                        habitacion.removeActuador(codDisp);
                    }
                    else {
                        jsonObjectRequest = casaController.removesensorFromHab(codDisp);
                        habitacion.removeSensor(codDisp);
                    }
                    requestQueue.add(jsonObjectRequest);
                    notifyItemRemoved(pos);
                    localDataSet = new ArrayList<>();
                    localDataSet.addAll(habitacion.getSensores());
                    localDataSet.addAll(habitacion.getActuadores());
                    notifyDataSetChanged();
                } catch (DispositivoNoAsignadoException e) {
                    e.printStackTrace();
                } catch (HabitacionNoExistenteException e) {
                    e.printStackTrace();
                }
            }
        });
        mADB.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        mADB.show();
    }
    public void cambiarHabDisp(int pos, String codDisp){
        MaterialAlertDialogBuilder mAterialDB = new MaterialAlertDialogBuilder(context);
        AlertDialog mADB = mAterialDB.create();
        mADB.setTitle("Escoge una habitacion");
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        mADB.setView(linearLayout);
        mADB.setCancelable(true);
        for (Habitacion hab : casa.getHabitaciones()){
            if (hab.getCodigo().equals(habitacion.getCodigo())){
                continue;
            }
            Button btnHab = new Button(context);
            btnHab.setText(hab.getNombre());
            btnHab.setOnClickListener(v ->{
                try {
                    Dispositivo dispositivo = getDisp(codDisp);
                    casa.cambiarDispositivoDeHabitacion(habitacion.getCodigo(), hab.getCodigo(), codDisp);
                    JsonObjectRequest jsonObjectRequest;
                    if (dispositivo.getClass().equals(Actuador.class)){
                        jsonObjectRequest = casaController.addActuadorToHabitacion(hab.getCodigo(), codDisp);
                        habitacion.removeActuador(codDisp);
                    }
                    else {
                        jsonObjectRequest = casaController.addSensorToHabitacion(hab.getCodigo(), codDisp);
                        habitacion.removeSensor(codDisp);
                    }
                    requestQueue.add(jsonObjectRequest);
                    notifyItemRemoved(pos);
                    localDataSet = new ArrayList<>();
                    localDataSet.addAll(habitacion.getSensores());
                    localDataSet.addAll(habitacion.getActuadores());
                    notifyDataSetChanged();
                    mADB.cancel();

                } catch (HabitacionNoExistenteException e) {
                    e.printStackTrace();
                }
            });
            linearLayout.addView(btnHab);
        }

        mADB.show();

    }

    public Dispositivo getDisp(String codDisp){
        for (Dispositivo dispositivo : localDataSet){
            if (dispositivo.getCodigo().equals(codDisp)){
                return dispositivo;
            }
        }
        return null;
    }
    // Replace the contents of a view (invoked by the layout manager)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}