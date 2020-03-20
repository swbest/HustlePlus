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
public class DeleteApplicationException extends Exception {

    /**
     * Creates a new instance of <code>DeleteApplicationException</code> without
     * detail message.
     */
    public DeleteApplicationException() {
    }

    /**
     * Constructs an instance of <code>DeleteApplicationException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteApplicationException(String msg) {
        super(msg);
    }
}
