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
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class IOPanel extends JPanel {
    private final Computer computer;
    
    public IOPanel(Computer computer) {
        super();
        this.computer = computer;
        this.initComponents();
    }
    
    private void initComponents() {
        BoxLayout ioBoxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(ioBoxlayout);
        
        // Output
        JTextField outputTextField = new JTextField();
        outputTextField.setEditable(false);
        this.add(outputTextField);
        
        // Input
        JTextField inputTextField = new JTextField();
        JButton inputButton = new JButton("Input");
        inputButton.addActionListener((ActionEvent ae) -> {
            
        });
        inputButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(inputTextField);
        this.add(inputButton);
    }
}
