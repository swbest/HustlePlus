/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author dtjldamien
 */
public class UpdateApplicationException extends Exception {

    /**
     * Creates a new instance of <code>UpdateApplicationException</code> without
     * detail message.
     */
    public UpdateApplicationException() {
    }

    /**
     * Constructs an instance of <code>UpdateApplicationException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateApplicationException(String msg) {
        super(msg);
    }
}
