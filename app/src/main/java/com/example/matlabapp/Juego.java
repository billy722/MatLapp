package com.example.matlabapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Juego extends AppCompatActivity {

    Button btn_nuevo_juego;
    Button btn_unirte_juego;

    TextView txt_codigo_juego_unirme;
    Button btn_aceptar_unirme;

    Button boton_perfil;
    Button boton_instrucciones;
    Button boton_ranking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        btn_nuevo_juego = findViewById(R.id.btn_nuevo_juego);
        btn_unirte_juego = findViewById(R.id.btn_unirte_juego);

        txt_codigo_juego_unirme = findViewById(R.id.txt_codigo_juego_unirme);
        btn_aceptar_unirme =  findViewById(R.id.btn_aceptar_unirme);

        cargarFuncionesMenu();


        btn_nuevo_juego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crear_nuevo_juego();
            }
        });

        btn_unirte_juego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_codigo_juego_unirme.setVisibility(View.VISIBLE);
                btn_aceptar_unirme.setVisibility(View.VISIBLE);
            }
        });

        btn_aceptar_unirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txt_codigo_juego_unirme.equals("")){
                    AlertDialog.Builder mensaje1 = new AlertDialog.Builder(Juego.this);
                    mensaje1.setMessage("DEBE INGRESAR UN CODIGO : ").create().show();
                }else{

                    unirse_juego();
                }
            }
        });


    }

    public void juegoEnSession(int id_juego, String jugador_creador){
        SharedPreferences preferencias = getSharedPreferences("datos_session_login",   Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("id_juego_activo" ,id_juego);
        editor.putString("jugador_creador" ,jugador_creador);
        editor.commit();
    }

    public void unirse_juego(){

        btn_aceptar_unirme.setText("Cargando...");
        btn_aceptar_unirme.setClickable(false);

        Response.Listener<String> consulta_unirse_juego_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    String respuesta = respuestaJson.getString("juego_existe");


                    if(respuesta.equals("si") || respuesta.equals("duplicado")){
                        String jugador_creador = respuestaJson.getString("jugador_creador");

                        juegoEnSession(Integer.parseInt(txt_codigo_juego_unirme.getText().toString()),jugador_creador);

                        Intent intent_preguntarjeta = new Intent(Juego.this, Preguntarjeta.class);
                        startActivity(intent_preguntarjeta);
                        finish();

                        btn_aceptar_unirme.setText("UNIRME");
                        btn_aceptar_unirme.setClickable(true);


                    }else if(respuesta.equals("no")){
                        //JUEGO NO EXISTE
                        AlertDialog.Builder mensaje1 = new AlertDialog.Builder(Juego.this);
                        mensaje1.setMessage("EL JUEGO INGRESADO NO EXISTE").create().show();

                        btn_aceptar_unirme.setText("UNIRME");
                        btn_aceptar_unirme.setClickable(true);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder mensaje1 = new AlertDialog.Builder(Juego.this);
                    mensaje1.setMessage("que paso : "+e.getMessage()).create().show();
                }
            }
        };

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        Juegos consulta_juego_existe = new Juegos(Integer.parseInt(txt_codigo_juego_unirme.getText().toString()),rut_jugador_logeado,"http://www.matlapp.cl/matlapp_app/juego/consultar_juego_existe.php" ,consulta_unirse_juego_listener );
        RequestQueue queue = Volley.newRequestQueue(Juego.this);
        queue.add(consulta_juego_existe);
    }


    public void crear_nuevo_juego(){

        btn_nuevo_juego.setText("Cargando...");
        btn_nuevo_juego.setClickable(false);

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        final String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        Response.Listener<String> consulta_nuevo_juego_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    String respuesta = respuestaJson.getString("creado");
                    int id_juego = respuestaJson.getInt("id_juego_creado");


                    if(respuesta.equals("si")){

                        juegoEnSession(id_juego,rut_jugador_logeado);


                        Intent intent_preguntarjeta = new Intent(Juego.this, Preguntarjeta.class);
                        startActivity(intent_preguntarjeta);
                        finish();



                    }else if(respuesta.equals("no")){
                        //PERMITE CREAR JUEGO O UNIRSE A UNO
                        btn_nuevo_juego.setText("CREAR JUEGO");
                        btn_nuevo_juego.setClickable(true);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder mensaje1 = new AlertDialog.Builder(Juego.this);
                    mensaje1.setMessage("que paso : "+e.getMessage()).create().show();

                    btn_nuevo_juego.setText("CREAR JUEGO");
                    btn_nuevo_juego.setClickable(true);
                }
            }
        };



        Jugador consulta_nuevo_juego = new Jugador(rut_jugador_logeado,"http://www.matlapp.cl/matlapp_app/juego/crear_juego.php" ,consulta_nuevo_juego_listener );
        RequestQueue queue = Volley.newRequestQueue(Juego.this);
        queue.add(consulta_nuevo_juego);
    }





    public void cargarFuncionesMenu(){
        //CODIGO PARA REDIRECCIONAR CON EL BORON_INSTRUCCIONES
        boton_instrucciones = findViewById(R.id.btn_instrucciones);
        boton_instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_instrucciones = new Intent(Juego.this, Instrucciones.class);
                Juego.this.startActivity(intent_instrucciones);
                finish();

            }
        });


        //CODIGO PARA REDIRECCIONAR CON EL BOTON_PERFIL
        boton_perfil = findViewById(R.id.btn_perfil);
        boton_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_perfil = new Intent(Juego.this, Perfil.class);
                Juego.this.startActivity(intent_perfil);
                finish();

            }
        });



        //CODIGO PARA REDIRECCIONAR CON EL BOTON RANKING
        boton_ranking = findViewById(R.id.btn_ranking);
        boton_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_ranking = new Intent(Juego.this, Ranking.class);
                Juego.this.startActivity(intent_ranking);
                finish();

            }
        });
    }




}
