package com.example.kingsecurecontrolapp.Controlador;

public class ControllerFactory {
    public ControllerFactory(){}
    public CasaController newCasaController(){
        return new CasaController();
    }
    public HabitacionController newHabitacionController(){
        return new HabitacionController();
    }
}
