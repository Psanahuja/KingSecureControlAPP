package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
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

    @Before
    public void inicializarCasa() {
        casa = new Casa("MiCasa");
        hab1 = new Habitacion("hab1", "cocina");
        hab2 = new Habitacion("hab2", "salon");
        casa.addHabitacion(hab1);
        sensor1 = new SensorApertura("sensor1", "VentanaCocina");
        sensor2 = new SensorApertura("sensor2", "VentanaCocina");
        sensor3 = new SensorApertura("sensor3", "VentanaCocina");
        sensor4 = new SensorApertura("sensor4", "VentanaCocina");
        sensor5 = new SensorApertura("sensor5", "VentanaCocina");
        try{
            casa.addDispositivoAHabitacion("hab1", "sensor1");
            casa.addDispositivoAHabitacion("hab1", "sensor2");
            casa.addDispositivoAHabitacion("hab1", "sensor3");
            casa.addDispositivoAHabitacion("hab1", "sensor4");
            casa.addDispositivoAHabitacion("hab1", "sensor5");
        }catch(DispositivoConHabitacionExpception e){
            System.out.println("El dispositivo ya tiene una habitación asignada.");
        }

    }

    @Test
    //Se intenta consultar la lista de dispositivos asignados a una habitación con dispositivos asignados.
    public void consultarHabitacionConDispositivos() {
        //Given: Una habitación con dispositivos asignados
        //When: Se intenta consultar la lista de dispositivos asignados
        ArrayList<Dispositivo> dispositivosHabitacion = casa.getDispositivosHabitacion("hab1");
        //Then: Se muestra un listado de los dispositivos asignados en esa habitación
        assertTrue(dispositivosHabitacion.size()==5);
        assertTrue(dispositivosHabitacion.contains(sensor1));
        assertTrue(dispositivosHabitacion.contains(sensor2));
        assertTrue(dispositivosHabitacion.contains(sensor3));
        assertTrue(dispositivosHabitacion.contains(sensor4));
        assertTrue(dispositivosHabitacion.contains(sensor5));
    }


    //Se intenta consultar la lista de dispositivos asignados a una habitación sin dispositivos asignados
    public void consultarHabitacionSinDispositivos() {
        //Given: Una habitación sin dispositivos asignados
        //When: Se intenta consultar la lista de dispositivos asignados
        ArrayList<Dispositivo> dispositivosHabitacion = casa.getDispositivosHabitacion("hab2");
        //Then: Se muestra el mensaje "La habitación seleccionada no tiene ningún dispositivo asignado"
        assertTrue(dispositivosHabitacion.size()==0);

    }
}
