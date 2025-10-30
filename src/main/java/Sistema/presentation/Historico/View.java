package Sistema.presentation.Historico;

import Sistema.logic.Receta;

import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener{
    private JPanel panel1;
    private JTable listaRecetas;
    private JButton buttonBuscar;
    private JButton buttonLimpiar;
    private JTextField NomIdTextFld;
    private JTable listaDetalles;

    private Controller controller;
    private Model model;

    public View() {
        listaRecetas.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nombre Paciente", "Id Paciente", "Fecha de confección", "Fecha de retiro"}
        ));

        listaDetalles.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Medicamento", "Cantidad", "Días", "Indicaciones"}
        ));

        buttonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.buscar(NomIdTextFld.getText());
            }
        });

        listaRecetas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = listaRecetas.getSelectedRow();
                if (selectedRow >= 0 && selectedRow < model.getList().size()) {
                    Receta recetaSeleccionada = model.getList().get(selectedRow);
                    controller.seleccionarReceta(recetaSeleccionada);
                }
            }
        });


    }


    private void limpiarCampos() {
        NomIdTextFld.setText("");
    }

    public JPanel getPanel() { return panel1; }
    public void setController(Controller controller) { this.controller = controller; }
    public void setModel(Sistema.presentation.Historico.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Sistema.presentation.Historico.Model.CURRENT:
                Receta r = model.getCurrent();
                break;

            case Model.LIST:
                listaRecetas.setModel(new DefaultTableModel(
                        model.getList().stream()
                                .map(rec -> new Object[]{
                                        rec.getPaciente().getNombre(),
                                        rec.getPaciente().getId(),
                                        LocalDate.now(),
                                        rec.getFecha()
                                })
                                .toArray(Object[][]::new),
                        new String[]{"Nombre Paciente", "Id Paciente", "Fecha de confección", "Fecha de retiro"}
                ));
                break;

            case Model.LISTAMED:
                listaDetalles.setModel(new DefaultTableModel(
                        model.getListaMedic().stream()
                                .map(med -> new Object[]{
                                        med.getMedicamento().getNombre(),
                                        med.getCantidad(),
                                        med.getDias(),
                                        med.getIndicaciones()
                                })
                                .toArray(Object[][]::new),
                        new String[]{"Medicamento", "Cantidad", "Días", "Indicaciones"}
                ));

                break;

        }
    }

}
