package br.com.miniecommerce.exception;

public class ItemPedidoNaoEncontradoException extends RuntimeException{
    public ItemPedidoNaoEncontradoException(String pedidoId, String produtoId){
        super("O produto " + produtoId + " não existe no pedido " + pedidoId);
    }
}
