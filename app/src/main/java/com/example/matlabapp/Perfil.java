package com.example.matlabapp;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.matlabapp.Clases.Jugador;

import org.json.JSONException;
import org.json.JSONObject;

public class Perfil extends AppCompatActivity implements View.OnClickListener {
    //DEFINO VARIABLES
    Spinner selector_cursos;
    EditText txt_rut, txt_nombre;
    Button btn_aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        //ASIGNO un elemnto a las variables
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

        //ASIGNO VALOR A LAS VARIABLES
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

                        String rut_recibido = respuestaJson.getString("rut");
                        String nombre_recibido = respuestaJson.getString("nombre");

                        txt_nombre.setText(nombre_recibido);

                        //mensaje en pantalla
                        AlertDialog.Builder alert_mensaje = new AlertDialog.Builder(Perfil.this);
                        alert_mensaje.setMessage("Bienvenido "+nombre_recibido).create().show();

                        //CREA VARIABLES DE SESION

                        SharedPreferences preferencias = getSharedPreferences("datos_session_login",   Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferencias.edit();
                        editor.putString("rut_jugador_logeado" , rut_recibido);
                        editor.putString("nombre_jugador_logeado" , nombre_recibido);
                        editor.commit();

                        //SharedPreferences prefs = getSharedPreferences("datos_session_login",   Context.MODE_PRIVATE);
                        //String email = prefs.getString("email", ""); // prefs.getString("nombre del campo" , "valor por defecto")

                    }else if(respuesta.equals("no")){

                        AlertDialog.Builder alert_mensaje = new AlertDialog.Builder(Perfil.this);
                        alert_mensaje.setMessage("NO ESTA REGISTRADO").create().show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Jugador login = new Jugador(rut, nombre, curso, loginListenter);
        RequestQueue queue = Volley.newRequestQueue(Perfil.this);
        queue.add(login);
    }
}
