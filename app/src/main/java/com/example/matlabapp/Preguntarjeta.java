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

import org.json.JSONException;
import org.json.JSONObject;

public class Preguntarjeta extends AppCompatActivity {
    Button boton_preguntarjeta;
    TextView txt_codigo_juego;


    Button boton_perfil;
    Button boton_instrucciones;
    Button boton_ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntarjeta);

        txt_codigo_juego = findViewById(R.id.txt_codigo_juego);

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        int id_juego_activo = prefs.getInt("id_juego_activo",0);
        txt_codigo_juego.setText(" "+id_juego_activo);

        //CODIGO PARA REDIRECCIONAR CON EL BORON_PREGUNTARJETA
        boton_preguntarjeta = findViewById(R.id.btn_solicitar_tajeta);
        boton_preguntarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_preguntarjeta = new Intent(Preguntarjeta.this, Preguntarjetadesplegada.class);
                Preguntarjeta.this.startActivity(intent_preguntarjeta);

            }
        });
        //FIN CODIGO BOTON_INSTRUCCIONES


        cargarFuncionesMenu();

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
