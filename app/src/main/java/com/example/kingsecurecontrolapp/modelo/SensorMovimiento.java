package com.example.kingsecurecontrolapp.modelo;

public class SensorMovimiento extends Sensor {

    private EstadoSMovimiento estado;

    public SensorMovimiento(String codigo, String nombre) {
        super(codigo, nombre, "movimiento");
    }

    public EstadoSMovimiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoSMovimiento estado) {
        this.estado = estado;
    }
}
