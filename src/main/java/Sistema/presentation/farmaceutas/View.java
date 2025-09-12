package Sistema.presentation.farmaceutas;

import Sistema.logic.Farmaceuta;
import Sistema.presentation.Highlighter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JTextField idFarmaceuta;
    private JTextField nombreFarmaceuta;
    private JButton guardar;
    private JButton limpiar;
    private JTextField nombreBuscar;
    private JButton buscar;
    private JButton reporte;
    private JTable table1;
    private JPanel panel1;
    private JPanel CambiarClave;
    private JTextField claveNueva;
    private JButton confirmarCambioDeContrasenaButton;

    private Controller controller;
    private Model model;

    public View() {
        // Configuración de la tabla
        table1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre"}
        ));

        // Acción para guardar un nuevo farmaceuta
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (idFarmaceuta.getText().trim().isEmpty() ||
                            nombreFarmaceuta.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(panel1,
                                "Complete los campos antes de guardar",
                                "Error de validación",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    controller.guardar(
                            idFarmaceuta.getText(),
                            nombreFarmaceuta.getText()
                    );

                    JOptionPane.showMessageDialog(panel1,
                            "Farmaceuta guardado con éxito",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);

                    limpiarCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para limpiar campos
        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        // Acción para buscar farmaceuta
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.buscar(nombreBuscar.getText());
            }
        });

        // Acción para cambiar la contraseña del farmaceuta seleccionado
        confirmarCambioDeContrasenaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(panel1,
                            "Seleccione un farmaceuta de la tabla",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String idSeleccionado = (String) table1.getValueAt(selectedRow, 0);
                String nuevaClave = claveNueva.getText().trim();

                if (nuevaClave.isEmpty()) {
                    JOptionPane.showMessageDialog(panel1,
                            "Ingrese una nueva contraseña",
                            "Error de validación",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    boolean exito = controller.cambiarClave(idSeleccionado, nuevaClave);
                    if (exito) {
                        JOptionPane.showMessageDialog(panel1,
                                "Contraseña cambiada con éxito para el farmaceuta con ID: " + idSeleccionado,
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);
                        claveNueva.setText("");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel1, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Highlighter para campos
        Highlighter highlighter = new Highlighter(Color.green);
        idFarmaceuta.addMouseListener(highlighter);
        nombreFarmaceuta.addMouseListener(highlighter);
    }

    private void limpiarCampos() {
        idFarmaceuta.setText("");
        nombreFarmaceuta.setText("");
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
                idFarmaceuta.setText(model.getCurrent().getId());
                nombreFarmaceuta.setText(model.getCurrent().getNombre());
                break;

            case Model.LIST:
                table1.setModel(new DefaultTableModel(
                        model.getList().stream()
                                .map(f -> new Object[]{f.getId(), f.getNombre()})
                                .toArray(Object[][]::new),
                        new String[]{"ID", "Nombre"}
                ));
                break;
        }
    }
}
