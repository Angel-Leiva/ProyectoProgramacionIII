package Sistema;

import Sistema.presentation.medicos.Controller;
import Sistema.presentation.medicos.Model;
import Sistema.presentation.medicos.View;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); }
        catch (Exception ex) {}

        JFrame window = new JFrame("Hospital - Sistema de Recetas");
        window.setSize(800, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();

        // Tab de Médicos
        View medicoView = new View();
        Model medicoModel = new Model();
        Controller medicoController = new Controller(medicoView, medicoModel);
        tabs.addTab("Médicos", medicoView.getPanel());

        // Aquí irán los demás (Farmacéutas, Pacientes, etc.)
        // tabs.addTab("Pacientes", pacienteView.getPanel());
        // tabs.addTab("Medicamentos", medicamentoView.getPanel());

        window.setContentPane(tabs);
        window.setVisible(true);
    }
}

