package com.example.kingsecurecontrolapp.Aceptacion;


import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

//Como usuario quiero consultar el listado de habitaciones.
public class HU12{

    private Casa casa;



    @Before
    public void inicializarCasa() throws HabitacionYaExistenteException {
        casa = new Casa("MiCasa");
    }

    @Test
    //Se intenta consultar el listado de habitaciones cuando existen diferentes habitaciones creadas.
    public void getHabitacionesCon() throws HabitacionNoExistenteException, DispositivoConHabitacionExpception, HabitacionYaExistenteException {
        //Given: Un conjunto de habitaciones creadas
        Habitacion hab1 = new Habitacion("hab1", "habitacion1");
        Habitacion hab2 = new Habitacion("hab2", "habitacion2");
        Habitacion hab3 = new Habitacion("hab3", "habitacion3");
        Habitacion hab4 = new Habitacion("hab4", "habitacion4");

        casa.addHabitacion(hab1);
        casa.addHabitacion(hab2);
        casa.addHabitacion(hab3);
        casa.addHabitacion(hab4);

        //When: Se intenta consultar el listado de habitaciones
        ArrayList<Habitacion> habitaciones = casa.getHabitaciones();
        //Then: Se muestra un listado con las diferentes habitaciones que existen
        assertTrue(habitaciones.size()==4);
    }

    @Test
    //Se intenta consultar el listado de habitaciones cuando no existe ninguna habitación creada.
    public void getHabitacionesSin() {
        //Given: No existen habitaciones creadas
        //When: Se intenta consultar el listado de habitaciones
        ArrayList<Habitacion> habitaciones = casa.getHabitaciones();
        //Then: Se muestra el mensaje “No hay ninguna habitación creada actualmente.”
        assertTrue(habitaciones.size()==0);


    }
}
