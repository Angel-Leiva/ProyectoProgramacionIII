package Sistema.presentation.farmaceutas;

import Sistema.logic.Farmaceuta;
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

    public void guardar(String id, String nombre) {
        try {
            // Asumo que la clave inicial es el ID. Si hay un campo de clave diferente en la UI, ajústalo.
            Farmaceuta f = new Farmaceuta(id, id, nombre);
            service.farmaceutaCreate(f); // Puede lanzar Exception
            model.setCurrent(f);
            JOptionPane.showMessageDialog(null, "Farmaceuta guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            refrescar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar farmaceuta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscar(String filtro) {
        try {
            List<Farmaceuta> result = service.farmaceutaAll().stream() // Puede lanzar Exception
                    .filter(f -> f.getNombre().toLowerCase().contains(filtro.toLowerCase())
                            || f.getId().toLowerCase().contains(filtro.toLowerCase())) // Añadido .toLowerCase() para consistencia
                    .collect(Collectors.toList());
            model.setList(result);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar farmaceutas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            model.setList(new ArrayList<>()); // Limpiar la lista en caso de error
        }
    }

    public void borrar(String id) {
        try {
            // Confirmación antes de borrar (buena práctica)
            int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el farmaceuta " + id + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.farmaceutaDelete(id); // ¡CAMBIO CLAVE! Usar el nuevo método de eliminar
                JOptionPane.showMessageDialog(null, "Farmaceuta eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                refrescar();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar farmaceuta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refrescar() {
        try {
            model.setList(service.farmaceutaAll()); // Puede lanzar Exception
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los farmaceutas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            model.setList(new ArrayList<>()); // Limpiar la lista en caso de error
        }
    }

    // ================= CAMBIO DE CONTRASEÑA =================
    public boolean cambiarClave(String id, String nuevaClave) {
        try {
            boolean exito = service.cambiarClaveFarmaceuta(id, nuevaClave); // Puede lanzar Exception
            if (exito) {
                JOptionPane.showMessageDialog(null, "Contraseña actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar la contraseña.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
            return exito;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cambiar la contraseña: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
