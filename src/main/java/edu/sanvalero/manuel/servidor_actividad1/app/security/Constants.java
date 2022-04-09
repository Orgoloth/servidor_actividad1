package edu.sanvalero.manuel.servidor_actividad1.app.security;

/**
 * Constantes para la configuración de seguridad
 */
public class Constants {
    public static final String USER_ROLE = "user";
    public static final String ADMIN_ROLE = "admin";

    public static final String LOGIN_URL = "/users/login";
    public static final String LOGIN_SUCCESS_URL = "/users/me";
    public static final String LOGIN_FAILURE_URL = "/users/login?error=true";

    public static final String LOGOUT_URL = "/users/logout";
    public static final String LOGOUT_SUCCESS_URL = "/?message=logout";
    public static final String JSESSIONID = "JSESSIONID";

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
