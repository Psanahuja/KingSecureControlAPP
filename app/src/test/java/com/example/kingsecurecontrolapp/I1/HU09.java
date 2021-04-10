package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//  Como usuario quiero consultar el estado de un dispositivo.
public class HU09 {

    private Casa casa;
    private Habitacion hab1;
    private SensorApertura sensor;

    @Before
    public void inicializarCasa() {
        casa = new Casa("MiCasa");
        hab1 = new Habitacion("hab1", "cocina");
        casa.addHabitacion(hab1);
        sensor = new SensorApertura("sensor1", "VentanaCocina");
        try{
            casa.addDispositivoAHabitacion("hab1", "sensor");
        }catch(DispositivoConHabitacionExpception e){
            System.out.println("El dispositivo ya tiene una habitacion asignada.");
        }
        sensor = new SensorApertura("sensor2", "PuertaBalcon");
    }

    @Test
    //Se intenta comprobar el estado de un dispositivo asignado a una habitacion. Cada 10 segundos se debe actualizar el estado de cada dispositivo.
    public void consultarEstadoDispositivoAsignado() {
        //Given: Un dispositivo asignado a una habitacion
        //When: Se intenta comprobar su estado
        String estadoDispositivo = casa.getEstadoDispositivo("sensor");
        //Then: Se muestra el estado del dispositivo y su habitacion asignada
        assertEquals(estadoDispositivo,"CLOSE#hab1");

    }

    @Test
    //Se intenta comprobar el estado de un dispositivo que no ha sido asignado a ninguna habitacion.
    public void consultarEstadoDispositivoNoAsignado() {
        //Given: Un dispositivo no asignado
        //When: Se intenta comprobar su estado
        String estadoDispositivo = casa.getEstadoDispositivo("sensor");
        //Then: Se muestra el estado del dispositvo(DISCONNECTED) y que aún no está asignado
        assertEquals(estadoDispositivo,"DISCONNECTED#" +
                "hab1");
    }
}

