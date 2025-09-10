package Sistema.presentation.login;

import Sistema.logic.Service;
import Sistema.logic.Usuario;

public class Model {
    public Usuario autenticar(String id, String clave) throws Exception {
        Service service = Service.instance();

        // Buscar en médicos
        try {
            Usuario m = service.medicoRead(id);
            if (m.getClave().equals(clave)) return m;
        } catch (Exception ignore) {}

        // Buscar en farmaceutas
        try {
            Usuario f = service.farmaceutaRead(id);
            if (f.getClave().equals(clave)) return f;
        } catch (Exception ignore) {}

        // Buscar en pacientes
        try {
            Usuario p = service.pacienteRead(id);
            if (p.getClave().equals(clave)) return p;
        } catch (Exception ignore) {}

        // Buscar en administradores
        for (var a : Service.instance().administradorAll()) {
            if (a.getId().equals(id) && a.getClave().equals(clave)) {
                return a;
            }
        }

        return null; // si no encontró nada
    }
}
