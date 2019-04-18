package com.example.matlabapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.matlabapp.Clases.Juegos;
import com.example.matlabapp.Clases.Preguntarjetas;
import com.example.matlabapp.Clases.Respuesta;

import org.json.JSONException;
import org.json.JSONObject;

public class Respuestas extends AppCompatActivity {

    ImageView imagen_respuesta;
    Button btn_seguir_jugando;
    int id_preguntarjeta;

    public MediaPlayer sonido;

    Button boton_perfil;
    Button boton_instrucciones;
    Button boton_preguntarjeta;
    Button boton_ranking;
    Button boton_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta_correcta);


        imagen_respuesta = findViewById(R.id.imagen_respuesta);

        int respuesta = getIntent().getExtras().getInt("respuesta");
        id_preguntarjeta = getIntent().getExtras().getInt("id_preguntarjeta");
        guardarRespuesta(respuesta, id_preguntarjeta);


        cargarFuncionesMenu();

        btn_seguir_jugando = findViewById(R.id.btn_seguir_jugando);
        btn_seguir_jugando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(consultar_jugador_logeado()) {
                    consultar_juego_activo_jugador();
                }
            }
        });
    }


    @Override
    public void onBackPressed (){
        if(consultar_jugador_logeado()) {
            consultar_juego_activo_jugador();
        }
    }

    public void cargarFuncionesMenu(){
        //CODIGO PARA REDIRECCIONAR CON EL BORON_INSTRUCCIONES
        boton_instrucciones = findViewById(R.id.btn_instrucciones);
        boton_instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_instrucciones = new Intent(Respuestas.this, Instrucciones.class);
                Respuestas.this.startActivity(intent_instrucciones);
                finish();
            }
        });


        //CODIGO PARA REDIRECCIONAR CON EL BOTON_PERFIL
        boton_perfil = findViewById(R.id.btn_perfil);
        boton_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_perfil = new Intent(Respuestas.this, Perfil.class);
                Respuestas.this.startActivity(intent_perfil);
                finish();

            }
        });


        //CODIGO PARA REDIRECCIONAR CON EL BOTON PREGINTARJETAS
        boton_preguntarjeta = findViewById(R.id.btn_preguntarjetas);
        boton_preguntarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(consultar_jugador_logeado()) {
                    consultar_juego_activo_jugador();
                }

            }
        });


        //CODIGO PARA REDIRECCIONAR CON EL BOTON RANKING
        boton_ranking = findViewById(R.id.btn_ranking);
        boton_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_juego = new Intent(Respuestas.this,Ranking.class);
                startActivity(intent_juego);
                finish();
            }
        });

        //BOTON VOLVER
        boton_volver = findViewById(R.id.btn_volver);
        boton_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(consultar_jugador_logeado()) {
                    consultar_juego_activo_jugador();
                }
            }
        });
    }

    public void guardarRespuesta(final int respuesta, int id_preguntarjeta){

        if(respuesta==1){
            imagen_respuesta.setBackgroundResource(R.drawable.respuesta_correcta);
            sonido = MediaPlayer.create(Respuestas.this,R.raw.correcto);
            sonido.start();

        }else{
            imagen_respuesta.setBackgroundResource(R.drawable.respuesta_incorrecta);
            sonido = MediaPlayer.create(Respuestas.this,R.raw.error);
            sonido.start();

            btn_seguir_jugando.setText("Esperar otro turno");
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

        Respuesta responder_preguntarjeta = new Respuesta(rut, id_preguntarjeta, respuesta, id_juego_activo,"http://www.matlapp.cl/matlapp_app/preguntarjeta/crear_respuesta_pregunta.php" ,responder_preguntarjeta_listener);
        RequestQueue queue = Volley.newRequestQueue(Respuestas.this);
        queue.add(responder_preguntarjeta);
    }


    public Boolean consultar_jugador_logeado(){
        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        if(rut_jugador_logeado.equals("")){
            //NO ESTA LOGEADO
            AlertDialog.Builder mensaje_login = new AlertDialog.Builder(Respuestas.this);
            mensaje_login.setPositiveButton("Ir a Perfil", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent_perfil = new Intent(Respuestas.this,Perfil.class);
                    startActivity(intent_perfil);
                }
            });
            mensaje_login.setMessage("INGRESA TUS DATOS PARA JUGAR").create().show();
            return false;
        }else{
            //ESTA LOGEADO
            return true;
        }
    }



    public void juegoEnSession(int id_juego, String jugador_creador){
        SharedPreferences preferencias = getSharedPreferences("datos_session_login",   Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("id_juego_activo" ,id_juego );
        editor.putString("jugador_creador" ,jugador_creador );
        editor.commit();
    }

    public void consultar_juego_activo_jugador(){

        Response.Listener<String> consulta_juego_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    String respuesta = respuestaJson.getString("juego_activo");

                    if(respuesta.equals("si")){
                        int id_juego = Integer.parseInt(respuestaJson.getString("id_juego"));
                        String jugador_creador = respuestaJson.getString("jugador_creador");


                        juegoEnSession(id_juego,jugador_creador);

                        Intent intent_preguntarjeta = new Intent(Respuestas.this, Preguntarjeta.class);
                        Respuestas.this.startActivity(intent_preguntarjeta);
                        finish();

                    }else if(respuesta.equals("no")){
                        //PERMITE CREAR JUEGO O UNIRSE A UNO
                        Intent intent_juego = new Intent(Respuestas.this, Juego.class);
                        startActivity(intent_juego);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        Juegos consulta_juego_activo = new Juegos(rut_jugador_logeado,"http://www.matlapp.cl/matlapp_app/juego/consultar_juego_jugador.php" ,consulta_juego_listener );
        RequestQueue queue = Volley.newRequestQueue(Respuestas.this);
        queue.add(consulta_juego_activo);
    }

}
