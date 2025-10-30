package Sistema.presentation.prescribir;

import Sistema.presentation.prescribir.buscarpaciente.*;

import java.awt.event.ActionEvent;
import Sistema.logic.*;
import javax.swing.*;
import java.time.LocalDate;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;


public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        initListeners();

        View prescribirView = new View();
        prescribirView.inicializarTablaMedicamentos();


        // Escuchar el botón buscar paciente
        this.view.getBuscaPaciContr().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirBuscarPaciente();
            }
        });

        // Escuchar el botón agregar medicamento
        this.view.getAgregamediContr().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirAgregarMedicamento();
            }
        });


    }

    private void abrirBuscarPaciente() {
        // Usamos el Model de prescribir
        Sistema.presentation.prescribir.buscarpaciente.View bpView =
                new Sistema.presentation.prescribir.buscarpaciente.View();

        Sistema.presentation.prescribir.buscarpaciente.Controller bpController =
                new Sistema.presentation.prescribir.buscarpaciente.Controller(
                        this.model,              // 👈 pasamos el model de prescribir
                        bpView,
                        view.getPasienteseleccionado() // 👈 JLabel donde se muestra el nombre
                );

        bpView.pack();
        bpView.setLocationRelativeTo(null);
        bpView.setVisible(true);
    }


    private void abrirAgregarMedicamento() {
        Sistema.presentation.prescribir.agregarmedicamento.Model amModel =
                new Sistema.presentation.prescribir.agregarmedicamento.Model();
        Sistema.presentation.prescribir.agregarmedicamento.View amView =
                new Sistema.presentation.prescribir.agregarmedicamento.View();
        new Sistema.presentation.prescribir.agregarmedicamento.Controller(amModel, amView, view.getListaMedicamentos());

        amView.pack();
        amView.setLocationRelativeTo(null);
        amView.setVisible(true);
    }



    private void initListeners() {
        view.getGuardar().addActionListener(e -> guardarReceta());
        view.getLimpiar().addActionListener(e -> limpiarFormulario());
        view.getDescartarMedi().addActionListener(e -> descartarMedicamento());
    }

    private void guardarReceta() {
        try {
            if (model.getPacienteSeleccionado() == null) {
                JOptionPane.showMessageDialog(view.getPanel1(), "Seleccione un paciente primero.");
                return;
            }

            Receta receta = new Receta();
            receta.setPaciente(model.getPacienteSeleccionado()); // guardamos el puntero al objeto

            if (view.getFechaRetiroMedi().getDate() != null) {
                receta.setFecha(view.getFechaRetiroMedi().getDate());
            }

            DefaultTableModel tableModel = (DefaultTableModel) view.getListaMedicamentos().getModel();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String codigo = (String) tableModel.getValueAt(i, 0); //IMPLEMENTAR ID PARA RECETA Y BORRAR LA REETA CON ESE ID
                Medicamento med = Service.instance().medicamentoRead(codigo);

                int cantidad = (int) tableModel.getValueAt(i, 2);
                int dias = (int) tableModel.getValueAt(i, 3);
                String indicaciones = (String) tableModel.getValueAt(i, 4);

                RecetaMedicamento rm = new RecetaMedicamento(med, cantidad, dias, indicaciones);
                receta.addMedicamento(rm);
            }

            Service.instance().recetaCreate(receta);

            JOptionPane.showMessageDialog(view.getPanel1(), "Receta guardada con éxito ✅");
            limpiarFormulario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel1(), "Error al guardar receta: " + ex.getMessage());
        }
    }



    private void limpiarFormulario() {
        model.setPacienteSeleccionado(null);
        view.getPasienteseleccionado().setText("");

        DefaultTableModel tableModel = (DefaultTableModel) view.getListaMedicamentos().getModel();
        tableModel.setRowCount(0);

        view.getFechaRetiroMedi().clear();
    }

    private void descartarMedicamento() {
        int fila = view.getListaMedicamentos().getSelectedRow();
        if (fila != -1) {
            DefaultTableModel tableModel = (DefaultTableModel) view.getListaMedicamentos().getModel();
            tableModel.removeRow(fila);
        } else {
            JOptionPane.showMessageDialog(view.getPanel1(), "Seleccione un medicamento para eliminar.");
        }
    }

}
