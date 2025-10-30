package Sistema.presentation.Historico;

import Sistema.logic.Service;
import Sistema.logic.Receta;
import Sistema.logic.Paciente; // No se usa directamente aquí, pero es útil para entender la relación

import javax.swing.JOptionPane; // Para mostrar mensajes de error al usuario
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList; // Necesario si manejamos listas vacías

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

        refrescar(); // Llama a refrescar al iniciar para cargar los datos
    }

    /**
     * Busca recetas por nombre o ID del paciente.
     * Captura excepciones del servicio y muestra un mensaje al usuario.
     * @param filtro El texto a buscar (puede ser parte del nombre o un ID exacto).
     */
    public void buscar(String filtro) {
        try {
            List<Receta> todasLasRecetas = service.recetaAll(); // Obtener todas las recetas del servicio
            List<Receta> result = todasLasRecetas.stream()
                    .filter(r -> {
                        Paciente p = r.getPaciente();
                        return p != null && (
                                p.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                                        p.getId().equalsIgnoreCase(filtro) // Buscar ID exacto
                        );
                    })
                    .collect(Collectors.toList());
            model.setList(result);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar recetas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            model.setList(new ArrayList<>()); // Limpiar la lista en caso de error
        }
    }

    /**
     * Establece la receta seleccionada en el modelo y sus medicamentos asociados.
     * @param receta La receta seleccionada.
     */
    public void seleccionarReceta(Receta receta) {
        model.setCurrent(receta); // Esto debería activar el propertyChange con Model.CURRENT
        if (receta != null && receta.getMedicamentos() != null) {
            model.setListaMedic(receta.getMedicamentos()); // Asume que Receta.getMedicamentos() devuelve List<RecetaMedicamento>
        } else {
            model.setListaMedic(new ArrayList<>()); // Limpiar la lista de medicamentos si no hay receta o medicamentos
        }
    }

    /**
     * Refresca la lista completa de recetas en el modelo.
     * Captura excepciones del servicio y muestra un mensaje al usuario.
     */
    public void refrescar() {
        try {
            model.setList(service.recetaAll());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar el historial de recetas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            model.setList(new ArrayList<>()); // Limpiar la lista en caso de error
        }
    }
}