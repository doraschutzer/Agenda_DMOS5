package br.edu.dmos5.agenda_dmos5.model;

import br.edu.dmos5.agenda_dmos5.enums.TelefoneEnum;

public class Telefone {
    private Integer id;
    private String numero;
    private TelefoneEnum telefoneEnum;
    private Contato contato;

    public Telefone(Integer id, String numero, TelefoneEnum telefoneEnum, Contato contato) {
        this.id = id;
        this.numero = numero;
        this.telefoneEnum = telefoneEnum;
        this.contato = contato;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setTelefoneEnum(TelefoneEnum telefoneEnum) {
        this.telefoneEnum = telefoneEnum;
    }

    public TelefoneEnum getTelefoneEnum() {
        return telefoneEnum;
    }

    public void setContato(Contato contato) { this.contato = contato; }

    public Contato getContato() {
        return contato;
    }
}
