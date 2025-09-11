package Sistema.presentation.prescribir;

import Sistema.logic.Paciente;
import Sistema.logic.Receta;
import Sistema.logic.RecetaMedicamento;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private Paciente pacienteSeleccionado;
    private Receta recetaActual;
    private String fechaRetiro;

    public Model() {
        this.recetaActual = new Receta();
    }

    // ===================== Paciente =====================
    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    public void setPacienteSeleccionado(Paciente pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
        if (recetaActual != null) {
            recetaActual.setPaciente(pacienteSeleccionado); // 👉 se guarda el puntero en la receta
        }
    }

    // ===================== Medicamentos =====================
    public List<RecetaMedicamento> getMedicamentos() {
        return recetaActual.getMedicamentos();
    }

    public void agregarMedicamento(RecetaMedicamento medicamento) {
        recetaActual.addMedicamento(medicamento);
    }

    public void eliminarMedicamento(int index) {
        if (index >= 0 && index < recetaActual.getMedicamentos().size()) {
            recetaActual.getMedicamentos().remove(index);
        }
    }

    // ===================== Fecha =====================
    public String getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
        // también la reflejamos en la receta
        if (recetaActual != null && fechaRetiro != null && !fechaRetiro.isEmpty()) {
            this.recetaActual.setFechaRetiro(java.time.LocalDate.parse(fechaRetiro));
        }
    }

    // ===================== Receta completa =====================
    public Receta getRecetaActual() {
        return recetaActual;
    }

    public void setRecetaActual(Receta recetaActual) {
        this.recetaActual = recetaActual;
    }
}

