package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.model.Usuario;

public class UsuarioDao {
    private Context context;

    public UsuarioDao(Context context) {
        this.context = context;
    }

    public void add(Usuario usuario) {
        if (usuario == null) throw new NullPointerException();

        SQLiteHelper dbHelper = new SQLiteHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsuarioContratoDao.ContatoEntry.COLUNA_NOME, usuario.getNome());
        values.put(UsuarioContratoDao.ContatoEntry.COLUNA_LOGIN, usuario.getLogin());
        values.put(UsuarioContratoDao.ContatoEntry.COLUNA_SENHA, usuario.getSenha());

        db.insert(UsuarioContratoDao.ContatoEntry.NOME_TABELA, null, values);
        db.close();
    }

    public List<Usuario> listAll() {
        List<Usuario> usuarios = new ArrayList<>();
        SQLiteHelper dbHelper = new SQLiteHelper(this.context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String colunas[] = new String[]{
                BaseColumns._ID,
                UsuarioContratoDao.ContatoEntry.COLUNA_NOME,
                UsuarioContratoDao.ContatoEntry.COLUNA_LOGIN,
                UsuarioContratoDao.ContatoEntry.COLUNA_SENHA
        };

        String orderBy = ContatoContratoDao.ContatoEntry.COLUNA_ID_USUARIO + " ASC";
        Cursor cursor = db.query(
                UsuarioContratoDao.ContatoEntry.NOME_TABELA,
                colunas,
                null,
                null,
                null,
                null,
                orderBy
        );

        Usuario usuario;

        while (cursor.moveToNext()) {
            usuario = new Usuario(
                    cursor.getString(1), cursor.getString(2), cursor.getString(3)
            );
            usuario.setId(cursor.getInt(0));
            usuarios.add(usuario);
        }
        cursor.close();
        db.close();
        return usuarios;
    }

    public Usuario find(String arg) {
        Usuario usuario = null;
        SQLiteHelper dbHelper = new SQLiteHelper(this.context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String columns[] = new String[]{
                BaseColumns._ID,
                UsuarioContratoDao.ContatoEntry.COLUNA_NOME,
                UsuarioContratoDao.ContatoEntry.COLUNA_LOGIN,
                UsuarioContratoDao.ContatoEntry.COLUNA_SENHA
        };

        String where = UsuarioContratoDao.ContatoEntry.COLUNA_LOGIN + " = ?";
        String[] argumentos = {arg};

        Cursor cursor = db.query(
                UsuarioContratoDao.ContatoEntry.NOME_TABELA,
                columns,
                where,
                argumentos,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            usuario = new Usuario(
                    cursor.getString(1), cursor.getString(2), cursor.getString(3)
            );
            usuario.setId(cursor.getInt(0));
        }
        cursor.close();
        db.close();
        return usuario;
    }
}
