package com.nsidentity.tracking.soap;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TreeMapHelper
{
    private LinkedTreeMap _tm;
    SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    public TreeMapHelper(LinkedTreeMap linkedTreeMap) {
        _tm=linkedTreeMap;
    }

    public TreeMapHelper(String json)
    {
        Gson gson = new Gson();
        _tm= gson.fromJson(json, LinkedTreeMap.class);
    }

    public Integer GetInteger(String Campo)
    {
        if(_tm== null) return 0;
        return Double.valueOf(_tm.get(Campo).toString()).intValue();
    }

    public ArrayList<LinkedTreeMap> GetArray(String Campo)
    {
        if(_tm== null) return null;
        return (ArrayList<LinkedTreeMap>)_tm.get(Campo);
    }


    public byte[] GetByteArray(String Campo)
    {
        if(_tm== null) return null;
        return (byte[])_tm.get(Campo);
    }


    public byte[] GetB64ByteArray(String Campo)
    {
        String cadena= GetString(Campo);

       byte[] result=null;

       try {
           result = Base64.decode(cadena, Base64.DEFAULT);
       }
       catch (Exception ex)
       {}

       return result;
    }

    public String GetString(String Campo)
    {
        if(_tm.get(Campo)== null) return "";
        return _tm.get(Campo).toString();
    }

    public String Serializa()
    {
        Gson gson = new Gson();
        String json = gson.toJson(_tm);
        return json;
    }


    public Date GetDate(String Campo)
    {
        if(_tm.get(Campo)== null) return null;
        try {

            String val=_tm.get(Campo).toString();
            if(val.length()==10) val=val+" 00:00:00";

            Date date = formatDateTime.parse(val);

            return date ;
        } catch (ParseException e) {
            return null;
        }
    }

    public String GetDateString(String Campo, String Formato)
    {
        SimpleDateFormat formatDate = new SimpleDateFormat(Formato);

        Date d= GetDate(Campo);
        if(d==null) return "";

        return formatDate.format(d);
    }

    public static String DateTimeConverter(String cadenaOrigen, String FormatoOrigen, String FormatoDestino)
    {
        SimpleDateFormat formatOrigen = new SimpleDateFormat(FormatoOrigen);
        SimpleDateFormat formatDestino = new SimpleDateFormat(FormatoDestino);

        Date fecha=null;

        try
        {
            fecha = formatOrigen.parse(cadenaOrigen);
        }
        catch(Exception ex)
        {
            return "";
        }

        return formatDestino.format(fecha);
    }

    public static Date StringToDate(String valor, String Formato)
    {
        SimpleDateFormat formatDate = new SimpleDateFormat(Formato);
        try {
            Date d = formatDate.parse(valor);
            return d;
        }
        catch(Exception ex)
        {}

        return null;
    }

    public static String DateToString(Date fecha, String Formato)
    {
        String c="";
        if(fecha==null) return c;
        SimpleDateFormat formatDate = new SimpleDateFormat(Formato);
        c=formatDate.format(fecha);
        return c;
    }
}
