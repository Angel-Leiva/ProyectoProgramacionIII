package Sistema.presentation.pacientes;

import Sistema.logic.Paciente;
import Sistema.logic.Service;

import java.time.LocalDate;
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

    public void guardar(String id, String nombre, LocalDate fecha, String telefono) throws Exception {
        Paciente p = new Paciente(id, nombre, fecha, telefono);
        service.pacienteCreate(p);
        model.setCurrent(p);
        refrescar();
    }

    public void buscar(String filtro) {
        List<Paciente> result = service.pacienteAll().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(filtro.toLowerCase())
                        || p.getId().contains(filtro))
                .collect(Collectors.toList());
        model.setList(result);
    }

    public void borrar(String id) throws Exception {
        Paciente p = service.pacienteRead(id);
        service.pacienteAll().remove(p);
        refrescar();
    }

    public void refrescar() {
        model.setList(service.pacienteAll());
    }
}
