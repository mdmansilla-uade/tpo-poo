package Views.Eventos;


import Controllers.EventosController;
import Models.Asistentes;
import Models.Eventos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EliminarAsistente extends JDialog {
    private JComboBox<Asistentes> comboAsistentes;
    private JButton btnEliminar;
    private JButton btnCancelar;
    private Eventos eventoSeleccionado;

    public EliminarAsistente(Frame owner, Eventos evento) {
        super(owner, "Eliminar Asistente", true);
        this.eventoSeleccionado = evento;

        initUI();
        cargarAsistentes();

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Asistentes asistenteSeleccionado = (Asistentes) comboAsistentes.getSelectedItem();
                int confirm = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el asistente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (asistenteSeleccionado != null) {
                        EventosController.getInstance().desinscribirParticipanteDelEvento(evento, asistenteSeleccionado);
                        dispose();
                    }
                }
            }
        });

        btnCancelar.addActionListener(e -> dispose());
        setSize(300, 200);
        setLocationRelativeTo(owner);
    }

    private void initUI() {
        comboAsistentes = new JComboBox<>();
        btnEliminar = new JButton("Eliminar");
        btnCancelar = new JButton("Cancelar");

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(comboAsistentes);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel);

        add(panel, BorderLayout.CENTER);
    }

    private void cargarAsistentes() {
        List<Asistentes> asistentes = eventoSeleccionado.getAsistentes();
        for (Asistentes asistente : asistentes) {
            comboAsistentes.addItem(asistente);
        }
    }
}