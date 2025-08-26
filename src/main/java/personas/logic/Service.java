package personas.logic;

import personas.data.Data;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Data data;

    private Service() {
        data = new Data();
    }

    // =============== PERSONAS ===============
    public void create(Persona e) throws Exception {
         Persona result = data.getPersonas().stream()
                .filter(i -> i.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getPersonas().add(e);
        } else {
            throw new Exception("Persona ya existe");
        }
    }

    public Persona read(Persona e) throws Exception {
        Persona result = data.getPersonas().stream()
                .filter(i -> i.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) {
            return result;
        } else {
            throw new Exception("Persona no existe");
        }
    }
 }
