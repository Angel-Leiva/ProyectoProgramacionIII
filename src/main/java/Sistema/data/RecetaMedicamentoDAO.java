// Sistema.data.RecetaMedicamentoDAO.java
package Sistema.data;

import Sistema.logic.RecetaMedicamento;
import Sistema.logic.Medicamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecetaMedicamentoDAO {

    private MedicamentoDAO medicamentoDAO = new MedicamentoDAO(); // Para obtener el objeto Medicamento

    /**
     * Inserta un medicamento en una receta. Este método está diseñado para ser llamado
     * dentro de una transacción junto con la inserción de la receta.
     * @param conn La conexión a la base de datos (parte de una transacción).
     * @param numeroReceta El número de la receta a la que pertenece el medicamento.
     * @param rm El objeto RecetaMedicamento a insertar.
     * @throws Exception Si ocurre un error de SQL.
     */
    public void insertar(Connection conn, int numeroReceta, RecetaMedicamento rm) throws Exception {
        String sql = "INSERT INTO Receta_Medicamento (numero_receta, codigo_medicamento, cantidad, dias, indicaciones) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, numeroReceta);
            pstmt.setString(2, rm.getMedicamento().getCodigo());
            pstmt.setInt(3, rm.getCantidad());
            pstmt.setInt(4, rm.getDias());
            pstmt.setString(5, rm.getIndicaciones());
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt); // Cerrar solo el PreparedStatement, no la conexión
        }
    }

    /**
     * Busca todos los medicamentos asociados a una receta específica.
     * @param numeroReceta El número de la receta.
     * @return Una lista de objetos RecetaMedicamento.
     * @throws Exception Si ocurre un error de SQL o conexión.
     */
    public List<RecetaMedicamento> buscarPorReceta(int numeroReceta) throws Exception {
        List<RecetaMedicamento> medicamentosReceta = new ArrayList<>();
        String sql = "SELECT codigo_medicamento, cantidad, dias, indicaciones FROM Receta_Medicamento WHERE numero_receta = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, numeroReceta);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String codigoMedicamento = rs.getString("codigo_medicamento");
                Medicamento medicamento = medicamentoDAO.buscarPorCodigo(codigoMedicamento); // Obtener el objeto Medicamento completo

                if (medicamento != null) {
                    RecetaMedicamento rm = new RecetaMedicamento(
                            medicamento,
                            rs.getInt("cantidad"),
                            rs.getInt("dias"),
                            rs.getString("indicaciones")
                    );
                    medicamentosReceta.add(rm);
                }
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
        return medicamentosReceta;
    }

    /**
     * Elimina todos los medicamentos asociados a una receta.
     * @param conn La conexión a la base de datos (parte de una transacción).
     * @param numeroReceta El número de la receta.
     * @throws Exception Si ocurre un error de SQL.
     */
    public void eliminarPorReceta(Connection conn, int numeroReceta) throws Exception {
        String sql = "DELETE FROM Receta_Medicamento WHERE numero_receta = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, numeroReceta);
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
        }
    }
    // No es común tener un 'actualizar' para RecetaMedicamento individualmente,
    // se suele eliminar y reinsertar o actualizar todos en conjunto para una receta.
}