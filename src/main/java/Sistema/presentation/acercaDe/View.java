package Sistema.presentation.acercaDe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

public class View{ //implements PropertyChangeListener{
    private JPanel panelImagen;
    private JPanel mainPanel;
    private JLabel JLabel2;
    private Sistema.presentation.acercaDe.Controller controller;
    private Sistema.presentation.acercaDe.Model model;

    public View() {

        URL url = getClass().getResource("/Sistema/recursos/HospitalProyecto.jpg");

        if (url == null) {
            System.out.println("❌ Imagen no encontrada");
        } else {
            System.out.println("✅ Imagen cargada: " + url);
        }

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
