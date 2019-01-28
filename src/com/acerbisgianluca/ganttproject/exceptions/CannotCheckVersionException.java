package com.acerbisgianluca.ganttproject.exceptions;

/**
 * Eccezione lanciata quando il server che gestisce l'indice delle versioni non
 * risponde.
 *
 * @author Gianluca
 */
public class CannotCheckVersionException extends Exception {

    /**
     * Crea una CannotCheckVersionException passando alla classe estesa il
     * messaggio.
     *
     * @param msg Il messaggio da stampare a video.
     */
    public CannotCheckVersionException(String msg) {
        super(msg);
    }
}
