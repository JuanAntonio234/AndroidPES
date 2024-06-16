package com.example.pes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ObtenerTodosMisCoches extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener_todos_mis_coches);
    }
/*
    public void  obtenerDatosCoche(View view){
        new Thread(new Runnable() {
            InputStream stream = null;
            String str = "";
            String result = null;
            Handler handler = new Handler();
            public void run() {

                try {
                    String query = String.format("http://10.0.2.2:9000/Application/obtenerCoches");
                    URL url = new URL(query);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 );
                    conn.setConnectTimeout(15000 /* milliseconds *///);
               /*     conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    stream = conn.getInputStream();

                    BufferedReader reader = null;

                    StringBuilder sb = new StringBuilder();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    result = sb.toString();
                    conn.disconnect();
                    // Mostrar resultat en el quadre de text.
                    // Codi incorrecte
                    // EditText n = (EditText) findViewById (R.id.edit_message);
                    //n.setText(result);

                    //Codi correcte
                    Log.i("serverTest", result);
                    handler.post(new Runnable() {
                        public void run() {
                            TextView n = (TextView) findViewById (R.id.textView);
                            n.setText(result);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
*/
  /*  public void connectServerPOST(View view) {

        new Thread(new Runnable() {
            InputStream stream = null;
            OutputStream streamOut = null;
            String str = "";
            String result = null;
            Handler handler = new Handler();
            public void run() {

                try {
                    String query = String.format("http://192.168.1.242:9000/Application/Login");
                    URL url = new URL(query);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 );
                    conn.setConnectTimeout(15000 /* milliseconds *///);
                   /* conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.connect();


                    // enviar paràmetres de la funció Login en el cos del missatge

                    String params = "username=lola&password=lolap";
                    Log.i("serverTest ", params);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(params.toString());

                    writer.flush();
                    writer.close();
                    os.close();

                    Log.i("serverTest ", "esperant resposta");

                    //obtenir resposta del servidor
                    stream = conn.getInputStream();
                    BufferedReader reader = null;
                    StringBuilder sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    result = sb.toString();
                    conn.disconnect();
                    // Mostrar resultat en el quadre de text.
                    // Codi incorrecte
                    // EditText n = (EditText) findViewById (R.id.edit_message);
                    //n.setText(result);

                    //Codi correcte
                    Log.i("serverTest", result);
                    handler.post(new Runnable() {
                        public void run() {
                            TextView n = (TextView) findViewById (R.id.textView);
                            n.setText(result);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
}