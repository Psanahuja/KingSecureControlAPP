package com.example.kingsecurecontrolapp.Controlador;

import com.example.kingsecurecontrolapp.modelo.Casa;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class CasaController {
    public CasaController(){}
    public Casa loadCasa(JsonObject json){
        Gson gson = new Gson();
        Casa casa = gson.fromJson(json.toString(), Casa.class);
        return casa;
    }
    public JsonObject getJsonCasa(){
        return null;
    }
}
