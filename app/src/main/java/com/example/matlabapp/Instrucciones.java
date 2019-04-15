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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.matlabapp.Clases.Juegos;

import org.json.JSONException;
import org.json.JSONObject;

public class Instrucciones extends AppCompatActivity {
    Button boton_perfil;
    Button boton_preguntarjeta;
    Button boton_ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);

        cargarFuncionesMenu();
    }

    public void cargarFuncionesMenu(){



        //CODIGO PARA REDIRECCIONAR CON EL BOTON_PERFIL
        boton_perfil = findViewById(R.id.btn_perfil);
        boton_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_perfil = new Intent(Instrucciones.this, Perfil.class);
                Instrucciones.this.startActivity(intent_perfil);
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

                Intent intent_ranking = new Intent(Instrucciones.this, Ranking.class);
                Instrucciones.this.startActivity(intent_ranking);
                finish();

            }
        });
    }



    public Boolean consultar_jugador_logeado(){
        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        if(rut_jugador_logeado.equals("")){
            //NO ESTA LOGEADO
            AlertDialog.Builder mensaje_login = new AlertDialog.Builder(Instrucciones.this);
            mensaje_login.setPositiveButton("Ir a Perfil", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent_perfil = new Intent(Instrucciones.this,Perfil.class);
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

                        Intent intent_preguntarjeta = new Intent(Instrucciones.this, Preguntarjeta.class);
                        Instrucciones.this.startActivity(intent_preguntarjeta);
                        finish();


                    }else if(respuesta.equals("no")){
                        //PERMITE CREAR JUEGO O UNIRSE A UNO
                        Intent intent_juego = new Intent(Instrucciones.this, Juego.class);
                        startActivity(intent_juego);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        Juegos consulta_juego_activo = new Juegos(rut_jugador_logeado,"http://146.66.99.89/~daemmulc/matlapp/juego/consultar_juego_jugador.php" ,consulta_juego_listener );
        RequestQueue queue = Volley.newRequestQueue(Instrucciones.this);
        queue.add(consulta_juego_activo);
    }
}
