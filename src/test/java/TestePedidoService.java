import br.com.miniecommerce.discount.DescontoPorValorTotal;
import br.com.miniecommerce.discount.PoliticaDesconto;
import br.com.miniecommerce.exception.ClienteNaoEncontradoException;
import br.com.miniecommerce.exception.EstoqueInsuficienteException;
import br.com.miniecommerce.exception.PedidoVazioException;
import br.com.miniecommerce.exception.ProdutoNaoEncontradoException;
import br.com.miniecommerce.model.Cliente;
import br.com.miniecommerce.model.Produtos;
import br.com.miniecommerce.repository.ClienteRepository;
import br.com.miniecommerce.repository.PedidoRepository;
import br.com.miniecommerce.repository.ProdutoRepository;
import br.com.miniecommerce.service.PedidoService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class TestePedidoService {

    private ProdutoRepository produtoRepo;
    private ClienteRepository clienteRepo;
    private PedidoRepository pedidoRepo;
    private PoliticaDesconto politica;
    private PedidoService service;

    @Before
    public void setup(){
        produtoRepo = new ProdutoRepository();
        clienteRepo = new ClienteRepository();
        pedidoRepo = new PedidoRepository();

        //desconto: >= 200 aplica 10%
        politica = new DescontoPorValorTotal(new BigDecimal("200.00"), new BigDecimal("0.10"));

        service = new PedidoService(pedidoRepo, clienteRepo, produtoRepo, politica);
    }

    @Test(expected = ClienteNaoEncontradoException.class)
    public void criarPedido_clienteNaoExiste_deveLancarExcecao(){
        service.criarPedido("PED1", "C999");
    }

    @Test(expected = ProdutoNaoEncontradoException.class)
    public void adicionarItem_produtoNaoExiste_deveLancarExcessao(){
        clienteRepo.save(new Cliente("C2", "Rodolfo", "rodolforib.zanchetta@gmail.com"));
        service.criarPedido("PED2", "C2");

        service.adicionarItem("PED2", "P999", 1);
    }

    @Test(expected = EstoqueInsuficienteException.class)
    public void adicionarItem_semEstoque_deveLancarExcecao(){
        clienteRepo.save(new Cliente("C3", "Clovis", "clovis.ita@gmail.com"));
        produtoRepo.save(new Produtos("C3", "Curso POO Ita", new BigDecimal("260.00"), 1));

        service.criarPedido("PED3", "C3");

        //pedi 3 mas so tem 2
        service.adicionarItem("PED3", "C3", 3);
    }

    @Test(expected = PedidoVazioException.class)
    public void fecharPedido_vazio_deveLancarExcecao(){
        clienteRepo.save(new Cliente("C4", "Eduardo Guerra", "eduardoguerra.ita@gmail.com"));
        service.criarPedido("PED4", "C4");

        service.fecharPedido("PED4");
    }

    @Test
    public void fecharPedido_deveBaixarEstoque(){
        clienteRepo.save(new Cliente("C5", "Vargas", "vargaspresidentebrasil@gmail.com"));
        produtoRepo.save(new Produtos("P5", "CONSTITUIÇÃO DE ESTADO NOVO", new BigDecimal("1937.00"), 10));

        service.criarPedido("PED5", "C5");
        service.adicionarItem("PED5", "P5", 3);

        service.fecharPedido("PED5");

        int estoqueFinal = produtoRepo.findById("P5").get().getEstoque();
        assertEquals(7, estoqueFinal);
    }

    @Test
    public void desconto_acimaDe200_aplica10porcento(){
        clienteRepo.save(new Cliente("C6", "KSCERATO", "kscerato.furia@gmail.com"));

        //3x100 = 300 -> desconto 10% = 30
        produtoRepo.save(new Produtos("P6", "PRODUTO", new BigDecimal("100.00"), 10));

        service.criarPedido("PED6", "C6");
        service.adicionarItem("PED6", "P6", 6);

        BigDecimal desconto = service.calcularDesconto("PED6");
        assertEquals(new BigDecimal("60.00"), desconto);

        BigDecimal totalFinal = service.totalFinal("PED6");
        assertEquals(new BigDecimal("540.00"), totalFinal);
    }
}
