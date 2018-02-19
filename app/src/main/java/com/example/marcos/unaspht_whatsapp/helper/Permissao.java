package com.example.marcos.unaspht_whatsapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Italo on 16/02/2018.
 */

public class Permissao {

    public static boolean validaPermissoes (int requestCode, Activity activity, String[] permissoes){

        if (Build.VERSION.SDK_INT >= 23){

            List<String> listapermissoes = new ArrayList<>();

            //percorrer as permissões passadas, verificando uma a uma se tá tem permissão liberada
            for (String permissao : permissoes){
                Boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if (!validaPermissao) listapermissoes.add(permissao);
            }

            //Caso a lista esteja vazia, não é necessário solicitar  a permissão
            if (listapermissoes.isEmpty()) return true;

            //converter ArrayList em Array
            String[] novasPermissoes = new String[listapermissoes.size()];
            listapermissoes.toArray(novasPermissoes);

            //Solicitar permissão
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
        }

        return true;
    }
}
