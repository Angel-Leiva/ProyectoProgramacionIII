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

            // ======== Despacho ========
            Sistema.presentation.despacho.View despachoView = new Sistema.presentation.despacho.View();
            Sistema.presentation.despacho.Model despachoModel = new Sistema.presentation.despacho.Model();
            Sistema.presentation.despacho.Controller despachoController =
                    new Sistema.presentation.despacho.Controller(despachoView, despachoModel);
            tabs.addTab("Despacho", despachoView.getPanel());

            // ======== Historico ========
            Sistema.presentation.Historico.View historicoView = new Sistema.presentation.Historico.View();
            Sistema.presentation.Historico.Model historicoModel = new Sistema.presentation.Historico.Model();
            Sistema.presentation.Historico.Controller historicoController =
                    new Sistema.presentation.Historico.Controller(historicoView, historicoModel);
            tabs.addTab("Historico", historicoView.getPanel());

            // ======== Acerca de ========
            Sistema.presentation.acercaDe.View acercaView = new Sistema.presentation.acercaDe.View();
            Sistema.presentation.acercaDe.Model acercaModel = new Sistema.presentation.acercaDe.Model();
            Sistema.presentation.acercaDe.Controller acercaController =
                    new Sistema.presentation.acercaDe.Controller(acercaView, acercaModel);
            tabs.addTab("Acerca de...", acercaView.getMainPanel());

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
                    case "Historico": historicoController.refrescar(); break;
                    case "Despacho": despachoController.refrescar(); break;
                }
            });

            window.setContentPane(tabs);
            window.setVisible(true);

        } else {
            System.exit(0);
        }
    }
}
