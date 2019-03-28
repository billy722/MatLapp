package com.example.matlabapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_jugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
