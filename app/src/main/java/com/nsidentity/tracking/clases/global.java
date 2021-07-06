package com.nsidentity.tracking.clases;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.nsidentity.tracking.MyService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class global {
    private static global pglobal;

    public static global GetGlobal() {
        if (pglobal == null) {
            pglobal = new global();
            return pglobal;
        }

        return pglobal;
    }

    private Date fechaActual = null;
    private Date fechaDefault = null;
    private Date fechaVencimiento = null;


    public String getLicenciasNS() {
        return "357130085219200";
    }


    public String RutaBase = "https:/ebserver.com.mx/ebcontrol/";


    public Date getFechaVencimiento() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date d = sdf.parse("01/06/2021 00:00");
        fechaDefault = d;
        return fechaDefault;

    }

    public void setFechaVencimiento(Date fechaDefault) {
        this.fechaDefault = fechaDefault;
    }


    public enum enmUniNeue {Black, BlackItalic, Bold, BoldItalic, Book, BookItalic, Heavy, HeavyItalic, Light, LightItalic, Regular, RegularItalic, Thin, ThinItalic}

    public static Typeface GetFont(Context c, enmUniNeue tipo) {
        Typeface face;
        String Archivo;

        Archivo = tipo.toString();
        face = Typeface.createFromAsset(c.getAssets(), "fonts/UniNeue-" + Archivo + ".otf");
        return face;
    }

    public Date getFechaActual() throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String strCurrentDate = sdf.format(cal.getTime());
        Date dateAct = sdf.parse(strCurrentDate);

        fechaActual = dateAct;
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }


    public String getURL() {
        //test
        // return "http://192.168.1.74/Admin/ws/wscomunicacion.asmx";
        // return "10.0.0.6/Admin/ws/wscomunicacion.asmx";
        return RutaBase + "ws/wsTracking.asmx";

    }


    public int ScreenW;
    public int ScreenH;

    public String version_code = "11";
    public String version = "2.0.2";






}
