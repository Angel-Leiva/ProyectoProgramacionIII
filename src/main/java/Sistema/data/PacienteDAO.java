// Sistema.data.PacienteDAO.java
package Sistema.data;

import Sistema.logic.Paciente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public void insertar(Paciente paciente) throws Exception {
        // En tu clase Paciente, no tienes 'clave' en el constructor directo, pero sí en Usuario padre.
        // La tabla Paciente en BD no tiene 'usuario' ni 'contrasena'.
        // Si necesitas login para paciente, tendrás que añadir esas columnas a la tabla Paciente.
        // Por ahora, solo insertamos los campos de la tabla Paciente.
        String sql = "INSERT INTO Paciente (id, nombre, fecha_nacimiento, telefono) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paciente.getId());
            pstmt.setString(2, paciente.getNombre());
            pstmt.setDate(3, java.sql.Date.valueOf(paciente.getFechaNacimiento())); // Convertir LocalDate a java.sql.Date
            pstmt.setString(4, paciente.getTelefono());
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public Paciente buscarPorId(String id) throws Exception {
        String sql = "SELECT id, nombre, fecha_nacimiento, telefono FROM Paciente WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Paciente paciente = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                // Tu constructor de Paciente es (idN, nombreN, fechaNacimiento, telefono)
                // y el de Usuario padre es (id, clave, nombre, tipoUsuario)
                // Aquí, el Paciente no tiene 'clave' de login directo en la BD
                paciente = new Paciente(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_nacimiento").toLocalDate(), // Convertir java.sql.Date a LocalDate
                        rs.getString("telefono")
                );
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
        return paciente;
    }

    public List<Paciente> buscarTodos() throws Exception {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT id, nombre, fecha_nacimiento, telefono FROM Paciente";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                pacientes.add(new Paciente(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getString("telefono")
                ));
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
        return pacientes;
    }
    // ... Implementar actualizar y eliminar
    public void actualizar(Paciente paciente) throws Exception {
        String sql = "UPDATE Paciente SET nombre = ?, fecha_nacimiento = ?, telefono = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paciente.getNombre());
            pstmt.setDate(2, java.sql.Date.valueOf(paciente.getFechaNacimiento()));
            pstmt.setString(3, paciente.getTelefono());
            pstmt.setString(4, paciente.getId());
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public void eliminar(String id) throws Exception {
        String sql = "DELETE FROM Paciente WHERE id = ?";
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