package com.nsidentity.tracking.soap;



import com.nsidentity.tracking.basic.SendBase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class token extends SendBase<token> {

    public String imei="";
    public String datetime="";
    public String telefono="";
    public String clave="";
    public String usuario="";
    public String userapp = "SafeVisitor2018";

    public token()
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        datetime = df.format(Calendar.getInstance().getTime());

        //if(global.GetGlobal().usrRegistro!=null)
        //{
            imei ="1111111111";// global.GetGlobal().usrRegistro.get_IMEI();
            telefono ="5526587684";// global.GetGlobal().getTelefono();
            clave ="521";// global.GetGlobal().usrRegistro.get_clave();
            usuario ="rmunoz";// global.GetGlobal().usrRegistro.get_cuenta();
       // }

    }


    @Override
    public String toString()
    {
        try
        {
            return this.JsonSerializa();
        }
        catch (Exception ex)
        {
            return "";
        }

    }


    public static String get()
    {
        token t= new token();
        return t.toString();
    }
}
