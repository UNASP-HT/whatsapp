package com.example.marcos.unaspht_whatsapp.acitivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.marcos.unaspht_whatsapp.Adapter.TabAdapter;
import com.example.marcos.unaspht_whatsapp.R;
import com.example.marcos.unaspht_whatsapp.config.ConfiguracaoFirebase;
import com.example.marcos.unaspht_whatsapp.helper.AlertDialogClass;
import com.example.marcos.unaspht_whatsapp.helper.SlidingTabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth usuarioAutenticacao;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private AlertDialogClass msgerro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioAutenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        toolbar = findViewById((R.id.toolbar));
        toolbar.setTitle("Notícias UNASP-HT");
        setSupportActionBar(toolbar);

        slidingTabLayout = findViewById(R.id.stl_tabs);
        viewPager = findViewById(R.id.vp_pagina);
        msgerro = new AlertDialogClass(this);

        //Configurar a tablayout para preencher a página e trocar a cor do indicativo da página
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorPrimary));
        slidingTabLayout.setBackgroundColor(0x00000000);

        //Configurar Adapter. O Adapter será usado pela viewPager
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInglater cria um objeto do menu inflater com cotexto do menu aplicação
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    //Escolher itens selecionados na toolbar
    public boolean onOptionsItemSelected(MenuItem item) {

        //Selecionando o item e as suas ações
        switch (item.getItemId()){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_adicionar:
                abrirCadastroNoticia();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCadastroNoticia(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Nova mensagem");
        alertDialog.setMessage("Por favor digite a sua mensagem abaixo:");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(this);
        alertDialog.setView(editText);

//        do {
//            editText.setError("Por favor colocar mais conteúdo antes de postar");
//        } while (editText.getText().toString().length() < 15);


        alertDialog.setPositiveButton("Postar Notícia", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Recuperar o que o usuário escreveu no editText
                String postdaNoticia = editText.getText().toString();

                if (postdaNoticia.isEmpty()){
                    msgerro.showText("", "Por favor colocar mais conteúdo antes de postar");
                } else {

                }

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();

    }

    private void deslogarUsuario() {
        usuarioAutenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
