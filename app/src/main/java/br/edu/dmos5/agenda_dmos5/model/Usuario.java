package br.edu.dmos5.agenda_dmos5.model;

public class Usuario {
    private Integer id;
    private String nome;
    private String login;
    private String senha;

    private static Usuario uniqueInstance;

    public static synchronized Usuario login(String nome, String login, String senha, Integer id) {
        if (uniqueInstance == null) {
            uniqueInstance = new Usuario(nome, login, senha, id);
        }
        return uniqueInstance;
    }

    public static Usuario getUsuarioLogado(){
        return uniqueInstance;
    }

    public static void setUsuarioDeslogado() { uniqueInstance = null; }

    private Usuario(String nome, String login, String senha, Integer id){
        this(nome, login, senha);
        this.id = id;
    }

    public Usuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
