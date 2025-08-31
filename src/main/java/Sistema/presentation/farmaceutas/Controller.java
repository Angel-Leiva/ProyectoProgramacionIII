package Sistema.presentation.farmaceutas;

import Sistema.logic.Farmaceuta;
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

    public void guardar(String id, String nombre) throws Exception {
        Farmaceuta f = new Farmaceuta(id, id, nombre); // clave inicial = id
        service.farmaceutaCreate(f);
        model.setCurrent(f);
        refrescar();
    }

    public void buscar(String filtro) {
        List<Farmaceuta> result = service.farmaceutaAll().stream()
                .filter(f -> f.getNombre().toLowerCase().contains(filtro.toLowerCase())
                        || f.getId().contains(filtro))
                .collect(Collectors.toList());
        model.setList(result);
    }

    public void borrar(String id) throws Exception {
        Farmaceuta f = service.farmaceutaRead(id);
        service.farmaceutaAll().remove(f);
        refrescar();
    }

    public void refrescar() {
        model.setList(service.farmaceutaAll());
    }
}
