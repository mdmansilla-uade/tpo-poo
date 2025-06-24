package Views.Recursos.Asistentes;

import Controllers.AsistentesController;
import Views.BaseView;
import java.awt.*;
import javax.swing.*;

public class AsistentesView extends BaseView {
    private final AsistentesController asistentesController = AsistentesController.getInstance();

    public AsistentesView() {
        configurarVentanaBase("Gestión de Asistentes", 700, 1000);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Gestión de Participantes",
            "Administra la base de datos de asistentes y sus perfiles"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelOpciones = crearPanelOpciones("Gestión de Participantes");
        
        JButton botonAgregar = crearBotonTarjeta(
            "Agregar Nuevo Asistente",
            "Registrar un nuevo participante en el sistema",
            "Captura información personal y de contacto completa",
            COLOR_PRINCIPAL,
            e -> abrirAgregarAsistente()
        );
        
        JButton botonEditar = crearBotonTarjeta(
            "Editar Información de Asistente",
            "Modificar datos de participantes existentes",
            "Actualiza contactos, preferencias y información personal",
            COLOR_PRINCIPAL,
            e -> abrirEditarAsistente()
        );
        
        JButton botonEliminar = crearBotonTarjeta(
            "Eliminar Asistente",
            "Remover participante del sistema permanentemente",
            "Borra registro completo y historial de participación",
            COLOR_PELIGRO,
            e -> abrirEliminarAsistente()
        );
        
        JButton botonMostrar = crearBotonTarjeta(
            "Ver Todos los Asistentes",
            "Mostrar listado completo de participantes registrados",
            "Consulta información detallada y estadísticas generales",
            COLOR_SECUNDARIO,
            e -> abrirMostrarTodos()
        );
        
        panelOpciones.add(botonAgregar);
        panelOpciones.add(crearEspaciado(15));
        panelOpciones.add(botonEditar);
        panelOpciones.add(crearEspaciado(15));
        panelOpciones.add(botonEliminar);
        panelOpciones.add(crearEspaciado(15));
        panelOpciones.add(botonMostrar);
        
        panelPrincipal.add(panelOpciones);
        panelPrincipal.add(Box.createVerticalGlue());
        
        JPanel panelInferior = crearPanelInferior();
        JButton botonRegresar = crearBotonSimple("Regresar a Recursos", 
            "Volver a la gestión de recursos", COLOR_PELIGRO, e -> dispose());
        
        panelInferior.add(botonRegresar);
        panelPrincipal.add(panelInferior);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Módulo de Asistentes Activo", 
            "Total participantes: " + asistentesController.obtenerTodosLosParticipantes().size());
    }

    private void abrirAgregarAsistente() {
        new AgregarAsistente();
    }

    private void abrirEditarAsistente() {
        new EditarAsistente();
    }

    private void abrirEliminarAsistente() {
        new EliminarAsistente();
    }

    private void abrirMostrarTodos() {
        new MostrarTodo(asistentesController.obtenerTodosLosParticipantes());
    }
}
