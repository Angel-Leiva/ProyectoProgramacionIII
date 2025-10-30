// Sistema.data.MedicoDAO.java
package Sistema.data;

import Sistema.logic.Medico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public void insertar(Medico medico) throws Exception {
        String sql = "INSERT INTO Medico (id, usuario, nombre, especialidad) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, medico.getId());
            pstmt.setString(2, medico.getClave()); // 'usuario' en BD es 'clave' en logic
            pstmt.setString(3, medico.getNombre());
            pstmt.setString(4, medico.getEspecialidad());
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public Medico buscarPorId(String id) throws Exception {
        String sql = "SELECT id, usuario, nombre, especialidad FROM Medico WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Medico medico = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                medico = new Medico(
                        rs.getString("id"),
                        rs.getString("usuario"), // Mapea a clave en tu clase logic
                        rs.getString("nombre"),
                        rs.getString("especialidad")
                );
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
        return medico;
    }

    public List<Medico> buscarTodos() throws Exception {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT id, usuario, nombre, especialidad FROM Medico";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                medicos.add(new Medico(
                        rs.getString("id"),
                        rs.getString("usuario"),
                        rs.getString("nombre"),
                        rs.getString("especialidad")
                ));
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
        return medicos;
    }
    // ... Implementar actualizar y eliminar de forma similar
    public void actualizar(Medico medico) throws Exception {
        String sql = "UPDATE Medico SET usuario = ?, nombre = ?, especialidad = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, medico.getClave());
            pstmt.setString(2, medico.getNombre());
            pstmt.setString(3, medico.getEspecialidad());
            pstmt.setString(4, medico.getId());
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public void eliminar(String id) throws Exception {
        String sql = "DELETE FROM Medico WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
    }
}