package com.example.kingsecurecontrolapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kingsecurecontrolapp.modelo.Habitacion;

import java.util.ArrayList;

public class HabitacionAdapter extends RecyclerView.Adapter<HabitacionAdapter.ViewHolder> {

    private ArrayList<Habitacion> localDataSet;

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
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public HabitacionAdapter(ArrayList<Habitacion> dataSet) {
        localDataSet = dataSet;
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
    }
    public void addHabitacion(Habitacion habitacion){
        notifyItemInserted(localDataSet.size() - 1);
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}