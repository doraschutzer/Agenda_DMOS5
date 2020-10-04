package br.edu.dmos5.agenda_dmos5.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Contato {
    private Integer id;
    private String nome;
    private String telefone;
    private String celular;
    private Usuario usuario;
    private List<Telefone> telefones;
    private List<Email> emails;
    private boolean favorito = false;

    public Contato(Integer id, String nome, Usuario usuario, boolean favorito) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
        this.usuario = usuario;
        this.favorito = favorito;
        telefones = new ArrayList<Telefone>(25);
        emails = new ArrayList<Email>(25);
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

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Usuario getUsuario() { return usuario; }

    public void insereTelefone(Telefone telefone) {
        telefones.add(telefone);
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void insereEmail(Email email) {
        emails.add(email);
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public boolean getFavorito() {
        return favorito;
    }

    @NonNull
    @Override
    public String toString() {
        return getNome();
    }
}
