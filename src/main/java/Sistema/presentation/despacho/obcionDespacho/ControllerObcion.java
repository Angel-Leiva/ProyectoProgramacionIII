package Sistema.presentation.despacho.obcionDespacho;

import Sistema.logic.Receta;
import Sistema.presentation.despacho.Controller;
import Sistema.logic.Service; // Importar Service

import javax.swing.*;
import java.util.ArrayList; // Para manejar el caso de lista de medicamentos vacía

public class ControllerObcion {
    private View view;
    private Model model;
    private Controller mainController; // El Controller del módulo Despacho principal
    private Service service; // Instancia del servicio

    public ControllerObcion(View view, Model model, Controller mainController) {
        this.view = view;
        this.model = model;
        this.mainController = mainController;
        this.service = Service.instance(); // Inicializar el servicio

        initListeners();
        cargarEstadoActual();
    }

    private void initListeners() {
        view.getGuardar().addActionListener(e -> {
            String estadoSeleccionado = (String) view.getEstadoReceta().getSelectedItem();
            Receta receta = model.getReceta();

            if (receta == null) {
                JOptionPane.showMessageDialog(view, "No hay receta seleccionada para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mapear el estado del JComboBox al char de la Receta
            switch (estadoSeleccionado) {
                case "Pendiente":
                    receta.setEstado('P');
                    receta.setEntregada(false); // Si 'Entregada' también se usa, establecerlo
                    break;
                case "Lista":
                    receta.setEstado('L');
                    receta.setEntregada(false);
                    break;
                case "Entregada":
                    receta.setEstado('E');
                    receta.setEntregada(true); // Una receta 'Entregada' marca isEntregada como true
                    break;
                case "Confeccionada":
                    receta.setEstado('C');
                    receta.setEntregada(false); // Asumimos que no está entregada si está 'Confeccionada'
                    break;
                default:
                    JOptionPane.showMessageDialog(view, "Estado de receta desconocido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            try {
                service.recetaUpdate(receta); // Guardar los cambios en la DB a través del Service
                JOptionPane.showMessageDialog(view, "Estado de la receta actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                mainController.refrescar(); // Refrescar la tabla principal de recetas
                view.dispose(); // Cerrar la ventana de opción
            } catch (Exception ex) {
                // Usar 'view' aquí es apropiado si 'View' es un JDialog/JFrame, sino usar 'null'
                JOptionPane.showMessageDialog(view, "Error al actualizar el estado de la receta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getCancelar().addActionListener(e -> view.dispose());
    }

    private void cargarEstadoActual() {
        Receta receta = model.getReceta();
        if (receta != null) {
            switch (receta.getEstado()) {
                case 'P': view.getEstadoReceta().setSelectedItem("Pendiente"); break;
                case 'L': view.getEstadoReceta().setSelectedItem("Lista"); break;
                case 'E': view.getEstadoReceta().setSelectedItem("Entregada"); break;
                case 'C': view.getEstadoReceta().setSelectedItem("Confeccionada"); break;
                default: view.getEstadoReceta().setSelectedItem("Pendiente"); // Estado por defecto si es desconocido
            }
        } else {
            // Si no hay receta, podrías deshabilitar el combobox o establecer un estado por defecto
            view.getEstadoReceta().setSelectedItem("Pendiente");
            view.getGuardar().setEnabled(false); // Opcional: deshabilitar guardar si no hay receta
        }
    }
}
