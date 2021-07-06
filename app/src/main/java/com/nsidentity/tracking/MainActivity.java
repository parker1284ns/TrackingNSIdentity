package com.nsidentity.tracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nsidentity.tracking.basic.EBreturn;
import com.nsidentity.tracking.clases.global;
import com.nsidentity.tracking.entidades.SQLiteHelper;
import com.nsidentity.tracking.entidades.log_tracking;
import com.nsidentity.tracking.entidades.log_tracking_abs;
import com.nsidentity.tracking.soap.CallSoap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    //Elementos Utilizados
    TextView txtLatitud,txtLongitud,txtResultado,txtRed,txtServicio;
    Button btnIniciar,btnDetener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteHelper.Inicializa(getApplication(),"tracking",1);
        //Nombrar elementos para poder usarlos
        txtLatitud = (TextView)findViewById(R.id.txtLatitud);
        txtLongitud = (TextView)findViewById(R.id.txtLongitud);
        txtResultado = (TextView)findViewById(R.id.txtResultado);
        txtRed = (TextView)findViewById(R.id.txtRed);
        txtServicio = (TextView)findViewById(R.id.txtEstadoServicio);

        btnIniciar = (Button)findViewById(R.id.btnActivar);
        btnDetener = (Button)findViewById(R.id.btnDetener);

        txtServicio.setText("Estado:");



        ObtenerUbicacion();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isConnected()){
                    txtRed.setText("Online");
                }else {
                    txtRed.setText("Offline");
                }
                handler.postDelayed(this,500);

            }
        },500);


    }


    //Variables para guardar la latitud y longitud
    public double latitud;
    public double longitud;


    //Metodo para obtener la ubicacion
    public void ObtenerUbicacion() {
        LocationManager locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                txtLatitud.setText("Latitud: "+location.getLatitude());
                txtLongitud.setText("Longitud: "+location.getLongitude());

                latitud = location.getLatitude();
                longitud = location.getLongitude();
                
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        int permisoCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, locationListener);
    }

    //Metodo para regresar a la ventana principal
    public void clickRegresar(View view){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    //Metodo para ir al Servicio
    public void clickIniciar(View view){
        startService(new Intent(this,MyService.class));
        txtServicio.setText("Estado: Iniciado");

    }

    public void clickDetener(View view){
        stopService(new Intent(this,MyService.class));
        txtServicio.setText("Estado: Detenido");

    }

    public void clickTomarEvidencia(View view){
        /*Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();*/

        Intent i = new Intent(this,CapturaEvidenciaActivity.class);
        startActivity(i);
    }

}