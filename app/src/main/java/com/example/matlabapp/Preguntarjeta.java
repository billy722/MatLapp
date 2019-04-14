package com.example.matlabapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.voice.AlwaysOnHotwordDetector;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Preguntarjeta extends AppCompatActivity {
    Button boton_preguntarjeta;
    TextView txt_codigo_juego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntarjeta);

        txt_codigo_juego = findViewById(R.id.txt_codigo_juego);

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        int id_juego_activo = prefs.getInt("id_juego_activo",0);
        txt_codigo_juego.setText(" "+id_juego_activo);

        //CODIGO PARA REDIRECCIONAR CON EL BORON_PREGUNTARJETA
        boton_preguntarjeta = findViewById(R.id.btn_instrucciones);
        boton_preguntarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_preguntarjeta = new Intent(Preguntarjeta.this, Preguntarjetadesplegada.class);
                Preguntarjeta.this.startActivity(intent_preguntarjeta);

            }
        });
        //FIN CODIGO BOTON_INSTRUCCIONES
    }
}
