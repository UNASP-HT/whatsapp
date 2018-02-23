package com.example.marcos.unaspht_whatsapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.marcos.unaspht_whatsapp.R;
import com.example.marcos.unaspht_whatsapp.model.Noticia;

import java.util.ArrayList;

/**
 * Created by Italo on 23/02/2018.
 */

public class NoticiaAdapter extends ArrayAdapter<Noticia>{

    private ArrayList<Noticia> noticiasList;
    private Context context;

    public NoticiaAdapter(Context c, ArrayList<Noticia> objects) {
        super(c, 0, objects);
        this.noticiasList = objects;
        this.context = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //Verificar se a lista está vazia
        if (noticiasList != null){
            //inicializar o objeto para montagem da view //LayoutInflater converte xml em view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Monar view a paritr do xml
            view = inflater.inflate(R.layout.lista_noticias, parent, false);

            //Recupera o elemento para exibicao
            TextView postNoticia = view.findViewById(R.id.tv_noticia);

            Noticia noticia = noticiasList.get(position);
            postNoticia.setText(noticia.getNoticia());
        }

        return view;

    }
}
