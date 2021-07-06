package com.nsidentity.tracking;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nsidentity.tracking.basic.EBreturn;
import com.nsidentity.tracking.clases.global;
import com.nsidentity.tracking.soap.CallSoap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    TextView txtResultado;
    EditText txtUsuario,txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SolicitarPermisios();
        txtResultado=(TextView)findViewById(R.id.txtResultado);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtUsuario = (EditText)findViewById(R.id.txtUsuario);

        try {
            SaveDB();
            Log.d("Se creó BD","Se creo BD con metodo SaveBD");
        }catch(Exception ex)
        {
            Log.d("error al crear file bd","Error al crear bd con metodo SaveBD");
        }
    }



    private void SolicitarPermisios() {

        int permisoCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int writeexternal = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readexternal = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int accesnet = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
        int readphone = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
        ,Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        //////////////////////////////////////////////////////////////////////////////////////////////
        if(readphone == PackageManager.PERMISSION_DENIED ){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)){

            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},
                        1);
            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////////
        if(permisoCheck == PackageManager.PERMISSION_DENIED ){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){

            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////////
        if(writeexternal == PackageManager.PERMISSION_DENIED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////////
        if(readexternal == PackageManager.PERMISSION_DENIED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////////
        if(accesnet == PackageManager.PERMISSION_DENIED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_NETWORK_STATE)){

            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                        1);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void clickImei(View view){
        String imei;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imei= Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager)LoginActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
             else {
                imei = mTelephony.getImei();
            }
        }

        txtResultado.setText(imei);
    }

    public void clickIngresar(View view){
       ConsultaWS();

    }

    public void clickConfiguracion(View view){
        Intent i = new Intent(this,ConfiguracionActivity.class);
        startActivity(i);
    }



    private void ConsultaWS() {

        if(txtUsuario.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()){
            Toast.makeText(LoginActivity.this,"Llena todo los campos.", Toast.LENGTH_SHORT).show();
        }else{

            String usuario = txtUsuario.getText().toString();
            String contrasena = txtPassword.getText().toString();

            String imei;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                imei = Settings.Secure.getString(this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            } else {
                final TelephonyManager mTelephony = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "No se tienen permisos", Toast.LENGTH_SHORT).show();
                }
                if (mTelephony.getDeviceId() != null) {
                    imei = mTelephony.getDeviceId();
                } else {
                    imei = Settings.Secure.getString(
                            this.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                }
            }

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        CallSoap wsSoap = new CallSoap(global.GetGlobal().getURL());
                        String result = null;
                        //Android 11 ó 10 (obtener *#06#)
                        final EBreturn resSoap = wsSoap.ValidaUsuario(imei,usuario,contrasena);
                        if(resSoap.Correcto)
                        {
                            runOnUiThread(new  Runnable() {
                                public void run() {
                                    //ShowSnack("Correcto:El código fue: "+ Codigo);
                                    try {
                                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(i);
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                        Log.d("error",e.toString());
                                    }
                                    /**********************************************************/
                                }});
                            /****************************************************************************/
                        }else{
                            Log.d("Error","Error al conectar con ws");
                            runOnUiThread(new  Runnable() {
                                public void run() {
                                    // ShowSnack("Correcto:El código fue: "+ Codigo);
                                    try {
                                        txtResultado.setText("Respuesta: " +resSoap.MensajeError);
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                        Log.d("error",e.toString());
                                    }
                                    /**********************************************************/
                                }});

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
        }
    }
    private void SaveDB()  {

        try{
            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
            final String inFileName = "/data/data/com.nsidentity.tracking/databases/" + "tracking";
            File dbFile = new File(inFileName);
            FileInputStream fis = null;
            fis = new FileInputStream(dbFile);
            File path = Environment.getExternalStorageDirectory();
            // String directorio = path.toString()+"/rmvbd";
            String directorio = path.toString();
            File d = new File(directorio);
            if (!d.exists()) {
                d.mkdir();
            }
            String outFileName = directorio + "/"+"tracking" + "_"+timeStamp;
            OutputStream output = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
        }catch(Exception ex){
            Log.d("error al crear file bd","Error al crear bd con metodo SaveBD");
        }


    }

}