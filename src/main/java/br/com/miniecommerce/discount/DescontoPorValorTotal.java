package br.com.miniecommerce.discount;

import br.com.miniecommerce.model.Pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DescontoPorValorTotal implements PoliticaDesconto{

    private final BigDecimal limite;
    private final BigDecimal percentual;


    public DescontoPorValorTotal(BigDecimal limite, BigDecimal percentual) {
        if (limite == null || limite.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Limite inválido.");
        if (percentual == null || percentual.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Percentual inválido.");
        if (percentual.compareTo(BigDecimal.ONE) > 0)
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 1. Ex: 0.10");

        this.limite = limite;
        this.percentual = percentual;
    }

    @Override
    public BigDecimal calcularDesconto(Pedido pedido){
        BigDecimal total = pedido.totalBruto();
        if(total.compareTo(limite) >= 0){
            return total.multiply(percentual).setScale(2, RoundingMode.HALF_UP);
        }

        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }
}
