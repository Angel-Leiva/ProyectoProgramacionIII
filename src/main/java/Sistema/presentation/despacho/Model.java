package Sistema.presentation.despacho;

import Sistema.logic.Receta;
import Sistema.logic.Service;

import java.util.List;

public class Model {
    private Service service;

    public Model() {
        this.service = Service.instance();
    }

    public List<Receta> getRecetas() {
        return service.recetaAll(); // siempre consulta al Service
    }
}

