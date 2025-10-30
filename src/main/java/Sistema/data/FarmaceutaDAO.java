// Sistema.data.FarmaceutaDAO.java
package Sistema.data;

import Sistema.logic.Farmaceuta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FarmaceutaDAO {

    public void insertar(Farmaceuta farmaceuta) throws Exception {
        String sql = "INSERT INTO Farmaceuta (id, usuario, nombre) VALUES (?, ?, ?)"; // Asumiendo que agregaste 'nombre' a la tabla
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, farmaceuta.getId());
            pstmt.setString(2, farmaceuta.getClave()); // 'usuario' en BD es 'clave' en logic
            pstmt.setString(3, farmaceuta.getNombre()); // Si agregaste columna 'nombre'
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public Farmaceuta buscarPorId(String id) throws Exception {
        String sql = "SELECT id, usuario, nombre FROM Farmaceuta WHERE id = ?"; // Si agregaste columna 'nombre'
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Farmaceuta farmaceuta = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                farmaceuta = new Farmaceuta(
                        rs.getString("id"),
                        rs.getString("usuario"), // clave
                        rs.getString("nombre")    // nombre
                );
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
        return farmaceuta;
    }

    public List<Farmaceuta> buscarTodos() throws Exception {
        List<Farmaceuta> farmaceutas = new ArrayList<>();
        String sql = "SELECT id, usuario, nombre FROM Farmaceuta"; // Si agregaste columna 'nombre'
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                farmaceutas.add(new Farmaceuta(
                        rs.getString("id"),
                        rs.getString("usuario"),
                        rs.getString("nombre")
                ));
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
        return farmaceutas;
    }
    // ... Implementar actualizar y eliminar
    public void actualizar(Farmaceuta farmaceuta) throws Exception {
        String sql = "UPDATE Farmaceuta SET usuario = ?, nombre = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, farmaceuta.getClave());
            pstmt.setString(2, farmaceuta.getNombre());
            pstmt.setString(3, farmaceuta.getId());
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public void eliminar(String id) throws Exception {
        String sql = "DELETE FROM Farmaceuta WHERE id = ?";
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