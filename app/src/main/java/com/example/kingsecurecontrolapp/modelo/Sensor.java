package com.example.kingsecurecontrolapp.modelo;

import java.io.Serializable;

public abstract class Sensor extends Dispositivo implements Serializable {

    private String tipoSensor;

    public Sensor(String codigo, String nombre, String tipoSensor) {
        super(codigo,nombre);
        this.tipoSensor=tipoSensor;
    }

    public String getTipoSensor(){
        return this.tipoSensor;
    }
    public void setTipoSensor(String tipo){ this.tipoSensor = tipo; }


}
