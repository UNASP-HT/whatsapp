package com.example.marcos.unaspht_whatsapp.model;

import com.example.marcos.unaspht_whatsapp.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

/**
 * Created by Italo on 18/02/2018.
 */

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;

    public void salvar(){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarios").child(getId()).setValue(this);

    }

    //@Exclude serve para não salvar o e-mail nem a senha dentro da database

    //@Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //@Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario(){

    }
}
