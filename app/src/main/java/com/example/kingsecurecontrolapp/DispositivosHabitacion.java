package com.example.kingsecurecontrolapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.kingsecurecontrolapp.Controlador.CasaController;
import com.example.kingsecurecontrolapp.Controlador.ControllerFactory;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;

public class DispositivosHabitacion extends AppCompatActivity {
    private Casa casa;
    DispositivoAdapter dispositivoAdapter;
    ControllerFactory controllerFactory;
    CasaController casaController;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    private Habitacion habitacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispositivos_fragment);
        getSupportActionBar().setTitle("Dispositivos disponibles");
        casa = (Casa) getIntent().getSerializableExtra("casa");
        habitacion = (Habitacion) getIntent().getSerializableExtra("habitacion");
        controllerFactory = new ControllerFactory();
        casaController = controllerFactory.newCasaController();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        dispositivoAdapter = new DispositivoAdapter(casa, this, requestQueue, casaController, habitacion);
        recyclerView = findViewById(R.id.recylcerViewDispNoAsig);
        recyclerView.setAdapter(dispositivoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
