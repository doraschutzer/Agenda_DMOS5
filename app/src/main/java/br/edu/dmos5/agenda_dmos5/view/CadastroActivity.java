package br.edu.dmos5.agenda_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.UsuarioDao;
import br.edu.dmos5.agenda_dmos5.helper.ShowMessageScreenHelper;
import br.edu.dmos5.agenda_dmos5.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private UsuarioDao usuarioDao;

    private ConstraintLayout constraintLayout;
    private EditText editNome;
    private EditText editUsuario;
    private EditText editSenha;
    private EditText editConfirmarSenha;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        usuarioDao = new UsuarioDao(getApplicationContext());

        constraintLayout = findViewById(R.id.layout_cadastro);
        editNome = findViewById(R.id.edit_usuario);
        editUsuario = findViewById(R.id.edit_usuario);
        editSenha = findViewById(R.id.edit_senha);

        btnSalvar = findViewById(R.id.button_salvar);
        btnSalvar.setOnClickListener(this::salvarUsuario);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void salvarUsuario(View view) {
        Usuario usuario = validateParaSalvar();
        if (usuario != null) {
            usuarioDao.add(usuario);
            finish();
        }
    }

    private Usuario validateParaSalvar() {
        String nome = editNome.getText().toString();
        String login = editUsuario.getText().toString();
        String senha = editSenha.getText().toString();

        Usuario user = usuarioDao.find(login);

        if (user != null) {
            mensagemErro(getString(R.string.usuario_existe));
            return null;
        } else if (senha.length() < 6 ) {
            mensagemErro(getString(R.string.senha_error));
            return null;
        } else if ( login.length() < 6 ) {
            mensagemErro(getString(R.string.usuario_error));
            return null;
        }
        return new Usuario(nome, login, senha);
    }

    private void mensagemErro(String mensagem) {
        ShowMessageScreenHelper.showSnackbar(mensagem, constraintLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}