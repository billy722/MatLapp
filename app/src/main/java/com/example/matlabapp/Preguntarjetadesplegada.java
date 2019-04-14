package com.example.matlabapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
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
    }

    public void setearOnClickBotones(){
        boton_alternativa_1 = findViewById(R.id.boton_alternativa_1);
        boton_alternativa_2 = findViewById(R.id.boton_alternativa_2);
        boton_alternativa_3 = findViewById(R.id.boton_alternativa_3);
        boton_alternativa_4 = findViewById(R.id.boton_alternativa_4);

        boton_alternativa_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responderPreguntarjeta(1);
            }
        });
        boton_alternativa_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responderPreguntarjeta(2);
            }
        });
        boton_alternativa_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responderPreguntarjeta(3);
            }
        });
        boton_alternativa_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responderPreguntarjeta(4);
            }
        });



    }

    public void responderPreguntarjeta(int alternativa){

        if(alternativa_correcta==alternativa){

        }else{

        }

        AlertDialog.Builder mensaje = new AlertDialog.Builder(Preguntarjetadesplegada.this);
        mensaje.setMessage("SELECCIONA OPCION: "+alternativa).create().show();

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

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(Preguntarjetadesplegada.this);
                    mensaje.setMessage("hola id pregunta: "+id_imagen_recibida).create().show();




                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder mensaje1 = new AlertDialog.Builder(Preguntarjetadesplegada.this);
                    mensaje1.setMessage("ERROR: "+e.getMessage()).create().show();
                }
            }
        };

        SharedPreferences prefs = getSharedPreferences("datos_session_login", Context.MODE_PRIVATE);
        int curso_jugador_logeado = prefs.getInt("curso_jugador_logeado", 0);

        Preguntarjetas consulta_preguntarjeta = new Preguntarjetas(curso_jugador_logeado,"http://146.66.99.89/~daemmulc/matlapp/preguntarjeta/solicitar_preguntarjeta.php" ,consulta_preguntarjeta_listener);
        RequestQueue queue = Volley.newRequestQueue(Preguntarjetadesplegada.this);
        queue.add(consulta_preguntarjeta);
    }


}
