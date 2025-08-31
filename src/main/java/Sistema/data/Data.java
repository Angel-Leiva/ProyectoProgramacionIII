package Sistema.data;

import Sistema.logic.Medico;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Medico> medicos;

    public Data() {
        medicos = new ArrayList<>();
        medicos.add(new Medico("111", "Juan Perez", 'M', "Soltero"));
        medicos.add(new Medico("222", "Maria Lopez", 'F', "Casado"));
    }

    public List<Medico> getPersonas() {
        return medicos;
    }
}
