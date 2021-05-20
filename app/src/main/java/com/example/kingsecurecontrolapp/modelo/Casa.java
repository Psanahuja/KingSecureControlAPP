package com.example.kingsecurecontrolapp.modelo;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.DispositivoNoAsignadoException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;

import java.util.ArrayList;

public class Casa {
    private String nombreCasa;
    private ArrayList<Habitacion> habitaciones;
    private final Habitacion sinAsignar;

    public Casa(String nombreCasa){
        this.nombreCasa = nombreCasa;
        this.habitaciones = new ArrayList<>();
        this.sinAsignar = new Habitacion("sin_asignar","Sin asignar");
    }
    public void pullData(){
        return;
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

    public void addHabitacion(Habitacion habitacion) throws HabitacionYaExistenteException {
        this.habitaciones.add(habitacion);
    }

    public void removeHabitacion(String codHabitacion) throws HabitacionNoExistenteException {
        return;
    }

    public void cambiarNombreHabitacion(String codHab, String nuevoNombre) throws HabitacionYaExistenteException{
        return;
    }

    public void addDispositivoAHabitacion(String codHabitacion, String codDispositivo) throws DispositivoConHabitacionExpception, HabitacionNoExistenteException {
        return;
    }

    public void cambiarDispositivoDeHabitacion(String codHabitacion1, String codHabitacion2, String codDispositivo) throws HabitacionNoExistenteException {
        return;
    }

    public void removeDispositivoDeHabitacion(String codHabitacion, String codDispositivo) throws DispositivoNoAsignadoException {
        return;
    }

    public void addDispositivoACasa(Dispositivo dispositivo){
        return;
    }

    public Habitacion getSinAsignar() {
        return sinAsignar;
    }

    //Devuelve un string con estado#codHabitacion
    public String getEstadoDispositivo(String codDispositivo){
        return "unimplemented";
    }

    public ArrayList<Dispositivo> getDispositivosHabitacion(String codHabitacion){
        return null;
    }

    public ArrayList<Sensor> getSensoresHabitacion(String codHabitacion){
        return null;
    }

    public ArrayList<Actuador> getActuadoresHabitacion(String codHabitacion){
        return null;
    }


}
