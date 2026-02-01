package br.com.miniecommerce.exception;

public class EstoqueInsuficienteException extends RuntimeException{
    public EstoqueInsuficienteException(String produtoNome, int disponivel, int solicitado){
        super("Estoque insuficiente para " + produtoNome + ". Disponivel: " + disponivel + ", solicitado: " + solicitado);
    }
}
