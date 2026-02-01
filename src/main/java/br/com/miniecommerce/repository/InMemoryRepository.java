package br.com.miniecommerce.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class InMemoryRepository<T> implements Repository<T> {

    protected final List<T> data = new ArrayList<>();

    // cada repository concreto define como obter o id
    protected abstract String getId(T entity);

    @Override
    public T save(T entity) {
        if (entity == null) throw new IllegalArgumentException("Entidade não pode ser null.");

        String id = getId(entity);
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Id da entidade é obrigatório.");

        // upsert: se existir, substitui; senão, adiciona
        deleteById(id);
        data.add(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        for (T entity : data) {
            if (getId(entity).equals(id)) return Optional.of(entity);
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(data); // cópia defensiva
    }

    @Override
    public void deleteById(String id) {
        if (id == null || id.isBlank()) return;
        data.removeIf(e -> getId(e).equals(id));
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }
}
