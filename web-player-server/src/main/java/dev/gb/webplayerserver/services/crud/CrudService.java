package dev.gb.webplayerserver.services.crud;

import java.util.Optional;

public interface CrudService<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    void delete(T entity);
}
