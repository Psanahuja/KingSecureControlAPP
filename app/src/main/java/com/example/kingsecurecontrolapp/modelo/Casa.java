package com.example.kingsecurecontrolapp.modelo;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.DispositivoNoAsignadoException;

import java.util.ArrayList;

public class Casa {
    private String nombreCasa;
    private ArrayList<Habitacion> habitaciones;

    public Casa(String nombreCasa){
        this.nombreCasa = nombreCasa;
        this.habitaciones = new ArrayList<>();
    }

    public String getNombreCasa() {
        return nombreCasa;
    }

    public void setNombreCasa(String nombreCasa) {
        this.nombreCasa = nombreCasa;
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void addHabitacion(Habitacion habitacion){
        this.habitaciones.add(habitacion);
    }

    public void addDispositivoAHabitacion(String codHabitacion, String codDispositivo) throws DispositivoConHabitacionExpception {
        return;
    }

    public void removeDispositivoDeHabitacion(String codHabitacion, String codDispositivo) throws DispositivoNoAsignadoException {
        return;
    }

    //Devuelve un string con estado#codHabitacion
    public String getEstadoDispositivo(String codDispositivo){
        return "unimplemented";
    }

    public ArrayList<Dispositivo> getDispositivosHabitacion(String codHabitacion){
        return null;
    }


}
