package personas.data;

import personas.logic.Persona;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Persona> personas;

    public Data() {
        personas = new ArrayList<>();
        personas.add(new Persona("111", "Juan Perez", 'M', "Soltero"));
        personas.add(new Persona("222", "Maria Lopez", 'F', "Casado"));
    }

    public List<Persona> getPersonas() {
        return personas;
    }
}
