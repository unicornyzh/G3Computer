/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer.ComputerExceptions;

import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class DeviceFailureException extends Exception {

    public DeviceFailureException() {
    }
    
    public void showAlert() {
        // Hard coded to show alert for Card Reader.
        JOptionPane.showMessageDialog(null, "Card Reader is not ready.", "Device Failure", JOptionPane.ERROR_MESSAGE);
    }
}
