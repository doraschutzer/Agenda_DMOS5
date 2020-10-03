package br.edu.dmos5.agenda_dmos5.dao;

import android.provider.BaseColumns;

public class TelefoneContratoDao {

    private TelefoneContratoDao() {
    }

    public static class TelefoneEntry implements BaseColumns {
        public static final String NOME_TABELA = "telefone";
        public static final String COLUNA_NUMERO = "numero";
        public static final String COLUNA_FLAG = "flag_tipo";
        public static final String COLUNA_ID_CONTATO = "id_contato";
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TelefoneEntry.NOME_TABELA + " ("
                    + BaseColumns._ID + " INTEGER PRIMARY KEY, "
                    + TelefoneEntry.COLUNA_NUMERO + " TEXT NOT NULL, "
                    + TelefoneEntry.COLUNA_FLAG + " TEXT NOT NULL, "
                    + TelefoneEntry.COLUNA_ID_CONTATO + " INTEGER, "
                    + "UNIQUE (" + TelefoneEntry.COLUNA_ID_CONTATO + ", "
                    + TelefoneEntry.COLUNA_NUMERO + "), "
                    + "FOREIGN KEY (" + TelefoneEntry.COLUNA_ID_CONTATO + ") "
                    + "REFERENCES " + ContatoContratoDao.ContatoEntry.NOME_TABELA
                    + " (" + ContatoContratoDao.ContatoEntry._ID + "));";

    public static final String INSERT_TEL_CELULARES =
            "INSERT INTO telefone (" + TelefoneEntry.COLUNA_NUMERO + ", " +
            TelefoneEntry.COLUNA_FLAG + ", " +
            TelefoneEntry.COLUNA_ID_CONTATO + ") " +
            "SELECT DISTINCT temp." + ContatoContratoDao.ContatoEntry.COLUNA_CELULAR +
            ", 'celular'" + ", temp." + ContatoContratoDao.ContatoEntry._ID +
            " FROM " + ContatoContratoDao.ContatoEntry.NOME_TABELA_TEMPORARIA + " temp " +
            "WHERE temp." + ContatoContratoDao.ContatoEntry.COLUNA_CELULAR + " != " +
                    "temp." + ContatoContratoDao.ContatoEntry.COLUNA_TELEFONE + " IS NOT NULL;";

    public static final String INSERT_TEL_FIXOS =
            "INSERT INTO telefone (" + TelefoneEntry.COLUNA_NUMERO + ", " +
            TelefoneEntry.COLUNA_FLAG + ", " +
            TelefoneEntry.COLUNA_ID_CONTATO + ") " +
            "SELECT DISTINCT temp." + ContatoContratoDao.ContatoEntry.COLUNA_TELEFONE +
            ", 'fixo'" + ", temp." + ContatoContratoDao.ContatoEntry._ID +
            " FROM " + ContatoContratoDao.ContatoEntry.NOME_TABELA_TEMPORARIA + " temp " +
            "WHERE temp." + ContatoContratoDao.ContatoEntry.COLUNA_TELEFONE + " != " +
            "temp." + ContatoContratoDao.ContatoEntry.COLUNA_CELULAR + " IS NOT NULL;";
}
