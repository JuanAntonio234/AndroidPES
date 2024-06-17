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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.net.URLEncoder;


public class ObtenerDatosConductor extends AppCompatActivity {

    private EditText q ;
    private Button obtenerDatosConductorBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener_datos_conductor);
        obtenerDatosConductorBtn=findViewById(R.id.obtenerDatosConductorBtn);

        obtenerDatosConductorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreConductor="w";
                obtenerDatosConductor(nombreConductor);
            }
        });
    }

    public void  obtenerDatosConductor(String nameDriver){
        new Thread(new Runnable() {
            InputStream stream = null;
            Handler handler = new Handler();

            public void run() {

                try {
                    String BASE_URL=String.format("http://10.0.2.2:9000/Application/datosConductor");
                    String urlStr = BASE_URL + "?nameDriver=" + URLEncoder.encode(nameDriver, "UTF-8");
                    URL url=new URL(urlStr);

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
                String resultado = "Nombre: " + name + "\nEdad: " + edad + "\nNúmero de coches: " + numCoches;

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