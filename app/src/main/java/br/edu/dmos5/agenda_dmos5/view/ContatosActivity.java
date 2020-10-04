package br.edu.dmos5.agenda_dmos5.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.constantes.Constantes;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.dao.UsuarioDao;
import br.edu.dmos5.agenda_dmos5.helper.ShowMessageScreenHelper;
import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Usuario;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class ContatosActivity extends AppCompatActivity {

    // Contém os dados dos contatos que serão apresentados
    private List<Contato> contatos;

    protected static final int REQUESTCODE_USUARIO_LOGIN = 96;
    protected static final int DETALHES_ITEM_CONTATO = 99;
    protected static final int REQUESTCODE_NOVO_CONTATO = 98;

    private FloatingActionButton adicionarContatoButton;
    private FloatingActionButton entrarButton;
    private FloatingActionButton sairButton;
    private ContatoDao contatoDao;
    private UsuarioDao usuarioDao;

    private RecyclerView contatosRecyclerView;
    private ImageView listaVaziaTextView;

    private ItemContatoAdapter contatoAdapter;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private TextView textUsuarioNaoLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        //Recuperar referências do layout
        contatosRecyclerView = findViewById(R.id.recycler_lista_contatos);
        contatosRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        listaVaziaTextView = findViewById(R.id.textview_lista_vazia);
        textUsuarioNaoLogado = findViewById(R.id.usuario_nao_logado);
        adicionarContatoButton = findViewById(R.id.button_adicionar_contato);
        entrarButton = findViewById(R.id.button_login);
        sairButton = findViewById(R.id.button_logout);

        adicionarContatoButton.setOnClickListener(this::adicionarContato);
        entrarButton.setOnClickListener(this::logar);
        sairButton.setOnClickListener(this::sair);

        usuarioDao = new UsuarioDao(this);
        contatoDao = new ContatoDao(this);

        sharedPreferences = this.getSharedPreferences(
                Constantes.KEY_FILE_USUARIO_SHARED_PREFERENCE
                , MODE_PRIVATE
        );
        editor = sharedPreferences.edit();
        autorizar();
    }

    public void autorizar() {
        if (verificaUsuarioLogado()) {
            listaContato();
            componentesUsuarioLogado();
        } else {
            componentesUsuarioNaoLogado();
        }
    }

    private void listaContato() {
        contatos = contatoDao.recuperateAll();
        contatoAdapter = new ItemContatoAdapter(contatos,getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        contatosRecyclerView.setLayoutManager(layoutManager);
        contatosRecyclerView.setAdapter(contatoAdapter);
        contatoAdapter.setClickListener(this::detalharContato);
    }

    private void logar(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, REQUESTCODE_USUARIO_LOGIN);
    }

    private void detalharContato(int position) {
        Bundle args = new Bundle();
        args.putString(Constantes.ATTR_NOME, contatos.get(position).getNome());
        args.putInt(Constantes.ATTR_ID, contatos.get(position).getId());
        args.putBoolean(Constantes.ATTR_FAVORITO, contatos.get(position).getFavorito());
        Intent intent = new Intent(getApplicationContext(), DetalheContatoActivity.class);
        intent.putExtras(args);
        startActivityForResult(intent, DETALHES_ITEM_CONTATO);
    }

    private void componentesUsuarioLogado() {
        contatosRecyclerView.setVisibility(View.VISIBLE);
        textUsuarioNaoLogado.setVisibility(View.INVISIBLE);
        adicionarContatoButton.show();
        sairButton.show();
        entrarButton.hide();
    }

    private void componentesUsuarioNaoLogado() {
        contatosRecyclerView.setVisibility(View.INVISIBLE);
        textUsuarioNaoLogado.setVisibility(View.VISIBLE);
        adicionarContatoButton.hide();
    }

    private boolean verificaUsuarioLogado() {
        String userPreferenceJsonToString = sharedPreferences.getString(
                Constantes.KEY_USUARIO_JSON
                , ""
        );

        if (Usuario.getUsuarioLogado() != null) {
            return true;
        }

        try {
            JSONObject userPreferenceJson = new JSONObject(userPreferenceJsonToString);

            String preferenciaLogin = userPreferenceJson.getString(Constantes.KEY_USUARIO);
            String preferenciaSenha = userPreferenceJson.getString(Constantes.KEY_SENHA);
            boolean preferenciaLembrar = userPreferenceJson.getBoolean(Constantes.KEY_LEMBRAR);

            if (preferenciaLembrar == true) {
                Usuario usuario = usuarioDao.find(preferenciaLogin);
                Usuario.login(usuario.getNome(), usuario.getLogin(), usuario.getSenha(), usuario.getId());
                if (preferenciaSenha.equals(usuario.getSenha()) && usuario != null) {
                    return true;
                }
            }
            return false;
        } catch (JSONException e) {
            return false;
        }
    }

    public void adicionarContato(View view) {
        Intent in = new Intent(this, NovoContatoActivity.class);
        startActivityForResult(in, REQUESTCODE_NOVO_CONTATO);
    }

    private void sair(View view) {
        contatos = null;
        Usuario.setUsuarioDeslogado();
        editor.putString(Constantes.KEY_USUARIO_JSON, "");
        editor.commit();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUESTCODE_NOVO_CONTATO: {
                if (resultCode == Activity.RESULT_OK) {
                    this.contatos.clear();
                    this.contatos.addAll(contatos);
                    contatosRecyclerView.getAdapter().notifyDataSetChanged();
                }
            } case REQUESTCODE_USUARIO_LOGIN: {
                if (resultCode == RESULT_OK) {
                    ShowMessageScreenHelper.showToast(Usuario.getUsuarioLogado().getNome(), getApplicationContext());
                    listaContato();
                    componentesUsuarioLogado();
                }
            } case DETALHES_ITEM_CONTATO:
                listaContato();
        }
    }
}