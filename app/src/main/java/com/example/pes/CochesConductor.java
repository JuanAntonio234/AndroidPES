package com.example.pes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class CochesConductor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coches_conductor);
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