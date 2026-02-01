package br.com.miniecommerce.repository;

import br.com.miniecommerce.model.Produtos;

public class ProdutoRepository extends InMemoryRepository<Produtos> {

    @Override
    protected String getId(Produtos entity){
        return entity.getId();
    }
}
