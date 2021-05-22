package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;

import org.junit.Before;
import org.junit.Test;

import java.io.PushbackInputStream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


//Como usuario quiero cambiar el nombre de una habitación existente del sistema.
public class HU04 {

    private Casa casa;
    private Habitacion hab1;
    private Habitacion hab2;
    private SensorApertura sensor;

    @Before
    public void inicializarCasa() throws HabitacionYaExistenteException {
        casa = new Casa("MiCasa");
        hab1 = new Habitacion("hab1", "cocina");
        hab2 = new Habitacion("hab2", "comedor");
        casa.addHabitacion(hab1);
        casa.addHabitacion(hab2);

    }

    @Test
    //El usuario intenta cambiar el nombre de una habitación a otro distinto de los que ya existen en el sistema.
    public void cambiarNombreHabCorrecto() throws HabitacionYaExistenteException {
        //Given:Un conjunto de habitaciones ya existentes
        //When: Se intenta cambiar el nombre de la habitación a uno diferente de los que ya existen
        casa.cambiarNombreHabitacion("hab1", "salon");
        //Then: Se cambia el nombre de la habitación
        boolean esta = false;
        for(Habitacion h : casa.getHabitaciones()){
            if (h.getNombre().equals("salon")){
                esta = true;
                break;
            }
        }
        assertTrue(esta);
    }
    @Test(expected = HabitacionYaExistenteException.class)
    //El usuario intenta cambiar el nombre de una habitación a uno que ya existe en el sistema.
    public void cambiarNombreHabitacionIncorrecto() throws HabitacionYaExistenteException {
        //Given: Un conjunto de habitaciones ya existentes
        //When: Se intenta cambiar el nombre de la habitación a uno de los que ya existen
        casa.cambiarNombreHabitacion("hab1", "comedor");
        fail();
        //Then: Se muestra el mensaje “Ya existe una habitación con ese nombre, por favor, indique uno nuevo.”
    }
}
