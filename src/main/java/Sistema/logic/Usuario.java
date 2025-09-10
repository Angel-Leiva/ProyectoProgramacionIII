package Sistema.logic;

public class Usuario {
    private char tipoUsuario;
    private String id;
    private String nombre;
    private String clave;

    /*
     * 0 - Admin
     * 1 - Doctor
     * 2 - Farmaceuta
     * 3 - Paciente
     * 4 - Nulo/Prueba
     */

    public Usuario() {
        this("","","", '4');
    }

    public Usuario(String id, String clave, String nombre, char tipoUsuario) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public char getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(char tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}
