package br.edu.dmos5.agenda_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.model.Contato;

public class DetalheContatoActivity extends AppCompatActivity {

    private TextView nomeTextView;
    private TextView telefoneTextView;
    private TextView celularTextView;

    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_contato);

        nomeTextView = findViewById(R.id.textview_nome);
        telefoneTextView = findViewById(R.id.textview_telefone);
        celularTextView = findViewById(R.id.textview_celular);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extrairArgumentos();
        exibeDados();
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void extrairArgumentos(){
        Intent intent = getIntent();
        Bundle embrulho = intent.getExtras();

        if(embrulho != null){
            String nome     = embrulho.getString(ContatosActivity.KEY_NOME);
            String telefone = embrulho.getString(ContatosActivity.KEY_TELEFONE);
            String celular  = embrulho.getString(ContatosActivity.KEY_CELULAR);

            contato = new Contato(nome,telefone,celular);
        }
    }

    private void exibeDados(){
        nomeTextView.setText(contato.getNome());
        telefoneTextView.setText(contato.getTelefone());
        celularTextView.setText(contato.getCelular());
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