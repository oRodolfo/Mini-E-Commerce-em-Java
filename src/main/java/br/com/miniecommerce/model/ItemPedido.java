package br.com.miniecommerce.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class ItemPedido {

    private final Produtos produtos;
    private int quantidade;
    private final BigDecimal precoUnitarioMomento;

    public ItemPedido(Produtos produtos, int quantidade) {
        if(produtos == null){
            throw new IllegalArgumentException("Identificar o produto é obrigatorio");
        }
        setQuantidade(quantidade);
        this.produtos = produtos;
        this.precoUnitarioMomento = produtos.getPreco();
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitarioMomento() {
        return precoUnitarioMomento;
    }

    public void setQuantidade(int quantidade) {
        if(quantidade <= 0){
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }
        this.quantidade = quantidade;
    }

    public BigDecimal subtotal() {
        return precoUnitarioMomento.multiply(BigDecimal.valueOf(quantidade)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemPedido)) return false;
        ItemPedido that = (ItemPedido) o;
        return produtos.equals(that.produtos); // 1 item por produto no pedido
    }

    @Override
    public int hashCode() {
        return Objects.hash(produtos);
    }

}
