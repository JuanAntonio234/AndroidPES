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

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    String BASE_URL = String.format("http://10.0.2.2:9000/Application/loginAndroid");
    private EditText loginName;
    private EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginName=findViewById(R.id.LoginNameInput);
        loginPassword=findViewById(R.id.LoginPasswordInput);
        Button loginButton = findViewById(R.id.LoginBTN);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = loginName.getText().toString();
                String password = loginPassword.getText().toString();
                login(name, password);
            }
        });
    }
        private void login(String name,String password){
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

                    String urlParameters = "nameUser=" +name + "&password="+password;
                    Log.i("parametros enviados login ", urlParameters);

                    OutputStream os=urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(urlParameters.toString());
                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode = urlConnection.getResponseCode();
                    StringBuilder response = new StringBuilder();

                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        stream = urlConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        urlConnection.disconnect();
                    }
                        String result = response.toString();


                    handler.post(new Runnable() {
                        public void run() {
                            if(responseCode==HttpURLConnection.HTTP_OK){
                                Log.i("serverTest", result);
                                Toast.makeText(Login.this, "Logueado correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Login.this, Menu.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(Login.this, "Error en el servidor: " + responseCode, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void returnToRegisterOnClick(View view){
        Intent intent=new Intent(Login.this, Register.class);
        startActivity(intent);
        finish();
    }
}

