package com.nsidentity.tracking;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nsidentity.tracking.basic.EBreturn;
import com.nsidentity.tracking.clases.global;
import com.nsidentity.tracking.clases.objEvidencia;
import com.nsidentity.tracking.soap.CallSoap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CapturaEvidenciaActivity extends AppCompatActivity {

    EditText txtNota1, txtNota2, txtNota3;
    ImageView imagen1, imagen2, imagen3;
    String RutaImagen,imei;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_evidencia);

        txtNota1 = (EditText) findViewById(R.id.txtNota1);
        txtNota2 = (EditText) findViewById(R.id.txtNota2);
        txtNota3 = (EditText) findViewById(R.id.txtNota3);

        //Imagenes
        imagen1 = (ImageView) findViewById(R.id.imagen1);
        imagen2 = (ImageView) findViewById(R.id.imagen2);
        imagen3 = (ImageView) findViewById(R.id.imagen3);
        ObtenerImei();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ObtenerImei(){

        String id;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imei= Settings.Secure.getString(CapturaEvidenciaActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager)CapturaEvidenciaActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(CapturaEvidenciaActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            if (mTelephony.getDeviceId() != null) {
                imei = mTelephony.getImei();//.getDeviceId();
            } else {
                imei = Settings.Secure.getString(CapturaEvidenciaActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }

    }

    public void GuardarEnservidorPrueba(View view){

        ArrayList<objEvidencia> lstEvidencias = new ArrayList<objEvidencia>();
        //objEvidencia objE1= new objEvidencia();
        //objE1.id=1
        //objE1.foto=convertir imagen uno en base64 (debes de hacer pequeña la imagen width 400)
        objEvidencia objE1= new objEvidencia();
        objE1.evidencia_id = 1;
        global global = new global();
        Bitmap bitmap1 = ((BitmapDrawable) imagen1.getDrawable()).getBitmap();
        objE1.imagen = global.GetImagenBase64(bitmap1);
        objE1.nota = txtNota1.getText().toString();
        objE1.fecha_captura = global.ObtenerFecha();
        objE1.imei = imei;
        lstEvidencias.add(objE1);

        objEvidencia objE2= new objEvidencia();
        objE2.evidencia_id = 2;
        Bitmap bitmap2 = ((BitmapDrawable) imagen2.getDrawable()).getBitmap();
        objE2.imagen = global.GetImagenBase64(bitmap2);
        objE2.nota = txtNota2.getText().toString();
        objE2.fecha_captura = global.ObtenerFecha();
        objE2.imei = imei;
        lstEvidencias.add(objE2);

        objEvidencia objE3= new objEvidencia();
        objE3.evidencia_id = 3;
        Bitmap bitmap3 = ((BitmapDrawable) imagen3.getDrawable()).getBitmap();
        objE3.imagen = global.GetImagenBase64(bitmap3);
        objE3.nota = txtNota3.getText().toString();
        objE3.fecha_captura = global.ObtenerFecha();
        objE3.imei = imei;
        lstEvidencias.add(objE3);
        if(lstEvidencias.size() > 0)
        {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    CallSoap wsSoap = new CallSoap(global.GetGlobal().getURL());
                    String result = null;
                    for(int i=0; i < lstEvidencias.size(); i++) {
                        final EBreturn resSoap = wsSoap.SalvaEvidencia(lstEvidencias.get(i).evidencia_id, lstEvidencias.get(i).imagen, lstEvidencias.get(i).nota, lstEvidencias.get(i).fecha_captura, lstEvidencias.get(i).imei);
                        if (resSoap.Correcto) {
                            lstEvidencias.remove(i);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    // ShowSnack("Correcto:El código fue: "+ Codigo);
                                    try {

                                        Toast.makeText(CapturaEvidenciaActivity.this, "Respuesta: Evidencia correcta", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d("error", e.toString());
                                    }
                                    /**********************************************************/
                                }
                            });
                            /****************************************************************************/
                        } else {
                            Log.d("Error", "Error al conectar con ws");
                            //guardar en bd locakl porque no0 t5ransmitio
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    // ShowSnack("Correcto:El código fue: "+ Codigo);
                                    try {
                                        //txtResultado.setText("Respuesta: " +resSoap.MensajeError);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d("error", e.toString());
                                    }

                                    /**********************************************************/
                                }
                            });

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

   /* public void GuardarEnServidor(View view)
    {
        //ArrayList<objEvidencia> lstEvidencias
        ArrayList<objEvidencia> lstEvidencias = new ArrayList<objEvidencia>();
        //objEvidencia objE1= new objEvidencia();
        //objE1.id=1
        //objE1.foto=convertir imagen uno en base64 (debes de hacer pequeña la imagen width 400)
        objEvidencia objE1= new objEvidencia();
        objE1.evidencia_id = 1;
        global global = new global();
        Bitmap bitmap1 = ((BitmapDrawable) imagen1.getDrawable()).getBitmap();
        objE1.imagen = global.GetImagenBase64(bitmap1);
        objE1.nota = txtNota1.getText().toString();
        objE1.fecha_captura = global.ObtenerFecha();
        objE1.imei = imei;
        lstEvidencias.add(objE1);

        objEvidencia objE2= new objEvidencia();
        objE2.evidencia_id = 2;
        Bitmap bitmap2 = ((BitmapDrawable) imagen2.getDrawable()).getBitmap();
        objE2.imagen = global.GetImagenBase64(bitmap2);
        objE2.nota = txtNota2.getText().toString();
        objE2.fecha_captura = global.ObtenerFecha();
        objE2.imei = imei;
        lstEvidencias.add(objE2);

        objEvidencia objE3= new objEvidencia();
        objE3.evidencia_id = 3;
        Bitmap bitmap3 = ((BitmapDrawable) imagen3.getDrawable()).getBitmap();
        objE3.imagen = global.GetImagenBase64(bitmap3);
        objE3.nota = txtNota3.getText().toString();
        objE3.fecha_captura = global.ObtenerFecha();
        objE3.imei = imei;
        lstEvidencias.add(objE3);
        //hjacer 2 veces mas

        //implementar hilo para subir
        //  for(lstEvidencias)
        //subir 1 x 1

        if(lstEvidencias.size() > 0)
        {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

                        CallSoap wsSoap = new CallSoap(global.GetGlobal().getURL());
                        String result = null;

                        for(int i=0; i < lstEvidencias.size(); i++) {

                            final EBreturn resSoap = wsSoap.SalvaEvidencia(lstEvidencias.get(i).evidencia_id, lstEvidencias.get(i).imagen, lstEvidencias.get(i).nota,lstEvidencias.get(i).fecha_captura,lstEvidencias.get(i).imei);
                            if (resSoap.Correcto) {
                                lstEvidencias.remove(i);
                                Log.d("Envio evidencia", "Si, con exito ");
                            } else {

                                Log.d("Error", "No hay datos para mandar");


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
*/
    public void abrirCamara1(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent,1);
        if(intent.resolveActivity(getPackageManager()) != null){

            File imagenarchivo = null;

            try {
                imagenarchivo = CrearImagen();
            }catch(IOException ex){
                Log.e("Error", ex.toString());
            }

            if(imagenarchivo != null){
                Uri fotouri = FileProvider.getUriForFile(this,"com.nsidentity.tracking.fileprovider",imagenarchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,fotouri);
                startActivityForResult(intent,1);
            }
        }
    }
    public void abrirCamara2(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent,1);
        if(intent.resolveActivity(getPackageManager()) != null){

            File imagenarchivo = null;

            try {
                imagenarchivo = CrearImagen();
            }catch(IOException ex){
                Log.e("Error", ex.toString());
            }

            if(imagenarchivo != null){
                Uri fotouri = FileProvider.getUriForFile(this,"com.nsidentity.tracking.fileprovider",imagenarchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,fotouri);
                startActivityForResult(intent,2);
            }
        }
    }

    public void abrirCamara3(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent,1);
        if(intent.resolveActivity(getPackageManager()) != null){

            File imagenarchivo = null;

            try {
                imagenarchivo = CrearImagen();
            }catch(IOException ex){
                Log.e("Error", ex.toString());
            }

            if(imagenarchivo != null){
                Uri fotouri = FileProvider.getUriForFile(this,"com.nsidentity.tracking.fileprovider",imagenarchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,fotouri);
                startActivityForResult(intent,3);
            }
        }
    }


    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            //Bundle extras = data.getExtras();
            Bitmap imgBitmap = BitmapFactory.decodeFile(RutaImagen);
            imagen1.setBackground(null);
            imagen1.setImageBitmap(imgBitmap);
            Toast.makeText(this, "Evidencia capturada", Toast.LENGTH_SHORT).show();
        }
        if(requestCode == 2 && resultCode == RESULT_OK){
            //Bundle extras = data.getExtras();
            Bitmap imgBitmap = BitmapFactory.decodeFile(RutaImagen);
            imagen2.setBackground(null);
            imagen2.setImageBitmap(imgBitmap);
            Toast.makeText(this, "Evidencia capturada", Toast.LENGTH_SHORT).show();
        }
        if(requestCode == 3 && resultCode == RESULT_OK){
            //Bundle extras = data.getExtras();
            Bitmap imgBitmap = BitmapFactory.decodeFile(RutaImagen);
            imagen3.setBackground(null);
            imagen3.setImageBitmap(imgBitmap);
            Toast.makeText(this, "Evidencia capturada", Toast.LENGTH_SHORT).show();
        }

    }

    private File CrearImagen() throws IOException {
        String nombreImagen = "Evidencia_";
        File Directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg",Directorio);

        RutaImagen = imagen.getAbsolutePath();
        return imagen;
    }


}