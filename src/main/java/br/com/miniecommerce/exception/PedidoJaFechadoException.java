package br.com.miniecommerce.exception;

public class PedidoJaFechadoException extends RuntimeException{
    public PedidoJaFechadoException(String pedidoId){
        super("Não é possível cancelar um pedido já fechado " + pedidoId);
    }
}
