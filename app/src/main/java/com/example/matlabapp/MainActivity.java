package com.example.matlabapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public MediaPlayer musica_fondo;

    Button btn_jugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musica_fondo = MediaPlayer.create(MainActivity.this,R.raw.musica_fondo);
        musica_fondo.setLooping(false);
        musica_fondo.start();

       btn_jugar = findViewById(R.id.btn_jugar);
       btn_jugar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intentInicio = new Intent(MainActivity.this, Inicio.class);
               MainActivity.this.startActivity(intentInicio);
           }
       });
    }
}
