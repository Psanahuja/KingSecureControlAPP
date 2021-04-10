package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.EstadoSApertura;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
//Como usuario quiero poder asignar una habitación a un dispositivo de forma que el dispositivo pertenezca a esa habitación y esté activo.
public class HU06 {

    private Casa casa;
    private Habitacion hab1;
    private Habitacion hab2;
    private SensorApertura sensor;

    @Before
    public void inicializarCasa() {
        casa = new Casa("MiCasa");
        hab1 = new Habitacion("hab1", "cocina");
        hab2 = new Habitacion("hab2", "salon");
        casa.addHabitacion(hab1);
        casa.addHabitacion(hab2);
        sensor = new SensorApertura("sensor1", "VentanaCocina");
    }

    @Test
    //El usuario intenta asignar una habitación a un dispositivo que no tiene ninguna habitación asignada.
    public void anyadirDispositivoNoAsignado() throws DispositivoConHabitacionExpception {
        //Given: Un dispositivo que no tiene una habitación asignada
        //When: Se le asigna una habitación
        casa.addDispositivoAHabitacion("hab1", "sensor1");

        //Then: El dispositivo forma parte de esa habitación y cambia su estado a activo
        assertTrue(hab1.getSensores().contains(sensor));
        assertNotEquals(sensor.getEstado(),EstadoSApertura.DISCONNECTED);
    }

    @Test(expected = DispositivoConHabitacionExpception.class)
    //El usuario intenta asignar una habitación a un dispositivo que no tiene ninguna habitación asignada.
    public void anyadirDispositivoAsignado() throws DispositivoConHabitacionExpception {
        //Given: Un dispositivo con una habitación ya asignada
        try {
            casa.addDispositivoAHabitacion("hab1", "sensor1");
        }catch (DispositivoConHabitacionExpception e){

            fail();
        }
        //When: Se asigna la misma habitación que la que tiene asignada el dispositivo
        casa.addDispositivoAHabitacion("hab1", "sensor1");
        //Then: Se muestra el mensaje “El dispositivo ya estaba asignado a esa habitación previamente”

    }
}

