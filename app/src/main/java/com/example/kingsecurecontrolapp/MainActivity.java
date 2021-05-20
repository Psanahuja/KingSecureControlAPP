package com.example.kingsecurecontrolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.kingsecurecontrolapp.Controlador.CasaController;
import com.example.kingsecurecontrolapp.Controlador.HabitacionController;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Casa casa = new Casa("Casa Pepe");
    private CasaController casaController = new CasaController();
    private HabitacionController habitacionController = new HabitacionController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //casa.setHabitaciones(habitacionController.loadHabitaciones(habitacionController.getJsonHabitaciones()));
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 10);

        }

    }
}