package br.edu.dmos5.agenda_dmos5.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.dao.EmailDao;
import br.edu.dmos5.agenda_dmos5.dao.TelefoneDao;
import br.edu.dmos5.agenda_dmos5.enums.TelefoneEnum;
import br.edu.dmos5.agenda_dmos5.helper.ShowMessageScreenHelper;
import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Email;
import br.edu.dmos5.agenda_dmos5.model.Telefone;
import br.edu.dmos5.agenda_dmos5.model.Usuario;

public class DetalheContatoActivity extends AppCompatActivity {

    private TextView nomeTextView;
    private TextView telefoneTextView;
    private TextView celularTextView;
    private FloatingActionButton buttonAdicionaEmail;
    private FloatingActionButton buttonAdicionaTelefone;
    private FloatingActionButton buttonEditarContato;
    private FloatingActionButton buttonRemoveContato;
    private Switch switchFavoritar;

    private RecyclerView telefoneRecyclerView;
    private RecyclerView emailRecyclerView;
    private ItemTelefoneAdapter telefoneAdapter;
    private ItemEmailAdapter emailAdapter;
    private RecyclerView.LayoutManager telefoneLayout;
    private RecyclerView.LayoutManager emailLayout;
    private AlertDialog dialog;

    private Contato contato;
    private TelefoneDao telefoneDao;
    private EmailDao emailDao;
    private ContatoDao contatoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_contato);

        nomeTextView = findViewById(R.id.textview_nome);
        emailRecyclerView = findViewById(R.id.recycler_lista_email);
        telefoneRecyclerView = findViewById(R.id.recycler_lista_telefone);
        buttonAdicionaEmail = findViewById(R.id.button_add_email);
        buttonAdicionaTelefone = findViewById(R.id.button_add_phone);
        buttonEditarContato = findViewById(R.id.button_editar_contato);
        buttonRemoveContato = findViewById(R.id.button_remover_contato);
        switchFavoritar = findViewById(R.id.switch_favoritar);

        Usuario usuario;

        try {
            usuario = Usuario.getUsuarioLogado();
            telefoneDao = new TelefoneDao(getApplicationContext());
            emailDao = new EmailDao(getApplicationContext());
            contatoDao = new ContatoDao(getApplicationContext());
        } catch (Exception error) {
            ShowMessageScreenHelper.showToast(error.getMessage(), getApplicationContext());
            finish();
        }

        extrairArgumentos();
        exibeDados();
        verificarFavorito();

        emailLayout = new LinearLayoutManager(getApplicationContext());
        emailRecyclerView.setLayoutManager(emailLayout);
        emailAdapter = new ItemEmailAdapter(contato.getEmails());
        emailRecyclerView.setAdapter(emailAdapter);

        telefoneLayout = new LinearLayoutManager(getApplicationContext());
        telefoneRecyclerView.setLayoutManager(telefoneLayout);
        telefoneAdapter = new ItemTelefoneAdapter(contato.getTelefones());
        telefoneRecyclerView.setAdapter(telefoneAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        switchFavoritar.setOnCheckedChangeListener(this::favoritar);
        buttonEditarContato.setOnClickListener(this::editarContato);
        buttonRemoveContato.setOnClickListener(this::removerContato);

        buttonAdicionaEmail.setOnClickListener(v -> {
            LayoutInflater layoutInflater = getLayoutInflater();

            View view = layoutInflater.inflate(R.layout.dialog_add_email, null);

            Button btnSaveEmail = view.findViewById(R.id.button_salvar_email);
            EditText editEmail = view.findViewById(R.id.edit_email);

            btnSaveEmail.setOnClickListener(vv -> {
                String email = editEmail.getText().toString();
                inserirEmail(email);
                dialog.dismiss();
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.salvar);
            builder.setView(view);
            dialog = builder.create();
            dialog.show();
        });

        buttonAdicionaTelefone.setOnClickListener(v -> {
            LayoutInflater layoutInflater = getLayoutInflater();

            View view = layoutInflater.inflate(
                    R.layout.dialog_add_telefone
                    , null
            );

            Button buttonSalvarTel = view.findViewById(R.id.button_salvar_telefone);
            EditText editNumero = view.findViewById(R.id.edit_numero);
            RadioGroup radio = view.findViewById(R.id.radio_grupo);

            buttonSalvarTel.setOnClickListener(vv -> {
                int radioTipo = radio.getCheckedRadioButtonId();
                String numero = editNumero.getText().toString();
                switch (radioTipo) {
                    case R.id.radio_celular:
                        inserirTelefone(TelefoneEnum.celular, numero);
                        break;
                    case R.id.radio_fixo:
                        inserirTelefone(TelefoneEnum.fixo, numero);
                        break;
                }
                dialog.dismiss();
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.salvar);
            builder.setView(view);
            dialog = builder.create();
            dialog.show();
        });
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void removerContato(View v) {
        contatoDao.remover(contato);
        finish();
    }

    private void favoritar(CompoundButton buttonView, boolean isChecked) {
        if (isChecked != true) {
            contato.setFavorito(false);
        } else {
            contato.setFavorito(true);
        }
        contatoDao.atualizar(contato);
    }

    public void verificarFavorito() {
        if ( !contato.getFavorito() ) switchFavoritar.setChecked(false);
        else switchFavoritar.setChecked(true);
    }

    private void editarContato(View v) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_edit_contato, null);

        Button buttonSalvarEdicao = view.findViewById(R.id.button_salvar_edicao);
        EditText editNovoNome = view.findViewById(R.id.edit_novo_nome);

        buttonSalvarEdicao.setOnClickListener(vv -> {
            String nome = editNovoNome.getText().toString();
            if (!nome.isEmpty()) {
                contato.setNome(nome);
                contatoDao.atualizar(contato);
                nomeTextView.setText(nome);
            } else {
                ShowMessageScreenHelper.showToast(getString(R.string.edit_nome_error),getApplicationContext());
            }
            dialog.dismiss();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.editar_nome_contato);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void extrairArgumentos() {
        Intent intent = getIntent();
        Bundle embrulho = intent.getExtras();

        if (embrulho != null) {
            Integer id = embrulho.getInt(Constantes.ATTR_ID);
            String nome = embrulho.getString(Constantes.ATTR_NOME);
            boolean favorito = embrulho.getBoolean(Constantes.ATTR_FAVORITO);
            contato = new Contato(id, nome, Usuario.getUsuarioLogado(), favorito);
            telefoneDao.listAll(contato);
            emailDao.listAll(contato);
        }
    }

    private void exibeDados() {
        if (contato != null) {
            nomeTextView.setText(contato.getNome());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void inserirEmail(String dominio) {
        if (contato != null && !dominio.isEmpty()) {
            try {
                Email email = new Email(null, dominio, contato);
                email.setContato(contato);
                emailDao.add(email);
                contato.insereEmail(email);
                emailRecyclerView.getAdapter().notifyDataSetChanged();
            } catch (SQLiteConstraintException e) {
                ShowMessageScreenHelper.showToast(getString(R.string.email_error), getApplicationContext());
            }
        } else {
            ShowMessageScreenHelper.showToast(getString(R.string.add_email_error), getApplicationContext());
        }
    }

    public void inserirTelefone(TelefoneEnum telefoneEnum, String numero) {
        if (contato != null && !numero.isEmpty()) {
            try {
                Telefone telefone = new Telefone(null, numero, telefoneEnum, contato);
                telefone.setContato(contato);
                telefoneDao.add(telefone);
                contato.insereTelefone(telefone);
                telefoneRecyclerView.getAdapter().notifyDataSetChanged();
            } catch (SQLiteConstraintException e) {
                ShowMessageScreenHelper.showToast(getString(R.string.telefone_error), getApplicationContext()
                );
            }
        } else {
            ShowMessageScreenHelper.showToast(getString(R.string.add_telefone_error), getApplicationContext());
        }
    }
}