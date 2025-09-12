package Sistema.presentation.despacho;

import javax.swing.*;

public class View extends JFrame {
    private JPanel panel1;
    JTable listaRecetas;

    public View() {
        setContentPane(panel1);
        setTitle("Despacho de Recetas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JTable getListaRecetas() {
        return listaRecetas;
    }

    public JPanel getPanel() {
        return panel1;
    }
}
