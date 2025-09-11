package Sistema.presentation.prescribir.agregarmedicamento;
import javax.swing.*;

public class View extends JDialog {
    private JPanel panel1;
    private JTextField buscarMedicamentoPorFiltro;
    private JTable listaConResultadosDeBusqueda;
    private JComboBox<String> filtrarBusquedaPor;

    public View() {
        super((JFrame) null, "Agregar Medicamento", true);
        setContentPane(panel1);
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        filtrarBusquedaPor.addItem("Código");
        filtrarBusquedaPor.addItem("Nombre");
    }

    public JTextField getBuscarMedicamentoPorFiltro() { return buscarMedicamentoPorFiltro; }
    public JTable getListaConResultadosDeBusqueda() { return listaConResultadosDeBusqueda; }
    public JComboBox<String> getFiltrarBusquedaPor() { return filtrarBusquedaPor; }
}

