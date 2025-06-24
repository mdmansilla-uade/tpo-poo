package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioArchivos<T> implements IRepositorioDatos<T> {
    private final String rutaArchivo;
    private final GestorErrores gestorErrores;
    
    public RepositorioArchivos(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.gestorErrores = GestorErrores.obtenerInstancia();
    }

    @Override
    public List<T> cargarTodos() {
        List<T> elementos = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                elementos.add((T) linea);
            }
        } catch (IOException e) {
            gestorErrores.registrarError("Error al cargar datos desde archivo: " + rutaArchivo, e);
        }
        return elementos;
    }

    @Override
    public void guardarTodos(List<T> elementos) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (T elemento : elementos) {
                String lineaFormateada = formatearElemento(elemento);
                escritor.write(lineaFormateada);
                escritor.newLine();
            }
        } catch (IOException e) {
            gestorErrores.registrarError("Error al guardar datos en archivo: " + rutaArchivo, e);
            throw new RuntimeException("Error de persistencia", e);
        }
    }

    @Override
    public void agregarElemento(T elemento) {
        List<T> elementosExistentes = cargarTodos();
        elementosExistentes.add(elemento);
        guardarTodos(elementosExistentes);
    }

    @Override
    public void actualizarElemento(T elemento) {
        List<T> elementosExistentes = cargarTodos();
        guardarTodos(elementosExistentes);
    }

    @Override
    public void eliminarElemento(T elemento) {
        List<T> elementosExistentes = cargarTodos();
        elementosExistentes.remove(elemento);
        guardarTodos(elementosExistentes);
    }

    protected String formatearElemento(T elemento) {
        return elemento.toString();
    }
} 