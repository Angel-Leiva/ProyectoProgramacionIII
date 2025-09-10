package Sistema.logic;

import Sistema.logic.Usuario;

public class Sesion {
    private static Usuario usuarioActivo;

    public static void setUsuario(Usuario usuario) {
        usuarioActivo = usuario;
    }
    public static Usuario getUsuario() {
        return usuarioActivo;
    }

    public static boolean isLoggedIn() {
        return usuarioActivo != null;
    }
    public static void logout() {
        usuarioActivo = null;
    }
}
