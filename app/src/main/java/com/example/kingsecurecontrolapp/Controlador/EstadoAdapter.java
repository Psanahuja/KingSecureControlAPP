package com.example.kingsecurecontrolapp.Controlador;

import com.example.kingsecurecontrolapp.modelo.EstadoActuador;
import com.example.kingsecurecontrolapp.modelo.EstadoSApertura;
import com.example.kingsecurecontrolapp.modelo.EstadoSMovimiento;

public class EstadoAdapter {
    public EstadoAdapter(){}
    public EstadoActuador estadoActuador(String estado){
        if (estado.equals(0)){
            return EstadoActuador.DISCONNECTED;
        }
        if (estado.equals(1)){
            return EstadoActuador.OFF;
        }
        return EstadoActuador.ON;
    }
    public EstadoSApertura estadoSApertura(String estado){
        if (estado.equals(0)){
            return EstadoSApertura.DISCONNECTED;
        }
        if (estado.equals(1)){
            return EstadoSApertura.CLOSE;
        }
        return EstadoSApertura.OPEN;
    }
    public EstadoSMovimiento estadoSMovimiento(String estado){
        if (estado.equals(0)){
            return EstadoSMovimiento.DISCONNECTED;
        }
        if (estado.equals(1)){
            return EstadoSMovimiento.NO_MOTION;
        }
        return EstadoSMovimiento.MOTION_DETECTED;
    }
}
