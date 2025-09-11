package Sistema.presentation.prescribir.agregarmedicamento;

import Sistema.logic.Medicamento;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Medicamento> resultados;

    public Model() {
        resultados = new ArrayList<>();
    }

    public List<Medicamento> getResultados() { return resultados; }
    public void setResultados(List<Medicamento> resultados) { this.resultados = resultados; }
}
