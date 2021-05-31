package com.example.kingsecurecontrolapp.modelo;

import java.io.Serializable;

public class SensorApertura extends Sensor implements Serializable {

    private EstadoSApertura estado;

    public SensorApertura(String codigo, String nombre) {
        super(codigo, nombre, "Apertura");
        this.estado = EstadoSApertura.DISCONNECTED;

    }

    public EstadoSApertura getEstado() {
        return estado;
    }

    public void setEstado(EstadoSApertura estado) {
        this.estado = estado;
    }
}
