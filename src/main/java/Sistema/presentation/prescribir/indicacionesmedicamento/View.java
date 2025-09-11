package Sistema.presentation.prescribir.indicacionesmedicamento;

import javax.swing.*;

public class View extends JDialog {
    private JPanel panel1;
    private JSpinner cantidadDeMedicamento;
    private JSpinner duracionDias;
    private JButton guardar;
    private JButton cancelar;
    private JTextArea indicacionesMedicamento;

    public View() {
        super((JFrame) null, "Indicaciones del Medicamento", true);
        setContentPane(panel1);
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public JSpinner getCantidadDeMedicamento() { return cantidadDeMedicamento; }
    public JSpinner getDuracionDias() { return duracionDias; }
    public JButton getGuardar() { return guardar; }
    public JButton getCancelar() { return cancelar; }
    public JTextArea getIndicacionesMedicamento() { return indicacionesMedicamento; }
}
