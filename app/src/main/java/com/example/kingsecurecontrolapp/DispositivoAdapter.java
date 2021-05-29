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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.kingsecurecontrolapp.Controlador.CasaController;
import com.example.kingsecurecontrolapp.exceptions.HabitacionConDispositivosException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Dispositivo;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
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



        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.dspTlt);
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
        holder.textView.setText(localDataSet.get(position).getNombre());
    }

    // Replace the contents of a view (invoked by the layout manager)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}