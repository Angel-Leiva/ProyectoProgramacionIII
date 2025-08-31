package Sistema.data;

import Sistema.logic.Medico;
import Sistema.logic.Farmaceuta;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Medico> medicos;
    private List<Farmaceuta> farmaceutas;

    public Data() {
        //Medico
        medicos = new ArrayList<>();
        medicos.add(new Medico("101", "101", "Dr. Juan Pérez", "Pediatría"));
        medicos.add(new Medico("102", "102", "Dra. María López", "Cardiología"));
        //Farma
        farmaceutas = new ArrayList<>();
        farmaceutas.add(new Farmaceuta("201", "201", "Ana Ramírez"));
        farmaceutas.add(new Farmaceuta("202", "202", "Carlos Jiménez"));
    }

    public List<Medico> getMedicos() {
        return medicos;
    }
    public List<Farmaceuta> getFarmaceutas() {
        return farmaceutas;
    }
}