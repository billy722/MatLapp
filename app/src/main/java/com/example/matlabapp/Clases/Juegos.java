package com.example.matlabapp.Clases;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Juegos extends StringRequest {

    private Map<String, String> parametros;


    public Juegos(String jugador,String url, Response.Listener<String> listener) {
        super(Method.POST, url, listener,null);


        parametros = new HashMap<>();
        parametros.put("rut",jugador);
    }



    @Override
    public Map<String, String> getParams(){
        return parametros;
    }

}
