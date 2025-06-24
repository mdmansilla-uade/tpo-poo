package Views.Recursos.Salones;

import Controllers.SalonController;
import Views.BaseView;
import java.awt.*;
import javax.swing.*;

public class SalonesView extends BaseView {
    private final SalonController salonController = SalonController.getInstance();

    public SalonesView() {
        configurarVentanaBase("Gestión de Salones", 700, 1000);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Gestión de Espacios",
            "Administra salones, auditorios y espacios para eventos"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelOpciones = crearPanelOpciones("Gestión de Espacios");
        
        JButton botonAgregar = crearBotonTarjeta(
            "Agregar Nuevo Salón",
            "Registrar un nuevo espacio disponible para eventos",
            "Configura capacidad, ubicación y características técnicas",
            COLOR_PRINCIPAL,
            e -> abrirAgregarSalon()
        );
        
        JButton botonEditar = crearBotonTarjeta(
            "Editar Información de Salón",
            "Modificar detalles de espacios existentes",
            "Actualiza capacidad, equipamiento y disponibilidad",
            COLOR_PRINCIPAL,
            e -> abrirEditarSalon()
        );
        
        JButton botonEliminar = crearBotonTarjeta(
            "Eliminar Salón",
            "Remover espacio del sistema permanentemente",
            "Desactiva salón y libera reservas futuras asociadas",
            COLOR_PELIGRO,
            e -> abrirEliminarSalon()
        );
        
        JButton botonMostrar = crearBotonTarjeta(
            "Ver Todos los Salones",
            "Mostrar listado completo de espacios disponibles",
            "Consulta capacidades, ubicaciones y estado de ocupación",
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
        
        agregarBarraEstado("Módulo de Salones Activo", 
            "Total espacios: " + salonController.obtenerTodosLosEspacios().size());
    }

    private void abrirAgregarSalon() {
        new AgregarSalon();
    }

    private void abrirEditarSalon() {
        new EditarSalon();
    }

    private void abrirEliminarSalon() {
        new EliminarSalon();
    }

    private void abrirMostrarTodos() {
        new MostrarTodo(salonController.obtenerTodosLosEspacios());
    }

}
