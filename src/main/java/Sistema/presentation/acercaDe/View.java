package Sistema.presentation.acercaDe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View{ //implements PropertyChangeListener{
    private JPanel panelImagen;
    private JPanel mainPanel;
    private JLabel JLabel2;
    private Sistema.presentation.acercaDe.Controller controller;
    private Sistema.presentation.acercaDe.Model model;

    public View() {


    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
       // model.addPropertyChangeListener(this);
    }
}
