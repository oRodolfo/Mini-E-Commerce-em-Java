package br.com.miniecommerce.discount;

import br.com.miniecommerce.model.Pedido;

import java.math.BigDecimal;

public interface PoliticaDesconto {
    BigDecimal calcularDesconto(Pedido pedido);
}
