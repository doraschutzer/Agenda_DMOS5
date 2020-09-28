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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.model.Contato;

public class NovoContatoActivity extends AppCompatActivity {

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
        telefoneEditText = findViewById(R.id.edittext_telefone);
        celularEditText = findViewById(R.id.edittext_celular);
        salvarButton = findViewById(R.id.button_salvar_contato);
        salvarButton.setOnClickListener(this::salvarContato);

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
        telefone = telefoneEditText.getText().toString();
        celular = celularEditText.getText().toString();

        if(nome.isEmpty() && (telefone.isEmpty() || celular.isEmpty())){
            showSnackbar(getString(R.string.erro_empty_dados));
        }else{
            contatoDao = new ContatoDao(getApplicationContext());
            try {
                contatoDao.add(new Contato(nome, telefone, celular));
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