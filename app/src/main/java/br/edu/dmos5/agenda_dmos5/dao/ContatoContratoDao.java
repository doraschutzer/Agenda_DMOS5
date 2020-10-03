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
        public static final String COLUNA_ID_USUARIO = "id_usuario";
        public static final String NOME_TABELA_TEMPORARIA = "temporaria";
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + ContatoEntry.NOME_TABELA + "("
                    + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                    + ContatoEntry.COLUNA_NOME + " TEXT NOT NULL,"
                    + ContatoEntry.COLUNA_TELEFONE + " TEXT,"
                    + ContatoEntry.COLUNA_CELULAR + " TEXT NOT NULL );";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + ContatoEntry.NOME_TABELA;

    public static final String ALTER_TABLE_ADD_USUARIO_ID =
            "ALTER TABLE " + ContatoEntry.NOME_TABELA
                    + " ADD " + ContatoEntry.COLUNA_ID_USUARIO + " INTEGER REFERENCES "
                    + UsuarioContratoDao.UsuarioEntry.NOME_TABELA + "(_id);";

    public static final String UPDATE_SET_USUARIO_ID_1 =
            "UPDATE " + ContatoEntry.NOME_TABELA + " SET " + ContatoEntry.COLUNA_ID_USUARIO + " = 1;";

    public static final String ALTER_TABLE_RENAME =
            "ALTER TABLE " + ContatoEntry.NOME_TABELA + " RENAME TO " + ContatoEntry.NOME_TABELA_TEMPORARIA;

    public static final String DELETE_OLD_TABLE = "DROP TABLE " + ContatoEntry.NOME_TABELA_TEMPORARIA;

    public static final String CREATE_NEW_TABLE =
            "CREATE TABLE " + ContatoEntry.NOME_TABELA + "("
                    + BaseColumns._ID + " INTEGER PRIMARY KEY, "
                    + ContatoEntry.COLUNA_NOME + " TEXT NOT NULL, "
                    + ContatoEntry.COLUNA_ID_USUARIO + " INTEGER NOT NULL,"
                    + " FOREIGN KEY (" + ContatoEntry.COLUNA_ID_USUARIO + ") "
                    + " REFERENCES " + UsuarioContratoDao.UsuarioEntry.NOME_TABELA
                    + " (" + UsuarioContratoDao.UsuarioEntry._ID + "));";

    public static final String INSERT_OLD_NEW =
            "INSERT INTO " + ContatoEntry.NOME_TABELA
                    + " (" + ContatoEntry._ID
                    + ", " + ContatoEntry.COLUNA_NOME
                    + ", " + ContatoEntry.COLUNA_ID_USUARIO + ")"
                    + " SELECT OLD." + ContatoEntry._ID
                    + ", OLD." + ContatoEntry.COLUNA_NOME
                    + ", OLD." + ContatoEntry.COLUNA_ID_USUARIO
                    + " FROM " + ContatoEntry.NOME_TABELA_TEMPORARIA + " OLD"
                    + " WHERE " + ContatoEntry.COLUNA_ID_USUARIO  + " IS NOT NULL";
}
