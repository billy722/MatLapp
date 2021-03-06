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

import org.json.JSONException;
import org.json.JSONObject;

public class Ranking extends AppCompatActivity {

    Button boton_perfil;
    Button boton_instrucciones;
    Button boton_preguntarjeta;
    Button btn_volver;

    TextView txt_primer;
    TextView txt_segundo;
    TextView txt_tercero;
    TextView txt_cuarto;
    TextView txt_quinto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        cargarFuncionesMenu();
        consultar_datos_jugador_logeado();


        txt_primer = findViewById(R.id.txt_primer);
        txt_segundo = findViewById(R.id.txt_segundo);
        txt_tercero = findViewById(R.id.txt_tercero);
        txt_cuarto = findViewById(R.id.txt_cuarto);
        txt_quinto = findViewById(R.id.txt_quinto);
    }


    public void consultar_datos_jugador_logeado(){
        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        if(rut_jugador_logeado.equals("")){
            //NO ESTA LOGEADO
            AlertDialog.Builder mensaje_login = new AlertDialog.Builder(Ranking.this);
            mensaje_login.setPositiveButton("Ir a Perfil", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent_perfil = new Intent(Ranking.this,Perfil.class);
                    startActivity(intent_perfil);
                }
            });
            mensaje_login.setMessage("INGRESA TUS DATOS PARA VER EL RANKING").create().show();

        }else{

            consultar_ranking();

        }
    }

    public void consultar_ranking(){

        Response.Listener<String> consulta_juego_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    String primero = respuestaJson.getString("primero");
                    String segundo = respuestaJson.getString("segundo");
                    String tercero = respuestaJson.getString("tercero");
                    String cuarto = respuestaJson.getString("cuarto");
                    String quinto = respuestaJson.getString("quinto");

                    txt_primer.setText(primero);
                    txt_segundo.setText(segundo);
                    txt_tercero.setText(tercero);
                    txt_cuarto.setText(cuarto);
                    txt_quinto.setText(quinto);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        int curso_jugador_logeado = prefs.getInt("curso_jugador_logeado", 0);

        Juegos consulta_ranking = new Juegos(curso_jugador_logeado,0,"http://www.matlapp.cl/matlapp_app/juego/ranking_curso.php" ,consulta_juego_listener );
        RequestQueue queue = Volley.newRequestQueue(Ranking.this);
        queue.add(consulta_ranking);
    }

    public void cargarFuncionesMenu(){
        //CODIGO PARA REDIRECCIONAR CON EL BORON_INSTRUCCIONES
        boton_instrucciones = findViewById(R.id.btn_instrucciones);
        boton_instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_instrucciones = new Intent(Ranking.this, Instrucciones.class);
                Ranking.this.startActivity(intent_instrucciones);
                finish();

            }
        });


        //CODIGO PARA REDIRECCIONAR CON EL BOTON_PERFIL
        boton_perfil = findViewById(R.id.btn_perfil);
        boton_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_perfil = new Intent(Ranking.this, Perfil.class);
                Ranking.this.startActivity(intent_perfil);
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


        //CODIGO PARA REDIRECCIONAR CON EL BOTON ATRAS
        btn_volver = findViewById(R.id.btn_volver);
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ranking.super.onBackPressed();
            }
        });


    }

    public Boolean consultar_jugador_logeado(){
        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        if(rut_jugador_logeado.equals("")){
            //NO ESTA LOGEADO
            AlertDialog.Builder mensaje_login = new AlertDialog.Builder(Ranking.this);
            mensaje_login.setPositiveButton("Ir a Perfil", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent_perfil = new Intent(Ranking.this,Perfil.class);
                    startActivity(intent_perfil);
                    finish();

                }
            });
            mensaje_login.setMessage("INGRESA TUS DATOS PARA JUGAR").create().show();
            return false;
        }else{
            //ESTA LOGEADO
            return true;
        }
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

                    if(respuesta.equals("si")){
                        int id_juego = Integer.parseInt(respuestaJson.getString("id_juego"));

                        juegoEnSession(id_juego);

                        Intent intent_preguntarjeta = new Intent(Ranking.this, Preguntarjeta.class);
                        Ranking.this.startActivity(intent_preguntarjeta);
                        finish();


                    }else if(respuesta.equals("no")){
                        //PERMITE CREAR JUEGO O UNIRSE A UNO
                        Intent intent_juego = new Intent(Ranking.this, Juego.class);
                        startActivity(intent_juego);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        Juegos consulta_juego_activo = new Juegos(rut_jugador_logeado,"http://www.matlapp.cl/matlapp_app/juego/consultar_juego_jugador.php" ,consulta_juego_listener );
        RequestQueue queue = Volley.newRequestQueue(Ranking.this);
        queue.add(consulta_juego_activo);
    }
}
