package com.example.kingsecurecontrolapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kingsecurecontrolapp.Controlador.CasaController;
import com.example.kingsecurecontrolapp.Controlador.ControllerFactory;
import com.example.kingsecurecontrolapp.Controlador.HabitacionController;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Casa casa = new Casa("Casa Pepe");
    private HabitacionController habitacionController = new HabitacionController();
    private HabitacionAdapter habitacionAdapter;
    private RecyclerView recyclerView;
    RequestQueue requestQueue;
    ControllerFactory controllerFactory;
    CasaController casaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controllerFactory = new ControllerFactory();
        casaController = controllerFactory.newCasaController();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //casa.setHabitaciones(habitacionController.loadHabitaciones(habitacionController.getJsonHabitaciones()));
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 10);

        }
        habitacionAdapter = new HabitacionAdapter(casa, this, requestQueue, casaController);
        JsonArrayRequest habsReq = casaController.loadHabitaciones(casa.getHabitaciones(), habitacionAdapter);

        requestQueue.add(habsReq);




        findViewById(R.id.addHabitacion).
                setOnClickListener(v -> {
                    addHabitacion();
                });
        recyclerView = findViewById(R.id.HabitacionesRecyclerView);

        recyclerView.setAdapter(habitacionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void addHabitacion(){
        MaterialAlertDialogBuilder mADB = new MaterialAlertDialogBuilder(this);
        mADB.setTitle("Añade una habitación");
        EditText inputCodigo = new EditText(this);
        inputCodigo.setText("Código");
        EditText inputNombre = new EditText(this);
        inputNombre.setText("Nombre");
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.addView(inputCodigo);
        linearLayout.addView(inputNombre);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        mADB.setView(linearLayout);
        MaterialAlertDialogBuilder habYaExiste = new MaterialAlertDialogBuilder(this);
        mADB.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Habitacion habitacion = new Habitacion(inputCodigo.getText().toString().trim(),
                        inputNombre.getText().toString().trim());
                try {
                    casa.addHabitacion(habitacion);
                    habitacionAdapter.addHabitacion(habitacion);
                    JsonObjectRequest habAddReq = casaController.addHabitacion(habitacion);
                    requestQueue.add(habAddReq);
                } catch (HabitacionYaExistenteException | JSONException e) {
                   habYaExiste.setTitle("Habitación ya existente");
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
}