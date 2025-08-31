package Sistema.presentation.medicos;

import Sistema.logic.Medico;
import Sistema.logic.Service;

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

        // cargar lista inicial
        refrescar();
    }

    public void guardar(String id, String clave, String nombre, String especialidad) throws Exception {
        Medico m = new Medico(id, clave, nombre, especialidad);
        service.medicoCreate(m);
        model.setCurrent(m);
        refrescar();
    }

    public void buscar(String filtro) throws Exception {
        List<Medico> result = service.medicoAll().stream()
                .filter(m -> m.getNombre().toLowerCase().contains(filtro.toLowerCase())
                        || m.getId().contains(filtro))
                .collect(Collectors.toList());
        model.setList(result);
    }

    public void borrar(String id) throws Exception {
        Medico m = service.medicoRead(id);
        service.medicoAll().remove(m);
        refrescar();
    }

    public void refrescar() {
        model.setList(service.medicoAll());
    }
}
