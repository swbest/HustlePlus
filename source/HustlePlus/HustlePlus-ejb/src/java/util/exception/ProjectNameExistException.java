/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author sw_be
 */
public class ProjectNameExistException extends Exception {

    /**
     * Creates a new instance of <code>ProjectNameExistException</code> without
     * detail message.
     */
    public ProjectNameExistException() {
    }

    /**
     * Constructs an instance of <code>ProjectNameExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ProjectNameExistException(String msg) {
        super(msg);
    }
}
