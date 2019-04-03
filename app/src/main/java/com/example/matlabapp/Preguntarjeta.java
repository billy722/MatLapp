package com.example.matlabapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Preguntarjeta extends AppCompatActivity {
    Button boton_preguntarjeta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntarjeta);

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
