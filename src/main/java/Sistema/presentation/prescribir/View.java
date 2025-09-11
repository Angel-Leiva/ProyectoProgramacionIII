package Sistema.presentation.prescribir;

import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;

public class View {
    private JPanel panel1;
    private JButton buscaPaciContr;
    private JButton agregamediContr;
    private JButton eliminamodiContr;
    private JButton guardar;
    private JButton limpiar;
    private JButton descartarMedi;
    private JButton detalles;
    private JTable listaMedicamentos;
    private JLabel pacienteSeleccionado;
    private DatePicker fechaRetiroMedi;
    private JLabel pasienteseleccionado;


    public View() {
        // Inicializamos la tabla con columnas
        String[] columnas = {"Código", "Nombre", "Cantidad", "Duración (días)", "Indicaciones"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        listaMedicamentos.setModel(model);
    }

    public JButton getBuscaPaciContr() {
        return buscaPaciContr;
    }

    public JButton getAgregamediContr() {
        return agregamediContr;
    }

    public JLabel getPasienteseleccionado() {
        return pasienteseleccionado;
    }

    public JTable getListaMedicamentos() {
        return listaMedicamentos;
    }

    public void inicializarTablaMedicamentos() {
        String[] columnas = {"Código", "Nombre", "Cantidad", "Duración (días)", "Indicaciones"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        listaMedicamentos.setModel(model);
    }

    public JButton getGuardar() { return guardar; }
    public JButton getLimpiar() { return limpiar; }
    public JButton getDescartarMedi() { return descartarMedi; }
    public DatePicker getFechaRetiroMedi() { return fechaRetiroMedi; }






    public JPanel getPanel1() {
        return panel1;
    }
}

