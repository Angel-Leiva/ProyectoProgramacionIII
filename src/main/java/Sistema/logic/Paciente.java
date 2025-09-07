package Sistema.logic;

import java.time.LocalDate;

public class Paciente extends Usuario{
    private LocalDate fechaNacimiento;
    private String telefono;

    public Paciente(String idN, String nombreN, LocalDate fechaNacimiento, String telefono) {
        super(idN, "", nombreN, '3');
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
    }

    public Paciente() {
        this("", "", LocalDate.now(), "");
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
