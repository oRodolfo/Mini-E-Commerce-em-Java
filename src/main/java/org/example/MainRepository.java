package org.example;

import br.com.miniecommerce.model.Cliente;
import br.com.miniecommerce.model.Produtos;
import br.com.miniecommerce.repository.ClienteRepository;
import br.com.miniecommerce.repository.ProdutoRepository;

import java.math.BigDecimal;

public class MainRepository {
    public static void main(String[] args) {
        ProdutoRepository produtoRepo = new ProdutoRepository();
        ClienteRepository clienteRepo = new ClienteRepository();

        Produtos p1 = new Produtos("P1", "Feijão", new BigDecimal("10.99"), 10);
        produtoRepo.save(p1);

        Cliente c1 = new Cliente("C1", "Rodolfo", "rodolforib@hotmail.com");
        clienteRepo.save(c1);

        System.out.println("Produtos: " + produtoRepo.findAll().size()); // 1
        System.out.println("Cliente C1 existe? " + clienteRepo.existsById("C1")); // true

        produtoRepo.deleteById("P1");
        System.out.println("Produtos após delete: " + produtoRepo.findAll().size()); // 0
    }
}
