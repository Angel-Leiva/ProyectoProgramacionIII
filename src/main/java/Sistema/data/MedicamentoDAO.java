// Sistema.data.MedicamentoDAO.java
package Sistema.data;

import Sistema.logic.Medicamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {

    public void insertar(Medicamento medicamento) throws Exception {
        String sql = "INSERT INTO Medicamento (codigo, nombre, presentacion) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, medicamento.getCodigo());
            pstmt.setString(2, medicamento.getNombre());
            pstmt.setString(3, medicamento.getPresentacion());
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public Medicamento buscarPorCodigo(String codigo) throws Exception {
        String sql = "SELECT codigo, nombre, presentacion FROM Medicamento WHERE codigo = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Medicamento medicamento = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, codigo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                medicamento = new Medicamento(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("presentacion")
                );
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
        return medicamento;
    }

    public List<Medicamento> buscarTodos() throws Exception {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT codigo, nombre, presentacion FROM Medicamento";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                medicamentos.add(new Medicamento(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("presentacion")
                ));
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
        return medicamentos;
    }
    // ... Implementar actualizar y eliminar
    public void actualizar(Medicamento medicamento) throws Exception {
        String sql = "UPDATE Medicamento SET nombre = ?, presentacion = ? WHERE codigo = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, medicamento.getNombre());
            pstmt.setString(2, medicamento.getPresentacion());
            pstmt.setString(3, medicamento.getCodigo());
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public void eliminar(String codigo) throws Exception {
        String sql = "DELETE FROM Medicamento WHERE codigo = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, codigo);
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
    }
}