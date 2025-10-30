// Sistema.logic.Receta.java (VERIFICAR Y USAR ESTA VERSIÓN)
package Sistema.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Receta {
    private int numeroReceta; // Mapea a 'numero_receta' en la DB
    private Medico medico; // El objeto Medico completo asociado
    private Paciente paciente; // El objeto Paciente completo asociado
    private LocalDate fecha; // Mapea a 'fecha' en la DB
    private boolean entregada; // Mapea a 'entregada' en la DB
    private char estado;

    private List<RecetaMedicamento> medicamentos = new ArrayList<>();

    // Constructor vacío
    public Receta() {
        this.numeroReceta = 0; // Cuando es nueva, la BD le asigna un valor
        this.medico = new Medico(); // Inicializar para evitar NullPointerException si no se setea
        this.paciente = new Paciente(); // Inicializar
        this.fecha = LocalDate.now();
        this.entregada = false;
    }

    // Constructor para crear una nueva receta (antes de obtener el numeroReceta de la DB)
    public Receta(Medico medico, Paciente paciente, LocalDate fecha, boolean entregada) {
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
        this.entregada = entregada;
    }

    // Constructor completo para cuando se recupera de la BD
    public Receta(int numeroReceta, Medico medico, Paciente paciente, LocalDate fecha, boolean entregada) {
        this.numeroReceta = numeroReceta;
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
        this.entregada = entregada;
    }

    // --- Getters y Setters ---
    public int getNumeroReceta() { return numeroReceta; }
    public void setNumeroReceta(int numeroReceta) { this.numeroReceta = numeroReceta; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public boolean isEntregada() { return entregada; }
    public void setEntregada(boolean entregada) { this.entregada = entregada; }

    public List<RecetaMedicamento> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<RecetaMedicamento> medicamentos) { this.medicamentos = medicamentos; }
    public void addMedicamento(RecetaMedicamento rm) {
        this.medicamentos.add(rm);
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }


    // Métodos utilitarios para UI (si tu UI usa char 'P', 'E', 'C', etc.)
    public char getEstadoChar() {
        if (entregada) return 'E'; // Entregada
        // Puedes añadir más lógica si necesitas 'P' (Pendiente) vs 'L' (Lista) vs 'C' (Confeccionada)
        // Por ahora, solo tenemos 'entregada' en la DB.
        return 'P'; // Pendiente por defecto si no está entregada
    }

    public void setEstadoChar(char estado) {
        this.entregada = (estado == 'E');
        // Si tienes otros estados como 'L' o 'C' que quieres guardar,
        // necesitarías una columna 'estado' char en la tabla Receta de la BD.
    }

    public String getEstadoTexto() {
        return entregada ? "Entregada" : "Pendiente";
    }
}