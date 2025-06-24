package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestorErrores {
    private static GestorErrores instancia;
    private static final String ARCHIVO_ERRORES = "src/resources/errores.txt";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private GestorErrores() {}

    public static GestorErrores obtenerInstancia() {
        if (instancia == null) {
            instancia = new GestorErrores();
        }
        return instancia;
    }

    public void registrarError(String mensaje, Exception excepcion) {
        String mensajeCompleto = String.format("[%s] ERROR: %s - %s", 
            LocalDateTime.now().format(FORMATO_FECHA), 
            mensaje, 
            excepcion.getMessage());
        
        System.out.println(mensajeCompleto);
        guardarEnArchivo(mensajeCompleto);
    }

    public void registrarError(String mensaje) {
        String mensajeCompleto = String.format("[%s] ERROR: %s", 
            LocalDateTime.now().format(FORMATO_FECHA), 
            mensaje);
        
        System.out.println(mensajeCompleto);
        guardarEnArchivo(mensajeCompleto);
    }

    private void guardarEnArchivo(String mensaje) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO_ERRORES, true))) {
            escritor.write(mensaje);
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de errores: " + e.getMessage());
        }
    }
} 