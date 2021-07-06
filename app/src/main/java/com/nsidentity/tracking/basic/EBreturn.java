package com.nsidentity.tracking.basic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EBreturn extends jsonSerializer<EBreturn>
{
    public boolean Correcto;
    public String MensajeError;
    public String ex;
    public boolean Incorrecto;

    public Object Value;

    public EBreturn()
    {
        MensajeError = "";
        ex = null;
        Value = null;
    }

    public void SetFail(String _mensajeError, String _ex)
    {
        Correcto=false;
        Incorrecto=true;
        MensajeError=_mensajeError;
        ex=_ex;
        Value=null;
    }

    public void SetOK(Object obj)
    {
        Correcto=true;
        Incorrecto=false;
        MensajeError="";
        ex="";
        Value=obj;
    }

    public Object GetObjetoRetorno(Object i )
    {
        Gson gson = new Gson();
       // i = gson.fromJson(ObjetoRetorno, i.getClass());

        return i;
    }

    public EBreturn ProcesaHttpRequest(String response)
    {
        EBreturn EB;
        EB= new EBreturn();
        if(response.contains("Error["))
        {
            EB.Incorrecto=true;
            EB.Correcto=false;
            EB.MensajeError= response;
            EB.Value="";
        }
        else
            {

                AESCrypt aes= new AESCrypt();
                try
                {
                    EB = (EBreturn) EB.JsonDesSerializa(aes.decrypt(response.toString()));
                }
                catch (Exception ex)
                {
                   String error=ex.getMessage();
                    EB.Incorrecto=true;
                    EB.Correcto=false;
                    EB.MensajeError= error;
                    EB.Value="";
                }
        }

        return EB;
    }


    public EBreturn JsonDesSerializa(String cadena)
    {
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter()).create();
        EBreturn retorno = gson.fromJson(cadena, this.getClass() );
        return retorno;
    }




}
