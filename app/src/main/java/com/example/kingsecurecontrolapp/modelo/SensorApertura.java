package com.example.kingsecurecontrolapp.modelo;

public class SensorApertura extends Sensor{

    private EstadoSApertura estado;

    public SensorApertura(String codigo, String nombre) {
        super(codigo, nombre);

    }

    public EstadoSApertura getEstado() {
        return estado;
    }

    public void setEstado(EstadoSApertura estado) {
        this.estado = estado;
    }
}
