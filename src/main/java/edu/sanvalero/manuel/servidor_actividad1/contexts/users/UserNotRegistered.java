package edu.sanvalero.manuel.servidor_actividad1.contexts.users;

/**
 * Excepción para controlar un fallo a la hora de registrar un usuario
 */
public class UserNotRegistered extends Exception {

    public UserNotRegistered() {
        super();
    }

    public UserNotRegistered(String message) {
        super(message);
    }
}
