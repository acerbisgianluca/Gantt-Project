package com.acerbisgianluca.ganttproject.exceptions;

/**
 * Eccezione usata quando si tenta di recuperare un
 * {@link com.acerbisgianluca.ganttproject.utilities.Task} con un nome che non esiste.
 *
 * @author Gianluca
 */
public class TaskNotFoundException extends Exception {

    /**
     * Crea una TaskNotFoundException passando alla classe estesa il messaggio.
     *
     * @param msg Il messaggio da stampare a video.
     */
    public TaskNotFoundException(String msg) {
        super(msg);
    }

}
