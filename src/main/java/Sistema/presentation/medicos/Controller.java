package Sistema.presentation.medicos;

import Sistema.logic.Medico;
import Sistema.logic.Service;

import javax.swing.JOptionPane; // Para mostrar mensajes de error
import java.util.ArrayList; // Para manejar listas vacías en caso de error
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    View view;
    Model model;
    Service service;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.service = Service.instance();

        view.setController(this);
        view.setModel(model);

        refrescar(); // Puede lanzar Exception
    }

    public void guardar(String id, String clave, String nombre, String especialidad) {
        try {
            Medico m = new Medico(id, clave, nombre, especialidad);
            service.medicoCreate(m); // Puede lanzar Exception
            model.setCurrent(m);
            JOptionPane.showMessageDialog(null, "Médico guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            refrescar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar médico: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscar(String filtro) { // Este método ahora maneja la excepción internamente
        try {
            List<Medico> result = service.medicoAll().stream() // Puede lanzar Exception
                    .filter(m -> m.getNombre().toLowerCase().contains(filtro.toLowerCase())
                            || m.getId().toLowerCase().contains(filtro.toLowerCase())) // Añadido .toLowerCase() para consistencia
                    .collect(Collectors.toList());
            model.setList(result);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar médicos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            model.setList(new ArrayList<>()); // Limpiar la lista en caso de error
        }
    }

    public void borrar(String id) {
        try {
            // Confirmación antes de borrar (buena práctica)
            int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el médico " + id + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.medicoDelete(id); // ¡CAMBIO CLAVE! Usar el nuevo método de eliminar
                JOptionPane.showMessageDialog(null, "Médico eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                refrescar();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar médico: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refrescar() {
        try {
            model.setList(service.medicoAll()); // Puede lanzar Exception
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los médicos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            model.setList(new ArrayList<>()); // Limpiar la lista en caso de error
        }
    }

    public void cambiarClave(String id, String nuevaClave) {
        try {
            service.cambiarClaveMedico(id, nuevaClave); // Puede lanzar Exception
            Medico actualizado = service.medicoRead(id); // Puede lanzar Exception
            model.setCurrent(actualizado); // actualizamos el modelo
            JOptionPane.showMessageDialog(null, "Contraseña actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // No refrescamos la tabla completa aquí, ya que solo cambió la clave de uno.
            // Si la vista mostrara la clave (no recomendado), necesitaría refrescarse.
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cambiar la contraseña del médico: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}