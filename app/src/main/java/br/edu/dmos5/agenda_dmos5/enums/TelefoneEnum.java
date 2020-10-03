package br.edu.dmos5.agenda_dmos5.enums;

public enum TelefoneEnum {
    fixo("fixo"),
    celular("celular");

    private String tipo;

    private TelefoneEnum(String phoneType) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
