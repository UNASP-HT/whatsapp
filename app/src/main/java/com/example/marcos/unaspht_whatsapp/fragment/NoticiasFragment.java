package com.example.marcos.unaspht_whatsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.marcos.unaspht_whatsapp.R;
import com.example.marcos.unaspht_whatsapp.acitivity.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> noticias;

    public NoticiasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Instanciar os objetos
        noticias = new ArrayList<>();
        noticias.add("Amanhã teremos nosso primeiro churrasco vegano, por favor estejam presentes");
        noticias.add("O UNASP-HT está pegando fogo!!!!!");
        noticias.add("Ficaremos sem energia durante todo o período da manhã");
        noticias.add("A manutenção estará fechada durante essa tarde");
        noticias.add("Amanhã teremos nosso primeiro churrasco vegano, por favor estejam presentes");
        noticias.add("O UNASP-HT está pegando fogo!!!!!");
        noticias.add("Ficaremos sem energia durante todo o período da manhã");
        noticias.add("A manutenção estará fechada durante essa tarde");
        noticias.add("Amanhã teremos nosso primeiro churrasco vegano, por favor estejam presentes");
        noticias.add("O UNASP-HT está pegando fogo!!!!!");
        noticias.add("Ficaremos sem energia durante todo o período da manhã");
        noticias.add("A manutenção estará fechada durante essa tarde");
        noticias.add("Amanhã teremos nosso primeiro churrasco vegano, por favor estejam presentes");
        noticias.add("O UNASP-HT está pegando fogo!!!!!");
        noticias.add("Ficaremos sem energia durante todo o período da manhã");
        noticias.add("A manutenção estará fechada durante essa tarde");
        noticias.add("Amanhã teremos nosso primeiro churrasco vegano, por favor estejam presentes");
        noticias.add("O UNASP-HT está pegando fogo!!!!!");
        noticias.add("Ficaremos sem energia durante todo o período da manhã");
        noticias.add("A manutenção estará fechada durante essa tarde");

        //View view para que possamos ter o objeto view em uma variável
        View view = inflater.inflate(R.layout.fragment_noticias, container, false);

        listView = view.findViewById(R.id.lv_noticias);
        adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                noticias
        );

        listView.setAdapter(adapter);

        return view;
    }

}
