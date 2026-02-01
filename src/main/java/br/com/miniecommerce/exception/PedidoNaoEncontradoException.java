package br.com.miniecommerce.exception;

public class PedidoNaoEncontradoException extends RuntimeException{
    public PedidoNaoEncontradoException(String pedidoId){
        super("Pedido não encontrado: " + pedidoId);
    }
}
