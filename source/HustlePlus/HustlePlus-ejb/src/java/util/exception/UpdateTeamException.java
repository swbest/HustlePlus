/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author amand
 */
public class UpdateTeamException extends Exception {

    /**
     * Creates a new instance of <code>UpdateTeamException</code> without detail
     * message.
     */
    public UpdateTeamException() {
    }

    /**
     * Constructs an instance of <code>UpdateTeamException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateTeamException(String msg) {
        super(msg);
    }
}
