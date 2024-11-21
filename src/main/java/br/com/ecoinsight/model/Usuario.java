package br.com.ecoinsight.model;

public class Usuario {
    private String name;
    private String email;
    private String senha;

    public Usuario() {}

    public Usuario(String name, String email, String senha) {
        this.name = name;
        this.email = email;
        this.senha = senha;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String password) { this.senha = senha; }
}
