package com.example.marcos.unaspht_whatsapp.helper;

import android.util.Base64;

/**
 * Created by Italo on 23/02/2018.
 */

public class Base64Custom {

    public static String codificarBase64(String texto){

        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

    }

    public static String decodificarBase64(String textoCodificado){
        return new String (Base64.decode(textoCodificado, Base64.DEFAULT));
    }
}
