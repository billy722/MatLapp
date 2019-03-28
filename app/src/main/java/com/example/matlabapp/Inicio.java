package com.example.matlabapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity {
    Button boton_perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        boton_perfil = findViewById(R.id.btn_perfil);

        boton_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_perfil = new Intent(Inicio.this, Perfil.class);
                Inicio.this.startActivity(intent_perfil);
            }
        });
    }
}
