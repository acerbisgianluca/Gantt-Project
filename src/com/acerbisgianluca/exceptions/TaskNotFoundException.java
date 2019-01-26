package com.acerbisgianluca.exceptions;

/**
 * Eccezione usata quando si tenta di recuperare un {@link com.acerbisgianluca.Task} con un nome che non esiste.
 * @author Gianluca
 */
public class TaskNotFoundException extends Exception {

    /**
     * Crea un TaskNotFoundException passando alla classe estesa il messaggio.
     * @param mex Il messaggio da stampare a video.
     */
    public TaskNotFoundException(String mex) {
        super(mex);
    }

}
