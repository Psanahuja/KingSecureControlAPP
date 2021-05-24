package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Actuador;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.Sensor;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//Como usuario quiero poder ver un listado de los dispositivos no asignados.
public class HU14{

    private Casa casa;



    @Before
    public void inicializarCasa() throws HabitacionYaExistenteException {
        casa = new Casa("MiCasa");
    }

    @Test
    //No existen dispositivos en el sistema que no estén asignados a alguna habitación.
    public void getHabitacionesCon() {
        //Given: Un conjunto de dispositivos no asignados vacío
        //When: El usuario pide un listado de los dispositivos
        Habitacion habNoAsig = casa.getSinAsignar();
        ArrayList<Sensor> sensores = habNoAsig.getSensores();
        ArrayList<Actuador> actuadores = habNoAsig.getActuadores();
        //Then: Se devolverá una lista vacía
        assertTrue(actuadores.size()==0);
        assertTrue(sensores.size()==0);
    }

    @Test
    //Existe uno o más dispositivos en el sistema que no están asignados a ninguna habitación.
    public void getHabitacionesSin() {
        //Given: Un conjunto de dispositivos no asignados con un dispositivo
        Sensor sensor = new SensorApertura("sensor1", "VentanaCocina");
        casa.addDispositivoACasa(sensor);
        //When: El usuario pide un listado de los dispositivos
        Habitacion habNoAsig = casa.getSinAsignar();
        ArrayList<Sensor> sensores = habNoAsig.getSensores();
        ArrayList<Actuador> actuadores = habNoAsig.getActuadores();
        //Then: Se devolverá una lista con un elemento
        assertEquals(0,actuadores.size());
        assertEquals(1,sensores.size());


    }
}
