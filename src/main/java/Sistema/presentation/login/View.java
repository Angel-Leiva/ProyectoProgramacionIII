package Sistema.presentation.login;

import javax.swing.*;
import java.awt.event.*;

public class View extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField usuarioContraLogin;
    private JTextField usuarioLoginID;

    public View() {
        setTitle("Iniciar Sesión");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // 🔹 Cerrar ventana al presionar la X
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // 🔹 Cerrar app con la X
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // 🔹 Cerrar con tecla ESC
        contentPane.registerKeyboardAction(new ActionListener() {
                                               public void actionPerformed(ActionEvent e) {
                                                   buttonCancel.doClick(); // simula botón cancelar
                                               }
                                           }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    // 🔹 Getters para que el Controller use los datos
    public JTextField getTextUsuario() { return usuarioLoginID; }
    public JPasswordField getTextPassword() { return usuarioContraLogin; }
    public JButton getBtnLogin() { return buttonOK; }
    public JButton getBtnCancelar() { return buttonCancel; }
}
