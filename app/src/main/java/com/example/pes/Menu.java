package com.example.pes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Menu extends AppCompatActivity {

    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
    }
    public void goDatosCocheOnClick(View view){
        Intent intent=new Intent(Menu.this, DatosCoche.class);
        startActivity(intent);
    }
    public void goCochesConductorOnClick(View view){
        Intent intent=new Intent(Menu.this, CochesConductor.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }
    public void goDatosConductoOnClick(View view){
        Intent intent=new Intent(Menu.this, ObtenerDatosConductor.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }
}