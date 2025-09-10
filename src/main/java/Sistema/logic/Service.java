package Sistema.logic;

import Sistema.data.Data;
import java.util.List;

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

    //Paciente
    public void pacienteCreate(Paciente p) throws Exception {
        Paciente result = data.getPacientes().stream()
                .filter(i -> i.getId().equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getPacientes().add(p);
        } else {
            throw new Exception("Paciente ya existe");
        }
    }

    public Paciente pacienteRead(String id) throws Exception {
        return data.getPacientes().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Paciente no existe"));
    }

    public List<Paciente> pacienteAll() {
        return data.getPacientes();
    }

    //Medicamentos
    public void medicamentoCreate(Medicamento m) throws Exception {
        Medicamento result = data.getMedicamentos().stream()
                .filter(i -> i.getCodigo().equals(m.getCodigo()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getMedicamentos().add(m);
        } else {
            throw new Exception("Medicamento ya existe");
        }
    }

    public Medicamento medicamentoRead(String codigo) throws Exception {
        return data.getMedicamentos().stream()
                .filter(i -> i.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new Exception("Medicamento no existe"));
    }

    public List<Medicamento> medicamentoAll() {
        return data.getMedicamentos();
    }

    // ================= ADMINISTRADORES =================
    public List<Administrador> administradorAll() {
        return data.getAdministradores();
    }

    //Los administradores son datos quemados. No se crean.

    public Administrador administradorRead(String id) throws Exception {
        return data.getAdministradores().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Administrador no existe"));
    }


}