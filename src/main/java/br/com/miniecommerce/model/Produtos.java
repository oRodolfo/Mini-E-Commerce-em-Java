package br.com.miniecommerce.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Produtos {

    private final String id;
    private String nome;
    private BigDecimal preco;
    private int estoque;

    public Produtos(String id, String nome, BigDecimal preco, int estoque) {
        if (id == null || id.isBlank()){
            throw new IllegalArgumentException("Identificar o id do produto é obrigatório.");
        }
        this.id = id;
        setNome(nome);
        setPreco(preco);
        setEstoque(estoque);
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Identificar o nome do produto é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }
        this.preco = preco;
    }

    public void setEstoque(int estoque) {
        if (estoque < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo.");
        }
        this.estoque = estoque;
    }

    public void baixarEstoque(int quantidade){
        if(quantidade <= 0){
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        if(quantidade > estoque){
            throw new IllegalStateException("Estoque insuficiente");
        }

        this.estoque -= quantidade;
    }

    public boolean temEstoque(int quantidade) {
        return quantidade > 0 && estoque >= quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produtos)) return false;
        Produtos produto = (Produtos) o;
        return id.equals(produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
