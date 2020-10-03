package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.enums.TelefoneEnum;
import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Telefone;

public class TelefoneDao {
    private SQLiteHelper sqHelper;

    public TelefoneDao(Context context) {
        sqHelper = new SQLiteHelper(context);
    }

    public void add(Telefone telefone) throws SQLException {
        if (telefone == null) throw new NullPointerException();

        SQLiteDatabase db = sqHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TelefoneContratoDao.TelefoneEntry.COLUNA_NUMERO, telefone.getNumero());
        values.put(TelefoneContratoDao.TelefoneEntry.COLUNA_FLAG, telefone.getTelefoneEnum().toString());
        values.put(TelefoneContratoDao.TelefoneEntry.COLUNA_ID_CONTATO, telefone.getContato().getId());

        if ( db.insert(TelefoneContratoDao.TelefoneEntry.NOME_TABELA, null, values) == - 1 ) {
            throw new SQLiteConstraintException("Número já cadastrado.");
        }

        db.close();
    }

    public List<Telefone> listAll(Contato contato) {
        List<Telefone> telefones = new ArrayList<>();
        Telefone telefone;

        SQLiteDatabase db = sqHelper.getReadableDatabase();

        String colunas[] = new String[]{
                BaseColumns._ID,
                TelefoneContratoDao.TelefoneEntry.COLUNA_NUMERO,
                TelefoneContratoDao.TelefoneEntry.COLUNA_FLAG
        };

        String where = TelefoneContratoDao.TelefoneEntry.COLUNA_ID_CONTATO + " = ?";
        String[] argumentos = {String.valueOf(contato.getId())};
        String orderBy = TelefoneContratoDao.TelefoneEntry.COLUNA_NUMERO + " ASC";

        Cursor cursor = db.query(
                TelefoneContratoDao.TelefoneEntry.NOME_TABELA,
                colunas,
                where,
                argumentos,
                null,
                null,
                orderBy
        );

        while (cursor.moveToNext()) {
            telefone = new Telefone(
                    cursor.getInt(0)
                    , cursor.getString(1)
                    , TelefoneEnum.valueOf(cursor.getString(2))
                    , contato
            );
            contato.insereTelefone(telefone);
        }

        cursor.close();

        db.close();

        return telefones;
    }
}
