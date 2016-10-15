/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class InterruptException extends Exception {
    private final ISA instruction;
    
    public InterruptException(ISA instruction) {
        super();
        this.instruction = instruction;
    }
    
    public InterruptException(ISA instruction, String msg) {
        super(msg);
        this.instruction = instruction;
    }
    
    public ISA getInstruction() {
        return this.instruction;
    }
    
    public void showAlert() {
        JOptionPane.showMessageDialog(null, "Disabled by interrupt.", "CPU Error", JOptionPane.ERROR_MESSAGE);
    }
}
