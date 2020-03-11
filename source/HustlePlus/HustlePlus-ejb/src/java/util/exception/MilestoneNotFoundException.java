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

public class MilestoneNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>MilestoneNotFoundException</code>
     * without detail message.
     */
    public MilestoneNotFoundException() {
    }

    /**
     * Constructs an instance of <code>MilestoneNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public MilestoneNotFoundException(String msg) {
        super(msg);
    }
}
