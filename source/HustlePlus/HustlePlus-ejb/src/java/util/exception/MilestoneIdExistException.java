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
public class MilestoneIdExistException extends Exception {
    /**
     * Creates a new instance of <code>MilestoneNameExistException</code> without
     * detail message.
     */
    public MilestoneIdExistException() {
    }

    /**
     * Constructs an instance of <code>MilestoneNameExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MilestoneIdExistException(String msg) {
        super(msg);
    }
    
    
}


