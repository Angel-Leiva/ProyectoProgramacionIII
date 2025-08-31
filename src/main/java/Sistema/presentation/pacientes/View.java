package Sistema.presentation.pacientes;

import Sistema.logic.Paciente;
import Sistema.presentation.Highlighter;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

public class View implements PropertyChangeListener {
    private JPanel panel1;
    private JTextField idPaciente;
    private JTextField nombrePaciente;
    private JButton guardar;
    private JButton limpiar;
    private JTextField telefonoPaciente;
    private JTextField nombreBusqueda;
    private JButton buscar;
    private JButton reporte;
    private JTable listaPacientes;
    private DatePicker FechaNacimiento;

    private Controller controller;
    private Model model;

    public View() {
        listaPacientes.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Fecha Nac.", "Teléfono"}
        ));

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (idPaciente.getText().trim().isEmpty() ||
                            nombrePaciente.getText().trim().isEmpty() ||
                            telefonoPaciente.getText().trim().isEmpty() ||
                            FechaNacimiento.getDate() == null) {
                        JOptionPane.showMessageDialog(panel1,
                                "Complete todos los campos antes de guardar",
                                "Error de validación",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    controller.guardar(
                            idPaciente.getText(),
                            nombrePaciente.getText(),
                            FechaNacimiento.getDate(),
                            telefonoPaciente.getText()
                    );

                    JOptionPane.showMessageDialog(panel1,
                            "Paciente guardado con éxito",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);

                    limpiarCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.buscar(nombreBusqueda.getText());
            }
        });

        Highlighter highlighter = new Highlighter(Color.green);
        idPaciente.addMouseListener(highlighter);
        nombrePaciente.addMouseListener(highlighter);
        telefonoPaciente.addMouseListener(highlighter);
    }

    private void limpiarCampos() {
        idPaciente.setText("");
        nombrePaciente.setText("");
        telefonoPaciente.setText("");
        FechaNacimiento.clear();
    }

    public JPanel getPanel() { return panel1; }
    public void setController(Controller controller) { this.controller = controller; }
    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CURRENT:
                Paciente p = model.getCurrent();
                idPaciente.setText(p.getId());
                nombrePaciente.setText(p.getNombre());
                telefonoPaciente.setText(p.getTelefono());
                FechaNacimiento.setDate(p.getFechaNacimiento());
                break;

            case Model.LIST:
                listaPacientes.setModel(new DefaultTableModel(
                        model.getList().stream()
                                .map(pac -> new Object[]{
                                        pac.getId(),
                                        pac.getNombre(),
                                        pac.getFechaNacimiento(),
                                        pac.getTelefono()
                                })
                                .toArray(Object[][]::new),
                        new String[]{"ID", "Nombre", "Fecha Nac.", "Teléfono"}
                ));
                break;
        }
    }
}
