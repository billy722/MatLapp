package com.example.matlabapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Perfil extends AppCompatActivity implements View.OnClickListener {
    Spinner selector_cursos;
    EditText txt_rut, txt_nombre;
    Button btn_aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        selector_cursos = findViewById(R.id.select_curso);
        txt_rut = findViewById(R.id.txt_rut_jugador);
        txt_nombre = findViewById(R.id.txt_nombre_jugador);
        btn_aceptar = findViewById(R.id.btn_aceptar_login);




        String [] cursos = {"Primero","Segundo","Tercero","Cuarto","Quinto","Sexto","Septimo","Octavo"};

        ArrayAdapter <String> lista_cursos = new ArrayAdapter<String>( this, R.layout.estilo_spinner,cursos);
        selector_cursos.setAdapter(lista_cursos);

        btn_aceptar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

//        AlertDialog.Builder alert_mensaje = new AlertDialog.Builder(Perfil.this);
//        alert_mensaje.setMessage("SI ESTA REGISTRADO").create().show();

        final String rut = txt_rut.getText().toString();
        final String nombre = txt_rut.getText().toString();
        final int curso = Integer.parseInt(txt_rut.getText().toString());

        Response.Listener<String> loginListenter = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    String respuesta = respuestaJson.getString("existe");

                    if(respuesta.equals("si")){

                        txt_nombre.setText(respuestaJson.getString("nombre"));

                        AlertDialog.Builder alert_mensaje = new AlertDialog.Builder(Perfil.this);
                        alert_mensaje.setMessage("SI ESTA REGISTRADO").create().show();

                    }else if(respuesta.equals("no")){

                        AlertDialog.Builder alert_mensaje = new AlertDialog.Builder(Perfil.this);
                        alert_mensaje.setMessage("NO ESTA REGISTRADO").create().show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ConexionLogin login = new ConexionLogin(rut, nombre, curso, loginListenter);
        RequestQueue queue = Volley.newRequestQueue(Perfil.this);
        queue.add(login);
    }
}
