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

public class Perfil extends AppCompatActivity implements View.OnFocusChangeListener {
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

        txt_rut.setOnFocusChangeListener(this);

        consultar_jugador_logeado();
    }

    public void consultar_jugador_logeado(){
        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        if(rut_jugador_logeado.equals("")){
            //NO ESTA LOGEADO
            activar_desactivar_campos_texto(true);
            txt_rut.setText("");
            txt_nombre.setText("");
            selector_cursos.setSelection(0);
        }else{
            //ESTA LOGEADO
            activar_desactivar_campos_texto(false);
            String nombre_jugador = prefs.getString("nombre_jugador_logeado", "");
            txt_rut.setText(rut_jugador_logeado);
            txt_nombre.setText(nombre_jugador);
        }
    }

    public void registrar_jugador(){

        //ASIGNO VALOR A LAS VARIABLES
        final String rut = txt_rut.getText().toString();
        final String nombre = txt_nombre.getText().toString();
        final int curso = (selector_cursos.getSelectedItemPosition()+1);

        Response.Listener<String> loginListenter = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    String respuesta = respuestaJson.getString("creado");
//                    String respuesta_campos = respuestaJson.getString("campos");


//                    AlertDialog.Builder alert_mensaje_prueba = new AlertDialog.Builder(Perfil.this);
//                    alert_mensaje_prueba.setMessage("RESULTADO: "+respuesta+" campos: "+respuesta_campos).create().show();

                    if(respuesta.equals("si")){

                        logear_jugador(rut,nombre);

                        AlertDialog.Builder alert_mensaje = new AlertDialog.Builder(Perfil.this);
                        alert_mensaje.setMessage("Jugador creado, ya puedes jugar¡").create().show();

                    }else if(respuesta.equals("no")){
                        AlertDialog.Builder alert_mensaje = new AlertDialog.Builder(Perfil.this);
                        alert_mensaje.setMessage("OCURRIO UN ERROR, INTENTE NUEVAMENTE").create().show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Jugador registro = new Jugador(rut, nombre, curso,"http://146.66.99.89/~daemmulc/matlapp/perfil/crear_jugador.php" ,loginListenter);
        RequestQueue queue = Volley.newRequestQueue(Perfil.this);
        queue.add(registro);


    }

    public void activar_desactivar_campos_texto(Boolean estado){

        if(estado){
           txt_rut.setEnabled(true);
           txt_nombre.setEnabled(true);
           selector_cursos.setEnabled(true);
           btn_aceptar.setText("ACEPTAR");

            btn_aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    registrar_jugador();
                }
            });

        }else{
           txt_rut.setEnabled(false);
           txt_nombre.setEnabled(false);
           selector_cursos.setEnabled(false);
           btn_aceptar.setText("CAMBIAR JUGADOR");

           btn_aceptar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   SharedPreferences preferencias = getSharedPreferences("datos_session_login",   Context.MODE_PRIVATE);
                   SharedPreferences.Editor editor = preferencias.edit();
                   editor.putString("rut_jugador_logeado" , "");
                   editor.putString("nombre_jugador_logeado" , "");
                   editor.commit();

                   consultar_jugador_logeado();
               }
           });

        }

    }


    public void logear_jugador(String rut_jugador, String nombre_jugador){
        //CREA VARIABLES DE SESION

        SharedPreferences preferencias = getSharedPreferences("datos_session_login",   Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("rut_jugador_logeado" , rut_jugador);
        editor.putString("nombre_jugador_logeado" , nombre_jugador);
        editor.commit();

        activar_desactivar_campos_texto(false);
    }

    public void consulta_jugador_existe(){
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

                        logear_jugador(rut_recibido,nombre_recibido);

                        //mensaje en pantalla
                        AlertDialog.Builder alert_mensaje = new AlertDialog.Builder(Perfil.this);
                        //alert_mensaje.setMessage("Bienvenido "+nombre_recibido).create().show();


                    }else if(respuesta.equals("no")){

                        AlertDialog.Builder alert_mensaje = new AlertDialog.Builder(Perfil.this);
                        alert_mensaje.setMessage("NO ESTA REGISTRADO").create().show();

                        activar_desactivar_campos_texto(true);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Jugador login = new Jugador(rut, nombre, curso,"http://146.66.99.89/~daemmulc/matlapp/perfil/login.php" ,loginListenter);
        RequestQueue queue = Volley.newRequestQueue(Perfil.this);
        queue.add(login);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus==false){

            consulta_jugador_existe();

        }

    }
}
