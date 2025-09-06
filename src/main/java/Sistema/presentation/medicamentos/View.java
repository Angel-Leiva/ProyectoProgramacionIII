package Sistema.presentation.medicamentos;

import Sistema.logic.Medicamento;
import Sistema.logic.Paciente;
import Sistema.presentation.Highlighter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JTextField CodigoFld;
    private JTextField NombreFld;
    private JTextField PresentacionFld;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JTextField NomCodFld;
    private JButton buscarButton;
    private JTable listaMedicamentos;
    private JLabel CodigoJLb;
    private JLabel NombreJLb;
    private JLabel PresentacionJLb;
    private JLabel NomCodJLb;
    private JPanel panel1;

    private Controller controller;
    private Model model;

    public View(){
        listaMedicamentos.setModel(new DefaultTableModel(
           new Object[][]{},
           new String[]{"Código", "Nombre", "Presentacion"}
        ));

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (CodigoFld.getText().trim().isEmpty() ||
                            NombreFld.getText().trim().isEmpty() ||
                            PresentacionJLb.getText().trim().isEmpty()){
                        JOptionPane.showMessageDialog(panel1,
                                "Complete todos los campos antes de guardar",
                                "Error de validación",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    controller.guardar(
                            CodigoFld.getText(),
                            NombreFld.getText(),
                            PresentacionFld.getText()
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

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.buscar(NomCodFld.getText()); //Busca por nombre y codigo
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = listaMedicamentos.getSelectedRow();
                if (row >= 0) {
                    String codigo = listaMedicamentos.getValueAt(row, 0).toString();
                    try {
                        controller.borrar(codigo); //Borrar
                        JOptionPane.showMessageDialog(panel1,
                                "Medicamento borrado con éxito",
                                "Información",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel1, ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel1,
                            "Seleccione un medicamento de la lista para borrar",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        Highlighter highlighter = new Highlighter(Color.green);
        CodigoFld.addMouseListener(highlighter);
        NombreFld.addMouseListener(highlighter);
        PresentacionFld.addMouseListener(highlighter);
        NomCodFld.addMouseListener(highlighter);
    }

    private void limpiarCampos() {
        CodigoFld.setText("");
        NombreFld.setText("");
        PresentacionFld.setText("");
        NomCodFld.setText("");
    }

    public JPanel getPanel() { return panel1; }
    public void setController(Sistema.presentation.medicamentos.Controller controller) { this.controller = controller; }

    public void setModel(Sistema.presentation.medicamentos.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Sistema.presentation.medicamentos.Model.CURRENT:
                Medicamento m = model.getCurrent();
                CodigoFld.setText(m.getCodigo());
                NombreFld.setText(m.getNombre());
                PresentacionFld.setText(m.getPresentacion());
                break;

            case Sistema.presentation.medicamentos.Model.LIST:
                listaMedicamentos.setModel(new DefaultTableModel(
                        model.getList().stream()
                                .map(pac -> new Object[]{
                                        pac.getCodigo(),
                                        pac.getNombre(),
                                        pac.getPresentacion()
                                })
                                .toArray(Object[][]::new),
                        new String[]{"Codigo", "Nombre", "Presentacion"}
                ));
                break;
        }
    }
}
