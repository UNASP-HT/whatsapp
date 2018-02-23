package com.example.marcos.unaspht_whatsapp.model;


import com.example.marcos.unaspht_whatsapp.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Noticia {

    private String id;
    private String mensagem;

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("noticia").child(getId()).setValue(this);

    }

    //@Exclude serve para não salvar o e-mail nem a senha dentro da database

    //@Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticia() {
        return mensagem;
    }

    public void setNoticia(String mensagem) {
        this.mensagem = mensagem;
    }

    public Noticia(){

    }
}
