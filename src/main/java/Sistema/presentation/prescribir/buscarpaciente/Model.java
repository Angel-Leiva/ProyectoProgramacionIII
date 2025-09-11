package Sistema.presentation.prescribir.buscarpaciente;

import Sistema.logic.Paciente;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Paciente> resultados;

    public Model() {
        resultados = new ArrayList<>();
    }

    public List<Paciente> getResultados() {
        return resultados;
    }

    public void setResultados(List<Paciente> resultados) {
        this.resultados = resultados;
    }
}
