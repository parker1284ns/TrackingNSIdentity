package com.nsidentity.tracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import java.util.ArrayList;

public class CapturaEvidenciaActivity extends AppCompatActivity {

    EditText txtNota1, txtNota2, txtNota3;
    ImageView imagen1, imagen2, imagen3;
    String RutaImagen;
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
    }


    private void GuardarEnServidor()
    {
        //ArrayList<objEvidencia> lstEvidencias
        ArrayList<objEvidencia> lstEvidencias = new ArrayList<objEvidencia>();
        //objEvidencia objE1= new objEvidencia();
        //objE1.id=1
        //objE1.foto=convertir imagen uno en base64 (debes de hacer pequeña la imagen width 400)
        objEvidencia objE1= new objEvidencia();
        objE1.evidenica_id = 1;
        Bitmap bitmap1 = ((BitmapDrawable) imagen1.getDrawable()).getBitmap();
        float proporcionancho = 300 / (float) bitmap1.getWidth();
        float proporcionalto = 300 / (float) bitmap1.getHeight();
        Bitmap resize1 = Bitmap.createScaledBitmap(bitmap1,(int) (bitmap1.getWidth()*proporcionancho), (int) (bitmap1.getHeight()*proporcionalto),true);
        ByteArrayOutputStream b1 = new ByteArrayOutputStream();
        resize1.compress(Bitmap.CompressFormat.JPEG,70,b1);
        byte[] imagen1 = b1.toByteArray();
        objE1.imagen = Base64.encodeToString(imagen1,Base64.DEFAULT);
        objE1.nota = txtNota1.getText().toString();
        lstEvidencias.add(objE1);

        objEvidencia objE2= new objEvidencia();
        objE1.evidenica_id = 2;
        Bitmap bitmap2 = ((BitmapDrawable) imagen2.getDrawable()).getBitmap();
        float proporcionancho2 = 300 / (float) bitmap1.getWidth();
        float proporcionalto2 = 300 / (float) bitmap1.getHeight();
        Bitmap resize2 = Bitmap.createScaledBitmap(bitmap2,(int) (bitmap2.getWidth()*proporcionancho2), (int) (bitmap2.getHeight()*proporcionalto2),true);
        ByteArrayOutputStream b2 = new ByteArrayOutputStream();
        resize2.compress(Bitmap.CompressFormat.JPEG,70,b2);
        byte[] imagen2 = b2.toByteArray();
        objE1.imagen = Base64.encodeToString(imagen2,Base64.DEFAULT);
        objE1.nota = txtNota2.getText().toString();
        lstEvidencias.add(objE2);

        objEvidencia objE3= new objEvidencia();
        objE1.evidenica_id = 3;
        Bitmap bitmap3 = ((BitmapDrawable) imagen3.getDrawable()).getBitmap();
        float proporcionancho3 = 300 / (float) bitmap1.getWidth();
        float proporcionalto3 = 300 / (float) bitmap1.getHeight();
        Bitmap resize3 = Bitmap.createScaledBitmap(bitmap3,(int) (bitmap3.getWidth()*proporcionancho3), (int) (bitmap3.getHeight()*proporcionalto3),true);
        ByteArrayOutputStream b3 = new ByteArrayOutputStream();
        resize3.compress(Bitmap.CompressFormat.JPEG,70,b3);
        byte[] imagen3 = b3.toByteArray();
        objE1.imagen = Base64.encodeToString(imagen3,Base64.DEFAULT);
        objE1.nota = txtNota3.getText().toString();
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
                            final EBreturn resSoap = wsSoap.SalvaEvidencia(lstEvidencias.get(i).evidenica_id, lstEvidencias.get(i).imagen, lstEvidencias.get(i).nota);
                            if (resSoap.Correcto) {
                                lstEvidencias.remove(i);
                                Log.d("Envio evidencia", "Si, con exito ");
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