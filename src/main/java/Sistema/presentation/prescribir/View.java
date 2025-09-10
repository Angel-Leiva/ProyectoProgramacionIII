package Sistema.presentation.prescribir;

import com.github.lgooddatepicker.components.DatePicker;

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

    public View() {
        // constructor vacío, pero puedes inicializar cosas si quieres
    }

    // 👉 Getters que necesita el controlador
    public JButton getBuscaPaciContr() {
        return buscaPaciContr;
    }

    public JPanel getPanel1() {
        return panel1;
    }
}

