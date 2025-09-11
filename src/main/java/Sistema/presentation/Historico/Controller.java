package Sistema.presentation.Historico;

import Sistema.logic.Service;
import Sistema.logic.Receta;
import Sistema.logic.Paciente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        refrescar();
    }

    public void buscar(String filtro) { //Busca por nombre o id
        List<Receta> result = service.recetaAll().stream()
                .filter(r -> r.getPaciente().getNombre().toLowerCase().contains(filtro.toLowerCase())
                        || r.getPaciente().getId().contains(filtro))
                .collect(Collectors.toList());
        model.setList(result);
    }

    public void seleccionarReceta(Receta receta) {
        model.setCurrent(receta); // Esto debería activar el propertyChange con Model.CURRENT
        model.setListaMedic(receta.getMedicamentos()); // <-- Asegúrate que esto exista
    }



    public void refrescar() {
        model.setList(service.recetaAll());
    }
}

