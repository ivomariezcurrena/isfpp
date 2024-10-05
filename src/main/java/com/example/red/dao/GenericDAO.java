package com.example.red.dao;

import java.util.List;

public interface GenericDAO<T> {
    T obtenerPorId(Long id);
    void guardar(T entidad);
    void eliminar(Long id);
    List<T> obtenerTodos();
}
