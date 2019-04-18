package com.example.matlabapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.matlabapp.Clases.Juegos;
import com.example.matlabapp.Clases.Jugador;

import org.json.JSONException;
import org.json.JSONObject;

public class Perfil extends AppCompatActivity implements View.OnFocusChangeListener {
    //DEFINO VARIABLES
    Spinner selector_cursos;
    EditText txt_rut, txt_nombre;
    Button btn_aceptar;


    Button boton_instrucciones;
    Button boton_preguntarjeta;
    Button boton_ranking;
    Button btn_volver;

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

        cargarFuncionesMenu();
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
            int curso_jugador = prefs.getInt("curso_jugador_logeado", 0);
            txt_rut.setText(rut_jugador_logeado);
            txt_nombre.setText(nombre_jugador);
            selector_cursos.setSelection(curso_jugador-1);
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


//                    AlertDialog.Builder alert_mensaje_prueba = new AlertDialog.Builder(Perfil.this);
//                    alert_mensaje_prueba.setMessage("RESULTADO: "+respuesta+" campos: "+respuesta_campos).create().show();

                    if(respuesta.equals("si")){

                        logear_jugador(rut,nombre,curso);

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

        Jugador registro = new Jugador(rut, nombre, curso,"http://www.matlapp.cl/matlapp_app/perfil/crear_jugador.php" ,loginListenter);
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


    public void logear_jugador(String rut_jugador, String nombre_jugador, int curso_jugador){
        //CREA VARIABLES DE SESION

        SharedPreferences preferencias = getSharedPreferences("datos_session_login",   Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("rut_jugador_logeado" , rut_jugador);
        editor.putString("nombre_jugador_logeado" , nombre_jugador);
        editor.putInt("curso_jugador_logeado" , curso_jugador);
        editor.commit();

        activar_desactivar_campos_texto(false);

//        Intent intent_inicio = new Intent(Perfil.this, Inicio.class);
//        Perfil.this.startActivity(intent_inicio);
    }

    public void consulta_jugador_existe(){
        //ASIGNO VALOR A LAS VARIABLES
        final String rut = txt_rut.getText().toString();
        final String nombre = txt_rut.getText().toString();
        final int curso = Integer.parseInt(txt_rut.getText().toString());

        txt_nombre.setText("Cargando...");

        Response.Listener<String> loginListenter = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    String respuesta = respuestaJson.getString("existe");

                    if(respuesta.equals("si")){

                        String rut_recibido = respuestaJson.getString("rut");
                        String nombre_recibido = respuestaJson.getString("nombre");
                        int curso_recibido = respuestaJson.getInt("curso");

                        txt_nombre.setText(nombre_recibido);
                        selector_cursos.setSelection(curso_recibido-1);

                        logear_jugador(rut_recibido,nombre_recibido,curso_recibido);

                        //mensaje en pantalla
                        AlertDialog.Builder alert_mensaje = new AlertDialog.Builder(Perfil.this);
                        alert_mensaje.setMessage("Bienvenido "+nombre_recibido).create().show();


                    }else if(respuesta.equals("no")){

//                        AlertDialog.Builder alert_mensaje = new AlertDialog.Builder(Perfil.this);
//                        alert_mensaje.setMessage("NO ESTA REGISTRADO").create().show();
                        txt_nombre.setText("");
                        activar_desactivar_campos_texto(true);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Jugador login = new Jugador(rut, nombre, curso,"http://www.matlapp.cl/matlapp_app/perfil/login.php" ,loginListenter);
        RequestQueue queue = Volley.newRequestQueue(Perfil.this);
        queue.add(login);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus==false){

            consulta_jugador_existe();

        }

    }


    public void cargarFuncionesMenu(){
        //CODIGO PARA REDIRECCIONAR CON EL BORON_INSTRUCCIONES
        boton_instrucciones = findViewById(R.id.btn_instrucciones);
        boton_instrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_instrucciones = new Intent(Perfil.this, Instrucciones.class);
                Perfil.this.startActivity(intent_instrucciones);
                finish();

            }
        });




        //CODIGO PARA REDIRECCIONAR CON EL BOTON PREGINTARJETAS
        boton_preguntarjeta = findViewById(R.id.btn_preguntarjetas);
        boton_preguntarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(consultar_jugador_logeado_menu()) {
                    consultar_juego_activo_jugador();
                }

            }
        });


        //CODIGO PARA REDIRECCIONAR CON EL BOTON RANKING
        boton_ranking = findViewById(R.id.btn_ranking);
        boton_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_ranking = new Intent(Perfil.this, Ranking.class);
                Perfil.this.startActivity(intent_ranking);
                finish();

            }
        });

        //CODIGO PARA REDIRECCIONAR CON EL BOTON ATRAS
        btn_volver = findViewById(R.id.btn_volver);
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Perfil.super.onBackPressed();
            }
        });
    }



    public Boolean consultar_jugador_logeado_menu(){
        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        if(rut_jugador_logeado.equals("")){
            //NO ESTA LOGEADO
            AlertDialog.Builder mensaje_login = new AlertDialog.Builder(Perfil.this);
            mensaje_login.setPositiveButton("Ir a Perfil", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent_perfil = new Intent(Perfil.this,Perfil.class);
                    startActivity(intent_perfil);
                    finish();

                }
            });
            mensaje_login.setMessage("INGRESA TUS DATOS PARA JUGAR").create().show();
            return false;
        }else{
            //ESTA LOGEADO
            return true;
        }
    }



    public void juegoEnSession(int id_juego, String jugador_creador){
        SharedPreferences preferencias = getSharedPreferences("datos_session_login",   Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("id_juego_activo" ,id_juego );
        editor.putString("jugador_creador" ,jugador_creador );
        editor.commit();
    }

    public void consultar_juego_activo_jugador(){

        Response.Listener<String> consulta_juego_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    String respuesta = respuestaJson.getString("juego_activo");

                    if(respuesta.equals("si")){
                        int id_juego = Integer.parseInt(respuestaJson.getString("id_juego"));
                        String jugador_creador = respuestaJson.getString("jugador_creador");


                        juegoEnSession(id_juego,jugador_creador);

                        Intent intent_preguntarjeta = new Intent(Perfil.this, Preguntarjeta.class);
                        Perfil.this.startActivity(intent_preguntarjeta);
                        finish();

                    }else if(respuesta.equals("no")){
                        //PERMITE CREAR JUEGO O UNIRSE A UNO
                        Intent intent_juego = new Intent(Perfil.this, Juego.class);
                        startActivity(intent_juego);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        Juegos consulta_juego_activo = new Juegos(rut_jugador_logeado,"http://www.matlapp.cl/matlapp_app/juego/consultar_juego_jugador.php" ,consulta_juego_listener );
        RequestQueue queue = Volley.newRequestQueue(Perfil.this);
        queue.add(consulta_juego_activo);
    }
}
