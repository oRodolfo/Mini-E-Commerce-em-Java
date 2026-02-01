package br.com.miniecommerce.exception;

public class PedidoVazioException extends RuntimeException{
    public PedidoVazioException(String pedidoId){
        super("Não é possivel fechar um pedido vazio: " + pedidoId);
    }
}
