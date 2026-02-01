package br.com.miniecommerce.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{
    public ProdutoNaoEncontradoException(String produtoId){
        super("Produto não encontrado: " + produtoId);
    }
}
