package com.example.matlabapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.matlabapp.Clases.Juegos;
import com.example.matlabapp.Clases.Jugador;

import org.json.JSONException;
import org.json.JSONObject;

public class Juego extends AppCompatActivity {

    Button btn_nuevo_juego;
    Button btn_unirte_juego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        btn_nuevo_juego = findViewById(R.id.btn_nuevo_juego);
        btn_unirte_juego = findViewById(R.id.btn_unirte_juego);


        consultar_juego_activo_jugador();
    }

    public void juegoEnSession(int id_juego){
        SharedPreferences preferencias = getSharedPreferences("datos_session_login",   Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("id_juego_activo" ,id_juego );
        editor.commit();
    }

    public void consultar_juego_activo_jugador(){
        Response.Listener<String> consulta_juego_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    String respuesta = respuestaJson.getString("juego_activo");
                    int id_juego = Integer.parseInt(respuestaJson.getString("id_juego"));

                    if(respuesta.equals("si")){

//                        AlertDialog.Builder mensaje = new AlertDialog.Builder(Juego.this);
//                        mensaje.setMessage("SI TIENE JUEGO ACTIVO ID: "+id_juego).create().show();

                        Intent intent_preguntarjeta = new Intent(Juego.this, Preguntarjeta.class);
                        startActivity(intent_preguntarjeta);

                        juegoEnSession(id_juego);


                    }else if(respuesta.equals("no")){
                        //PERMITE CREAR JUEGO O UNIRSE A UNO
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        Juegos consulta_juego_activo = new Juegos(rut_jugador_logeado,"http://146.66.99.89/~daemmulc/matlapp/juego/consultar_juego_jugador.php" ,consulta_juego_listener );
        RequestQueue queue = Volley.newRequestQueue(Juego.this);
        queue.add(consulta_juego_activo);
    }

}
