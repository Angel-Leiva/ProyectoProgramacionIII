package Sistema.presentation.despacho;

import Sistema.logic.Receta;

import javax.swing.table.DefaultTableModel;

public class Controller {
    private Model model;
    private View view;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        refrescar();
    }

    public void refrescar() {
        String[] columnNames = {"Paciente", "Fecha Retiro", "Estado", "Medicamentos"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Receta r : model.getRecetas()) {
            Object[] row = {
                    (r.getPaciente() != null ? r.getPaciente().getNombre() : "Sin paciente"),
                    r.getFechaRetiro(),
                    r.getEstadoTexto(),
                    r.getMedicamentos().size()
            };
            tableModel.addRow(row);
        }

        view.getListaRecetas().setModel(tableModel);
    }
}

