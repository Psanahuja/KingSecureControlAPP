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

import static org.junit.Assert.assertTrue;
//Como usuario quiero cambiar la habitación asignada a un dispositivo que se encuentra en otra habitación.
public class HU05 {

    private Casa casa;
    private Habitacion hab1;
    private Habitacion hab2;
    private SensorApertura sensor;

    @Before
    public void inicializarCasa() throws HabitacionYaExistenteException, HabitacionNoExistenteException, DispositivoConHabitacionExpception {
        casa = new Casa("MiCasa");
        hab1 = new Habitacion("hab1", "cocina");
        casa.addHabitacion(hab1);
        hab2 = new Habitacion("hab2", "salon");
        casa.addHabitacion(hab2);

        sensor = new SensorApertura("sensor1", "VentanaCocina");
        casa.addDispositivoACasa(sensor);
        casa.addDispositivoAHabitacion("hab1","sensor1");
    }

    @Test
    //El usuario intenta cambiar la habitación asignada  de un dispositivo a otra habitación que ya existe.
    public void anyadirDispAHabCorrecta() throws HabitacionNoExistenteException {
        //Given: Un conjunto de habitaciones ya existentes y un dispositivo asignado a una habitación
        //When: Se intenta cambiar la habitación asignada al dispositivo a una existente
        casa.cambiarDispositivoDeHabitacion("hab1", "hab2", "sensor1");


        //Then: Se cambia la habitación asignada al dispositivo
        boolean esta = false;
        for(Sensor s : casa.getSensoresHabitacion("hab2")){
            if(s.getCodigo().equals("sensor1")){
                esta = true;
                break;
            }
        }
        assertTrue(esta);
    }
    @Test(expected = HabitacionNoExistenteException.class)
    //El usuario intenta cambiar la habitación asignada de un dispositivo a una habitación que no existe.
    public void anyadirDispAHabIncorrecta() throws HabitacionNoExistenteException, DispositivoConHabitacionExpception {
        //Given: Un conjunto de habitaciones ya existentes y un dispositivo asignado a una habitación
        //When: Se intenta cambiar la habitación asignada al dispositivo a una que no existe
        casa.cambiarDispositivoDeHabitacion("hab1", "hab34", "sensor");
        //Then: Se muestra el mensaje “La habitación seleccionada no existe, por favor, seleccione una habitación válida.”
    }
}
