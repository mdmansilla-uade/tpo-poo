package Views.Eventos;

import Controllers.EventosController;
import Models.Eventos;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class Calendario extends JFrame {

    private final EventosController eventosController = EventosController.getInstance();

    public Calendario() {
        setTitle("Calendario de Eventos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initView();

        setVisible(true);
    }

    private void initView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JCalendar calendar = new JCalendar();
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);


        calendar.addPropertyChangeListener("calendar", evt -> {
            Date selectedDate = calendar.getDate();
            List<Eventos> eventosFecha = eventosController.buscarEventosPorFecha(selectedDate);
            new MostrarTodos(eventosFecha);
        });

        panel.add(calendar, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
    }
}
