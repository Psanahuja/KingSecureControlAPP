package com.example.kingsecurecontrolapp.Controlador;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.kingsecurecontrolapp.HabitacionAdapter;
import com.example.kingsecurecontrolapp.modelo.Actuador;
import com.example.kingsecurecontrolapp.modelo.Casa;
import com.example.kingsecurecontrolapp.modelo.EstadoSMovimiento;
import com.example.kingsecurecontrolapp.modelo.Habitacion;
import com.example.kingsecurecontrolapp.modelo.Sensor;
import com.example.kingsecurecontrolapp.modelo.SensorApertura;
import com.example.kingsecurecontrolapp.modelo.SensorMovimiento;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.android.volley.RequestQueue;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CasaController extends Application {
    Retrofit retrofit;
    public CasaController(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://kingserve.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public JsonArrayRequest loadCasa(Casa casa,ArrayList<Habitacion> habitacions, HabitacionAdapter habitacionAdapter){
        EstadoAdapter estadoAdapter = new EstadoAdapter();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://kingserve.herokuapp.com/casa", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Gson gson = new Gson();
                    for (int j = 0; j < response.length(); j++) {
                        JSONObject habitacion = response.getJSONObject(j);
                        JSONObject habJson = habitacion.getJSONObject("habitacion");
                        Habitacion hab =gson.fromJson(String.valueOf(habJson), Habitacion.class);
                        JSONArray actJson = habitacion.getJSONArray("actuadores");
                        JSONArray senApJson = habitacion.getJSONArray("sensores_apertura");
                        JSONArray senMovJson = habitacion.getJSONArray("sensores_movimiento");
                        ArrayList<Actuador> actuadors = new ArrayList<>();
                        ArrayList<Sensor> sensors = new ArrayList<>();
                        for (int i = 0; i<actJson.length(); i++){
                            JSONObject actuadorJson = actJson.getJSONObject(i);
                            Actuador actuador = gson.fromJson(String.valueOf(actuadorJson), Actuador.class);
                            String estado = actuadorJson.getString("estado");
                            actuador.setEstado(estadoAdapter.estadoActuador(estado));
                            actuadors.add(actuador);
                        }
                        for (int i=0; i<senApJson.length(); i++){
                            JSONObject sensApJson = senApJson.getJSONObject(i);
                            SensorApertura sensorApertura = gson.fromJson(String.valueOf(sensApJson), SensorApertura.class);
                            String estado = sensApJson.getString("estado");
                            sensorApertura.setEstado(estadoAdapter.estadoSApertura(estado));
                            sensors.add(sensorApertura);
                        }
                        for (int i=0; i<senMovJson.length(); i++){
                            JSONObject senmovJson = senMovJson.getJSONObject(i);
                            SensorMovimiento sensorMovimiento = gson.fromJson(String.valueOf(senmovJson), SensorMovimiento.class);
                            String estado = senmovJson.getString("estado");
                            sensorMovimiento.setEstado(estadoAdapter.estadoSMovimiento(estado));
                            sensors.add(sensorMovimiento);
                        }
                        if (!hab.getCodigo().equals("000")) {
                            hab.setActuadores(actuadors);
                            hab.setSensores(sensors);
                            habitacions.add(hab);
                        }
                        else {
                            casa.getSinAsignar().setSensores(sensors);
                            casa.getSinAsignar().setActuadores(actuadors);
                        }
                    }

                    habitacionAdapter.dataSetChanged();
                } catch (JSONException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        return jsonArrayRequest;
    }
    public JsonObject getJsonCasa(){
        return null;
    }
    public JsonArrayRequest loadHabitaciones(ArrayList<Habitacion> habitacions, HabitacionAdapter habitacionAdapter){
        Gson gson = new Gson();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://kingserve.herokuapp.com/habitaciones", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject habitacion = response.getJSONObject(i);
                        Habitacion hab = gson.fromJson(String.valueOf(habitacion), Habitacion.class);
                        if (!hab.getCodigo().equals("000"))
                            habitacions.add(hab);

                    }
                    habitacionAdapter.dataSetChanged();
                } catch (JSONException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        return jsonArrayRequest;

    }
    public JsonObjectRequest addHabitacion(Habitacion habitacion) throws JSONException {
        JSONObject habJson = new JSONObject();
        habJson.put("codigo", habitacion.getCodigo());
        habJson.put("nombre", habitacion.getNombre());

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, "https://kingserve.herokuapp.com/habitacion/add", habJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        return jsonRequest;
    }
    public JsonRequest deleteHabitacion(String codHabitacion){
        String url = "https://kingserve.herokuapp.com/habitacion/delete/" + codHabitacion;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
                });
        return jsonObjectRequest;
    }
    public JsonObjectRequest editHabitacion(String codHab, String nombre) throws JSONException {
        JSONObject habJson = new JSONObject();
        habJson.put("codigo", codHab);
        habJson.put("nombre", nombre);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, "https://kingserve.herokuapp.com/habitacion/update", habJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        return jsonRequest;
    }
    public JsonArrayRequest getActuadoresHab(Habitacion habitacion){
        Gson gson = new Gson();
        String url = "https://kingserve.herokuapp.com/actuadores/" + habitacion.getCodigo();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject actuadores = response.getJSONObject(i);
                        Actuador actuador = gson.fromJson(String.valueOf(actuadores), Actuador.class);
                        habitacion.addActuador(actuador);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        return jsonArrayRequest;
    }
    public JsonObjectRequest getSensoresHab(Habitacion habitacion){
        Gson gson = new Gson();
        String url = "https://kingserve.herokuapp.com/sensores/" + habitacion.getCodigo();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("sensores_apertura");
                    ArrayList<Sensor> sensors = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject senAp = jsonArray.getJSONObject(i);
                        SensorApertura sensorApertura = gson.fromJson(String.valueOf(senAp), SensorApertura.class);
                        if (sensorApertura != null)
                            sensors.add(sensorApertura);

                    }
                    jsonArray = response.getJSONArray("sensores_movimiento");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject senMov = jsonArray.getJSONObject(i);
                        SensorMovimiento sensorMovimiento = gson.fromJson(String.valueOf(senMov), SensorMovimiento.class);
                        if (sensorMovimiento!=null)
                            sensors.add(sensorMovimiento);

                    }
                    habitacion.setSensores(sensors);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        return jsonObjectRequest;
    }
}
