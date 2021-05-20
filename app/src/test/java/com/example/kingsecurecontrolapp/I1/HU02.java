package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//Como usuario quiero poder crear una nueva habitación donde asignar mis dispositivos.
public class HU02 {

    private Casa casa;

    @Before
    public void inicializarCasa() {
        casa = new Casa("MiCasa");

    }

    @Test
    //El usuario intenta crear una nueva habitación que aún no existe en el sistema.
    public void crearHabNombreValido() throws HabitacionYaExistenteException {
        //Given: Un conjunto vacío de habitaciones
        //When: Se intenta crear una nueva habitación que aún no existe en el sistema
        Habitacion hab1 = new Habitacion("hab1", "cocina");
        casa.addHabitacion(hab1);
        //Then: Se crea la nueva habitación
        boolean esta = false;
        for(Habitacion h : casa.getHabitaciones()){
            if (h.getCodigo().equals("hab1")){
                esta = true;
                break;
            }
        }
        assertTrue(esta);
    }
    @Test(expected = HabitacionYaExistenteException.class)
    //El usuario intenta crear una habitación que ya existe en el sistema.
    public void crearHabNombreInvalido() throws HabitacionYaExistenteException {
        //Given: Se crea la nueva habitación
        Habitacion hab1 = new Habitacion("hab1", "cocina");
        try {
            casa.addHabitacion(hab1);
        } catch (HabitacionYaExistenteException e) {
            fail();
        }
        //When: Se intenta crear una nueva habitación que ya existe en el sistema
        Habitacion hab1_2 = new Habitacion("hab1", "cocina");
        casa.addHabitacion(hab1_2);
        //Then: Se muestra el mensaje “Ya existe una habitación con el mismo nombre, por favor, indique otro nombre.”
    }
}
