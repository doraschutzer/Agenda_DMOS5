package br.edu.dmos5.agenda_dmos5.dao;

import android.provider.BaseColumns;

public class EmailContratoDao {
    private EmailContratoDao() {
    }

    public static class EmailEntry implements BaseColumns {
        public static final String NOME_TABELA = "email";
        public static final String COLUNA_DOMINIO = "dominio";
        public static final String COLUNA_ID_CONTATO = "id_contato";
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + EmailEntry.NOME_TABELA
            + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY, "
            + EmailEntry.COLUNA_DOMINIO + " TEXT NOT NULL, "
            + EmailEntry.COLUNA_ID_CONTATO + " INTEGER, "
            + "UNIQUE (" + EmailEntry.COLUNA_ID_CONTATO + ", "
            + EmailEntry.COLUNA_DOMINIO + "), "
            + "FOREIGN KEY (" + EmailEntry.COLUNA_ID_CONTATO + ") "
            + "REFERENCES " + ContatoContratoDao.ContatoEntry.NOME_TABELA
            + " (" + ContatoContratoDao.ContatoEntry._ID + "));";
}
