package Sistema.presentation.medicamentos;

import Sistema.logic.Medicamento;
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

    public void guardar(String codigo, String nombre, String presentacion) {
        try {
            Medicamento m = new Medicamento(codigo, nombre, presentacion);
            service.medicamentoCreate(m); // Puede lanzar Exception
            model.setCurrent(m);
            JOptionPane.showMessageDialog(null, "Medicamento guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            refrescar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar medicamento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscar(String filtro) { //Nombre
        try {
            List<Medicamento> result = service.medicamentoAll().stream() // Puede lanzar Exception
                    .filter(p -> p.getNombre().toLowerCase().contains(filtro.toLowerCase())
                            || p.getCodigo().toLowerCase().contains(filtro.toLowerCase())) // Añadido .toLowerCase() para consistencia
                    .collect(Collectors.toList());
            model.setList(result);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar medicamentos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            model.setList(new ArrayList<>()); // Limpiar la lista en caso de error
        }
    }

    public void borrar(String codigo) {
        try {
            // Confirmación antes de borrar (buena práctica)
            int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el medicamento " + codigo + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.medicamentoDelete(codigo); // ¡CAMBIO CLAVE! Usar el nuevo método de eliminar
                JOptionPane.showMessageDialog(null, "Medicamento eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                refrescar();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar medicamento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refrescar() {
        try {
            model.setList(service.medicamentoAll()); // Puede lanzar Exception
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los medicamentos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            model.setList(new ArrayList<>()); // Limpiar la lista en caso de error
        }
    }
}
