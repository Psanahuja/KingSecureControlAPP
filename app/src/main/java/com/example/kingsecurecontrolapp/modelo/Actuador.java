package com.example.kingsecurecontrolapp.modelo;

public class Actuador extends Dispositivo {

    private EstadoActuador estado;

    public Actuador(String codigo, String nombre) {
        super(codigo, nombre);
    }

    public EstadoActuador getEstado() {
        return estado;
    }

    public void setEstado(EstadoActuador estado) {
        this.estado = estado;
    }
}
