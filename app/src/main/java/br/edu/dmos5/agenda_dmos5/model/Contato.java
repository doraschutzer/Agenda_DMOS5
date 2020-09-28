package br.edu.dmos5.agenda_dmos5.model;

import androidx.annotation.NonNull;

public class Contato {
    private Integer id;
    private String nome;
    private String telefone;
    private String celular;

    public Contato(Integer id, String nome, String telefone, String celular) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
    }

    public Contato(String nome, String telefone, String celular) {
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @NonNull
    @Override
    public String toString() {
        return getNome();
    }
}
