package br.edu.dmos5.agenda_dmos5.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "contato.db";

    private Context context;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContatoContratoDao.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(UsuarioContratoDao.CREATE_TABLE);
                db.execSQL(UsuarioContratoDao.INSERT_PRIMEIRO_USUARIO);
                db.execSQL(ContatoContratoDao.ALTER_TABLE_ADD_USUARIO_ID);
                db.execSQL(ContatoContratoDao.UPDATE_SET_USUARIO_ID_1);
                break;
            case 2:
                db.execSQL(ContatoContratoDao.ALTER_TABLE_RENAME);
                db.execSQL(ContatoContratoDao.CREATE_NEW_TABLE);
                db.execSQL(ContatoContratoDao.INSERT_OLD_NEW);
                db.execSQL(TelefoneContratoDao.CREATE_TABLE);
                db.execSQL(EmailContratoDao.CREATE_TABLE);
                db.execSQL(TelefoneContratoDao.INSERT_TEL_FIXOS);
                db.execSQL(TelefoneContratoDao.INSERT_TEL_CELULARES);
                db.execSQL(ContatoContratoDao.DELETE_OLD_TABLE);
                break;
        }
    }

}
