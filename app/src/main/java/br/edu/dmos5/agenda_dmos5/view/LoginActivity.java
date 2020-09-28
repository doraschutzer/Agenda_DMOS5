package br.edu.dmos5.agenda_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.dao.UsuarioDao;
import br.edu.dmos5.agenda_dmos5.helper.ShowMessageScreenHelper;
import br.edu.dmos5.agenda_dmos5.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private UsuarioDao usuarioDao;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private EditText editUsuario;
    private EditText editSenha;
    private Button loginButton;
    private Button cadastroButton;
    private CheckBox checkLembrar;

    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioDao = new UsuarioDao(getApplicationContext());

        sharedPreferences = this.getSharedPreferences(
                Constantes.KEY_FILE_USUARIO_SHARED_PREFERENCE
                , MODE_PRIVATE
        );
        editor = sharedPreferences.edit();

        constraintLayout = findViewById(R.id.layout_login);
        editUsuario = findViewById(R.id.edit_usuario);
        editSenha = findViewById(R.id.edit_senha);
        loginButton = findViewById(R.id.button_login);
        cadastroButton = findViewById(R.id.button_cadastro);
        checkLembrar = findViewById(R.id.check_lembrar_usuario);

        loginButton.setOnClickListener(this::logar);
        cadastroButton.setOnClickListener(this::cadastrar);
    }

    private void logar(View view) {
        Usuario usuario = validarLogin();
        if (usuario != null) {
            Usuario.login(usuario.getNome(), usuario.getLogin(), usuario.getSenha(), usuario.getId());
            addPreferencias();
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    private void cadastrar(View view) {
        Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
        startActivity(intent);
    }

    private Usuario validarLogin() {
        String login = editUsuario.getText().toString();
        String password = editSenha.getText().toString();
        Usuario usuario = usuarioDao.find(login);
        if (usuario == null) {
            ShowMessageScreenHelper.showSnackbar(getString(R.string.login_usuario_error), constraintLayout);
            return null;
        } else if (!password.equals(usuario.getSenha())) {
            ShowMessageScreenHelper.showSnackbar(getString(R.string.senha_invalida_error), constraintLayout);
            return null;
        }
        return usuario;
    }

    private void addPreferencias() {
        String login = editUsuario.getText().toString();
        String senha = editSenha.getText().toString();

        editor.putString(Constantes.KEY_USUARIO_JSON, "");
        editor.commit();

        JSONObject jsonObject = new JSONObject();
        if ( usuarioDao.find(login) != null && checkLembrar.isChecked() ) {
            try {
                jsonObject.put(Constantes.KEY_LEMBRAR, true);
                jsonObject.put(Constantes.KEY_USUARIO, login);
                jsonObject.put(Constantes.KEY_SENHA, senha);
            } catch (JSONException e) {
                ShowMessageScreenHelper.showToast(getString(R.string.preferences_usuario_error), getApplicationContext());
            }
            editor.putString(Constantes.KEY_USUARIO_JSON, jsonObject.toString());
            editor.commit();
        }
    }
}