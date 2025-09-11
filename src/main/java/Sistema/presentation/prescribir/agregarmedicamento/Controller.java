package Sistema.presentation.prescribir.agregarmedicamento;

import Sistema.logic.Medicamento;
import Sistema.logic.Service;
import Sistema.presentation.prescribir.indicacionesmedicamento.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private Model model;
    private View view;
    private JTable listaMedicamentosPrescribir; // tabla de Prescribir

    public Controller(Model model, View view, JTable listaMedicamentosPrescribir) {
        this.model = model;
        this.view = view;
        this.listaMedicamentosPrescribir = listaMedicamentosPrescribir;

        view.getBuscarMedicamentoPorFiltro().addActionListener(e -> buscarMedicamentos());

        // Selección del medicamento en la tabla
        view.getListaConResultadosDeBusqueda().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = view.getListaConResultadosDeBusqueda().getSelectedRow();
                if (fila >= 0) {
                    String codigo = (String) view.getListaConResultadosDeBusqueda().getValueAt(fila, 0);
                    try {
                        Medicamento m = Service.instance().medicamentoRead(codigo);
                        // Abrir ventana de indicaciones, pasando la tabla de Prescribir
                        new Sistema.presentation.prescribir.indicacionesmedicamento.Controller(
                                m, listaMedicamentosPrescribir
                        );
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, ex.getMessage());
                    }
                }
            }
        });
    }

    private void buscarMedicamentos() {
        String filtro = view.getBuscarMedicamentoPorFiltro().getText();
        String opcion = (String) view.getFiltrarBusquedaPor().getSelectedItem();

        List<Medicamento> resultados;

        if ("Código".equalsIgnoreCase(opcion)) {
            resultados = Service.instance().medicamentoAll().stream()
                    .filter(m -> m.getCodigo().equalsIgnoreCase(filtro))
                    .collect(Collectors.toList());
        } else {
            resultados = Service.instance().medicamentoAll().stream()
                    .filter(m -> m.getNombre().toLowerCase().contains(filtro.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.setResultados(resultados);
        actualizarTabla();
    }

    private void actualizarTabla() {
        String[] columnas = {"Código", "Nombre", "Presentación"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);

        for (Medicamento m : model.getResultados()) {
            Object[] row = {m.getCodigo(), m.getNombre(), m.getPresentacion()};
            tableModel.addRow(row);
        }

        view.getListaConResultadosDeBusqueda().setModel(tableModel);
    }
}
