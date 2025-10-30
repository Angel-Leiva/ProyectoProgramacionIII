package Sistema.presentation.pacientes;

import Sistema.logic.Paciente;
import Sistema.logic.Service;

import javax.swing.JOptionPane; // Para mostrar mensajes de error
import java.time.LocalDate;
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

    public void guardar(String id, String nombre, LocalDate fecha, String telefono) {
        try {
            Paciente p = new Paciente(id, nombre, fecha, telefono);
            service.pacienteCreate(p); // Puede lanzar Exception
            model.setCurrent(p);
            JOptionPane.showMessageDialog(null, "Paciente guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            refrescar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscar(String filtro) {
        try {
            // NOTA: Para eficiencia, si tu PacienteDAO tiene buscarPorNombre y buscarPorId
            // sería mejor usar service.buscarPacientesPorNombre(filtro)
            // o service.buscarPacientesPorId(filtro) en lugar de filtrar todo en memoria.
            List<Paciente> result = service.pacienteAll().stream() // Puede lanzar Exception
                    .filter(p -> p.getNombre().toLowerCase().contains(filtro.toLowerCase())
                            || p.getId().toLowerCase().contains(filtro.toLowerCase())) // Añadido .toLowerCase() para consistencia
                    .collect(Collectors.toList());
            model.setList(result);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar pacientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            model.setList(new ArrayList<>()); // Limpiar la lista en caso de error
        }
    }

    public void borrar(String id) {
        try {
            // Confirmación antes de borrar (buena práctica)
            int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el paciente " + id + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.pacienteDelete(id); // ¡CAMBIO CLAVE! Usar el nuevo método de eliminar
                JOptionPane.showMessageDialog(null, "Paciente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                refrescar();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refrescar() {
        try {
            model.setList(service.pacienteAll()); // Puede lanzar Exception
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los pacientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            model.setList(new ArrayList<>()); // Limpiar la lista en caso de error
        }
    }
}