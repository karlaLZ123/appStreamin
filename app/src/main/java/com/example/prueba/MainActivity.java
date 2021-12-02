package com.example.prueba;

import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.content.DialogInterface;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {
    Uri uriImage1;
    Uri uriImage2;
    Uri uriImage3;
    Uri uriImage4;

    String url;
    String url2;
    String url3;
    String url4;
    String url5;
    String sendUrl;

    int pos1;
    int pos2;
    int pos3;
    int pos4;


    private String jugador;
    //Varibles para conexion al sevicio

    private RequestQueue conexionSer;
    private StringRequest peticionSer;

    private ArrayList<ImageButton> ibCartas;
    private GridLayout glCartas;
    ImageButton img2;
    ImageButton img;
    ImageButton img3;
    ImageButton img4;
    private String cap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glCartas = findViewById(R.id.gl_cartas);

        conexionSer = Volley.newRequestQueue(this);
        img = (ImageButton) this.findViewById(R.id.video_1);
        img2 = (ImageButton) this.findViewById(R.id.video_2);
        img3 = (ImageButton) this.findViewById(R.id.video_3);
        img4 = (ImageButton) this.findViewById(R.id.video_4);

    /*

El constructor Request siempre toma como parámetros el tipo de método
(GET, POST, etc.), la URL del recurso y los detectores de eventos.
Luego, dependiendo del tipo de solicitud, puede solicitar algunas variables más.
     */
        peticionSer = new StringRequest(
                    /*
                   1.- TIPO DE PETICION(GET/POST)
                   2.- URL DONDE ESTA EL SERVICIO
                   3.-
                     */

                Request.Method.GET, "http://192.168.100.9/AppStreming/index.php/servicios/get_temporadas",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            /*
                            Intentar convertir response en Arreglo Json
                            */

                        try {
                            JSONArray objResp = new JSONArray(response);

                            for (int i = 0; i < objResp.length(); i++) {

                                JSONObject score = objResp.getJSONObject(i);


                                if (score.getInt("id") == 1) {
                                    img.setImageResource(R.drawable.img1);
                                }
                                if (score.getInt("id") == 2) {
                                    img2.setImageResource(R.drawable.img2);

                                }
                                if (score.getInt("id") == 3) {
                                    img3.setImageResource(R.drawable.img4);

                                }
                                if (score.getInt("id") == 4) {
                                    img4.setImageResource(R.drawable.img5);

                                }

                            }


                        } catch (JSONException ex) {
                            Toast.makeText(MainActivity.this
                                    , "ERROR ::c ---->" + ex.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this
                                , "ERROR :c ---->" + error.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }
                }

        );

        //Ejecutar servicio

        conexionSer.add(peticionSer);
    }

    public void verVideo(View v){

          startActivity(
                 new Intent(
                       MainActivity.this,
                       video.class
                 )
                 .putExtra("id", v.getTag().toString())

              );

    }
    

}