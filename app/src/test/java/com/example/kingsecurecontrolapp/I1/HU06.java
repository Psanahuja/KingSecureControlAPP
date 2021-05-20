package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.EstadoSApertura;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.Sensor;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//Como usuario quiero poder asignar una habitación a un dispositivo de forma que el dispositivo pertenezca a esa habitación y esté activo.
public class HU06 {

    private Casa casa;
    private Habitacion hab1;
    private Habitacion hab2;
    private SensorApertura sensor;

    @Before
    public void inicializarCasa() throws HabitacionYaExistenteException {
        casa = new Casa("MiCasa");
        hab1 = new Habitacion("hab1", "cocina");
        hab2 = new Habitacion("hab2", "salon");
        casa.addHabitacion(hab1);
        casa.addHabitacion(hab2);
        sensor = new SensorApertura("sensor1", "VentanaCocina");
    }

    @Test
    //El usuario intenta asignar una habitación a un dispositivo que no tiene ninguna habitación asignada.
    public void anyadirDispositivoNoAsignado() throws DispositivoConHabitacionExpception, HabitacionNoExistenteException {
        //Given: Un dispositivo que no tiene una habitación asignada
        //When: Se le asigna una habitación
        Habitacion sinAsignar = casa.getSinAsignar();
        sinAsignar.addSensor(sensor);
        casa.addDispositivoAHabitacion("hab1", "sensor1");
        //Then: El dispositivo forma parte de esa habitación y cambia su estado a activo
        boolean esta = false;
        for(Sensor s : casa.getSensoresHabitacion("hab1")){
            if(s.getCodigo().equals("sensor1")){
                esta = true;
                break;
            }
        }
        assertTrue(esta);
        boolean desconectado = false;
        for(Sensor s: casa.getSensoresHabitacion("hab1")){
            if(s.getCodigo().equals("sensor1")){
                SensorApertura sApertura = (SensorApertura) s;
                if( sApertura.getEstado().equals(EstadoSApertura.DISCONNECTED))
                    desconectado = true;
                break;
            }
        }
        assertFalse(desconectado);
    }

    @Test(expected = DispositivoConHabitacionExpception.class)
    //El usuario intenta asignar una habitación a un dispositivo que tiene habitación asignada.
    public void anyadirDispositivoAsignado() throws DispositivoConHabitacionExpception, HabitacionNoExistenteException {
        //Given: Un dispositivo con una habitación ya asignada
        try {
            Habitacion sinAsignar = casa.getSinAsignar();
            sinAsignar.addSensor(sensor);
            hab1.addSensor(sensor);
            casa.addDispositivoAHabitacion("hab1", "sensor1");
            fail();
        }catch (DispositivoConHabitacionExpception | HabitacionNoExistenteException e){

        }
        //When: Se asigna la misma habitación que la que tiene asignada el dispositivo
        casa.addDispositivoAHabitacion("hab1", "sensor1");
        //Then: Se muestra el mensaje “El dispositivo ya estaba asignado a esa habitación previamente”

    }


}

