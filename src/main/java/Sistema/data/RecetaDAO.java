// Sistema.data.RecetaDAO.java (COMPLETA)
package Sistema.data;

import Sistema.logic.Receta;
import Sistema.logic.Medico;
import Sistema.logic.Paciente;
import Sistema.logic.RecetaMedicamento;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecetaDAO {

    private MedicoDAO medicoDAO = new MedicoDAO();
    private PacienteDAO pacienteDAO = new PacienteDAO();
    private RecetaMedicamentoDAO recetaMedicamentoDAO = new RecetaMedicamentoDAO();

    /**
     * Inserta una nueva receta en la base de datos y sus medicamentos asociados.
     * @param receta El objeto Receta a insertar.
     * @return El numero_receta generado por la BD.
     * @throws Exception Si ocurre un error de SQL o conexión.
     */
    public int insertar(Receta receta) throws Exception {
        String sqlReceta = "INSERT INTO Receta (fecha, id_medico, id_paciente, entregada) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmtReceta = null;
        ResultSet rs = null;
        int numeroRecetaGenerado = 0;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            pstmtReceta = conn.prepareStatement(sqlReceta, Statement.RETURN_GENERATED_KEYS);
            pstmtReceta.setDate(1, java.sql.Date.valueOf(receta.getFecha()));
            pstmtReceta.setString(2, receta.getMedico().getId());
            pstmtReceta.setString(3, receta.getPaciente().getId());
            pstmtReceta.setBoolean(4, receta.isEntregada());
            pstmtReceta.executeUpdate();

            rs = pstmtReceta.getGeneratedKeys();
            if (rs.next()) {
                numeroRecetaGenerado = rs.getInt(1);
                receta.setNumeroReceta(numeroRecetaGenerado); // Actualiza el objeto Receta con el ID generado
            } else {
                throw new SQLException("Error al obtener el número de receta generado, no se insertó.");
            }

            // Insertar los medicamentos de la receta en la tabla Receta_Medicamento
            for (RecetaMedicamento rm : receta.getMedicamentos()) {
                recetaMedicamentoDAO.insertar(conn, numeroRecetaGenerado, rm); // Pasamos la conexión
            }

            conn.commit(); // Confirmar transacción
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Deshacer en caso de error
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            throw new Exception("Error al insertar receta y sus medicamentos: " + e.getMessage(), e);
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(pstmtReceta);
            DatabaseConnection.closeConnection(conn);
        }
        return numeroRecetaGenerado;
    }

    /**
     * Busca una receta por su número, incluyendo los objetos Medico y Paciente asociados,
     * y la lista de RecetaMedicamento.
     * @param numeroReceta El número de la receta a buscar.
     * @return El objeto Receta encontrado, o null si no existe.
     * @throws Exception Si ocurre un error de SQL o conexión.
     */
    public Receta buscarPorNumero(int numeroReceta) throws Exception {
        String sql = "SELECT numero_receta, fecha, id_medico, id_paciente, entregada FROM Receta WHERE numero_receta = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Receta receta = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, numeroReceta);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                // Obtener Medico y Paciente
                Medico medico = medicoDAO.buscarPorId(rs.getString("id_medico"));
                Paciente paciente = pacienteDAO.buscarPorId(rs.getString("id_paciente"));

                receta = new Receta(
                        rs.getInt("numero_receta"),
                        medico,
                        paciente,
                        rs.getDate("fecha").toLocalDate(),
                        rs.getBoolean("entregada")
                );

                // Cargar los medicamentos asociados a esta receta
                List<RecetaMedicamento> medicamentos = recetaMedicamentoDAO.buscarPorReceta(numeroReceta);
                receta.setMedicamentos(medicamentos);
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
        return receta;
    }

    /**
     * Obtiene una lista de todas las recetas, cargando sus médicos, pacientes y medicamentos.
     * @return Una lista de objetos Receta.
     * @throws Exception Si ocurre un error de SQL o conexión.
     */
    public List<Receta> buscarTodas() throws Exception {
        List<Receta> recetas = new ArrayList<>();
        String sql = "SELECT numero_receta, fecha, id_medico, id_paciente, entregada FROM Receta ORDER BY fecha DESC";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Medico medico = medicoDAO.buscarPorId(rs.getString("id_medico"));
                Paciente paciente = pacienteDAO.buscarPorId(rs.getString("id_paciente"));

                Receta receta = new Receta(
                        rs.getInt("numero_receta"),
                        medico,
                        paciente,
                        rs.getDate("fecha").toLocalDate(),
                        rs.getBoolean("entregada")
                );
                // Cargar los medicamentos para cada receta
                receta.setMedicamentos(recetaMedicamentoDAO.buscarPorReceta(receta.getNumeroReceta()));
                recetas.add(receta);
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
        return recetas;
    }

    /**
     * Actualiza una receta existente y sus medicamentos asociados.
     * Elimina los medicamentos viejos y reinserta los nuevos.
     * @param receta El objeto Receta con la información actualizada.
     * @throws Exception Si ocurre un error de SQL o conexión.
     */
    public void actualizar(Receta receta) throws Exception {
        String sqlReceta = "UPDATE Receta SET fecha = ?, id_medico = ?, id_paciente = ?, entregada = ? WHERE numero_receta = ?";
        Connection conn = null;
        PreparedStatement pstmtReceta = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            pstmtReceta = conn.prepareStatement(sqlReceta);
            pstmtReceta.setDate(1, java.sql.Date.valueOf(receta.getFecha()));
            pstmtReceta.setString(2, receta.getMedico().getId());
            pstmtReceta.setString(3, receta.getPaciente().getId());
            pstmtReceta.setBoolean(4, receta.isEntregada());
            pstmtReceta.setInt(5, receta.getNumeroReceta());
            pstmtReceta.executeUpdate();

            // Eliminar los medicamentos actuales de la receta y reinsertar los nuevos
            recetaMedicamentoDAO.eliminarPorReceta(conn, receta.getNumeroReceta());
            for (RecetaMedicamento rm : receta.getMedicamentos()) {
                recetaMedicamentoDAO.insertar(conn, receta.getNumeroReceta(), rm);
            }

            conn.commit(); // Confirmar transacción
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Deshacer en caso de error
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            throw new Exception("Error al actualizar receta y sus medicamentos: " + e.getMessage(), e);
        } finally {
            DatabaseConnection.closeStatement(pstmtReceta);
            DatabaseConnection.closeConnection(conn);
        }
    }

    /**
     * Elimina una receta y todos sus medicamentos asociados.
     * @param numeroReceta El número de la receta a eliminar.
     * @throws Exception Si ocurre un error de SQL o conexión.
     */
    public void eliminar(int numeroReceta) throws Exception {
        String sqlReceta = "DELETE FROM Receta WHERE numero_receta = ?";
        Connection conn = null;
        PreparedStatement pstmtReceta = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // Primero eliminar los medicamentos de la receta (por Foreign Key CASCADE o manual)
            recetaMedicamentoDAO.eliminarPorReceta(conn, numeroReceta);

            // Luego eliminar la receta principal
            pstmtReceta = conn.prepareStatement(sqlReceta);
            pstmtReceta.setInt(1, numeroReceta);
            pstmtReceta.executeUpdate();

            conn.commit(); // Confirmar transacción
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Deshacer en caso de error
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            throw new Exception("Error al eliminar receta y sus medicamentos: " + e.getMessage(), e);
        } finally {
            DatabaseConnection.closeStatement(pstmtReceta);
            DatabaseConnection.closeConnection(conn);
        }
    }
}