package br.com.miniecommerce.model;

import br.com.miniecommerce.exception.PedidoJaCanceladoException;
import br.com.miniecommerce.exception.PedidoJaFechadoException;
import br.com.miniecommerce.exception.PedidoNaoAbertoException;
import br.com.miniecommerce.exception.PedidoVazioException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private final String id;
    private final Cliente cliente;
    private final List<ItemPedido> itens = new ArrayList<>();
    private StatusPedido status = StatusPedido.ABERTO;

    public Pedido(String id, Cliente cliente) {
        if(id == null || id.isBlank()){
            throw new IllegalArgumentException("Identificar o id do produto é obrigatorio");
        }

        if(cliente == null){
            throw new IllegalArgumentException("Identificar o cliente é obrigatorio");
        }
        this.id = id;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public List<ItemPedido> getItens(){
        return Collections.unmodifiableList(itens);
    }

    public void adicionarItem(Produtos produtos, int quantidade){
        garantirPedidoAberto();

        //se ja existe algum produto igual ao inves de criar o mesmo novamente so adiciona na quantidade para nao ter duplicidade de produto
        for(ItemPedido item : itens){
            if(item.getProdutos().equals(produtos)){
                item.setQuantidade((item.getQuantidade() + quantidade));

                return;
            }
        }
        itens.add(new ItemPedido(produtos, quantidade));
    }

    private void garantirPedidoAberto() {
        if(status != StatusPedido.ABERTO){
            throw new PedidoNaoAbertoException(status);
        }
    }

    public void removerItemPorProdutoID(String produtoID){
        garantirPedidoAberto();
        itens.removeIf(i -> i.getProdutos().getId().equals(produtoID));
    }

    public BigDecimal totalBruto(){
        BigDecimal total = BigDecimal.ZERO;
        for(ItemPedido item : itens){
            total = total.add(item.subtotal());
        }
        return  total.setScale(2, RoundingMode.HALF_UP);
    }

    public boolean estaVazio(){
        return itens.isEmpty();
    }

    public void fechar(){
        garantirPedidoAberto();
        if(itens.isEmpty()){
            throw new PedidoVazioException(id);
        }
        this.status = StatusPedido.FECHADO;
    }
    
    public void cancelar(){
        if (status == StatusPedido.FECHADO) {
            throw new PedidoJaFechadoException(id);
        }
        if (status == StatusPedido.CANCELADO) {
            throw new PedidoJaCanceladoException(id);
        }
        this.status = StatusPedido.CANCELADO;
    }
}
