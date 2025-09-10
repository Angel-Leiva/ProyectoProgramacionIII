package Sistema.presentation.prescribir.buscarpaciente;

import javax.swing.*;

public class View extends JDialog {
    private JPanel panel1;
    private JComboBox<String> filtrarBusqueda;
    private JTextField buscarNombreConFiltro;
    private JTable resultadosBusqueda;

    public View() {
        super((JFrame) null, "Buscar Paciente", true);
        setContentPane(panel1);
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}

