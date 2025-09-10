package Sistema.presentation.login;

import Sistema.logic.Sesion;
import Sistema.logic.Service;
import Sistema.logic.Usuario;

import javax.swing.*;
import java.awt.event.*;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        // Botón para hacer Login
        this.view.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Botón para Cancelar
        this.view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });
    }

    private void login() {
        String id = view.getTextUsuario().getText().trim();
        String clave = new String(view.getTextPassword().getPassword()).trim();

        try {
            Usuario user = model.autenticar(id, clave);
            if (user != null) {
                Sesion.setUsuario(user);
                JOptionPane.showMessageDialog(view, "Bienvenido " + user.getNombre());
                view.dispose();
                // cerrar el login
            } else {
                JOptionPane.showMessageDialog(view, "Credenciales incorrectas");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    private void cancelar() {
        Sesion.logout();
        view.dispose();
    }
}
