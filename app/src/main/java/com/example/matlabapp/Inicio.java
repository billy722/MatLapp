package com.example.matlabapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity {
    Button boton_perfil;
    Button boton_instrucciones;
    Button boton_preguntarjeta;
    Button boton_ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //CODIGO PARA REDIRECCIONAR CON EL BORON_INSTRUCCIONES
        boton_instrucciones = findViewById(R.id.btn_instrucciones);
        boton_instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_instrucciones = new Intent(Inicio.this, Instrucciones.class);
                Inicio.this.startActivity(intent_instrucciones);

            }
        });
        //FIN CODIGO BOTON_INSTRUCCIONES


        //CODIGO PARA REDIRECCIONAR CON EL BORON_PERFIL
        boton_perfil = findViewById(R.id.btn_perfil);
        boton_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_perfil = new Intent(Inicio.this, Perfil.class);
                Inicio.this.startActivity(intent_perfil);
            }
        });
        //FIN CODIGO REDIRECCION BOTON_PERFIL



        //CODIGO PARA REDIRECCIONAR CON EL BOTON INSTRUCCIONES
        boton_preguntarjeta = findViewById(R.id.btn_preguntarjetas);
        boton_preguntarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_preguntarjeta = new Intent(Inicio.this, Preguntarjeta.class);
                Inicio.this.startActivity(intent_preguntarjeta);
            }
        });
        //FIN CODIGO REDIRECCION BOTON_INSTRUCCIONES

        //CODIGO PARA REDIRECCIONAR CON EL BOTON RANKING
        boton_ranking = findViewById(R.id.btn_ranking);
        boton_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_ranking = new Intent(Inicio.this, Ranking.class);
                Inicio.this.startActivity(intent_ranking);
            }
        });
        //FIN CODIGO REDIRECCION BOTON_RANKING


    }
}
