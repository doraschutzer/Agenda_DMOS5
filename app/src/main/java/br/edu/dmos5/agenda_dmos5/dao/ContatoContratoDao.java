package br.edu.dmos5.agenda_dmos5.dao;

import android.provider.BaseColumns;

public class ContatoContratoDao {
    private ContatoContratoDao() {
    }

    public static class ContatoEntry implements BaseColumns {
        public static final String NOME_TABELA = "contato";
        public static final String COLUNA_NOME = "nome";
        public static final String COLUNA_TELEFONE = "telefone";
        public static final String COLUNA_CELULAR = "celular";
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + ContatoEntry.NOME_TABELA + "("
                    + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                    + ContatoEntry.COLUNA_NOME + " TEXT NOT NULL,"
                    + ContatoEntry.COLUNA_TELEFONE + " TEXT,"
                    + ContatoEntry.COLUNA_CELULAR + " TEXT NOT NULL );";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + ContatoEntry.NOME_TABELA;
}
