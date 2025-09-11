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
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Inicializamos las opciones del combo
        filtrarBusqueda.addItem("Nombre");
        filtrarBusqueda.addItem("ID");
    }

    public JComboBox<String> getFiltrarBusqueda() {
        return filtrarBusqueda;
    }

    public JTextField getBuscarNombreConFiltro() {
        return buscarNombreConFiltro;
    }

    public JTable getResultadosBusqueda() {
        return resultadosBusqueda;
    }
}
