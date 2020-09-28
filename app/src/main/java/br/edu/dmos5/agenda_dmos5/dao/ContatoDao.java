package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.model.Contato;

public class ContatoDao {
    private Context context;

    public ContatoDao(Context context) {
        this.context = context;
    }

    public void add(Contato contato) {
        if (contato == null) throw new NullPointerException();

        SQLiteHelper dbHelper = new SQLiteHelper(this.context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContatoContratoDao.ContatoEntry.COLUNA_NOME, contato.getNome());
        values.put(ContatoContratoDao.ContatoEntry.COLUNA_TELEFONE, contato.getTelefone());
        values.put(ContatoContratoDao.ContatoEntry.COLUNA_CELULAR, contato.getCelular());

        db.insert(ContatoContratoDao.ContatoEntry.NOME_TABELA, null, values);

        db.close();
    }

    public List<Contato> recuperateAll(){
        List<Contato> contatos;
        Contato contato;
        Cursor cursor;

        contatos = new ArrayList<Contato>();
        SQLiteHelper dbHelper = new SQLiteHelper(this.context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String colunas[] = new String[]{
                BaseColumns._ID,
                ContatoContratoDao.ContatoEntry.COLUNA_NOME,
                ContatoContratoDao.ContatoEntry.COLUNA_TELEFONE,
                ContatoContratoDao.ContatoEntry.COLUNA_CELULAR
        };
        String orderBy = ContatoContratoDao.ContatoEntry.COLUNA_NOME + " ASC";
        cursor = db.query(
                ContatoContratoDao.ContatoEntry.NOME_TABELA,
                colunas,
                null,
                null,
                null,
                null,
                orderBy
        );

        while (cursor.moveToNext()){
            contato = new Contato(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            contatos.add(contato);
        }
        cursor.close();
        db.close();
        return contatos;
    }
}
