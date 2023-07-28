package dev.gb.webplayerserver.services.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class CrudServiceImp<T, ID> implements CrudService<T, ID> {
    @Autowired
    private JpaRepository<T, ID> jpaRepository;


    @Override
    public T save(T entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void delete(T entity) {
        jpaRepository.delete(entity);
    }
}