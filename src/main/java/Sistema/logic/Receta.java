package Sistema.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Receta {
    private Paciente paciente;
    private LocalDate fechaRetiro;
    private List<RecetaMedicamento> medicamentos = new ArrayList<>();

    public Receta() {
        this.paciente = null;
        this.fechaRetiro = LocalDate.now();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(LocalDate fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public List<RecetaMedicamento> getMedicamentos() {
        return medicamentos;
    }

    public void addMedicamento(RecetaMedicamento rm) {
        medicamentos.add(rm);
    }
}

