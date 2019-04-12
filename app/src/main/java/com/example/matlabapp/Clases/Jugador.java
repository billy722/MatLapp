package com.example.matlabapp.Clases;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Jugador extends StringRequest {

    private Map<String, String> parametros;


    public Jugador(String rut, String nombre, int curso, String url,Response.Listener<String> listener) {
        super(Method.POST, url, listener,null);


        parametros = new HashMap<>();
        parametros.put("rut",rut);
        parametros.put("nombre",nombre);
        parametros.put("curso",curso+"");
    }

    public Jugador(String rut, String url, Response.Listener<String> listener){
      super(Method.POST, url, listener,null);
        parametros = new HashMap<>();
        parametros.put("rut",rut);
    }

    @Override
    public Map<String, String> getParams(){
        return parametros;
    }

}
