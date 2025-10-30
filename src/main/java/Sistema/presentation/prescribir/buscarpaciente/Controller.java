package Sistema.presentation.prescribir.buscarpaciente;

import Sistema.logic.Paciente;
import Sistema.logic.Service;
import Sistema.presentation.prescribir.Model; // 👈 Importamos el model de prescribir

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; // Para manejar listas vacías en caso de error
import java.util.List;

public class Controller {
    private Model prescribirModel;   //usamos el model de prescribir
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
                buscarPacientes(); // Este método ahora maneja sus propias excepciones
            }
        });

        // Acción al seleccionar un paciente en la tabla
        view.getResultadosBusqueda().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = view.getResultadosBusqueda().getSelectedRow();
                if (fila >= 0) {
                    try {
                        String id = (String) view.getResultadosBusqueda().getValueAt(fila, 0); // columna 0 = ID
                        Paciente seleccionado = Service.instance().pacienteRead(id); // Puede lanzar Exception

                        //guardamos el puntero en el modelo de prescribir
                        prescribirModel.setPacienteSeleccionado(seleccionado);

                        //mostramos el nombre en el label
                        pacienteSeleccionadoEnPrescribir.setText(seleccionado.getNombre());

                        view.dispose(); // opcional: cerrar la ventana de búsqueda
                    } catch (Exception ex) {
                        // Usar 'view' es apropiado si la View es un JDialog/JFrame, sino usar 'null'
                        JOptionPane.showMessageDialog(view, "Error al seleccionar paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        // Opcional: limpiar la selección o refrescar la tabla si hay un problema
                        actualizarTabla(new ArrayList<>()); // Limpiar tabla si hubo un error de lectura
                    }
                }
            }
        });
    }

    private void buscarPacientes() {
        String filtro = view.getBuscarNombreConFiltro().getText();
        String opcion = (String) view.getFiltrarBusqueda().getSelectedItem();

        List<Paciente> resultados = new ArrayList<>(); // Inicializar con lista vacía
        try {
            if ("ID".equalsIgnoreCase(opcion)) {
                resultados = Service.instance().buscarPacientesPorId(filtro); // Puede lanzar Exception
            } else {
                resultados = Service.instance().buscarPacientesPorNombre(filtro); // Puede lanzar Exception
            }
        } catch (Exception ex) {
            // Usar 'view' es apropiado si la View es un JDialog/JFrame, sino usar 'null'
            JOptionPane.showMessageDialog(view, "Error al buscar pacientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            // La lista 'resultados' permanecerá vacía, lo cual es correcto en caso de error
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