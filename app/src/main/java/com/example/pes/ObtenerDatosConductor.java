package com.example.pes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.io.BufferedReader;


public class ObtenerDatosConductor extends AppCompatActivity {

    String BASE_URL=String.format("http://10.0.2.2:9000/Application/datosConductor");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener_datos_conductor);
    }

    public void  obtenerDatosCoche(View view){
        new Thread(new Runnable() {
            InputStream stream = null;
            Handler handler = new Handler();

            public void run() {

                try {
                    URL url=new URL(BASE_URL);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000 );
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setDoInput(true);
                    urlConnection.connect();

                    stream = urlConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));;

                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    String result = sb.toString();
                    urlConnection.disconnect();

                    //Codi correcte
                    Log.i("serverTest", result);
                    handler.post(new Runnable() {
                        public void run() {
                            procesarRespuesta(result);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void procesarRespuesta(String jsonResponse){
        try {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            if (jsonObject.has("error")) {
                String error = jsonObject.getString("error");
                Log.e("serverTest", error);
                Toast.makeText(ObtenerDatosConductor.this, error, Toast.LENGTH_SHORT).show();
            }else {
                String name = jsonObject.getString("name");
                int edad = jsonObject.getInt("edad");
                int numCoches = jsonObject.getInt("numcoches");

                TextView obtenerMisDatosTV = (TextView) findViewById(R.id.textViewObtenerMisDatos);
                String resultado = "Nombre: " + name + "\nEdad: " + edad + "\nNúmero de ccoches: " + numCoches;

                obtenerMisDatosTV.setText(resultado);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void returnToMenuOnClick(View view){
        Intent intent=new Intent(ObtenerDatosConductor.this, Menu.class);
        startActivity(intent);
        finish();
    }
}