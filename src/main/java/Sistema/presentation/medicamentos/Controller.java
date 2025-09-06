package Sistema.presentation.medicamentos;

import Sistema.logic.Medicamento;
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

        refrescar();
    }

    public void guardar(String codigo, String nombre, String presentacion) throws Exception {
        Medicamento m = new Medicamento(codigo, nombre, presentacion);
        service.medicamentoCreate(m);
        model.setCurrent(m);
        refrescar();
    }

    public void buscar(String filtro) { //Nombre
        List<Medicamento> result = service.medicamentoAll().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(filtro.toLowerCase())
                        || p.getCodigo().contains(filtro))
                .collect(Collectors.toList());
        model.setList(result);
    }

    public void borrar(String codigo) throws Exception {
        Medicamento m = service.medicamentoRead(codigo);
        service.medicamentoAll().remove(m);
        refrescar();
    }

    public void refrescar() {
        model.setList(service.medicamentoAll());
    }
}

