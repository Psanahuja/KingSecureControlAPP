package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.Sensor;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

//Como usuario quiero consultar el estado de los dispositivos de una habitación
public class HU11{

    private Casa casa;
    private Habitacion hab1;
    private SensorApertura sensor;
    private SensorApertura sensor2;

    @Before
    public void inicializarCasa() throws HabitacionYaExistenteException {
        casa = new Casa("MiCasa");
        hab1 = new Habitacion("hab1", "cocina");
        casa.addHabitacion(hab1);
        sensor = new SensorApertura("sensor1", "VentanaCocina");
        casa.addDispositivoACasa(sensor);
        sensor2 = new SensorApertura("sensor2", "PuertaBalcon");
        casa.addDispositivoACasa(sensor2);
    }

    @Test
    //Se intenta consultar el estado de los dispositivos asignados a una habitación con dispositivos asignados.
    public void getEstadosHabLlena() throws HabitacionNoExistenteException, DispositivoConHabitacionExpception {
        //Given: Un habitación con dispositivos asignados
        casa.addDispositivoAHabitacion("hab1", "sensor1");
        casa.addDispositivoAHabitacion("hab1", "sensor2");
        //When: Se intenta comprobar el estado de los dispositivos asignados
        ArrayList<Sensor> sensores = casa.getSensoresHabitacion("hab1");
        //Then: Se muestra un listado con los estados de los diferentes dispositivos de la habitación.
        assertTrue(sensores.size()==2);//Los sensores contienen el estado
    }

    @Test
    //Se intenta consultar el estado de los dispositivos de una habitación sin dispositivos asignados.
    public void getEstadosHabEmpty() {
        //Given: Un habitación sin dispositivos asignados
        //When: Se intenta comprobar el estado de los dispositivos asignados
        ArrayList<Sensor> sensores = casa.getSensoresHabitacion("hab1");
        //Then: Se muestra el mensaje “La habitación seleccionada no tiene ningún dispositivo asignado”
        assertTrue(sensores.size()==0);//Los sensores contienen el estado

    }
}
