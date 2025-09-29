package Sistema.presentation.despacho.obcionDespacho;

import javax.swing.*;

public class View extends JFrame {
    private JPanel panel1;
    private JComboBox<String> estadoReceta;
    private JButton guardar;
    private JButton cancelar;

    public View() {
        setContentPane(panel1);
        setTitle("Cambiar Estado de Receta");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Opciones de estado
        estadoReceta.addItem("Pendiente");
        estadoReceta.addItem("Lista");
        estadoReceta.addItem("Entregada");
        estadoReceta.addItem("Confeccionada");
    }

    public JComboBox<String> getEstadoReceta() {
        return estadoReceta;
    }

    public JButton getGuardar() {
        return guardar;
    }

    public JButton getCancelar() {
        return cancelar;
    }

    public JPanel getPanel1() {
        return panel1;
    }
}
