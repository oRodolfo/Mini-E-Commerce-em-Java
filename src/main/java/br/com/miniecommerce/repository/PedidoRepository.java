package br.com.miniecommerce.repository;

import br.com.miniecommerce.model.Pedido;

public class PedidoRepository extends InMemoryRepository<Pedido> {

    @Override
    protected String getId(Pedido entity){
        return entity.getId();
    }
}
