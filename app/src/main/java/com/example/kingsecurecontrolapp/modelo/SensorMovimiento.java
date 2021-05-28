package com.example.kingsecurecontrolapp.modelo;

import java.io.Serializable;

public class SensorMovimiento extends Sensor implements Serializable {

    private EstadoSMovimiento estado;

    public SensorMovimiento(String codigo, String nombre) {
        super(codigo, nombre, "Movimiento");
        this.estado = EstadoSMovimiento.DISCONNECTED;
    }

    public EstadoSMovimiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoSMovimiento estado) {
        this.estado = estado;
    }
}
