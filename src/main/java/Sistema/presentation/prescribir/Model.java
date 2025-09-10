package Sistema.presentation.prescribir;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private String pacienteSeleccionado;
    private List<String> medicamentos;
    private String fechaRetiro;

    public Model() {
        this.medicamentos = new ArrayList<>();
    }

    public String getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    public void setPacienteSeleccionado(String pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }

    public List<String> getMedicamentos() {
        return medicamentos;
    }

    public void agregarMedicamento(String medicamento) {
        this.medicamentos.add(medicamento);
    }

    public String getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }
}
