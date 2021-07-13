package com.nsidentity.tracking.soap;


import com.nsidentity.tracking.basic.CallSoapBase;
import com.nsidentity.tracking.basic.EBreturn;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CallSoap extends CallSoapBase
{
        public CallSoap(String soap_address) {
                super(soap_address);
        }



    public EBreturn SalvaLogTrackingTest(String nombre)
    {
        String mensaje="";

        pSalvaLogTrackingTest parametros= new pSalvaLogTrackingTest();
        parametros.nombre=nombre;

        try
        {
            mensaje = parametros.JsonSerializa();
        }
        catch(Exception ex)
        {
            EBreturn reterr= new EBreturn();
            reterr.SetFail("Error al serializar objeto." , ex.getMessage());
            return reterr;
     }

        EBreturn ret= this.ConsultaMetodo("SalvaLogTrackingTest", mensaje, token.get());

        return ret;
    }


    public EBreturn SalvaLogTracking(double lat, double lon, int cuenta_id,String imei)
    {
        String mensaje="";

        pSalvaLogTracking parametros= new pSalvaLogTracking();
        parametros.cuenta_id=cuenta_id;
        parametros.imei = imei;
        parametros.latitud = lat;
        parametros.longitud = lon;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String fecha = dateFormat.format(date);

        parametros.fecha_registro = fecha;


       //Obtner la fehca en android

        try
        {
            mensaje = parametros.JsonSerializa();
        }
        catch(Exception ex)
        {
            EBreturn reterr= new EBreturn();
            reterr.SetFail("Error al serializar objeto." , ex.getMessage());
            return reterr;
        }

        EBreturn ret= this.ConsultaMetodo("SalvaLogTracking", mensaje, token.get());

        return ret;
    }

    //SalvaLogTrackingRegistrosOffline(double lat, double lon, int cuenta_id,String imei,String fecha_reg)

    public EBreturn SalvaLogTrackingRegistrosOffline(double lat, double lon, int cuenta_id,String imei,String fecha_reg){
        String mensaje="";

        pSalvaLogTracking parametros= new pSalvaLogTracking();
        parametros.cuenta_id=cuenta_id;
        parametros.imei = imei;
        parametros.latitud = lat;
        parametros.longitud = lon;


        try
        {
            mensaje = parametros.JsonSerializa();
        }
        catch(Exception ex)
        {
            EBreturn reterr= new EBreturn();
            reterr.SetFail("Error al serializar objeto." , ex.getMessage());
            return reterr;
        }

        EBreturn ret= this.ConsultaMetodo("SalvaLogTracking", mensaje, token.get());

        return ret;
    }

    public EBreturn SalvaEvidencia(int evidicena_id,String imagen,String nota){
        String mensaje="";

        pSalvaEvidencia parametros= new pSalvaEvidencia();
        parametros.evidencia_id = evidicena_id;
        parametros.imagen = imagen;
        parametros.nota = nota;


        try
        {
            mensaje = parametros.JsonSerializa();
        }
        catch(Exception ex)
        {
            EBreturn reterr= new EBreturn();
            reterr.SetFail("Error al serializar objeto." , ex.getMessage());
            return reterr;
        }

        EBreturn ret= this.ConsultaMetodo("SalvaEvidencia", mensaje, token.get());

        return ret;
    }


    public EBreturn ValidaUsuario(String imei, String nombre_usuario, String contrasena)
    {
        String mensaje="";

        pValidaUsuario parametros = new pValidaUsuario();

        parametros.imei = imei;
        parametros.contrasena = contrasena;
        parametros.nombre_usuario = nombre_usuario;


        //Obtner la fehca en android

        try
        {
            mensaje = parametros.JsonSerializa();
        }
        catch(Exception ex)
        {
            EBreturn reterr= new EBreturn();
            reterr.SetFail("Error al serializar objeto." , ex.getMessage());
            return reterr;
        }

        EBreturn ret= this.ConsultaMetodo("ValidaUsuario", mensaje, token.get());

        return ret;
    }

}

