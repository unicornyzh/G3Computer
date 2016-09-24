/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import computer.Computer;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class ControlPanel extends JPanel {
    private final Computer computer;
    
    public ControlPanel(Computer computer) {
        super();
        this.computer = computer;
        this.initComponents();
    }
    
    private void initComponents() {
        JButton iplButton = new JButton("IPL");
        JButton runButton = new JButton("RUN");
        JButton ssButton = new JButton("Single Step");
        
        iplButton.addActionListener((ActionEvent ae) -> {
            computer.romLoader.load();
        });
        
        runButton.addActionListener((ActionEvent ae) -> {
            computer.cpu.run();
        });
        
        ssButton.addActionListener((ActionEvent ae) -> {
            computer.cpu.singleStep();
        });
        
        this.add(iplButton);
        this.add(runButton);
        this.add(ssButton);
    }
}
