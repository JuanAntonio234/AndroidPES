package com.example.pes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Register extends AppCompatActivity {

    private static final String TAG = "Login";
    String BASE_URL = String.format("http://10.0.2.2:9000/Application/registerAndroid");
    private EditText registerName;
    private EditText registerPassword;
    private EditText registerConfirmPassword;
    private EditText registerEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerName=findViewById(R.id.RegisterNameInput);
        registerPassword=findViewById(R.id.RegisterPasswordInput);
        registerConfirmPassword=findViewById(R.id.RegisterConfirmPasswordInput);
        registerEdad=findViewById(R.id.RegisterEdadInput);
        Button registerBtn = findViewById(R.id.RegisterBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = registerName.getText().toString();
                String edadStr=registerEdad.getText().toString();
                String password = registerPassword.getText().toString();
                String confirmPassword=registerConfirmPassword.getText().toString();

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(Register.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int edad = Integer.parseInt(edadStr);
                    if (edad < 18) {
                        Toast.makeText(Register.this, "Debes ser mayor de 18 años para registrarte", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    register(name, edad, password);
                } catch (NumberFormatException e) {
                    Toast.makeText(Register.this, "Por favor, introduce una edad válida", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void register(String name,int edad,String password){
        new Thread(new Runnable() {
            InputStream stream = null;
            Handler handler = new Handler();

            @Override
            public void run() {
                try {
                    URL url = new URL(BASE_URL );
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setReadTimeout(10000 );
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.connect();

                    String urlParameters = "nameUser=" +name +"&edad"+edad+ "&password="+password;
                    Log.i("parametros enviados register ", urlParameters);

                    OutputStream os=urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(urlParameters.toString());
                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode = urlConnection.getResponseCode();
                    Log.i("serverTest", "Response Code: " + responseCode);
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        stream = urlConnection.getInputStream();
                        BufferedReader reader = null;
                        StringBuilder response = new StringBuilder();
                        reader = new BufferedReader(new InputStreamReader(stream));
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        String result = response.toString();
                        urlConnection.disconnect();

                        handler.post(new Runnable() {
                            public void run() {
                                Log.i("serverTest", result);
                                if (result.contains("success")) {
                                    Toast.makeText(Register.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, Menu.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "Error en el registro: " + result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(Register.this, "No autorizado. Verifica tus credenciales.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(Register.this, "Recurso no encontrado en el servidor.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(Register.this, "Error en el servidor. Código: " + responseCode, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(Register.this, "Error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void returnToLoginOnClick(View view){
        Intent intent=new Intent(Register.this, Login.class);
        startActivity(intent);
        finish();
    }
}
