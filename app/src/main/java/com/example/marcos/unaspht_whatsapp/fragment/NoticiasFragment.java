package com.example.marcos.unaspht_whatsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcos.unaspht_whatsapp.Adapter.NoticiaAdapter;
import com.example.marcos.unaspht_whatsapp.R;
import com.example.marcos.unaspht_whatsapp.acitivity.MainActivity;
import com.example.marcos.unaspht_whatsapp.config.ConfiguracaoFirebase;
import com.example.marcos.unaspht_whatsapp.helper.AlertDialogClass;
import com.example.marcos.unaspht_whatsapp.helper.Preferencias;
import com.example.marcos.unaspht_whatsapp.model.Noticia;
import com.example.marcos.unaspht_whatsapp.model.Usuario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Noticia> noticiasList;
    private DatabaseReference firebase;
    private AlertDialogClass msgErro;

    public NoticiasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Instanciar os objetos
        noticiasList = new ArrayList<>();

        //Inflaciona o layout para esse fragmento
        View view = inflater.inflate(R.layout.fragment_noticias, container, false);

        //Monta a listview e o adapter
        listView = view.findViewById(R.id.lv_noticias);

//        adapter = new ArrayAdapter(
//                getActivity(),
//                android.R.layout.simple_list_item_1,
//                noticias
//        );

        adapter = new NoticiaAdapter(getActivity(), noticiasList);
        listView.setAdapter(adapter);

        //Recuperar mensagens do firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                .child("noticia");

        try {
        //Listener para recuperar contatos
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar lista para não sobreescrever o último dado inserido/acionado
                noticiasList.clear();

                //vai ser chamado sempre quando os dados forem alterados atualizando as chamadas
                //datasnapshot recupera os dados

                for ( DataSnapshot dados: dataSnapshot.getChildren()){

                    Noticia noticia = dados.getValue( Noticia.class);
                    //Definir o nome que eu quero adicionar
                    noticiasList.add(noticia);

                }
                //Avisar o adapter que os dados mudaram
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}catch (Exception e){
            msgErro.showText("Erroooou!", "não sei o que tá rolando, olha esse erro: " +e);
        }

        return view;
    }

}
