package br.com.miniecommerce.service;

import br.com.miniecommerce.discount.PoliticaDesconto;
import br.com.miniecommerce.exception.*;
import br.com.miniecommerce.model.Pedido;
import br.com.miniecommerce.model.Produtos;
import br.com.miniecommerce.repository.ClienteRepository;
import br.com.miniecommerce.repository.PedidoRepository;
import br.com.miniecommerce.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.List;

public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PoliticaDesconto politicaDesconto;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository, PoliticaDesconto politicaDesconto) {
        if(pedidoRepository == null || clienteRepository == null || produtoRepository == null || politicaDesconto == null){
            throw new IllegalArgumentException("Repositorios não podem ser nulos");
        }

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.politicaDesconto = politicaDesconto;
    }

    //criando pedido
    public Pedido criarPedido(String pedidoId, String clienteId){
        if(pedidoId == null || pedidoId.isBlank()){
            throw new IllegalArgumentException("Id do pedido é obrigatorio");
        }

        if(clienteId == null || clienteId.isBlank()){
            throw new IllegalArgumentException("Id do cliente é obrigatorio");
        }

        var cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNaoEncontradoException(clienteId));

        Pedido pedido = new Pedido(pedidoId, clienteRepository.findById(clienteId).get());
        return pedidoRepository.save(pedido);
    }

    //adicionar item
    public Pedido adicionarItem(String pedidoId, String produtoId, int quantidade){
        if(quantidade <= 0){
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));

        Produtos produtos = produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));

        //validando o estoque antes de adicionar
        if(quantidade > produtos.getEstoque()){
            throw new EstoqueInsuficienteException(produtos.getNome(), produtos.getEstoque(), quantidade);
        }

        pedido.adicionarItem(produtos, quantidade);
        return pedidoRepository.save(pedido);
    }

    //removendo item
    public Pedido removerItem(String pedidoId, String produtoId){
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));

        boolean itemExiste = pedido.getItens().stream()
                .anyMatch(i -> i.getProdutos().getId().equals(produtoId));

        if (!itemExiste) {
            throw new ItemPedidoNaoEncontradoException(pedidoId, produtoId);
        }

        pedido.removerItemPorProdutoID(produtoId);
        return pedidoRepository.save(pedido);
    }

    //fechar pedido: valida + baixa estoque
    public Pedido fecharPedido(String pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));

        if (pedido.estaVazio()) {
            throw new PedidoVazioException(pedidoId);
        }

        //baixa estoque de todos os itens
        pedido.getItens().forEach(item -> {
            Produtos produto = produtoRepository.findById(item.getProdutos().getId()).orElseThrow(() -> new ProdutoNaoEncontradoException(item.getProdutos().getId()));

            int qtd = item.getQuantidade();
            if (qtd > produto.getEstoque()) {
                throw new EstoqueInsuficienteException(produto.getNome(), produto.getEstoque(), qtd);
            }

            produto.baixarEstoque(qtd);
            produtoRepository.save(produto);

        });

        pedido.fechar();
        return pedidoRepository.save(pedido);
    }

    //buscar pedido
    public Pedido buscarPedido(String pedidoId){
        return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    //listrando todos os pedidos
    public List<Pedido> listarPedidos(){
        return pedidoRepository.findAll();
    }

    public BigDecimal calcularDesconto(String pedidoId) {
        Pedido pedido = buscarPedido(pedidoId);
        return politicaDesconto.calcularDesconto(pedido);
    }

    public BigDecimal totalFinal(String pedidoId) {
        Pedido pedido = buscarPedido(pedidoId);
        BigDecimal desconto = politicaDesconto.calcularDesconto(pedido);
        return pedido.totalBruto().subtract(desconto).setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
