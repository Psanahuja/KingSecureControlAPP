package com.example.kingsecurecontrolapp.Controlador;

import com.example.kingsecurecontrolapp.modelo.Habitacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CasaService {
    @GET("/habitaciones")
    public Call<List<Habitacion>> getHabitaciones();

}
