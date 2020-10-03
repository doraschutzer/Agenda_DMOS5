package br.edu.dmos5.agenda_dmos5.dao;

import android.provider.BaseColumns;

public class UsuarioContratoDao {
    private UsuarioContratoDao() {}

    public static class UsuarioEntry implements BaseColumns {
        public static final String NOME_TABELA = "usuario";
        public static final String COLUNA_NOME = "nome";
        public static final String COLUNA_LOGIN = "login";
        public static final String COLUNA_SENHA = "senha";
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + UsuarioEntry.NOME_TABELA + "("
            + BaseColumns._ID + " INTEGER PRIMARY KEY,"
            + UsuarioEntry.COLUNA_NOME + " TEXT NOT NULL,"
            + UsuarioEntry.COLUNA_LOGIN + " TEXT NOT NULL UNIQUE,"
            + UsuarioEntry.COLUNA_SENHA + " TEXT NOT NULL );";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + UsuarioEntry.COLUNA_NOME + ";";

    public static final String INSERT_PRIMEIRO_USUARIO =
            "INSERT INTO " + UsuarioEntry.NOME_TABELA + " ("
            + UsuarioEntry.COLUNA_NOME
            + "," + UsuarioEntry.COLUNA_LOGIN
            + "," + UsuarioEntry.COLUNA_SENHA
            + ") VALUES ('Isadora Schutzer', 'ischutzer', 'teste123');";
}
