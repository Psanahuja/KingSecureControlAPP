package com.example.kingsecurecontrolapp.I1;

import com.example.kingsecurecontrolapp.MainActivity;
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

//Como usuario quiero que cuando cierre la aplicación se guarden los cambios que he realizado de forma que cuando vuelva a abrir la aplicación después de cerrarla sigan estando los dispositivos en las habitaciones que les asigne.
public class HU08{

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
    //El usuario abre la aplicación por primera vez y está la información por defecto(dispositivos sin asignar).
    public void anyadirDispAHabCorrecta()  {
        //Given:La aplicación nunca ha sido abierta
        //When:Se abre por primera vez
        casa = new Casa("MiCasaNueva");
        //Then: Está la información por defecto en la aplicación(ningún dispositivo asignado)
        assertTrue(casa.getHabitaciones().size()==0);
    }
    @Test
    //El usuario abre la aplicación y modifica la información asignando a un dispositivo una habitación,el usuario cierra la aplicación y cuando se vuelve a abrir estas modificaciones que realizó anteriormente al cierre de la aplicación estarán guardadas.
    public void anyadirDispAHabIncorrecta() throws HabitacionYaExistenteException {
        ///Given: La aplicación ya ha sido abierta y tiene cambios realizados
        casa = new Casa("MiCasa");
        hab1 = new Habitacion("hab1", "cocina");
        casa.addHabitacion(hab1);
        //When: Se cierra la aplicación
        casa = new Casa("otraCasa");

        casa.pullData();
        //Then: Los cambios realizados están guardados para la siguiente vez que se abra la app
        assertTrue(casa.getHabitaciones().size()>0);

    }
}
