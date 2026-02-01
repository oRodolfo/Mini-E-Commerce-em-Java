package br.com.miniecommerce.exception;

public class QuantidadeInvalidaException extends RuntimeException{

    public QuantidadeInvalidaException(int quantidade){
        super("Quantidade inválida: " + quantidade + " deve ser maior que zero");
    }
}
