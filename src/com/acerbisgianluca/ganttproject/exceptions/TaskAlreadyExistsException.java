package com.acerbisgianluca.ganttproject.exceptions;

/**
 * Eccezione lanciata quando si tenta di inserire un
 * {@link com.acerbisgianluca.ganttproject.utilities.Task} con un nome già presente.
 *
 * @author Gianluca
 */
public class TaskAlreadyExistsException extends Exception {

    /**
     * Crea una TaskAlreadyExistsException passando alla classe estesa il
     * messaggio.
     *
     * @param msg Il messaggio da stampare a video.
     */
    public TaskAlreadyExistsException(String msg) {
        super(msg);
    }

}
