package com.example.matlabapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.matlabapp.Clases.Juegos;
import com.example.matlabapp.Clases.Preguntarjetas;

import org.json.JSONException;
import org.json.JSONObject;

public class Preguntarjetadesplegada extends AppCompatActivity {

    ImageView imagen_preguntarjeta;
    TextView txt_alternativa_1;
    TextView txt_alternativa_2;
    TextView txt_alternativa_3;
    TextView txt_alternativa_4;

    int id_preguntarjeta;
    int alternativa_correcta;
    int eje_tematico;

    //botones (lineaLayout)
    LinearLayout boton_alternativa_1;
    LinearLayout boton_alternativa_2;
    LinearLayout boton_alternativa_3;
    LinearLayout boton_alternativa_4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntarjetadesplegada);

        imagen_preguntarjeta = findViewById(R.id.imagen_preguntarjeta);
        txt_alternativa_1 = findViewById(R.id.txt_alternativa_1);
        txt_alternativa_2 = findViewById(R.id.txt_alternativa_2);
        txt_alternativa_3 = findViewById(R.id.txt_alternativa_3);
        txt_alternativa_4 = findViewById(R.id.txt_alternativa_4);

        solicitar_preguntarjeta_azar();

        //asigna botones
        setearOnClickBotones();

//        cargarFuncionesMenu();
    }


    @Override
    public void onBackPressed (){

    }


    public void setearOnClickBotones(){
        boton_alternativa_1 = findViewById(R.id.boton_alternativa_1);
        boton_alternativa_2 = findViewById(R.id.boton_alternativa_2);
        boton_alternativa_3 = findViewById(R.id.boton_alternativa_3);
        boton_alternativa_4 = findViewById(R.id.boton_alternativa_4);

        boton_alternativa_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton_alternativa_1.setClickable(false);
                txt_alternativa_1.setText("Cargando...");
                responderPreguntarjeta(1);
            }
        });
        boton_alternativa_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton_alternativa_2.setClickable(false);
                txt_alternativa_2.setText("Cargando...");
                responderPreguntarjeta(2);
            }
        });
        boton_alternativa_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton_alternativa_3.setClickable(false);
                txt_alternativa_3.setText("Cargando...");
                responderPreguntarjeta(3);
            }
        });
        boton_alternativa_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton_alternativa_4.setClickable(false);
                txt_alternativa_4.setText("Cargando...");
                responderPreguntarjeta(4);
            }
        });



    }

    public void responderPreguntarjeta(int alternativa){

        int respuesta;

        if(alternativa_correcta==alternativa){
            respuesta = 1;
        }else{
            respuesta = 2;
        }

        Intent intent_respuesta = new Intent(Preguntarjetadesplegada.this, Respuestas.class);
        intent_respuesta.putExtra("respuesta", respuesta);
        intent_respuesta.putExtra("id_preguntarjeta", id_preguntarjeta);
        this.startActivity(intent_respuesta);
        finish();


    }

    public void solicitar_preguntarjeta_azar(){


        Response.Listener<String> consulta_preguntarjeta_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject respuestaJson = new JSONObject(response);
                    int id_imagen_recibida = respuestaJson.getInt("id_preguntarjeta");
                    int eje_tematico_recibido = respuestaJson.getInt("eje_tematico");
                    String imagen = respuestaJson.getString("imagen");
                    String alternativa_1 = respuestaJson.getString("alternativa1");
                    String alternativa_2 = respuestaJson.getString("alternativa2");
                    String alternativa_3 = respuestaJson.getString("alternativa3");
                    String alternativa_4 = respuestaJson.getString("alternativa4");
                    int alternativa_correcta_recibida = respuestaJson.getInt("alternativa_correcta");

                    txt_alternativa_1.setText(alternativa_1);
                    txt_alternativa_2.setText(alternativa_2);
                    txt_alternativa_3.setText(alternativa_3);
                    txt_alternativa_4.setText(alternativa_4);

                    id_preguntarjeta = id_imagen_recibida;
                    alternativa_correcta = alternativa_correcta_recibida;
                    eje_tematico = eje_tematico_recibido;

                    //MUESTRA IMAGEN
                    Bitmap bitmap_imagen_recibida;
                    byte[] byte_imagen = Base64.decode(imagen,Base64.DEFAULT);
                    bitmap_imagen_recibida = BitmapFactory.decodeByteArray(byte_imagen,0,byte_imagen.length);

                    imagen_preguntarjeta.setImageBitmap(bitmap_imagen_recibida);

//                    AlertDialog.Builder mensaje = new AlertDialog.Builder(Preguntarjetadesplegada.this);
//                    mensaje.setMessage("hola id pregunta: "+id_imagen_recibida).create().show();


                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder mensaje1 = new AlertDialog.Builder(Preguntarjetadesplegada.this);
                    mensaje1.setMessage("ERROR: "+e.getMessage()).create().show();
                }
            }
        };

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        int curso_jugador_logeado = prefs.getInt("curso_jugador_logeado", 0);

        Preguntarjetas consulta_preguntarjeta = new Preguntarjetas(curso_jugador_logeado,"http://www.matlapp.cl/matlapp_app/preguntarjeta/solicitar_preguntarjeta.php" ,consulta_preguntarjeta_listener);
        RequestQueue queue = Volley.newRequestQueue(Preguntarjetadesplegada.this);
        queue.add(consulta_preguntarjeta);
    }







    public Boolean consultar_jugador_logeado_menu(){
        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        String rut_jugador_logeado = prefs.getString("rut_jugador_logeado", "");

        if(rut_jugador_logeado.equals("")){
            //NO ESTA LOGEADO
            AlertDialog.Builder mensaje_login = new AlertDialog.Builder(Preguntarjetadesplegada.this);
            mensaje_login.setPositiveButton("Ir a Perfil", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent_perfil = new Intent(Preguntarjetadesplegada.this,Perfil.class);
                    startActivity(intent_perfil);
                }
            });
            mensaje_login.setMessage("INGRESA TUS DATOS PARA JUGAR").create().show();
            return false;
        }else{
            //ESTA LOGEADO
            return true;
        }
    }



    public void juegoEnSession(int id_juego){
        SharedPreferences preferencias = getSharedPreferences("datos_session_login",   Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("id_juego_activo" ,id_juego );
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

                        juegoEnSession(id_juego);

                        Intent intent_preguntarjeta = new Intent(Preguntarjetadesplegada.this, Preguntarjeta.class);
                        Preguntarjetadesplegada.this.startActivity(intent_preguntarjeta);

                    }else if(respuesta.equals("no")){
                        //PERMITE CREAR JUEGO O UNIRSE A UNO
                        Intent intent_juego = new Intent(Preguntarjetadesplegada.this, Juego.class);
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
        RequestQueue queue = Volley.newRequestQueue(Preguntarjetadesplegada.this);
        queue.add(consulta_juego_activo);
    }


}
