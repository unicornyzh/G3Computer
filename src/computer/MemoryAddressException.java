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
public class MemoryAddressException extends Exception {
    public MemoryAddressException() {}
    
    public void showAlert() {
        JOptionPane.showMessageDialog(null, "Wrong address.", "Memory Error", JOptionPane.ERROR_MESSAGE);
    }
}
