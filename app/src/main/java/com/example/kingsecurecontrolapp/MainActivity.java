package com.example.kingsecurecontrolapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
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

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements Serializable{
    private Casa casa = new Casa("Casa Pepe");
    private HabitacionController habitacionController = new HabitacionController();
    private HabitacionAdapter habitacionAdapter;
    private RecyclerView recyclerView;
    RequestQueue requestQueue;
    ControllerFactory controllerFactory;
    CasaController casaController;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        /*NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment3);
        System.out.println(getSupportFragmentManager().findFragmentById(R.id.fragment3));
        navController = navHostFragment.getNavController();*/
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
        JsonArrayRequest habsReq = casaController.loadCasa(casa, casa.getHabitaciones(), habitacionAdapter);

        requestQueue.add(habsReq);


        findViewById(R.id.btnSeeHab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDispNoAsig(v);
            }
        });

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
    private void goToDispNoAsig(View v){
        Intent intent = new Intent(this, DispositivosNoAsignados.class);
        try{
            //Creating the object
            //Creating stream and writing the object
            FileOutputStream fout=new FileOutputStream("casa");
            ObjectOutputStream out=new ObjectOutputStream(fout);
            out.writeObject(casa);
            out.flush();
            //closing the stream
            out.close();
            System.out.println("success");
        }catch(Exception e){System.out.println(e);}
        intent.putExtra("casa", casa);
        startActivity(intent);
        //navController.navigate(R.id.action_mainActivity_to_dispositivosNoAsignados);
    }
}