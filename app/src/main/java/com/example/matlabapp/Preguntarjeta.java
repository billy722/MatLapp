package com.example.matlabapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.voice.AlwaysOnHotwordDetector;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.matlabapp.Clases.Juegos;
import com.example.matlabapp.Clases.Jugador;

import org.json.JSONException;
import org.json.JSONObject;

public class Preguntarjeta extends AppCompatActivity {
    Button boton_preguntarjeta;
    TextView txt_codigo_juego;
    Button btn_finalizar_juego;

    Button boton_perfil;
    Button boton_instrucciones;
    Button boton_ranking;

    int id_juego_activo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntarjeta);

        txt_codigo_juego = findViewById(R.id.txt_codigo_juego);
        btn_finalizar_juego = findViewById(R.id.btn_finalizar_juego);


        //MUESTRA O NO EL BOTON PARA FINALIZAR JUEGO
        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
         id_juego_activo = prefs.getInt("id_juego_activo",0);
        String jugador_creador = prefs.getString("jugador_creador","");
        String jugador_logeado = prefs.getString("rut_jugador_logeado","");
        txt_codigo_juego.setText(" "+id_juego_activo);

        if(jugador_creador.equals(jugador_logeado)){

            btn_finalizar_juego.setVisibility(View.VISIBLE);
        }else{
            btn_finalizar_juego.setVisibility(View.GONE);
        }


        //CODIGO PARA REDIRECCIONAR CON EL BORON_PREGUNTARJETA
        boton_preguntarjeta = findViewById(R.id.btn_solicitar_tajeta);
        boton_preguntarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_preguntarjeta = new Intent(Preguntarjeta.this, Preguntarjetadesplegada.class);
                Preguntarjeta.this.startActivity(intent_preguntarjeta);
                finish();

            }
        });
        //FIN CODIGO BOTON_INSTRUCCIONES


        //BOTON FINALIZAR JUEGO
        btn_finalizar_juego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizarJuego();
                btn_finalizar_juego.setText("Cargando...");
                btn_finalizar_juego.setClickable(false);
            }
        });

        cargarFuncionesMenu();

    }

    public void quitarJuegoEnSession(){
        SharedPreferences preferencias = getSharedPreferences("datos_session_login",   Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("id_juego_activo" ,0);
        editor.putString("jugador_creador" ,"");
        editor.commit();
    }


    public void finalizarJuego(){


        Response.Listener<String> consulta_nuevo_juego_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    String respuesta = respuestaJson.getString("correcto");

                    if(respuesta.equals("si")){

                        quitarJuegoEnSession();

                        Intent intent_preguntarjeta = new Intent(Preguntarjeta.this, Juego.class);
                        startActivity(intent_preguntarjeta);
                        finish();

                    }else if(respuesta.equals("no")){
                        //PERMITE CREAR JUEGO O UNIRSE A UNO
                        AlertDialog.Builder mensaje1 = new AlertDialog.Builder(Preguntarjeta.this);
                        mensaje1.setMessage("Ops! Ocurrió un error, intenta nuevamente.").create().show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder mensaje1 = new AlertDialog.Builder(Preguntarjeta.this);
                    mensaje1.setMessage("ERROR : "+e.getMessage()).create().show();
                }
            }
        };



        Juegos consulta_nuevo_juego = new Juegos(id_juego_activo,"http://www.matlapp.cl/matlapp_app/juego/finalizar_juego.php" ,consulta_nuevo_juego_listener );
        RequestQueue queue = Volley.newRequestQueue(Preguntarjeta.this);
        queue.add(consulta_nuevo_juego);
    }


    public void cargarFuncionesMenu(){
        //CODIGO PARA REDIRECCIONAR CON EL BORON_INSTRUCCIONES
        boton_instrucciones = findViewById(R.id.btn_instrucciones);
        boton_instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_instrucciones = new Intent(Preguntarjeta.this, Instrucciones.class);
                Preguntarjeta.this.startActivity(intent_instrucciones);
                finish();

            }
        });


        //CODIGO PARA REDIRECCIONAR CON EL BOTON_PERFIL
        boton_perfil = findViewById(R.id.btn_perfil);
        boton_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_perfil = new Intent(Preguntarjeta.this, Perfil.class);
                Preguntarjeta.this.startActivity(intent_perfil);
                finish();

            }
        });


        //CODIGO PARA REDIRECCIONAR CON EL BOTON RANKING
        boton_ranking = findViewById(R.id.btn_ranking);
        boton_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_ranking = new Intent(Preguntarjeta.this, Ranking.class);
                Preguntarjeta.this.startActivity(intent_ranking);
                finish();

            }
        });
    }


}
