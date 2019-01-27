package com.acerbisgianluca.exceptions;

/**
 * Eccezione lanciata quando si tenta di inserire un
 * {@link com.acerbisgianluca.Task} con un nome già presente.
 *
 * @author Gianluca
 */
public class TaskAlreadyExistsException extends Exception {

    /**
     * Crea un TaskAlreadyExistsException passando alla classe estesa il
     * messaggio.
     *
     * @param mex Il messaggio da stampare a video.
     */
    public TaskAlreadyExistsException(String mex) {
        super(mex);
    }

}
