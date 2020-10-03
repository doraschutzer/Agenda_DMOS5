package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Email;

public class EmailDao {
    private SQLiteHelper dbHelper;

    public EmailDao(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void add(Email email) throws SQLException {
        if (email == null) throw new NullPointerException();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EmailContratoDao.EmailEntry.COLUNA_DOMINIO, email.getDominio());
        values.put(EmailContratoDao.EmailEntry.COLUNA_ID_CONTATO, email.getContato().getId());

        long success = db.insert(EmailContratoDao.EmailEntry.NOME_TABELA, null, values);

        if (success == -1) {
            throw new SQLiteConstraintException("Email já cadastrado para o contato.");
        }

        db.close();
    }

    public List<Email> listAll(Contato contact) {

        List<Email> emails = new ArrayList<>();
        Email email;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String colunas[] = new String[]{
                BaseColumns._ID,
                EmailContratoDao.EmailEntry.COLUNA_DOMINIO,
                EmailContratoDao.EmailEntry.COLUNA_ID_CONTATO
        };

        String where = EmailContratoDao.EmailEntry.COLUNA_ID_CONTATO + " = ?";
        String[] argumentos = {String.valueOf(contact.getId())};
        String orderrBy = EmailContratoDao.EmailEntry.COLUNA_DOMINIO + " ASC";

        Cursor cursor = db.query(
                EmailContratoDao.EmailEntry.NOME_TABELA,
                colunas,
                where,
                argumentos,
                null,
                null,
                orderrBy
        );

        while (cursor.moveToNext()) {
            email = new Email(
                    cursor.getInt(0)
                    , cursor.getString(1)
                    , contact
            );
            contact.insereEmail(email);
        }

        cursor.close();

        db.close();

        return emails;
    }
}
