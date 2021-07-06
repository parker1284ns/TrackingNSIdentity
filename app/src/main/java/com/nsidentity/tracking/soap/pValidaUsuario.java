package com.nsidentity.tracking.soap;

import com.google.gson.JsonSerializer;
import com.nsidentity.tracking.basic.EBreturn;
import com.nsidentity.tracking.basic.SendBase;

public class pValidaUsuario extends SendBase<pValidaUsuario> {

    public String imei;
    public String nombre_usuario;
    public String contrasena;

}
