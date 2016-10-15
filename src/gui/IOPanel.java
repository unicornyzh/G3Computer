/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import computer.Computer;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class IOPanel extends JPanel {
    private final Computer computer;
    
    public JTextField outputTextField;
    public JTextField inputTextField;
    public JButton inputButton;
    
    public IOPanel(Computer computer) {
        super();
        this.computer = computer;
        this.initComponents();
    }
    
    private void initComponents() {
        BoxLayout ioBoxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(ioBoxlayout);
        
        // Output
        this.outputTextField = new JTextField();
        outputTextField.setEditable(false);
        this.add(outputTextField);
        
        // Input
        this.inputTextField = new JTextField();
        this.inputButton = new JButton("Input");
        inputButton.addActionListener((ActionEvent ae) -> {
            // Validate the timing.
            if (!computer.cpu.isInterrupted()) {
                JOptionPane.showMessageDialog(null, "Input denied: No input instruction executed.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate the input.
            try {
                Integer.parseInt(this.inputTextField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Incorrect input.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            computer.cpu.recover();
        });
        inputButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(inputTextField);
        this.add(inputButton);
    }
}
