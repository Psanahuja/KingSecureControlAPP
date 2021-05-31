package com.example.kingsecurecontrolapp.modelo;

import java.io.Serializable;

public class Actuador extends Dispositivo implements Serializable {

    private EstadoActuador estado;

    public Actuador(String codigo, String nombre) {
        super(codigo, nombre);
        this.estado = EstadoActuador.DISCONNECTED;
    }

    public EstadoActuador getEstado() {
        return estado;
    }

    public void setEstado(EstadoActuador estado) {
        this.estado = estado;
    }
}
