package com.nsidentity.tracking.basic;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SendBase<T> extends jsonSerializer<T> {

    @Override
    public String JsonSerializa() throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        AESCrypt aes;
        aes = new AESCrypt();

        String cadena=super.JsonSerializa();
        return aes.encrypt(cadena);
    }

}