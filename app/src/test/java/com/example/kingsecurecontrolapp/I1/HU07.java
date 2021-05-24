package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.DispositivoNoAsignadoException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.EstadoSApertura;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.Sensor;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
//Como usuario quiero poder eliminar la asignación de un dispositivo a una habitación de forma que ese dispositivo deje de pertenecer a esa habitación y deje de estar activo.
public class HU07 {

    private Casa casa;
    private Habitacion hab1;
    private SensorApertura sensor1;
    private SensorApertura sensor2;

    @Before
    public void inicializarCasa() throws HabitacionYaExistenteException {
        casa = new Casa("MiCasa");
        hab1 = new Habitacion("hab1", "cocina");
        casa.addHabitacion(hab1);
        sensor1 = new SensorApertura("sensor1", "VentanaCocina");
        casa.addDispositivoACasa(sensor1);

        try{
            casa.addDispositivoAHabitacion("hab1", "sensor1");
        }catch(DispositivoConHabitacionExpception | HabitacionNoExistenteException e){
            System.out.println("El dispositivo ya tiene una habitación asignada.");
        }
        sensor2 = new SensorApertura("sensor2", "PuertaBalcon");
        casa.addDispositivoACasa(sensor2);

    }

    @Test
    //El usuario intenta borrar de un dispositivo la habitación que tiene asignada.
    public void borrarDispositivoAsignado() throws DispositivoNoAsignadoException, HabitacionNoExistenteException {
        //Given: Un dispositivo que se desea eliminar de una habitación a la cual está asignado
        //When: Se intenta eliminar su asignación
        casa.removeDispositivoDeHabitacion("hab1", "sensor1");

        //Then: El dispositivo deja de estar asignado a esta habitación y pasa a estado no activo
        boolean esta = false;
        for(Sensor s: casa.getSensoresHabitacion("hab1")){
            if (s.getCodigo().equals("sensor1")){
                esta = true;
                break;
            }
        }
        assertFalse(esta);
        Habitacion sinAsignar = casa.getSinAsignar();
        ArrayList<Sensor> sensores = sinAsignar.getSensores();

        boolean desconectado = false;
        for(Sensor s: sensores){
            if(s.getCodigo().equals("sensor1")){
                SensorApertura sApertura = (SensorApertura) s;
                if( sApertura.getEstado().equals(EstadoSApertura.DISCONNECTED))
                    desconectado = true;
                break;
            }
        }
        assertTrue(desconectado);
    }

    @Test(expected = DispositivoNoAsignadoException.class)
    //El usuario intenta borrar la asignación de una habitación a un dispositivo que no tiene habitación asignada.
    public void borrarDispositivoNoAsignado() throws DispositivoNoAsignadoException, HabitacionNoExistenteException {
        //Given: Un dispositivo que se desea eliminar su asignación cuando no tiene ninguna habitación asignada
        //When: Se intenta eliminar su asignación
        casa.removeDispositivoDeHabitacion("hab1", "sensor2");

        //Then: Se muestra el mensaje “El dispositivo seleccionado no tenía ninguna habitación asignada”

    }
}

