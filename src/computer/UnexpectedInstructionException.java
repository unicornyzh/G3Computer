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
public class UnexpectedInstructionException extends Exception {
    public UnexpectedInstructionException() {}
    
    public void showAlert() {
        JOptionPane.showMessageDialog(null, "Unexpected instruction encountered. System rebooted.", "Unexpected Instruction", JOptionPane.WARNING_MESSAGE);
    }
}
