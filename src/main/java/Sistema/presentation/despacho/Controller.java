package Sistema.presentation.despacho;

import Sistema.logic.Receta;
import Sistema.presentation.despacho.obcionDespacho.*;

import javax.swing.table.DefaultTableModel;

public class Controller {
    private Model model;
    private View view;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        initListeners();
        refrescar();
    }

    private void initListeners() {
        view.getListaRecetas().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = view.getListaRecetas().getSelectedRow();
                if (fila >= 0) {
                    Receta receta = model.getRecetas().get(fila);
                    abrirOpciones(receta);
                }
            }
        });
    }

    private void abrirOpciones(Receta receta) {
        // Crear la ventana de opciones
        Sistema.presentation.despacho.obcionDespacho.View opView =
                new Sistema.presentation.despacho.obcionDespacho.View();
        Sistema.presentation.despacho.obcionDespacho.Model opModel =
                new Sistema.presentation.despacho.obcionDespacho.Model();
        opModel.setReceta(receta);

        new ControllerObcion(opView, opModel, this);

        opView.setLocationRelativeTo(view);
        opView.setVisible(true);
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

