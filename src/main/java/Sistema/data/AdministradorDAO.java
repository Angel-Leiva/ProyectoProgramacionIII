// Sistema.data.AdministradorDAO.java
package Sistema.data;

import Sistema.logic.Administrador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    public void insertar(Administrador admin) throws Exception {
        String sql = "INSERT INTO Administrador (id, usuario, contrasena) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, admin.getId());
            pstmt.setString(2, admin.getClave()); // 'usuario' en BD es 'clave' en logic
            pstmt.setString(3, admin.getClave()); // 'contrasena' en BD también es 'clave' en logic
            pstmt.executeUpdate();
        } finally {
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
    }

    public Administrador buscarPorId(String id) throws Exception {
        String sql = "SELECT id, usuario, contrasena FROM Administrador WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Administrador admin = null;
        try {
            conn = DatabaseConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                admin = new Administrador(
                        rs.getString("id"),
                        rs.getString("usuario"), // Mapea a clave en tu clase logic
                        rs.getString("usuario") // Nombre en tu clase logic. O si guardas el nombre aparte, busca el campo.
                        // Nota: tu constructor de Admin es (idN, claveN, nombreN).
                        // Si la DB tiene 'usuario' y 'contrasena' y tu logic solo 'clave',
                        // aquí hay un mismatch. Ajustemos para que 'usuario' de DB sea 'clave' de logic,
                        // y 'nombre' de logic sea el mismo que 'usuario' de DB para este tipo de usuario,
                        // o crea una columna 'nombre' en la tabla 'Administrador' si lo necesitas.
                        // Por ahora, asumimos que 'usuario' de DB es para login y clave y también sirve como nombre.
                );
                // Si quieres el nombre real, la tabla Administrador debería tener una columna 'nombre'
                // admin.setNombre(rs.getString("nombre_real_si_existiera"));
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(pstmt);
            DatabaseConnection.closeConnection(conn);
        }
        return admin;
    }

    public List<Administrador> buscarTodos() throws Exception {
        List<Administrador> administradores = new ArrayList<>();
        String sql = "SELECT id, usuario, contrasena FROM Administrador";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                administradores.add(new Administrador(
                        rs.getString("id"),
                        rs.getString("usuario"),
                        rs.getString("usuario") // Asumiendo que 'usuario' de DB es también el 'nombre' de tu clase logic
                ));
            }
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(stmt);
            DatabaseConnection.closeConnection(conn);
        }
        return administradores;
    }
    // ... Implementar actualizar y eliminar de forma similar
}