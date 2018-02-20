package com.example.marcos.unaspht_whatsapp.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.marcos.unaspht_whatsapp.fragment.ContatosFragment;
import com.example.marcos.unaspht_whatsapp.fragment.NoticiasFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    //Serve para tratar os conteúdos da ViewPage

    private String[] tituloAbas = {"NOTÍCIAS", "CONTATOS"};

    //Construtor
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    //get Item Retorna os fragmentos para a página
    public Fragment getItem(int position) {
        //recebe por padrão nulo
        Fragment fragment = null;

        //Definir posições das fragments
        switch (position){
            case 0:
                fragment = new NoticiasFragment();
                break;
            case 1:
                fragment = new ContatosFragment();
                break;
        }

        return fragment;
    }

    @Override
    //Retorna a quantidade de tabs
    public int getCount() {
        //Retorna o tamanho de acordo com o tamanho da tituloAbas
        return tituloAbas.length;
    }

    @Override
    //Recupera os títulos de cada aba
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}
