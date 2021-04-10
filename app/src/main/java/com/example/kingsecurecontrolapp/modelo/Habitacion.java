package com.example.kingsecurecontrolapp.modelo;

import java.util.ArrayList;


public class Habitacion {

    private String codigo;
    private String nombre;
    private ArrayList<Sensor> sensores;
    private ArrayList<Actuador> actuadores;

    public Habitacion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sensores = new ArrayList<>();
        this.actuadores = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Sensor> getSensores() {
        return sensores;
    }

    public void addSensor(Sensor sensor) {
        this.sensores.add(sensor);
    }

    public void removeSensor(String codigoSensor){
        for(int i=0; i<this.sensores.size(); i++){
            if (this.sensores.get(i).getCodigo().equals(codigoSensor)){
                this.sensores.remove(i);
                break;
            }
        }
    }

    public ArrayList<Actuador> getActuadores() {
        return actuadores;
    }

    public void addActuador(Actuador actuador) {
        this.actuadores.add(actuador);
    }


    public void removeActuador(String codigoActuador){
        for(int i=0; i<this.actuadores.size(); i++){
            if (this.actuadores.get(i).getCodigo().equals(codigoActuador)){
                this.actuadores.remove(i);
                break;
            }
        }
    }


}
