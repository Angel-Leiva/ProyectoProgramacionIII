package Sistema;

import Sistema.presentation.medicos.Controller;
import Sistema.presentation.medicos.Model;
import Sistema.presentation.medicos.View;
import Sistema.presentation.farmaceutas.*;
import Sistema.presentation.pacientes.*;
import Sistema.presentation.medicamentos.*;


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

        // ======== Médicos ========
        View medicoView = new View();
        Model medicoModel = new Model();
        Controller medicoController = new Controller(medicoView, medicoModel);
        tabs.addTab("Médicos", medicoView.getPanel());

        // ======== Farmaceutas ========
        Sistema.presentation.farmaceutas.View fView = new Sistema.presentation.farmaceutas.View();
        Sistema.presentation.farmaceutas.Model fModel = new Sistema.presentation.farmaceutas.Model();
        Sistema.presentation.farmaceutas.Controller fController = new Sistema.presentation.farmaceutas.Controller(fView, fModel);
        tabs.addTab("Farmaceutas", fView.getPanel());

        // ======= Paciente ========
        Sistema.presentation.pacientes.View pView = new Sistema.presentation.pacientes.View();
        Sistema.presentation.pacientes.Model pModel = new Sistema.presentation.pacientes.Model();
        Sistema.presentation.pacientes.Controller pController =
                new Sistema.presentation.pacientes.Controller(pView, pModel);
        tabs.addTab("Pacientes", pView.getPanel());

        // ======= Medicamentos ========
        Sistema.presentation.medicamentos.View mView = new Sistema.presentation.medicamentos.View();
        Sistema.presentation.medicamentos.Model mModel = new Sistema.presentation.medicamentos.Model();
        Sistema.presentation.medicamentos.Controller mController =
                new Sistema.presentation.medicamentos.Controller(mView, mModel);
        tabs.addTab("Medicamentos", mView.getPanel());

        // ======== Evento cuando se cambia de pestaña ========
        tabs.addChangeListener(e -> {
            int index = tabs.getSelectedIndex();
            String title = tabs.getTitleAt(index);

            switch (title) {
                case "Médicos":
                    medicoController.refrescar();
                    break;
                case "Farmaceutas":
                    fController.refrescar();
                    break;
                case "Pacientes":
                    pController.refrescar();
                    break;
            }
        });

        window.setContentPane(tabs);
        window.setVisible(true);
    }
}

