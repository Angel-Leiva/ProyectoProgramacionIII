package Sistema.logic;

public class Administrador extends Usuario {

    public Administrador() {
        super("", "", "", '0'); // por defecto tipo 0 = Admin
    }

    public Administrador(String idN, String claveN, String nombreN) {
        super(idN, claveN, nombreN, '0');
    }

}
