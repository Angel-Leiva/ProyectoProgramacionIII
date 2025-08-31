package Sistema.data;

import Sistema.logic.Medico;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Medico> medicos;

    public Data() {
        medicos = new ArrayList<>();
        medicos.add(new Medico("101", "101", "Dr. Juan Pérez", "Pediatría"));
        medicos.add(new Medico("102", "102", "Dra. María López", "Cardiología"));
    }

    public List<Medico> getMedicos() {
        return medicos;
    }
}