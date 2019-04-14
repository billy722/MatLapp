package com.example.matlabapp.Clases;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Respuesta extends StringRequest {

    private Map<String, String> parametros;


    public Respuesta(String rut, int id_preguntarjeta, int respuesta, int id_juego, String url, Response.Listener<String> listener) {
        super(Method.POST, url, listener,null);

        parametros = new HashMap<>();
        parametros.put("rut",rut);
        parametros.put("pregunta",String.valueOf(id_preguntarjeta));
        parametros.put("correcta",String.valueOf(respuesta));
        parametros.put("id_juego",String.valueOf(id_juego));
    }


    @Override
    public Map<String, String> getParams(){
        return parametros;
    }

}
