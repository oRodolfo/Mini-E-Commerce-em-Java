package org.example;

import br.com.miniecommerce.model.Cliente;
import br.com.miniecommerce.model.Pedido;
import br.com.miniecommerce.model.Produtos;

import java.math.BigDecimal;

public class MainModel {
    public static void main(String[] args) {
        Cliente c1 = new Cliente("C1", "Rodolfo", "rodolforib@hotmail.com");
        Produtos p1 = new Produtos("P1", "FEIJAO", new BigDecimal("10.99"), 10);

        Pedido pedido = new Pedido("PEDIDO1", c1);
        pedido.adicionarItem(p1, 3);


        System.out.println("Preço produto: " + p1.getPreco());
        System.out.println("Qtd estoque: " + p1.getEstoque());

        System.out.println("Itens no pedido: " + pedido.getItens().size());
        System.out.println("Subtotal item: " + pedido.getItens().get(0).subtotal());

        System.out.println("Total bruto: " + pedido.totalBruto());
    }
}