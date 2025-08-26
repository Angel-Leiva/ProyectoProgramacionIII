package personas.presentation.personas;

import personas.logic.Persona;
import personas.presentation.Highlighter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JTextField idFld;
    private JTextField nombreFld;
    private JRadioButton sexoFldMasc;
    private JRadioButton sexoFldFem;
    private JComboBox estadoFld;
    private JCheckBox pasatiempoFldMusica;
    private JCheckBox pasatiempoFldCine;
    private JCheckBox pasatiempoFldDeporte;
    private JCheckBox pasatiempoFldVideoJuegos;
    private JCheckBox pasatiempoFldCocina;
    private JCheckBox pasatiempoFldOtro;
    private JTextField pasatiempoFldOtroDescripcion;
    private JButton guardarFld;
    private JButton cancelarFld;
    private JButton consultarFld;

    private boolean validarCampos() {
        boolean valido = true;

        // resetear colores a blanco antes de validar
        idFld.setBackground(Color.white);
        nombreFld.setBackground(Color.white);
        estadoFld.setBackground(Color.white);

        if (idFld.getText().trim().isEmpty()) {
            idFld.setBackground(Color.pink); // o Color.red, pero pink es más suave
            valido = false;
        }
        if (nombreFld.getText().trim().isEmpty()) {
            nombreFld.setBackground(Color.pink);
            valido = false;
        }
        if (!sexoFldMasc.isSelected() && !sexoFldFem.isSelected()) {
            sexoFldMasc.setBackground(Color.pink);
            sexoFldFem.setBackground(Color.pink);
            valido = false;
        } else {
            sexoFldMasc.setBackground(panel.getBackground());
            sexoFldFem.setBackground(panel.getBackground());
        }
        if (estadoFld.getSelectedIndex() == -1) {
            estadoFld.setBackground(Color.pink);
            valido = false;
        }

        return valido;
    }


    public View() {

        consultarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    controller.read(idFld.getText());
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(panel, ex.getMessage(),
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });



        guardarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!validarCampos()) {
                        JOptionPane.showMessageDialog(panel,
                                "Complete los campos en rojo antes de guardar",
                                "Error de validación",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String id = idFld.getText();
                    String nombre = nombreFld.getText();
                    char sexo = sexoFldMasc.isSelected() ? 'M' :
                            sexoFldFem.isSelected() ? 'F' : ' ';
                    String estado = (String) estadoFld.getSelectedItem();

                    controller.guardar(id, nombre, sexo, estado);

                    JOptionPane.showMessageDialog(panel, "Persona guardada con éxito",
                            "Información", JOptionPane.INFORMATION_MESSAGE);

                    // limpiar campos
                    idFld.setText("");
                    nombreFld.setText("");
                    sexoFldMasc.setSelected(false);
                    sexoFldFem.setSelected(false);
                    estadoFld.setSelectedIndex(-1);
                    pasatiempoFldMusica.setSelected(false);
                    pasatiempoFldCine.setSelected(false);
                    pasatiempoFldDeporte.setSelected(false);
                    pasatiempoFldVideoJuegos.setSelected(false);
                    pasatiempoFldCocina.setSelected(false);
                    pasatiempoFldOtro.setSelected(false);
                    pasatiempoFldOtroDescripcion.setText("");
                    pasatiempoFldOtroDescripcion.setEnabled(false);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });




        pasatiempoFldOtro.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (pasatiempoFldOtro.isSelected()) {
                    pasatiempoFldOtroDescripcion.setEnabled(true);
                } else {
                    pasatiempoFldOtroDescripcion.setEnabled(false);
                    pasatiempoFldOtroDescripcion.setText("");
                }
            }
        });

        Highlighter highlighter = new Highlighter(Color.green);
        idFld.addMouseListener(highlighter);
        nombreFld.addMouseListener(highlighter);
        sexoFldMasc.addMouseListener(highlighter);
        sexoFldFem.addMouseListener(highlighter);
        estadoFld.addMouseListener(highlighter);
        pasatiempoFldMusica.addMouseListener(highlighter);
        pasatiempoFldCine.addMouseListener(highlighter);
        pasatiempoFldDeporte.addMouseListener(highlighter);
        pasatiempoFldVideoJuegos.addMouseListener(highlighter);
        pasatiempoFldCocina.addMouseListener(highlighter);
        pasatiempoFldOtro.addMouseListener(highlighter);
        pasatiempoFldOtroDescripcion.addMouseListener(highlighter);


        cancelarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idFld.setText("");
                nombreFld.setText("");
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    //-------- MVC ---------
    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CURRENT:
                idFld.setText(String.valueOf(model.getCurrent().getId()));
                nombreFld.setText(String.valueOf(model.getCurrent().getNombre()));
                char sexo = model.getCurrent().getSexo();
                if (sexo == 'M') {
                    sexoFldMasc.setSelected(true);
                    sexoFldFem.setSelected(false);
                } else if (sexo == 'F') {
                    sexoFldFem.setSelected(true);
                    sexoFldMasc.setSelected(false);
                } else {
                    sexoFldMasc.setSelected(false);
                    sexoFldFem.setSelected(false);
                }
                estadoFld.setSelectedItem(model.getCurrent().getEstadoCivil());
                break;
        }
    }



}


