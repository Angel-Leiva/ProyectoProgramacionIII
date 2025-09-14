package Sistema.logic;

import Sistema.logic.Usuario;

public class Sesion {
    private static Usuario usuarioActivo;
    private static String rol; // "ADMIN", "MEDICO", "FARMACEUTA", "PACIENTE"

    public static void setUsuario(Usuario usuario) {
        usuarioActivo = usuario;

        switch (usuario.getTipoUsuario()) {
            case '0': rol = "ADMIN"; break;
            case '1': rol = "MEDICO"; break;
            case '2': rol = "FARMACEUTA"; break;
            case '3': rol = "PACIENTE"; break;
            default: rol = "DESCONOCIDO"; break;
        }
    }

    public static Usuario getUsuario() {
        return usuarioActivo;
    }

    public static boolean isLoggedIn() {
        return usuarioActivo != null;
    }

    public static void logout() {
        usuarioActivo = null;
        rol = null;
    }

    public static String getRol() {
        return rol;
    }
}
