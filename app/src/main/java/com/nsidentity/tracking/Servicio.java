package com.nsidentity.tracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Servicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);
    }

    public void clickIniciar(View view){
        startService(new Intent(this,MyService.class));
    }

    public void clickDetener(View view){
        stopService(new Intent(this,MyService.class));
    }
}