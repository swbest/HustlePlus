/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Nurhidayah
 */

public class UpdateMilestoneException extends Exception {

    /**
     * Creates a new instance of <code>UpdateMilestoneException</code> without
     * detail message.
     */
    public UpdateMilestoneException() {
    }

    /**
     * Constructs an instance of <code>UpdateMilestoneException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateMilestoneException(String msg) {
        super(msg);
    }
}
