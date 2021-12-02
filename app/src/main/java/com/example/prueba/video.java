package com.example.prueba;

import androidx.fragment.app.FragmentActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;


public class video extends FragmentActivity {
    VideoView videoView;
    private RequestQueue conexionSer;
    private StringRequest peticionSer;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);

        conexionSer = Volley.newRequestQueue(this);
        MediaController mediaController = new MediaController(this);

        id = getIntent().getStringExtra("id");

        peticionSer = new StringRequest(
                Request.Method.GET, "http://192.168.100.9/AppStreming/index.php/servicios/get_temporadas",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray objResp = new JSONArray(response);
                            for (int i = 0; i < objResp.length(); i++) {
                                JSONObject datos = objResp.getJSONObject(i);
                                if (datos.getInt("id") == Integer.parseInt(id)) {
                                    String videoUri = datos.getString("urlVideo");
                                    videoView = (VideoView) findViewById(R.id.video);
                                    videoView.requestFocus();
                                    if (videoView == null) {
                                        Log.e(TAG, "Video view null");
                                    } else {
                                        videoView.setVideoURI(Uri.parse(videoUri));
                                        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                                            public void onPrepared(MediaPlayer mp) {
                                                videoView.start();
                                            }
                                        });
                                    }

                                }

                            }


                        } catch (JSONException ex) {
                            Toast.makeText(video.this
                                    , "ERROR ::c ---->" + ex.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(video.this
                                , "ERROR :c ---->" + error.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                    }
                }

        );

        conexionSer.add(peticionSer);

    }
}