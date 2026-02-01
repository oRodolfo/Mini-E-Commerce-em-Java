package br.com.miniecommerce.exception;

public class PedidoJaCanceladoException extends RuntimeException{
    public PedidoJaCanceladoException(String pedidoId){
        super("Pedido já está cancelado. " + pedidoId);
    }
}
