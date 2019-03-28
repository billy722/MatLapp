package com.example.matlabapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Perfil extends AppCompatActivity {
    Spinner selector_cursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        selector_cursos = findViewById(R.id.select_curso);

        String [] cursos = {"Primero","Segundo","Tercero","Cuarto","Quinto","Sexto","Septimo","Octavo"};

        ArrayAdapter <String> lista_cursos = new ArrayAdapter<String>( this, R.layout.estilo_spinner,cursos);
        selector_cursos.setAdapter(lista_cursos);
    }
}
