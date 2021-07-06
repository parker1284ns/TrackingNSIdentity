package com.nsidentity.tracking;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nsidentity.tracking.basic.EBreturn;
import com.nsidentity.tracking.clases.global;
import com.nsidentity.tracking.entidades.log_tracking;
import com.nsidentity.tracking.entidades.log_tracking_abs;
import com.nsidentity.tracking.soap.CallSoap;

import java.util.ArrayList;
import java.util.Date;


public class MyService extends Service {

    public double latitud;
    public double longitud;
    public String imei;

    public MyService() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
/////////////////////////////Inicio del servico////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ObtenerImei();
        Toast.makeText(this, "Se inicio el servicio", Toast.LENGTH_SHORT).show();
        IniciarTimer();
        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        DetenerTimer();
        Toast.makeText(this, "El servicio se detuvo", Toast.LENGTH_SHORT).show();
    }


    ////////////////////////Timer//////////////////////////////////////////////////////////

    private Handler myHandler = new Handler();

    private Runnable myToastRunnable = new Runnable() {
        @Override
        public void run() {

            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){
                //sincronizarAserver();
                ConsultaWS();
            }else {
                ObtenerUbicacion();
                if(latitud==0 && longitud==0)return;
                GuardaCoordenadasOffline(latitud,longitud,19,imei);

            }
            myHandler.postDelayed(this, 10000);
        }
    };
////////////////////////Inciar o detenr el servico/////////////////////////////////////////////////////
    public void IniciarTimer() {
        myToastRunnable.run();
    }

    public void DetenerTimer() {
        myHandler.removeCallbacks(myToastRunnable);
    }




    private void ConsultaWS() {

        //1-obtengo coordenadas
        ObtenerUbicacion();
        //2-consumo ws

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    CallSoap wsSoap = new CallSoap(global.GetGlobal().getURL());
                    String result = null;


                    if(latitud==0 && longitud==0)return;

                    final EBreturn resSoap = wsSoap.SalvaLogTracking(latitud,longitud,19,imei);
                    if(resSoap.Correcto)
                    {

                     Log.d("Envio coordenadas","Si, con exito");


                    }else{

                        Log.d("Error","Error al conectar con ws");


                        return;
                    }

                } catch (Exception e) {
                    // if(dialog.isShowing())dialog.dismiss();
                    e.printStackTrace();
                    Log.d("error",e.toString());
                }
            }//fin de hilo
        });

        thread.start();

    }//fin ConsultaWs


    public void ObtenerUbicacion() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


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
        int permisoCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, locationListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ObtenerImei(){

        String id;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
             imei= Settings.Secure.getString(MyService.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager)MyService.this.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(MyService.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            if (mTelephony.getDeviceId() != null) {
                imei = mTelephony.getImei();//.getDeviceId();
            } else {
                imei = Settings.Secure.getString(MyService.this.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }

        Toast.makeText(this, ""+ imei, Toast.LENGTH_SHORT).show();

    }

    public void GuardaCoordenadasOffline(double lat, double log, int cuentaId, String imei)
    {

        log_tracking lgTracking = new log_tracking();
        lgTracking.set_latitud(lat);
        lgTracking.set_longitud(log);
        lgTracking.set_cuenta_id(cuentaId);
        lgTracking.set_imei(imei);
        Date date = new Date();
        lgTracking.set_fecha_registro(date);

        if(!lgTracking.Save())
        {


            Log.d("Error","Error al guardar coordenadas");


        }else {
            Log.d("Envio coordenadas","Si, con exito offline");
        }
    }

    public void sincronizarAserver(){
        ArrayList<log_tracking> lstlog = log_tracking.GetAll(log_tracking_abs.enm_log_tracking.fecha_registro);
        if(lstlog.size() > 0)
        {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

                        CallSoap wsSoap = new CallSoap(global.GetGlobal().getURL());
                        String result = null;


                        if(latitud==0 && longitud==0)return;
                        for(int i=0; i < lstlog.size(); i++) {
                            final EBreturn resSoap = wsSoap.SalvaLogTracking(lstlog.get(i).get_latitud(), lstlog.get(i).get_longitud(), lstlog.get(i).get_cuenta_id(), lstlog.get(i).get_imei());
                            if (resSoap.Correcto) {
                                log_tracking.DeleteByID(lstlog.get(i).get_log_tracking_id());
                                Log.d("Envio coordenadas", "Si, con exito sincronizado");


                            } else {

                                Log.d("Error", "No hay datos para sincronizar");


                                return;
                            }
                        }
                    } catch (Exception e) {
                        // if(dialog.isShowing())dialog.dismiss();
                        e.printStackTrace();
                        Log.d("error",e.toString());
                    }
                }//fin de hilo
            });

            thread.start();
        }
    }
}


