package Sistema.logic;

public class Medico extends Usuario {
    private String especialidad;

    public Medico(String idN, String claveN, String nombreN, String especialidadN) {
        super(idN, claveN, nombreN, '1');
        especialidad = especialidadN;
    }

    public Medico() {
        this("", "", "","");
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}
