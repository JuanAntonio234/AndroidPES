package com.example.pes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CochesConductor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coches_conductor);
    }
    public void returnToMenuOnClick(View view){
        Intent intent=new Intent(CochesConductor.this, Menu.class);
        startActivity(intent);
        finish();
    }
}