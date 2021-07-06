package com.nsidentity.tracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ConfiguracionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
    }

    public void clickActivar(View view){
        Toast.makeText(this,"Diste clic en Activar" , Toast.LENGTH_SHORT).show();
    }
    public void clickRegresar(View view){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}