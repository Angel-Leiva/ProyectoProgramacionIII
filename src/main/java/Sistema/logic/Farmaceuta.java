package Sistema.logic;

public class Farmaceuta extends Usuario {

    public Farmaceuta() {
        super("", "", "", '2'); // por defecto tipo 2 = Farmaceuta
    }

    public Farmaceuta(String idN, String claveN, String nombreN) {
        super(idN, claveN, nombreN, '2');
    }


}
