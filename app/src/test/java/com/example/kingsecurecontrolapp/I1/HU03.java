package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.HabitacionConDispositivosException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//Como usuario quiero borrar una habitación del sistema.
public class HU03 {

    private Casa casa;

    @Before
    public void inicializarCasa() {
        casa = new Casa("MiCasa");
    }

    @Test
    //El usuario intenta borrar una habitación que existe en el sistema.
    public void anyadirDispAHabCorrecta() throws HabitacionYaExistenteException, HabitacionNoExistenteException, HabitacionConDispositivosException {
        //Given:Un conjunto de habitaciones ya existentes
        Habitacion hab1 = new Habitacion("hab1", "cocina");
        casa.addHabitacion(hab1);
        //When: Se intenta borrar una habitación existente
        casa.removeHabitacion("hab1");
        //Then: Se elimina la habitación del sistema
        boolean esta = false;
        for(Habitacion h : casa.getHabitaciones()){
            if (h.getCodigo().equals("hab1")){
                esta = true;
                break;
            }
        }
        assertFalse(esta);
    }
    @Test(expected = HabitacionNoExistenteException.class)
    //El usuario intenta borrar una habitación que no existe en el sistema.
    public void anyadirDispAHabIncorrecta() throws HabitacionNoExistenteException, HabitacionConDispositivosException {
        //Given: Un conjunto de habitaciones ya existentes
        //When: Se intenta borrar una habitación que no existe
        casa.removeHabitacion("hab2");
        fail();
        //Then: Se muestra el mensaje “No existe una habitación con ese nombre, por favor, inténtelo con otro nombre.”
    }
}