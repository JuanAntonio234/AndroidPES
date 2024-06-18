package com.example.pes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DatosCoche extends AppCompatActivity {

    private Button obtenerMarcaCoche;
    private EditText marcaCocheET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_coche);
        marcaCocheET=findViewById(R.id.marcaCoche);
        obtenerMarcaCoche = findViewById(R.id.obtenerDatosCocheBtn);

        obtenerMarcaCoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String marca = marcaCocheET.getText().toString();
                if((marca.isEmpty())||(marca.equals("Marca"))){
                    Toast.makeText(DatosCoche.this, "Introduce una marca", Toast.LENGTH_SHORT).show();
                }else if((!marca.equals("Peugeot"))&&!marca.equals("Ford")&&!marca.equals("Citroen")&&!marca.equals("Ferrari")){
                    Toast.makeText(DatosCoche.this, "No hay coches disponibles de esa marca", Toast.LENGTH_SHORT).show();
                }else{
                    obtenerDatosCoche(marca);
                }
            }
        });
    }

    public void  obtenerDatosCoche(String marca){
        new Thread(new Runnable() {
            InputStream stream = null;
            Handler handler = new Handler();
            public void run() {

                try {
                    String BASE_URL = "http://10.0.2.2:9000/Application/datosCoche";
                    String urlStr = BASE_URL + "?carName=" + URLEncoder.encode(marca, "UTF-8");
                    URL url = new URL(urlStr);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000 );
                    urlConnection.setConnectTimeout(15000  );
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
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.has("error")) {
                String error = jsonObject.getString("error");
                Log.e("serverTest", error);
                Toast.makeText(DatosCoche.this, error, Toast.LENGTH_SHORT).show();

            } else {
                String marca = jsonObject.getString("marca");
                String matricula = jsonObject.getString("matricula");
                String tipo = jsonObject.getString("tipo");

                TextView textView = findViewById(R.id.textViewObtenerDatosCoche);
                String resultado="Introduzca la marca del coche de la que desea información. Disponemos de las siguientes marca:\n-Peugeot" +
                        "        \n-Citroen \n-Ferrari \n-Ford\n \nMarca: " + marca + "\nMatrícula: " + matricula + "\nTipo: " + tipo;
                textView.setText(resultado);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("serverTest", "Error procesando el JSON");
        }
    }
    public void returnToMenuOnClick(View view){
        Intent intent=new Intent(DatosCoche.this, Menu.class);
        startActivity(intent);
        finish();
    }
}