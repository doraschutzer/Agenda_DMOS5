package br.edu.dmos5.agenda_dmos5.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.helper.ShowMessageScreenHelper;
import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Usuario;

public class NovoContatoActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private EditText nomeEditText;
    private EditText telefoneEditText;
    private EditText celularEditText;
    private Button salvarButton;

    private ContatoDao contatoDao;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);

        nomeEditText = findViewById(R.id.edittext_nome);
        salvarButton = findViewById(R.id.button_salvar_contato);
        salvarButton.setOnClickListener(this::salvarContato);
        constraintLayout = findViewById(R.id.layout_novo_contato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finalizar(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarContato(View view){
        String nome, telefone, celular;
        nome = nomeEditText.getText().toString();

        if( nome.isEmpty() ){
            ShowMessageScreenHelper.showSnackbar(
                    getString(R.string.erro_empty_dados)
                    , constraintLayout
            );
        }else{
            contatoDao = new ContatoDao(getApplicationContext());
            try {
                contatoDao.add(new Contato(null, nome, Usuario.getUsuarioLogado(), false));
                finalizar(true);
            } catch (NullPointerException e){
                showSnackbar(getString(R.string.erro_null_contato));
            } catch (Exception e) {
                showSnackbar("Erro ao inserir contato!");
            }
        }
    }

    private void showSnackbar(String mensagem){
        Snackbar snackbar;
        ConstraintLayout constraintLayout = findViewById(R.id.layout_novo_contato);
        snackbar = Snackbar.make(constraintLayout, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void finalizar(boolean sucesso){
        if(sucesso){
            setResult(Activity.RESULT_OK);
        }else{
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }
}