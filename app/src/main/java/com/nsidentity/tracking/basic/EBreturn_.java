package com.nsidentity.tracking.basic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EBreturn_ extends jsonSerializer<EBreturn_>
{
    public boolean Correcto;
    public String MensajeError;
    public String ex;
    public boolean Incorrecto;

    public EBreturn_()
    {
        MensajeError = "";
        ex = null;
    }

    public void SetFail(String _mensajeError, String _ex)
    {
        Correcto=false;
        Incorrecto=true;
        MensajeError=_mensajeError;
        ex=_ex;
    }

    public void SetOK(String obj)
    {
        Correcto=true;
        Incorrecto=false;
        MensajeError="";
        ex="";
    }

    public EBreturn_ ProcesaHttpRequest(String response)
    {
        EBreturn_ EB, TEMP=null;

        EB= new EBreturn_();

        if(response.contains("Error"))
        {

            EB.Incorrecto=true;
            EB.Correcto=false;
            EB.MensajeError= response;

        }
        else
        {
            AESCrypt aes= new AESCrypt();
            try
            {
                EB = (EBreturn_) EB.JsonDesSerializa(aes.decrypt(response.toString()));
            }
            catch (Exception ex)
            {
                String error=ex.getMessage();
                EB.Incorrecto=true;
                EB.Correcto=false;
                EB.MensajeError= error;
            }
        }

        return EB;
    }


    public EBreturn_ JsonDesSerializa(String cadena)
    {
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter()).create();
        EBreturn_ retorno = gson.fromJson(cadena, this.getClass() );

        return retorno;
    }


}