package Sistema.presentation.despacho;

import Sistema.logic.Receta;
import Sistema.presentation.despacho.obcionDespacho.*;
import Sistema.logic.Service; // Asegúrate de que Service esté importado si lo usas directamente

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane; // Para mostrar mensajes de error
import java.util.ArrayList; // Para manejar listas vacías en caso de error

public class Controller {
    private Model model;
    private View view;
    // Asumo que el Controller principal de despacho también tiene una instancia del servicio
    // private Service service; // Descomenta si necesitas usar service directamente aquí

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        // this.service = Service.instance(); // Descomenta si necesitas usar service directamente aquí

        initListeners();
        refrescar(); // Llamada inicial que ahora necesita try-catch
    }

    private void initListeners() {
        view.getListaRecetas().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = view.getListaRecetas().getSelectedRow();
                if (fila >= 0) {
                    try {
                        // Aquí se necesita un try-catch porque model.getRecetas() puede lanzar Exception
                        Receta receta = model.getRecetas().get(fila);
                        abrirOpciones(receta);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al seleccionar receta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        // Opcional: refrescar la lista si hay un problema
                        refrescar();
                    }
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

        // Pasamos 'this' (este Controller) a ControllerObcion para que pueda llamar a refrescar()
        new ControllerObcion(opView, opModel, this);

        opView.setLocationRelativeTo(view); // Centrar la ventana de opciones respecto a la ventana principal
        opView.setVisible(true);
    }

    public void refrescar() {
        String[] columnNames = {"Paciente", "Fecha Retiro", "Estado", "Medicamentos"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        try {
            // Aquí se necesita un try-catch porque model.getRecetas() puede lanzar Exception
            for (Receta r : model.getRecetas()) {
                // Asegúrate de que r.getFecha() y r.getEstadoTexto() existan y devuelvan String/Object
                // Si getEstado() devuelve 'char', deberías formatearlo a un String legible aquí o en la clase Receta.
                // Asumo que tienes un getEstadoTexto() en Receta que devuelve la representación String del char.
                Object[] row = {
                        (r.getPaciente() != null ? r.getPaciente().getNombre() : "Sin paciente"),
                        r.getFecha(), // Asumo que este es el método correcto para la fecha de emisión
                        getEstadoString(r.getEstado()), // Usar un helper para convertir char a String
                        r.getMedicamentos() != null ? r.getMedicamentos().size() : 0
                };
                tableModel.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar las recetas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            // Si el modelo falló al obtener las recetas, la tabla quedará vacía, lo cual es correcto.
        }

        view.getListaRecetas().setModel(tableModel);
    }

    // Helper para convertir el char de estado a un String legible
    private String getEstadoString(char estado) {
        switch (estado) {
            case 'P': return "Pendiente";
            case 'L': return "Lista";
            case 'E': return "Entregada";
            case 'C': return "Confeccionada";
            default: return "Desconocido";
        }
    }
}