package Sistema.logic;

import Sistema.data.Data;
import java.util.List;
import Sistema.logic.Farmaceuta;

public class Service {
    private static Service theInstance;
    private Data data;

    private Service() {
        data = new Data();
    }

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    // ================= MÉDICOS =================
    public void medicoCreate(Medico m) throws Exception {
        Medico result = data.getMedicos().stream()
                .filter(i -> i.getId().equals(m.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getMedicos().add(m);
        } else {
            throw new Exception("Médico ya existe");
        }
    }

    public Medico medicoRead(String id) throws Exception {
        return data.getMedicos().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Médico no existe"));
    }

    public List<Medico> medicoAll() {
        return data.getMedicos();
    }

    //Farma
    public void farmaceutaCreate(Farmaceuta f) throws Exception {
        Farmaceuta result = data.getFarmaceutas().stream()
                .filter(i -> i.getId().equals(f.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getFarmaceutas().add(f);
        } else {
            throw new Exception("Farmaceuta ya existe");
        }
    }

    public Farmaceuta farmaceutaRead(String id) throws Exception {
        return data.getFarmaceutas().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Farmaceuta no existe"));
    }

    public List<Farmaceuta> farmaceutaAll() {
        return data.getFarmaceutas();
    }
}