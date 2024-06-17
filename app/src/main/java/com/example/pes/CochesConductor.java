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

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CochesConductor extends AppCompatActivity {

    private Button obtenerCochesConductorBtn;
    private TextView obtenerCochesConductorTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coches_conductor);
        obtenerCochesConductorTV=findViewById(R.id.textViewObtenerCochesConductor);
        obtenerCochesConductorBtn=findViewById(R.id.obtenerCochesConductorBtn);

        obtenerCochesConductorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String conductor="w";
                obtenerCochesConductor(conductor);
            }
        });
    }

    public void  obtenerCochesConductor(String nameDriver){
        new Thread(new Runnable() {
            InputStream stream = null;
            Handler handler = new Handler();
            public void run() {

                try {
                    String BASE_URL = "http://10.0.2.2:9000/Application/cochesConductor";
                    String urlStr = BASE_URL + "?nameDriver=" + URLEncoder.encode(nameDriver, "UTF-8");

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
            JSONObject jsonObject=new JSONObject(jsonResponse);
            if (jsonObject.has("error")) {
                String error = jsonObject.getString("error");
                Log.e("serverTest", error);
                Toast.makeText(CochesConductor.this, error, Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(CochesConductor.this, Menu.class);
        startActivity(intent);
        finish();
    }
}