package com.nada.yuri.pessoa;

import java.io.Serializable;

public class Pessoa implements Serializable{
    private static String nome,telefone,email,endereco;

    public Pessoa(){
        this.nome = "";
        this.telefone = "";
        this.email = "";
        this.endereco = "";
    }

    public Pessoa(String nome, String telefone, String email, String endereco){
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public static void setEmail(String email) {
        Pessoa.email = email;
    }

    public static void setEndereco(String endereco) {
        Pessoa.endereco = endereco;
    }

    public static void setNome(String nome) {
        Pessoa.nome = nome;
    }

    public static void setTelefone(String telefone) {
        Pessoa.telefone = telefone;
    }

    public static String getEmail() {
        return email;
    }

    public static String getEndereco() {
        return endereco;
    }

    public static String getNome() {
        return nome;
    }

    public static String getTelefone() {
        return telefone;
    }
}
