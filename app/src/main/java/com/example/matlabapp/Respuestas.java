package com.example.matlabapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.matlabapp.Clases.Preguntarjetas;
import com.example.matlabapp.Clases.Respuesta;

import org.json.JSONException;
import org.json.JSONObject;

public class Respuestas extends AppCompatActivity {

    ImageView imagen_respuesta;
    int id_preguntarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta_correcta);


        imagen_respuesta = findViewById(R.id.imagen_respuesta);

        int respuesta = getIntent().getExtras().getInt("respuesta");
        id_preguntarjeta = getIntent().getExtras().getInt("id_preguntarjeta");
        guardarRespuesta(respuesta, id_preguntarjeta);

    }

    public void guardarRespuesta(final int respuesta, int id_preguntarjeta){

        if(respuesta==1){
            imagen_respuesta.setBackgroundResource(R.drawable.respuesta_correcta);
        }else{
            imagen_respuesta.setBackgroundResource(R.drawable.respuesta_incorrecta);
        }


        Response.Listener<String> responder_preguntarjeta_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    String respuesta_json = respuestaJson.getString("creado");

                      if(respuesta_json.equals("no")){
                          AlertDialog.Builder mensaje1 = new AlertDialog.Builder(Respuestas.this);
                          mensaje1.setMessage("OPS! OCURRIO UN ERROR, ELIGE UNA PREGUNTA NUEVAMENTE: ").create().show();
                      }else{
                          System.out.print("se guardo correctamente en bd. "+respuesta_json);
                      }


                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder mensaje1 = new AlertDialog.Builder(Respuestas.this);
                    mensaje1.setMessage("ERROR: "+e.getMessage()).create().show();
                }
            }
        };

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut = prefs.getString("rut_jugador_logeado", "");
        int id_juego_activo = prefs.getInt("id_juego_activo", 0);

//        AlertDialog.Builder mensaje1 = new AlertDialog.Builder(Respuestas.this);
//        mensaje1.setMessage("rut: "+rut+"  juego: "+id_juego_activo+" correcta: "+correcta+" id_juego: "+id_juego_activo).create().show();

        Respuesta responder_preguntarjeta = new Respuesta(rut, id_preguntarjeta, respuesta, id_juego_activo,"http://146.66.99.89/~daemmulc/matlapp/preguntarjeta/crear_respuesta_pregunta.php" ,responder_preguntarjeta_listener);
        RequestQueue queue = Volley.newRequestQueue(Respuestas.this);
        queue.add(responder_preguntarjeta);
    }

}
