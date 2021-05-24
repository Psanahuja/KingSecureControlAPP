package com.example.kingsecurecontrolapp.modelo;

import com.example.kingsecurecontrolapp.exceptions.DispositivoConHabitacionExpception;
import com.example.kingsecurecontrolapp.exceptions.DispositivoNoAsignadoException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionNoExistenteException;
import com.example.kingsecurecontrolapp.exceptions.HabitacionYaExistenteException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Casa {
    private String nombreCasa;
    private ArrayList<Habitacion> habitaciones;
    private final Habitacion sinAsignar;

    public Casa(String nombreCasa){
        this.nombreCasa = nombreCasa;
        this.habitaciones = new ArrayList<>();
        this.sinAsignar = new Habitacion("sin_asignar","Sin asignar");
    }
    public void pullData(){
        return;
    }
    public String getNombreCasa() {
        return nombreCasa;
    }

    public void setNombreCasa(String nombreCasa) {
        this.nombreCasa = nombreCasa;
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void addHabitacion(Habitacion habitacion) throws HabitacionYaExistenteException {
        for (Habitacion hab: habitaciones){
            if (hab.getCodigo().equals(habitacion.getCodigo())) {

                throw new HabitacionYaExistenteException();
            }
        }
        this.habitaciones.add(habitacion);
    }

    public void removeHabitacion(String codHabitacion) throws HabitacionNoExistenteException {
        boolean esta = false;
        for (Habitacion hab : habitaciones){
            if (hab.getCodigo().equals(codHabitacion)){
                esta = true;
                habitaciones.remove(hab);
                break;
            }
        }
        if (!esta){
            throw new HabitacionNoExistenteException();
        }
    }

    public void cambiarNombreHabitacion(String codHab, String nuevoNombre) throws HabitacionYaExistenteException{
        for (Habitacion hab : habitaciones){
            if (hab.getCodigo().equals(codHab)){
                for (Habitacion habitacion : habitaciones){
                    if (habitacion.getNombre().equals(nuevoNombre)){
                        throw new HabitacionYaExistenteException();
                    }
                }
                hab.setNombre(nuevoNombre);
            }
        }
    }

    public void addDispositivoAHabitacion(String codHabitacion, String codDispositivo) throws DispositivoConHabitacionExpception, HabitacionNoExistenteException {
        ArrayList<Sensor> sensoresDisp = sinAsignar.getSensores();
        ArrayList<Actuador> actuadors = sinAsignar.getActuadores();

        boolean aBorrar = false;
        if (!sensoresDisp.isEmpty()){
            for (Sensor sensor : sensoresDisp){
                if (sensor.getCodigo().equals(codDispositivo)){
                    for (Habitacion habitacion : habitaciones){
                        if (habitacion.getCodigo().equals(codHabitacion)){
                            if (habitacion.getSensores().contains(sensor)){
                                throw new DispositivoConHabitacionExpception();
                            }
                            habitacion.addSensor(sensor);
                            aBorrar = true;
                            if (sensor.getTipoSensor().equals("Apertura")){
                                SensorApertura sA = (SensorApertura) sensor;
                                sA.setEstado(EstadoSApertura.CLOSE);
                            }
                            else{
                                SensorMovimiento sM = (SensorMovimiento) sensor;
                                sM.setEstado(EstadoSMovimiento.NO_MOTION);
                            }
                            break;
                        }
                    }
                }
            }
            if (aBorrar){
                sinAsignar.removeSensor(codDispositivo);
            }
        }
        if (!aBorrar){

            for (Actuador actuador : actuadors){
                if (actuador.getCodigo().equals(codDispositivo)){
                    for (Habitacion habitacion : habitaciones){
                        if (habitacion.getCodigo().equals(codHabitacion)){
                            if (habitacion.getActuadores().contains(actuador)){
                                throw new DispositivoConHabitacionExpception();
                            }
                            habitacion.addActuador(actuador);
                            aBorrar = true;
                            actuador.setEstado(EstadoActuador.OFF);
                        }
                    }
                }
            }
            if (aBorrar){
                sinAsignar.removeActuador(codDispositivo);
            }
            else {
                throw new HabitacionNoExistenteException();
            }
        }
    }

    public void cambiarDispositivoDeHabitacion(String codHabitacion1, String codHabitacion2, String codDispositivo) throws HabitacionNoExistenteException {
        Habitacion hab1 = null;
        Habitacion hab2 = null;
        for(Habitacion habitacion : habitaciones){
            if (habitacion.getCodigo().equals(codHabitacion1)){
                hab1 = habitacion;
            }
            if (habitacion.getCodigo().equals(codHabitacion2)){
                hab2 = habitacion;
            }
        }
        if(hab1 == null || hab2 == null){
            throw new HabitacionNoExistenteException();
        }
        boolean necesarioOtroFor = true;
        for (Sensor sensor : hab1.getSensores()){
            if (sensor.getCodigo().equals(codDispositivo)){
                hab2.addSensor(sensor);
                hab1.removeSensor(codDispositivo);
                necesarioOtroFor = false;
                break;
            }
        }
        if (necesarioOtroFor){

            for (Actuador actuador : hab1.getActuadores()){
                if (actuador.getCodigo().equals(codDispositivo)) {
                    hab2.addActuador(actuador);
                    hab1.removeActuador(codDispositivo);
                    break;
                }
            }
        }
    }

    public void removeDispositivoDeHabitacion(String codHabitacion, String codDispositivo) throws DispositivoNoAsignadoException {
        Habitacion habitacion = null;
        boolean aBorrar = false;
        for (Habitacion hab : habitaciones){
            if (hab.getCodigo().equals(codHabitacion)){
                habitacion = hab;
                break;
            }
        }
        for (Sensor sensor : habitacion.getSensores()){
            if (sensor.getCodigo().equals(codDispositivo)){
                sinAsignar.addSensor(sensor);
                aBorrar=true;
            }
        }
        if (aBorrar){
            habitacion.removeSensor(codDispositivo);
        }
        else{
            for (Actuador actuador : habitacion.getActuadores()){
                if (actuador.getCodigo().equals(codDispositivo)){
                    sinAsignar.addActuador(actuador);
                    aBorrar=true;
                }
            }
        }
        if (aBorrar){
            sinAsignar.removeActuador(codDispositivo);
        }
        else {
            throw new DispositivoNoAsignadoException();
        }
    }

    public void addDispositivoACasa(Dispositivo dispositivo){
        if (dispositivo.getClass().equals(Actuador.class)){
            sinAsignar.addActuador((Actuador) dispositivo);
        }
        else {
            sinAsignar.addSensor((Sensor) dispositivo);
        }
    }

    public Habitacion getSinAsignar() {
        return sinAsignar;
    }

    //Devuelve un string con estado#codHabitacion
    public String getEstadoDispositivo(String codDispositivo, String codHabitacion){
        String estado = "";
        boolean encontrado = false;
        if (codHabitacion.equals("sin_asignar")){
            for (Actuador actuador : sinAsignar.getActuadores()){
                if (actuador.getCodigo().equals(codDispositivo)){
                    estado = actuador.getEstado().toString() + "#" +codHabitacion;
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado){
                for (Sensor sensor : sinAsignar.getSensores()){
                    if (sensor.getCodigo().equals(codDispositivo)){
                        encontrado = true;
                        if (sensor.getTipoSensor().equals("Apertura")){
                            SensorApertura sA = (SensorApertura) sensor;
                            estado = sA.getEstado().toString() + "#" + codHabitacion;
                        }
                        else {
                            SensorMovimiento sM = (SensorMovimiento) sensor;
                            estado = sM.getEstado().toString() + "#" + codHabitacion;
                        }
                        break;
                    }
                }
            }
        }
        else{
            for (Habitacion habitacion : habitaciones){
                if (habitacion.getCodigo().equals(codHabitacion)){
                    for (Actuador actuador : habitacion.getActuadores()){
                        if (actuador.getCodigo().equals(codDispositivo)){
                            encontrado = true;
                            estado = actuador.getEstado() + "#" + codHabitacion;
                            break;
                        }
                    }
                    if (!encontrado){
                        for (Sensor sensor : habitacion.getSensores()){
                            if (sensor.getCodigo().equals(codDispositivo)){
                                encontrado = true;
                                if (sensor.getTipoSensor().equals("Apertura")){
                                    SensorApertura sensorApertura = (SensorApertura) sensor;
                                    estado = sensorApertura.getEstado().toString() + "#" + codHabitacion;
                                }
                                else {
                                    SensorMovimiento sensorMovimiento = (SensorMovimiento) sensor;
                                    estado = sensorMovimiento.getEstado().toString() + "#" + codHabitacion;
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }

        return estado;
    }

    public ArrayList<Sensor> getSensoresHabitacion(String codHabitacion) throws HabitacionNoExistenteException {
        if (!habitaciones.isEmpty()){
            for (Habitacion hab : habitaciones){
                if (hab.getCodigo().equals(codHabitacion)){
                    return hab.getSensores();
                }
            }
        }
        throw new HabitacionNoExistenteException();
    }

    public ArrayList<Actuador> getActuadoresHabitacion(String codHabitacion) throws HabitacionNoExistenteException {

        if (!habitaciones.isEmpty()){
            for (Habitacion hab : habitaciones){
                if (hab.getCodigo().equals(codHabitacion)){
                    return hab.getActuadores();
                }
            }
        }
        throw new HabitacionNoExistenteException();
    }


}