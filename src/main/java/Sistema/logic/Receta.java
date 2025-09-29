package Sistema.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Receta {
    private Paciente paciente;
    private LocalDate fechaRetiro;
    private List<RecetaMedicamento> medicamentos = new ArrayList<>();
    private char estado;

    public Receta() {
        this.paciente = null;
        this.fechaRetiro = LocalDate.now();
        this.estado = 'P';
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

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public String getEstadoTexto() {
        switch (estado) {
            case 'P': return "Pendiente";
            case 'L': return "Lista";
            case 'E': return "Entregada";
            case 'C': return "Confeccionada";
            default: return "Desconocido";
        }
    }
}

