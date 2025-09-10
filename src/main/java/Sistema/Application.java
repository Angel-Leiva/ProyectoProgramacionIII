package Sistema;

import Sistema.logic.Sesion;
import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {}

        // ======== LOGIN ========
        Sistema.presentation.login.View loginView = new Sistema.presentation.login.View();
        Sistema.presentation.login.Model loginModel = new Sistema.presentation.login.Model();
        Sistema.presentation.login.Controller loginController =
                new Sistema.presentation.login.Controller(loginView, loginModel);

        loginView.pack();
        loginView.setLocationRelativeTo(null);
        loginView.setVisible(true);

        // ======== SI LOGIN FUNCIONA ========
        if (Sesion.isLoggedIn()) {
            JFrame window = new JFrame("Hospital - Sistema de Recetas");
            window.setSize(900, 600);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTabbedPane tabs = new JTabbedPane();

            // ======== Médicos ========
            Sistema.presentation.medicos.View medicoView = new Sistema.presentation.medicos.View();
            Sistema.presentation.medicos.Model medicoModel = new Sistema.presentation.medicos.Model();
            Sistema.presentation.medicos.Controller medicoController =
                    new Sistema.presentation.medicos.Controller(medicoView, medicoModel);
            tabs.addTab("Médicos", medicoView.getPanel());

            // ======== Farmaceutas ========
            Sistema.presentation.farmaceutas.View fView = new Sistema.presentation.farmaceutas.View();
            Sistema.presentation.farmaceutas.Model fModel = new Sistema.presentation.farmaceutas.Model();
            Sistema.presentation.farmaceutas.Controller fController =
                    new Sistema.presentation.farmaceutas.Controller(fView, fModel);
            tabs.addTab("Farmaceutas", fView.getPanel());

            // ======== Pacientes ========
            Sistema.presentation.pacientes.View pView = new Sistema.presentation.pacientes.View();
            Sistema.presentation.pacientes.Model pModel = new Sistema.presentation.pacientes.Model();
            Sistema.presentation.pacientes.Controller pController =
                    new Sistema.presentation.pacientes.Controller(pView, pModel);
            tabs.addTab("Pacientes", pView.getPanel());

            // ======== Medicamentos ========
            Sistema.presentation.medicamentos.View mView = new Sistema.presentation.medicamentos.View();
            Sistema.presentation.medicamentos.Model mModel = new Sistema.presentation.medicamentos.Model();
            Sistema.presentation.medicamentos.Controller mController =
                    new Sistema.presentation.medicamentos.Controller(mView, mModel);
            tabs.addTab("Medicamentos", mView.getPanel());

            // ======== Prescribir ========
            Sistema.presentation.prescribir.View prescView = new Sistema.presentation.prescribir.View();
            Sistema.presentation.prescribir.Model prescModel = new Sistema.presentation.prescribir.Model();
            Sistema.presentation.prescribir.Controller prescController =
                    new Sistema.presentation.prescribir.Controller(prescModel, prescView);
            tabs.addTab("Prescribir", prescView.getPanel1());

            // ======== Refrescar según pestaña ========
            tabs.addChangeListener(e -> {
                int index = tabs.getSelectedIndex();
                String title = tabs.getTitleAt(index);

                switch (title) {
                    case "Médicos": medicoController.refrescar(); break;
                    case "Farmaceutas": fController.refrescar(); break;
                    case "Pacientes": pController.refrescar(); break;
                    case "Medicamentos": mController.refrescar(); break;
                    case "Prescribir": /* no refresca, se maneja solo */ break;
                }
            });

            window.setContentPane(tabs);
            window.setVisible(true);

        } else {
            System.exit(0);
        }
    }
}
