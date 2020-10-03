package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Usuario;

import java.util.ArrayList;
import java.util.List;

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
        values.put(ContatoContratoDao.ContatoEntry.COLUNA_ID_USUARIO, Usuario.getUsuarioLogado().getId());

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
                ContatoContratoDao.ContatoEntry._ID,
                ContatoContratoDao.ContatoEntry.COLUNA_NOME,
                ContatoContratoDao.ContatoEntry.COLUNA_ID_USUARIO
        };

        String orderBy = ContatoContratoDao.ContatoEntry.COLUNA_NOME + " ASC";
        String where = ContatoContratoDao.ContatoEntry.COLUNA_ID_USUARIO + " = ?";
        String[] argumentos = {String.valueOf(Usuario.getUsuarioLogado().getId())};

        cursor = db.query(
                ContatoContratoDao.ContatoEntry.NOME_TABELA,
                colunas,
                where,
                argumentos,
                null,
                null,
                orderBy
        );

        while (cursor.moveToNext()){
            contato = new Contato(
                    cursor.getInt(0),
                    cursor.getString(1),
                    Usuario.getUsuarioLogado()
            );
            contatos.add(contato);
        }
        cursor.close();
        db.close();
        return contatos;
    }
}
