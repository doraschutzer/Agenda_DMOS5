package br.edu.dmos5.agenda_dmos5.dao;

import android.provider.BaseColumns;

public class UsuarioContratoDao {
    private UsuarioContratoDao() {}

    public static class ContatoEntry implements BaseColumns {
        public static final String NOME_TABELA = "usuario";
        public static final String COLUNA_NOME = "nome";
        public static final String COLUNA_LOGIN = "login";
        public static final String COLUNA_SENHA = "senha";
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + ContatoEntry.NOME_TABELA + "("
            + BaseColumns._ID + " INTEGER PRIMARY KEY,"
            + ContatoEntry.COLUNA_NOME + " TEXT NOT NULL,"
            + ContatoEntry.COLUNA_LOGIN + " TEXT NOT NULL UNIQUE,"
            + ContatoEntry.COLUNA_SENHA + " TEXT NOT NULL );";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + ContatoEntry.COLUNA_NOME + ";";

    public static final String INSERT_PRIMEIRO_USUARIO =
            "INSERT INTO " + ContatoEntry.NOME_TABELA + " ("
            + ContatoEntry.COLUNA_NOME
            + "," + ContatoEntry.COLUNA_LOGIN
            + "," + ContatoEntry.COLUNA_SENHA
            + ") VALUES ('Isadora Schutzer', 'ischutzer', 'teste123');";
}
