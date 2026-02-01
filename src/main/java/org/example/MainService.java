package org.example;

import br.com.miniecommerce.discount.DescontoPorValorTotal;
import br.com.miniecommerce.discount.PoliticaDesconto;
import br.com.miniecommerce.model.Cliente;
import br.com.miniecommerce.model.Produtos;
import br.com.miniecommerce.repository.ClienteRepository;
import br.com.miniecommerce.repository.PedidoRepository;
import br.com.miniecommerce.repository.ProdutoRepository;
import br.com.miniecommerce.service.PedidoService;

import java.math.BigDecimal;

public class MainService {
    public static void main(String[] args) {
        ProdutoRepository produtoRepo = new ProdutoRepository();
        ClienteRepository clienteRepo = new ClienteRepository();
        PedidoRepository pedidoRepo = new PedidoRepository();

        clienteRepo.save(new Cliente("C1", "Rodolfo", "rodolforib@hotmail.com"));
        produtoRepo.save(new Produtos("P1", "Feijão", new BigDecimal("10.99"), 10));
        PoliticaDesconto politicaDesconto = new DescontoPorValorTotal(new BigDecimal("200.00"), new BigDecimal("0.10"));

        PedidoService service = new PedidoService(pedidoRepo, clienteRepo, produtoRepo, politicaDesconto);

        service.criarPedido("PED1", "C1");
        service.adicionarItem("PED1", "P1", 3);

        System.out.println(service.buscarPedido("PED1").totalBruto()); // 32.97
        service.fecharPedido("PED1");

        System.out.println(produtoRepo.findById("P1").get().getEstoque()); // 7
    }
}
