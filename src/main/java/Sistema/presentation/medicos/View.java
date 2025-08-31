package Sistema.presentation.medicos;

import Sistema.logic.Medico;
import Sistema.presentation.Highlighter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel1;
    private JTable listado;
    private JTextField idMedico;
    private JTextField nombreMedico;
    private JButton guardar;
    private JButton cancelar;
    private JTextField especialidad;
    private JButton borrar;
    private JTextField nombreBusqueda;
    private JButton buscar;
    private JButton reporte;

    private Controller controller;
    private Model model;

    // --- Constructor ---
    public View() {
        // Configuración inicial del JTable
        listado.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Especialidad"}
        ));

        // Evento Guardar
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!validarCampos()) {
                        JOptionPane.showMessageDialog(panel1,
                                "Complete los campos en rojo antes de guardar",
                                "Error de validación",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    controller.guardar(
                            idMedico.getText(),
                            idMedico.getText(), // la clave inicial = id
                            nombreMedico.getText(),
                            especialidad.getText()
                    );

                    JOptionPane.showMessageDialog(panel1,
                            "Médico guardado con éxito",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);

                    limpiarCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Evento Cancelar
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        // Evento Buscar
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.buscar(nombreBusqueda.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, ex.getMessage(),
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Evento Borrar
        borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = listado.getSelectedRow();
                if (row >= 0) {
                    String id = listado.getValueAt(row, 0).toString();
                    try {
                        controller.borrar(id);
                        JOptionPane.showMessageDialog(panel1,
                                "Médico borrado con éxito",
                                "Información",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel1, ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel1,
                            "Seleccione un médico de la lista para borrar",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Highlighter para resaltar campos
        Highlighter highlighter = new Highlighter(Color.green);
        idMedico.addMouseListener(highlighter);
        nombreMedico.addMouseListener(highlighter);
        especialidad.addMouseListener(highlighter);
    }

    // --- Validación de campos ---
    private boolean validarCampos() {
        boolean valido = true;
        idMedico.setBackground(Color.white);
        nombreMedico.setBackground(Color.white);
        especialidad.setBackground(Color.white);

        if (idMedico.getText().trim().isEmpty()) { idMedico.setBackground(Color.pink); valido = false; }
        if (nombreMedico.getText().trim().isEmpty()) { nombreMedico.setBackground(Color.pink); valido = false; }
        if (especialidad.getText().trim().isEmpty()) { especialidad.setBackground(Color.pink); valido = false; }

        return valido;
    }

    // --- Limpiar campos ---
    private void limpiarCampos() {
        idMedico.setText("");
        nombreMedico.setText("");
        especialidad.setText("");
    }

    // --- Integración con MVC ---
    public JPanel getPanel() {
        return panel1;
    }

    public void setController(Controller controller) { this.controller = controller; }
    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    // --- Actualizar vista cuando el modelo cambia ---
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CURRENT:
                Medico m = model.getCurrent();
                idMedico.setText(m.getId());
                nombreMedico.setText(m.getNombre());
                especialidad.setText(m.getEspecialidad());
                break;

            case Model.LIST:
                listado.setModel(new DefaultTableModel(
                        model.getList().stream()
                                .map(medico -> new Object[]{medico.getId(), medico.getNombre(), medico.getEspecialidad()})
                                .toArray(Object[][]::new),
                        new String[]{"ID", "Nombre", "Especialidad"}
                ));
                break;
        }
    }
}
