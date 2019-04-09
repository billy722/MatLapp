package com.example.matlabapp.Clases;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Jugador extends StringRequest {

    private static final String url_login = "http://146.66.99.89/~daemmulc/matlapp/login.php";
    private Map<String, String> parametros;


    public Jugador(String rut, String nombre, int curso, Response.Listener<String> listener) {
        super(Method.POST, url_login, listener,null);


        parametros = new HashMap<>();
        parametros.put("rut",rut);
        parametros.put("nombre",nombre);
        parametros.put("curso",curso+"");
    }

    @Override
    public Map<String, String> getParams(){
        return parametros;
    }

}
