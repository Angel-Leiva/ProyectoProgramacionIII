package Sistema;

import Sistema.logic.Sesion;
import javax.swing.*;
import java.awt.*;

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

            String rol = Sesion.getRol();

            // ======== VISTAS Y CONTROLLERS ========
            // Médicos
            Sistema.presentation.medicos.View medicoView = new Sistema.presentation.medicos.View();
            Sistema.presentation.medicos.Model medicoModel = new Sistema.presentation.medicos.Model();
            Sistema.presentation.medicos.Controller medicoController =
                    new Sistema.presentation.medicos.Controller(medicoView, medicoModel);

            // Farmaceutas
            Sistema.presentation.farmaceutas.View fView = new Sistema.presentation.farmaceutas.View();
            Sistema.presentation.farmaceutas.Model fModel = new Sistema.presentation.farmaceutas.Model();
            Sistema.presentation.farmaceutas.Controller fController =
                    new Sistema.presentation.farmaceutas.Controller(fView, fModel);

            // Pacientes
            Sistema.presentation.pacientes.View pView = new Sistema.presentation.pacientes.View();
            Sistema.presentation.pacientes.Model pModel = new Sistema.presentation.pacientes.Model();
            Sistema.presentation.pacientes.Controller pController =
                    new Sistema.presentation.pacientes.Controller(pView, pModel);

            // Medicamentos
            Sistema.presentation.medicamentos.View mView = new Sistema.presentation.medicamentos.View();
            Sistema.presentation.medicamentos.Model mModel = new Sistema.presentation.medicamentos.Model();
            Sistema.presentation.medicamentos.Controller mController =
                    new Sistema.presentation.medicamentos.Controller(mView, mModel);

            // Prescribir
            Sistema.presentation.prescribir.View prescView = new Sistema.presentation.prescribir.View();
            Sistema.presentation.prescribir.Model prescModel = new Sistema.presentation.prescribir.Model();
            Sistema.presentation.prescribir.Controller prescController =
                    new Sistema.presentation.prescribir.Controller(prescModel, prescView);

            // Despacho
            Sistema.presentation.despacho.View despachoView = new Sistema.presentation.despacho.View();
            Sistema.presentation.despacho.Model despachoModel = new Sistema.presentation.despacho.Model();
            Sistema.presentation.despacho.Controller despachoController =
                    new Sistema.presentation.despacho.Controller(despachoView, despachoModel);

            // Histórico
            Sistema.presentation.Historico.View historicoView = new Sistema.presentation.Historico.View();
            Sistema.presentation.Historico.Model historicoModel = new Sistema.presentation.Historico.Model();
            Sistema.presentation.Historico.Controller historicoController =
                    new Sistema.presentation.Historico.Controller(historicoView, historicoModel);

            //Dashboard
            Sistema.presentation.dashboard.View dashboardView = new Sistema.presentation.dashboard.View();
            Sistema.presentation.dashboard.Model dashboardModel = new Sistema.presentation.dashboard.Model();
            Sistema.presentation.dashboard.Controller dashboardController =
                    new Sistema.presentation.dashboard.Controller(dashboardModel,dashboardView);


            // Acerca de
            Sistema.presentation.acercaDe.View acercaView = new Sistema.presentation.acercaDe.View();
            Sistema.presentation.acercaDe.Model acercaModel = new Sistema.presentation.acercaDe.Model();
            Sistema.presentation.acercaDe.Controller acercaController =
                    new Sistema.presentation.acercaDe.Controller(acercaView, acercaModel);

            // ======== AGREGAR TABS SEGÚN ROL ========
            int tamImg = 22;
            if ("ADMIN".equals(rol)) {
                tabs.addTab("Médicos", getScaledIcon("/Sistema/recursos/Medico.png", tamImg, tamImg), medicoView.getPanel());
                tabs.addTab("Farmaceutas", getScaledIcon("/Sistema/recursos/Farmaceuta.png", tamImg, tamImg), fView.getPanel());
                tabs.addTab("Pacientes", getScaledIcon("/Sistema/recursos/Paciente.png", tamImg, tamImg), pView.getPanel());
                tabs.addTab("Medicamentos", getScaledIcon("/Sistema/recursos/Medicamento.png", tamImg, tamImg), mView.getPanel());
                tabs.addTab("Prescribir", getScaledIcon("/Sistema/recursos/Prescripcion.png", tamImg, tamImg), prescView.getPanel1());
                tabs.addTab("Despacho", getScaledIcon("/Sistema/recursos/Despacho.png", tamImg, tamImg), despachoView.getPanel());
                tabs.addTab("Historico", getScaledIcon("/Sistema/recursos/Historico.png", tamImg, tamImg), historicoView.getPanel());
                tabs.addTab("Dashboard", getScaledIcon("/Sistema/recursos/Estadistica.png", tamImg, tamImg), dashboardView.getPanel());
                tabs.addTab("Acerca de...", getScaledIcon("/Sistema/recursos/AcercaDe.png", tamImg, tamImg), acercaView.getMainPanel());
            } else if ("MEDICO".equals(rol)) {
                tabs.addTab("Prescribir", getScaledIcon("/Sistema/recursos/Prescripcion.png", tamImg, tamImg), prescView.getPanel1());
                tabs.addTab("Pacientes", getScaledIcon("/Sistema/recursos/Paciente.png", tamImg, tamImg), pView.getPanel());
                tabs.addTab("Historico", getScaledIcon("/Sistema/recursos/Historico.png", tamImg, tamImg), historicoView.getPanel());
                tabs.addTab("Dashboard", getScaledIcon("/Sistema/recursos/Estadistica.png", tamImg, tamImg), dashboardView.getPanel());
            } else if ("FARMACEUTA".equals(rol)) {
                tabs.addTab("Despacho", getScaledIcon("/Sistema/recursos/Despacho.png", tamImg, tamImg), despachoView.getPanel());
                tabs.addTab("Historico", getScaledIcon("/Sistema/recursos/Historico.png", tamImg, tamImg), historicoView.getPanel());
                tabs.addTab("Dashboard", getScaledIcon("/Sistema/recursos/Estadistica.png", tamImg, tamImg), dashboardView.getPanel());
            } else if ("PACIENTE".equals(rol)) {
                tabs.addTab("Historico", getScaledIcon("/Sistema/recursos/Historico.png", tamImg, tamImg), historicoView.getPanel());
                tabs.addTab("Acerca de...", getScaledIcon("/Sistema/recursos/AcercaDe.png", tamImg, tamImg), acercaView.getMainPanel());
            }

            // ======== Refrescar según pestaña ========
            tabs.addChangeListener(e -> {
                int index = tabs.getSelectedIndex();
                if (index == -1) return; // por si no hay pestañas
                String title = tabs.getTitleAt(index);

                switch (title) {
                    case "Médicos": medicoController.refrescar(); break;
                    case "Farmaceutas": fController.refrescar(); break;
                    case "Pacientes": pController.refrescar(); break;
                    case "Medicamentos": mController.refrescar(); break;
                    case "Historico": historicoController.refrescar(); break;
                    case "Despacho": despachoController.refrescar(); break;
                    case "Dashboard": dashboardController.refrescar(); break;

                }
            });

            window.setContentPane(tabs);
            window.setVisible(true);

        } else {
            System.exit(0);
        }
    }
    public static ImageIcon getScaledIcon(String resourcePath, int width, int height) {
        java.net.URL url = Application.class.getResource(resourcePath);
        if (url == null) {
            System.err.println("Imagen no encontrada " + resourcePath);
            return null;
        }
        ImageIcon icon = new ImageIcon(url);
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
