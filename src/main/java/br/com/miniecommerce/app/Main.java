package br.com.miniecommerce.app;

import br.com.miniecommerce.discount.DescontoPorValorTotal;
import br.com.miniecommerce.discount.PoliticaDesconto;
import br.com.miniecommerce.model.Cliente;
import br.com.miniecommerce.model.Pedido;
import br.com.miniecommerce.model.Produtos;
import br.com.miniecommerce.repository.ClienteRepository;
import br.com.miniecommerce.repository.PedidoRepository;
import br.com.miniecommerce.repository.ProdutoRepository;
import br.com.miniecommerce.service.PedidoService;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Repos
        ProdutoRepository produtoRepo = new ProdutoRepository();
        ClienteRepository clienteRepo = new ClienteRepository();
        PedidoRepository pedidoRepo = new PedidoRepository();

        // Política de desconto: >= 200 aplica 10%
        PoliticaDesconto politica = new DescontoPorValorTotal(new BigDecimal("200.00"), new BigDecimal("0.10"));

        // Service
        PedidoService pedidoService = new PedidoService(pedidoRepo, clienteRepo, produtoRepo, politica);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MINI E-COMMERCE ===");
            System.out.println("1) Produtos");
            System.out.println("2) Clientes");
            System.out.println("3) Pedidos");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");

            String opcao = scanner.nextLine();

            try {
                switch (opcao) {
                    case "1" -> menuProdutos(scanner, produtoRepo);
                    case "2" -> menuClientes(scanner, clienteRepo);
                    case "3" -> menuPedidos(scanner, pedidoService);
                    case "0" -> {
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RuntimeException e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }
    }

    //MENUS de produto, cliente e pedido
    private static void menuProdutos(Scanner sc, ProdutoRepository produtoRepo) {
        System.out.println("\n--- PRODUTOS ---");
        System.out.println("1) Cadastrar");
        System.out.println("2) Listar");
        System.out.println("3) Remover");
        System.out.print("Escolha: ");

        String op = sc.nextLine();

        switch (op) {
            case "1" -> {
                System.out.print("ID: ");
                String id = sc.nextLine();
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("Preço (ex: 10.99): ");
                BigDecimal preco = new BigDecimal(sc.nextLine());
                System.out.print("Estoque: ");
                int estoque = Integer.parseInt(sc.nextLine());

                produtoRepo.save(new Produtos(id, nome, preco, estoque));
                System.out.println("Produto cadastrado.");
            }
            case "2" -> {
                System.out.println("\nLista de Produtos:");
                for (Produtos p : produtoRepo.findAll()) {
                    System.out.println(p.getId() + " | " + p.getNome() + " | R$ " + p.getPreco() + " | Estoque: " + p.getEstoque());
                }
            }
            case "3" -> {
                System.out.print("ID do produto para remover: ");
                String id = sc.nextLine();

                if (!produtoRepo.existsById(id)) {
                    System.out.println("Produto não encontrado: " + id);
                    return;
                }

                produtoRepo.deleteById(id);
                System.out.println("Produto removido com sucesso.");
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void menuClientes(Scanner sc, ClienteRepository clienteRepo) {
        System.out.println("\n--- CLIENTES ---");
        System.out.println("1) Cadastrar");
        System.out.println("2) Listar");
        System.out.println("3) Remover");
        System.out.print("Escolha: ");

        String op = sc.nextLine();

        switch (op) {
            case "1" -> {
                System.out.print("ID: ");
                String id = sc.nextLine();
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();

                clienteRepo.save(new Cliente(id, nome, email));
                System.out.println("Cliente cadastrado.");
            }
            case "2" -> {
                System.out.println("\nLista de Clientes:");
                for (Cliente c : clienteRepo.findAll()) {
                    System.out.println(c.getId() + " | " + c.getNome() + " | " + c.getEmail());
                }
            }
            case "3" -> {
                System.out.print("ID do cliente para remover: ");
                String id = sc.nextLine();

                if (!clienteRepo.existsById(id)) {
                    System.out.println("Cliente não encontrado: " + id);
                    return;
                }

                clienteRepo.deleteById(id);
                System.out.println("Cliente removido com sucesso.");
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void menuPedidos(Scanner sc, PedidoService pedidoService) {
        System.out.println("\n--- PEDIDOS ---");
        System.out.println("1) Criar pedido");
        System.out.println("2) Adicionar item");
        System.out.println("3) Remover item");
        System.out.println("4) Ver resumo do pedido");
        System.out.println("5) Fechar pedido");
        System.out.println("6) Listar pedidos");
        System.out.print("Escolha: ");

        String op = sc.nextLine();

        switch (op) {
            case "1" -> {
                System.out.print("ID do pedido: ");
                String pedidoId = sc.nextLine();
                System.out.print("ID do cliente: ");
                String clienteId = sc.nextLine();

                Pedido p = pedidoService.criarPedido(pedidoId, clienteId);
                System.out.println("Pedido criado: " + p.getId());
            }
            case "2" -> {
                System.out.print("ID do pedido: ");
                String pedidoId = sc.nextLine();
                System.out.print("ID do produto: ");
                String produtoId = sc.nextLine();
                System.out.print("Quantidade: ");
                int qtd = Integer.parseInt(sc.nextLine());

                pedidoService.adicionarItem(pedidoId, produtoId, qtd);
                System.out.println("Item adicionado.");
            }
            case "3" -> {
                System.out.print("ID do pedido: ");
                String pedidoId = sc.nextLine();
                System.out.print("ID do produto: ");
                String produtoId = sc.nextLine();

                pedidoService.removerItem(pedidoId, produtoId);
                System.out.println("Item removido.");
            }
            case "4" -> {
                System.out.print("ID do pedido: ");
                String pedidoId = sc.nextLine();

                Pedido pedido = pedidoService.buscarPedido(pedidoId);
                BigDecimal bruto = pedido.totalBruto();
                BigDecimal desconto = pedidoService.calcularDesconto(pedidoId);
                BigDecimal totalFinal = pedidoService.totalFinal(pedidoId);

                System.out.println("\nPedido: " + pedido.getId());
                System.out.println("Status: " + pedido.getStatus());
                System.out.println("Itens: " + pedido.getItens().size());
                System.out.println("Total bruto: R$ " + bruto);
                System.out.println("Desconto: R$ " + desconto);
                System.out.println("Total final: R$ " + totalFinal);
            }
            case "5" -> {
                System.out.print("ID do pedido: ");
                String pedidoId = sc.nextLine();

                pedidoService.fecharPedido(pedidoId);
                System.out.println("Pedido fechado.");
            }
            case "6" -> {
                System.out.println("\nLista de Pedidos:");
                for (Pedido p : pedidoService.listarPedidos()) {
                    System.out.println(p.getId() + " | Cliente: " + p.getCliente().getNome() + " | Status: " + p.getStatus()
                            + " | Bruto: R$ " + p.totalBruto());
                }
            }
            default -> System.out.println("Opção inválida.");
        }
    }
}
