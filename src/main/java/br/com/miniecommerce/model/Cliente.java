package br.com.miniecommerce.model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Cliente {

    private final String id;
    private String nome;
    private String email;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Cliente(String id, String nome, String email) {
        if(id == null || id.isBlank()){
            throw new IllegalArgumentException("Identificar o id do cliente é obrigatorio");
        }
        this.id = id;
        setNome(nome);
        setEmail(email);
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Identificar o nome do cliente é obrigatorio");
        }
        this.nome = nome.trim();
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()){
            throw new IllegalArgumentException("Identificar o email é obrigatorio");
        }
        String normalized = email.trim();

        if(!EMAIL_PATTERN.matcher(normalized).matches()){
            throw new IllegalArgumentException("Email invalido: " + normalized);
        }

        this.email = normalized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return id.equals(cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
