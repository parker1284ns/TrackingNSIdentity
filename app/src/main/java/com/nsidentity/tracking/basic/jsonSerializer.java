package com.nsidentity.tracking.basic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class jsonSerializer<T>
{
    public String JsonSerializa() throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter()).create();
        String json = gson.toJson(this);


        return json;
    }


}
