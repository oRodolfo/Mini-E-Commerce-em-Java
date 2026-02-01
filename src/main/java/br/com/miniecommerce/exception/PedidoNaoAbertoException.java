package br.com.miniecommerce.exception;

import br.com.miniecommerce.model.StatusPedido;

public class PedidoNaoAbertoException extends RuntimeException{
    public PedidoNaoAbertoException(StatusPedido statusAtual){
        super("Pedido não está aberto para alterações. Status atual: " + statusAtual);
    }
}
