package com.example.pes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;

public class ObtenerTodosMisCoches extends AppCompatActivity {

    String BASE_URL=String.format("http://10.0.2.2:9000/Application/datosConductor");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener_todos_mis_coches);
    }
/*
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
                    urlConnection.setConnectTimeout(15000 /* milliseconds*/ /*);*/
             /*       urlConnection.setDoInput(true);
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
                 /*   handler.post(new Runnable() {
                        public void run() {
                            TextView n = (TextView) findViewById (R.id.textView);
                            n.setText(result);
                        }
                    });
*/
     /*           } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
*/
}