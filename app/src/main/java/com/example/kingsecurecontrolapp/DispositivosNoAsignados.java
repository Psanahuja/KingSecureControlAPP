package com.example.kingsecurecontrolapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kingsecurecontrolapp.modelo.Casa;

public class DispositivosNoAsignados extends AppCompatActivity {
    private Casa casa;
    DispositivoAdapter dispositivoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispositivos_fragment);
        getSupportActionBar().setTitle("Dispositivos disponibles");
        casa = (Casa) getIntent().getSerializableExtra("casa");
        System.out.println(casa.getSinAsignar().getActuadores().size());

    }

}
