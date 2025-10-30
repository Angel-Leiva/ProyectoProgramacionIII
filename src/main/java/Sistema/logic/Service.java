// Sistema.logic.Service.java (VERSION MODIFICADA PARA USAR DAOs)
package Sistema.logic;

// Importa tus DAOs
import Sistema.data.AdministradorDAO;
import Sistema.data.FarmaceutaDAO;
import Sistema.data.MedicoDAO;
import Sistema.data.PacienteDAO;
import Sistema.data.MedicamentoDAO;
import Sistema.data.RecetaDAO;
import Sistema.data.RecetaMedicamentoDAO; // Si necesitas operaciones directas aquí, aunque RecetaDAO lo maneja

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;

    // Sustituimos 'private DatabaseConnection data;' con instancias de los DAOs
    private AdministradorDAO administradorDAO;
    private MedicoDAO medicoDAO;
    private FarmaceutaDAO farmaceutaDAO;
    private PacienteDAO pacienteDAO;
    private MedicamentoDAO medicamentoDAO;
    private RecetaDAO recetaDAO;
    // RecetaMedicamentoDAO no se instancia directamente aquí porque RecetaDAO lo usa internamente.

    private Service() {
        // Inicializar los DAOs
        administradorDAO = new AdministradorDAO();
        medicoDAO = new MedicoDAO();
        farmaceutaDAO = new FarmaceutaDAO();
        pacienteDAO = new PacienteDAO();
        medicamentoDAO = new MedicamentoDAO();
        recetaDAO = new RecetaDAO();
    }

    public static Service instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    // ================= MÉDICOS =================
    public void medicoCreate(Medico m) throws Exception {
        // Antes: usaba lista en memoria. Ahora: usa MedicoDAO
        Medico result = medicoDAO.buscarPorId(m.getId()); // Busca si ya existe
        if (result == null) {
            medicoDAO.insertar(m); // Inserta en la DB
        } else {
            throw new Exception("Médico ya existe");
        }
    }

    public void medicoDelete(String id) throws Exception {
        Medico medico = medicoDAO.buscarPorId(id);
        if (medico == null) {
            throw new Exception("Médico a eliminar no existe.");
        }
        medicoDAO.eliminar(id); // Llama al método de tu DAO
    }

    public Medico medicoRead(String id) throws Exception {
        // Antes: usaba lista en memoria. Ahora: usa MedicoDAO
        Medico medico = medicoDAO.buscarPorId(id);
        if (medico == null) {
            throw new Exception("Médico no existe");
        }
        return medico;
    }

    public List<Medico> medicoAll() throws Exception { // Ahora puede lanzar Exception
        // Antes: usaba lista en memoria. Ahora: usa MedicoDAO
        return medicoDAO.buscarTodos();
    }


    // ================= CAMBIO DE CONTRASEÑA MÉDICO =================
    public void cambiarClaveMedico(String id, String nuevaClave) throws Exception {
        Medico medico = medicoRead(id); // Usa el método de Service, que a su vez usa el DAO
        medico.setClave(nuevaClave);
        medicoDAO.actualizar(medico); // Actualiza en la DB
    }

    // ================= FARMACEUTAS =================
    public void farmaceutaCreate(Farmaceuta f) throws Exception {
        Farmaceuta result = farmaceutaDAO.buscarPorId(f.getId());
        if (result == null) {
            farmaceutaDAO.insertar(f);
        } else {
            throw new Exception("Farmaceuta ya existe");
        }
    }
    public void farmaceutaDelete(String id) throws Exception {
        Farmaceuta farmaceuta = farmaceutaDAO.buscarPorId(id);
        if (farmaceuta == null) {
            throw new Exception("Farmaceuta a eliminar no existe.");
        }
        farmaceutaDAO.eliminar(id); // Llama al método de tu DAO
    }

    public Farmaceuta farmaceutaRead(String id) throws Exception {
        Farmaceuta farmaceuta = farmaceutaDAO.buscarPorId(id);
        if (farmaceuta == null) {
            throw new Exception("Farmaceuta no existe");
        }
        return farmaceuta;
    }

    public List<Farmaceuta> farmaceutaAll() throws Exception { // Ahora puede lanzar Exception
        return farmaceutaDAO.buscarTodos();
    }

    public boolean cambiarClaveFarmaceuta(String id, String nuevaClave) throws Exception {
        Farmaceuta f = farmaceutaRead(id);
        f.setClave(nuevaClave);
        farmaceutaDAO.actualizar(f); // Actualiza en la DB
        return true;
    }

    // ================= PACIENTES =================
    public void pacienteCreate(Paciente p) throws Exception {
        Paciente result = pacienteDAO.buscarPorId(p.getId());
        if (result == null) {
            pacienteDAO.insertar(p);
        } else {
            throw new Exception("Paciente ya existe");
        }
    }

    public void pacienteDelete(String id) throws Exception {
        Paciente paciente = pacienteDAO.buscarPorId(id);
        if (paciente == null) {
            throw new Exception("Paciente a eliminar no existe.");
        }
        pacienteDAO.eliminar(id); // Llama al método de tu DAO
    }

    public Paciente pacienteRead(String id) throws Exception {
        Paciente paciente = pacienteDAO.buscarPorId(id);
        if (paciente == null) {
            throw new Exception("Paciente no existe");
        }
        return paciente;
    }

    public List<Paciente> pacienteAll() throws Exception { // Ahora puede lanzar Exception
        return pacienteDAO.buscarTodos();
    }

    public List<Paciente> buscarPacientesPorNombre(String filtro) throws Exception { // Ahora puede lanzar Exception
        // Esta funcionalidad requiere buscar por nombre en la DB.
        // Si tu PacienteDAO no tiene un método buscarPorNombre, lo necesitará.
        // Por ahora, traemos todos y filtramos en memoria, pero NO ES EFICIENTE para muchos datos.
        // Lo ideal sería añadir un método like 'buscarPorNombre(String nombre)' a PacienteDAO
        return pacienteDAO.buscarTodos().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(filtro.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Paciente> buscarPacientesPorId(String id) throws Exception { // Ahora puede lanzar Exception
        // Igual que buscarPorNombre, lo ideal es que PacienteDAO tenga un método buscarPorId(String id) que devuelva una lista
        // si se espera más de uno (aunque id es PK, asi que debería ser 0 o 1).
        // Si solo se espera uno, tu `pacienteRead(id)` es suficiente.
        // Como 'id' es PRIMARY KEY, debería devolver un máximo de 1 paciente.
        Paciente p = pacienteDAO.buscarPorId(id);
        List<Paciente> result = new ArrayList<>();
        if (p != null) {
            result.add(p);
        }
        return result;
    }

    public List<Paciente> getPacientes() throws Exception { // Alias para pacienteAll(), puede lanzar Exception
        return pacienteAll();
    }

    // ================= MEDICAMENTOS =================
    public void medicamentoCreate(Medicamento m) throws Exception {
        Medicamento result = medicamentoDAO.buscarPorCodigo(m.getCodigo());
        if (result == null) {
            medicamentoDAO.insertar(m);
        } else {
            throw new Exception("Medicamento ya existe");
        }
    }

    public void medicamentoDelete(String codigo) throws Exception {
        Medicamento medicamento = medicamentoDAO.buscarPorCodigo(codigo);
        if (medicamento == null) {
            throw new Exception("Medicamento a eliminar no existe.");
        }
        medicamentoDAO.eliminar(codigo); // Llama al método de tu DAO
    }

    public Medicamento medicamentoRead(String codigo) throws Exception {
        Medicamento medicamento = medicamentoDAO.buscarPorCodigo(codigo);
        if (medicamento == null) {
            throw new Exception("Medicamento no existe");
        }
        return medicamento;
    }

    public List<Medicamento> medicamentoAll() throws Exception { // Ahora puede lanzar Exception
        return medicamentoDAO.buscarTodos();
    }

    // ================= RECETAS =================
    public void recetaCreate(Receta r) throws Exception { // Ahora puede lanzar Exception
        recetaDAO.insertar(r);
        // Si necesitas el numeroReceta generado, el método insertar de RecetaDAO ya actualiza el objeto 'r'
        // r.getNumeroReceta() te daría el ID
    }

    public void recetaUpdate(Receta r) throws Exception {
        // Podrías buscar primero para asegurarte que la receta existe
        Receta existingReceta = recetaDAO.buscarPorNumero(r.getNumeroReceta());
        if (existingReceta == null) {
            throw new Exception("Receta a actualizar no existe.");
        }
        recetaDAO.actualizar(r); // Llama al método de tu DAO
    }

    public Receta recetaRead(int numeroReceta) throws Exception { // Nuevo método para leer una receta por su número
        Receta receta = recetaDAO.buscarPorNumero(numeroReceta);
        if (receta == null) {
            throw new Exception("Receta no existe");
        }
        return receta;
    }

    public List<Receta> recetaAll() throws Exception { // Ahora puede lanzar Exception
        return recetaDAO.buscarTodas();
    }

    // Nota: El método eliminarRecetaMedicamento de tu clase Data original
    // ya no se usa directamente así, porque RecetaDAO maneja la lógica
    // de actualizar o eliminar medicamentos de una receta.
    // Si necesitas eliminar un medicamento específico de una receta,
    // tendrías que:
    // 1. Leer la receta completa (recetaRead).
    // 2. Modificar la lista de medicamentos del objeto Receta.
    // 3. Llamar a recetaDAO.actualizar(receta) para que se encargue de la DB.
    public void eliminarRecetaMedicamento(int numeroReceta, String codigoMedicamento) throws Exception {
        Receta receta = recetaRead(numeroReceta);
        if (receta == null) {
            throw new Exception("No se encontró la receta con el número: " + numeroReceta);
        }

        boolean eliminado = receta.getMedicamentos().removeIf(rm ->
                rm.getMedicamento().getCodigo().equalsIgnoreCase(codigoMedicamento)
        );

        if (!eliminado) {
            throw new Exception("No se encontró el medicamento '" + codigoMedicamento + "' en la receta " + numeroReceta);
        }

        // Ahora actualizamos la receta en la base de datos para que se refleje el cambio
        recetaDAO.actualizar(receta);
        System.out.println("Medicamento " + codigoMedicamento + " eliminado de la receta " + numeroReceta);
    }

    // ================= ADMINISTRADORES =================
    public List<Administrador> administradorAll() throws Exception { // Ahora puede lanzar Exception
        return administradorDAO.buscarTodos();
    }

    public Administrador administradorRead(String id) throws Exception {
        Administrador admin = administradorDAO.buscarPorId(id);
        if (admin == null) {
            throw new Exception("Administrador no existe");
        }
        return admin;
    }

    // ================= Autenticación (LOGIN) =================
    // Este método es crucial para el login, ya que Usuario no tiene tabla propia.
    public Usuario login(String id, String clave) throws Exception {
        // Intentar como Administrador
        Administrador admin = administradorDAO.buscarPorId(id);
        if (admin != null && admin.getClave().equals(clave)) {
            return admin;
        }

        // Intentar como Medico
        Medico medico = medicoDAO.buscarPorId(id);
        if (medico != null && medico.getClave().equals(clave)) {
            return medico;
        }

        // Intentar como Farmaceuta
        Farmaceuta farmaceuta = farmaceutaDAO.buscarPorId(id);
        if (farmaceuta != null && farmaceuta.getClave().equals(clave)) {
            return farmaceuta;
        }

        // Intentar como Paciente (¡IMPORTANTE! Si Paciente necesita login,
        // su tabla en DB debe tener campos 'usuario' y 'contrasena'
        // y el PacienteDAO debe recuperarlos. Actualmente tu DB no los tiene para Paciente).
        // Si los pacientes no se loguean con clave, no se intenta aquí.
        // Si SÍ se loguean, y tu tabla Paciente en DB tiene 'usuario' y 'contrasena',
        // entonces el PacienteDAO debería tener un método para buscar por credenciales,
        // o tu clase logic.Paciente debería tener un 'clave'.
        // Aquí asumiría que pacienteDAO.buscarPorId(id) ya cargaría la clave si existiera.
        // Paciente paciente = pacienteDAO.buscarPorId(id);
        // if (paciente != null && paciente.getClave().equals(clave)) { // Necesitas un getClave() en Paciente
        //    return paciente;
        // }

        throw new Exception("Credenciales inválidas o usuario no encontrado.");
    }


    // ================= Otros métodos (adaptados) =================
    public int getCantEstadoRecetas(char estado) throws Exception { // Puede lanzar Exception
        int contador = 0;
        // La lógica de estado en la DB es solo 'entregada' (boolean)
        // Necesitamos mapear tu 'char estado' a 'boolean entregada'
        boolean estaEntregada = (estado == 'E'); // Solo 'E' es entregada en tu lógica original
        // Si tu lógica original tiene 'P', 'L', 'C' que no son 'E', se considerarían no entregadas.

        List<Receta> todasLasRecetas = recetaDAO.buscarTodas();
        for (Receta receta : todasLasRecetas) {
            if (receta.isEntregada() == estaEntregada) { // Comparamos con el boolean de la DB
                contador++;
            }
        }
        return contador;
    }
}