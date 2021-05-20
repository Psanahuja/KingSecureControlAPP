package com.example.kingsecurecontrolapp.Controlador;

import androidx.lifecycle.MutableLiveData;

import com.example.kingsecurecontrolapp.modelo.Actuador;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.Sensor;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;
import com.example.kingsecurecontrolapp.modelo.SensorMovimiento;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class HabitacionController {
    public Habitacion loadHabitacion(JsonObject json){
        Gson gson = new Gson();
        Habitacion habitacion = gson.fromJson(json.toString(), Habitacion.class);
        return habitacion;
    }
    public ArrayList<Habitacion> loadHabitaciones(JsonObject json){
        Gson gson = new Gson();
        Habitacion[]  habs= gson.fromJson(json.toString(), Habitacion[].class);
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        for (Habitacion h : habs){
            habitaciones.add(h);
        }
        return habitaciones;
    }

    public JsonObject getJsonHabitaciones(){
        return new JsonObject();
    }

    public ArrayList<Sensor> loadSensores(JsonObject json){
        Gson gson = new Gson();
        JsonObject jsonApertura = json.getAsJsonObject("sensores_apertura");
        JsonObject jsonMovimiento = json.getAsJsonObject("sensores_movimiento");
        SensorApertura[] senAP = gson.fromJson(jsonApertura.toString(), SensorApertura[].class);
        SensorMovimiento[] senMov = gson.fromJson(jsonMovimiento.toString(), SensorMovimiento[].class);
        ArrayList<Sensor> sensores = new ArrayList<>();
        for (SensorApertura s : senAP){
            sensores.add(s);
        }
        for (SensorMovimiento s: senMov){
            sensores.add(s);
        }
        return sensores;
    }

    public ArrayList<Actuador> loadActuadores(JsonObject json){
        Gson gson = new Gson();
        Actuador[] act = gson.fromJson(json.toString(), Actuador[].class);
        ArrayList<Actuador> actuadores = new ArrayList<>();
        for (Actuador a : act){
            actuadores.add(a);
        }
        return actuadores;
    }
}
