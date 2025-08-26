package Sistema.presentation.personas;

import Sistema.logic.Persona;
import Sistema.logic.Service;

public class Controller {
    View view;
    Model model;
    Service service;;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.service = Service.instance();

        view.setController(this);
        view.setModel(model);
    }

    public void read(String id) throws Exception {
        Persona e = new Persona();
        e.setId(id);
        model.setCurrent(Service.instance().read(e));
    }

    public void guardar(String id, String nombre, char sexo, String estado) throws Exception {
        Persona p = new Persona();
        p.setId(id);
        p.setNombre(nombre);
        p.setSexo(sexo);
        p.setEstadoCivil(estado);

        service.create(p);   // llama al service
        model.setCurrent(p); // actualiza el modelo
    }

}