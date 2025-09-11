package Sistema.presentation.prescribir.buscarpaciente;

import Sistema.logic.Paciente;
import Sistema.logic.Service;
import Sistema.presentation.prescribir.Model; // 👈 Importamos el model de prescribir

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Controller {
    private Model prescribirModel;   // 👈 usamos el model de prescribir
    private View view;
    private JLabel pacienteSeleccionadoEnPrescribir;

    public Controller(Model prescribirModel, View view, JLabel pacienteSeleccionadoEnPrescribir) {
        this.prescribirModel = prescribirModel;
        this.view = view;
        this.pacienteSeleccionadoEnPrescribir = pacienteSeleccionadoEnPrescribir;

        // Acción cuando se escribe en el campo de búsqueda
        view.getBuscarNombreConFiltro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPacientes();
            }
        });

        // Acción al seleccionar un paciente en la tabla
        view.getResultadosBusqueda().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = view.getResultadosBusqueda().getSelectedRow();
                if (fila >= 0) {
                    try {
                        String id = (String) view.getResultadosBusqueda().getValueAt(fila, 0); // columna 0 = ID
                        Paciente seleccionado = Service.instance().pacienteRead(id);

                        // 👈 guardamos el puntero en el modelo de prescribir
                        prescribirModel.setPacienteSeleccionado(seleccionado);

                        // 👈 mostramos el nombre en el label
                        pacienteSeleccionadoEnPrescribir.setText(seleccionado.getNombre());

                        view.dispose(); // opcional: cerrar la ventana de búsqueda
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, "Error al seleccionar paciente: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private void buscarPacientes() {
        String filtro = view.getBuscarNombreConFiltro().getText();
        String opcion = (String) view.getFiltrarBusqueda().getSelectedItem();

        List<Paciente> resultados;
        if ("ID".equalsIgnoreCase(opcion)) {
            resultados = Service.instance().buscarPacientesPorId(filtro);
        } else {
            resultados = Service.instance().buscarPacientesPorNombre(filtro);
        }

        actualizarTabla(resultados);
    }

    private void actualizarTabla(List<Paciente> resultados) {
        String[] columnas = {"ID", "Nombre", "Fecha Nac.", "Teléfono"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);

        for (Paciente p : resultados) {
            Object[] row = {p.getId(), p.getNombre(), p.getFechaNacimiento(), p.getTelefono()};
            tableModel.addRow(row);
        }

        view.getResultadosBusqueda().setModel(tableModel);
    }
}
