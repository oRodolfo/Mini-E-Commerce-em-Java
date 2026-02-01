package br.com.miniecommerce.repository;

import br.com.miniecommerce.model.Cliente;

public class ClienteRepository extends InMemoryRepository<Cliente> {

    @Override
    protected String getId(Cliente entity){
        return entity.getId();
    }
}
