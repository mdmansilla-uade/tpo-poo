package services;

import java.util.List;

public interface IRepositorioDatos<T> {
    List<T> cargarTodos();
    void guardarTodos(List<T> elementos);
    void agregarElemento(T elemento);
    void actualizarElemento(T elemento);
    void eliminarElemento(T elemento);
} 