package br.com.ecoinsight.model;

import br.com.ecoinsight.exception.ValidationException;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {}

    public Usuario(int id, String nome, String email, String senha) {
        this.setId(id);
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(senha);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new ValidationException("O ID não pode ser negativo.");
        }
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidationException("O nome não pode ser vazio.");
        }
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new ValidationException("Formato de e-mail inválido.");
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.trim().length() < 8) {
            throw new ValidationException("A senha deve ter pelo menos 8 caracteres.");
        }
        if (!isSenhaForte(senha)) {
            throw new ValidationException("A senha deve conter pelo menos um número e um caractere especial.");
        }
        this.senha = senha;
    }

    public boolean isSenhaForte(String senha) {
        return senha.matches("^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$");
    }
}
