package com.nsidentity.tracking.basic;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by RMUNOZ on 08/03/2016.
 */
public class CallSoapBase {


    public String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    public String SOAP_ADDRESS="";// "http://10.10.2.28:8091/wsMBiometrics.asmx";//"http://wsairport.dtdns.net/wsCatchLocation.asmx";


    public CallSoapBase(String soap_address)
    {
        SOAP_ADDRESS=soap_address;
    }

    public String RequestResult(SoapObject request, String SOAP_ACTION)
        {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(	SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);


            HttpsTransportSE  httpTransport = new HttpsTransportSE("ebserver.com.mx",443,"/ebcontrol/ws/wsTracking.asmx",1000);
          //  HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

            Object response=null;
            EBreturn_ EB;

            try
            {
                httpTransport.call(SOAP_ACTION, envelope);
                response = envelope.getResponse();
            }
            catch (java.net.ConnectException ex)
            {
                return "Error[180710143800]\nNo se pudo conectar con el servidor.\nVerifique que tiene conexión a internet.";
            }
            catch (java.net.SocketTimeoutException ex)
            {
                return "Error[180710143800]\nTiempo de espera superado.\nVerifique que tiene conexión a internet.";
            }
            catch (XmlPullParserException ex)
            {
                return "Error[180714164920]\nFormato de respuesta incorrecto.\nVerifique que no esta bloqueado el internet.";

            }
            catch (Exception exception)
            {

                return "Error[180710143800]\n" + exception.toString();
            }

            return response.toString();
        }

    public EBreturn ConsultaMetodo(String Metodo, String Cadena, String Token)
    {
        String OPERATION_NAME = Metodo;
        String SOAP_ACTION = "http://tempuri.org/"+Metodo;

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        PropertyInfo pi=new PropertyInfo();

        pi.setName("Cadena");
        pi.setValue(Cadena);
        pi.setType(String.class);
        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("Token");
        pi.setValue(Token);
        pi.setType(String.class);
        request.addProperty(pi);

        EBreturn ret= new EBreturn();

        return ret.ProcesaHttpRequest(RequestResult(request,SOAP_ACTION ));
    }

    public  EBreturn_ ConsultaMetodo_(String Metodo, String Cadena, String Token)
    {
        String OPERATION_NAME = Metodo;
        String SOAP_ACTION = "http://tempuri.org/"+Metodo;

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
        PropertyInfo pi=new PropertyInfo();

        pi.setName("Cadena");
        pi.setValue(Cadena);
        pi.setType(String.class);
        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("Token");
        pi.setValue(Token);
        pi.setType(String.class);
        request.addProperty(pi);

        EBreturn_ ret= new EBreturn_();

        return ret.ProcesaHttpRequest(RequestResult(request,SOAP_ACTION ));

    }

}
