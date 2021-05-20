package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.Dispositivo;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.Sensor;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

//Como usuario quiero agrupar los dispositivos actuales en una habitacion
public class HU01 {

    private Casa casa;
    private Habitacion hab1;
    private SensorApertura sensor;

    @Before
    public void inicializarCasa() throws HabitacionYaExistenteException {
        casa = new Casa("MiCasa");
        hab1 = new Habitacion("hab1", "cocina");
        casa.addHabitacion(hab1);
        sensor = new SensorApertura("sensor1", "VentanaCocina");
        casa.addDispositivoACasa(sensor);
    }

    @Test
    //Un usuario intenta agrupar un conjunto de dispositivos que aún no tienen una habitación asignada, a una habitación existente en el sistema.
    public void anyadirDispAHabCorrecta() throws HabitacionNoExistenteException, DispositivoConHabitacionExpception {
        //Given: Un conjunto de dispositivos existentes sin estar asignados a ninguna habitación
            //(sensor1)
        //When: Se intenta asignar los dispositivos a una habitación existente.

        casa.addDispositivoAHabitacion("hab1","sensor1");
        //Then: Cambia la habitación asignada de los dispositivos a la habitación indicada
        boolean esta = false;
        for(Sensor s : casa.getSensoresHabitacion("hab1")){
            if(s.getNombre().equals("sensor1")){
                esta = true;
                break;
            }
        }
        assertTrue(esta);
    }
    @Test(expected = HabitacionNoExistenteException.class)
    //Se intenta agrupar un conjunto de dispositivos que aún no tienen una habitación asignada a una habitación que no existe en el sistema.
    public void anyadirDispAHabIncorrecta() throws HabitacionNoExistenteException, DispositivoConHabitacionExpception {
        //Given: Un conjunto de dispositivos existentes sin estar asignados a ninguna habitación
            //(sensor1)
        //When: Se intenta asignar los dispositivos a una habitación inexistente
        casa.addDispositivoAHabitacion("hab1","sensor1");
        //Then: Se muestra el mensaje: “La habitación seleccionada no ha sido creada, por favor, seleccione una habitación ya creada”
    }
}
