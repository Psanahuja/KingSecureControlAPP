package com.example.kingsecurecontrolapp.Aceptacion;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Actuador;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Dispositivo;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;

//  Como usuario quiero consultar los dispositivos de una habitacion
public class HU10 {
    private Casa casa;
    private Habitacion hab1;
    private Habitacion hab2;
    private SensorApertura sensor1;
    private SensorApertura sensor2;
    private SensorApertura sensor3;
    private SensorApertura sensor4;
    private SensorApertura sensor5;
    private Actuador act1;

    @Before
    public void inicializarCasa() throws HabitacionYaExistenteException {
        casa = new Casa("MiCasa");
        hab1 = new Habitacion("hab1", "cocina");
        hab2 = new Habitacion("hab2", "salon");
        casa.addHabitacion(hab1);
        casa.addHabitacion(hab2);
        sensor1 = new SensorApertura("sensor1", "VentanaCocina1");
        sensor2 = new SensorApertura("sensor2", "VentanaCocina2");
        sensor3 = new SensorApertura("sensor3", "VentanaCocina3");
        sensor4 = new SensorApertura("sensor4", "VentanaCocina4");
        sensor5 = new SensorApertura("sensor5", "VentanaCocina5");
        act1 = new Actuador("alarma1", "Alarma cocina");

        casa.addDispositivoACasa(sensor1);
        casa.addDispositivoACasa(sensor2);
        casa.addDispositivoACasa(sensor3);
        casa.addDispositivoACasa(sensor4);
        casa.addDispositivoACasa(sensor5);
        casa.addDispositivoACasa(act1);

        try{
            casa.addDispositivoAHabitacion("hab1", "sensor1");
            casa.addDispositivoAHabitacion("hab1", "sensor2");
            casa.addDispositivoAHabitacion("hab1", "sensor3");
            casa.addDispositivoAHabitacion("hab1", "sensor4");
            casa.addDispositivoAHabitacion("hab1", "sensor5");
            casa.addDispositivoAHabitacion("hab1", "alarma1");
        }catch(DispositivoConHabitacionExpception | HabitacionNoExistenteException e){
            System.out.println("El dispositivo ya tiene una habitaci??n asignada.");
        }

    }

    @Test
    //Se intenta consultar la lista de dispositivos asignados a una habitaci??n con dispositivos asignados.
    public void consultarHabitacionConDispositivos() throws HabitacionNoExistenteException {
        //Given: Una habitaci??n con dispositivos asignados
        //When: Se intenta consultar la lista de dispositivos asignados
        ArrayList<Dispositivo> dispositivosHabitacion = new ArrayList<>();
        dispositivosHabitacion.addAll(casa.getActuadoresHabitacion("hab1"));
        dispositivosHabitacion.addAll(casa.getSensoresHabitacion("hab1"));
        //Then: Se muestra un listado de los dispositivos asignados en esa habitaci??n
        assertEquals(6, dispositivosHabitacion.size());
        assertTrue(dispositivosHabitacion.contains(sensor1));
        assertTrue(dispositivosHabitacion.contains(sensor2));
        assertTrue(dispositivosHabitacion.contains(sensor3));
        assertTrue(dispositivosHabitacion.contains(sensor4));
        assertTrue(dispositivosHabitacion.contains(sensor5));
        assertTrue(dispositivosHabitacion.contains(sensor5));
    }

    @Test
    //Se intenta consultar la lista de dispositivos asignados a una habitaci??n sin dispositivos asignados
    public void consultarHabitacionSinDispositivos() throws HabitacionNoExistenteException {
        //Given: Una habitaci??n sin dispositivos asignados
        //When: Se intenta consultar la lista de dispositivos asignados
        ArrayList<Actuador> actuadores =casa.getActuadoresHabitacion("hab2");
        assertEquals(0,casa.getActuadoresHabitacion("hab2").size());
        assertEquals(0,casa.getSensoresHabitacion("hab2").size());
        //Then: Se muestra el mensaje "La habitaci??n seleccionada no tiene ning??n dispositivo asignado"

    }
}
