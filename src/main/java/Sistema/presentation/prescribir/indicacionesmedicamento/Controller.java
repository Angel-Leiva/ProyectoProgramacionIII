package Sistema.presentation.prescribir.indicacionesmedicamento;

import Sistema.logic.Medicamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Controller {
    private Model model;
    private View view;
    private JTable listaMedicamentosPrescribir;

    public Controller(Medicamento medicamento, JTable listaMedicamentosPrescribir) {
        this.model = new Model(medicamento);
        this.view = new View();
        this.listaMedicamentosPrescribir = listaMedicamentosPrescribir;

        view.getGuardar().addActionListener(e -> guardar());
        view.getCancelar().addActionListener(e -> view.dispose());

        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    private void guardar() {
        model.setCantidad((Integer) view.getCantidadDeMedicamento().getValue());
        model.setDuracionDias((Integer) view.getDuracionDias().getValue());
        model.setIndicaciones(view.getIndicacionesMedicamento().getText());

        DefaultTableModel tableModel = (DefaultTableModel) listaMedicamentosPrescribir.getModel();
        Object[] row = {
                model.getMedicamento().getCodigo(),
                model.getMedicamento().getNombre(),
                model.getCantidad(),
                model.getDuracionDias(),
                model.getIndicaciones()
        };
        tableModel.addRow(row);

        view.dispose();
    }
}
