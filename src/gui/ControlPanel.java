/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import computer.Computer;
import computer.ComputerExceptions.MemoryAddressException;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        JButton loadHexButton = new JButton("Load Hex");
        JButton loadOctButton = new JButton("Load Oct");
        JButton loadBinButton = new JButton("Load Bin");
        JButton runButton = new JButton("RUN");
        JButton ssButton = new JButton("Single Step");
        
        iplButton.addActionListener((ActionEvent ae) -> {
            try {
                computer.romLoader.load();
            } catch (MemoryAddressException ex) {
                ex.showAlert();
            }
        });
        
        loadHexButton.addActionListener((ActionEvent ae) -> {
            computer.romLoader.loadFromFile(16);
        });
        
        loadOctButton.addActionListener((ActionEvent ae) -> {
            computer.romLoader.loadFromFile(8);
        });
        
        loadBinButton.addActionListener((ActionEvent ae) -> {
            computer.romLoader.loadFromFile(2);
        });
        
        runButton.addActionListener((ActionEvent ae) -> {
            computer.cpu.run();
        });
        
        ssButton.addActionListener((ActionEvent ae) -> {
            computer.cpu.singleStep();
        });
        
        this.add(iplButton);
        this.add(loadHexButton);
        this.add(loadOctButton);
        this.add(loadBinButton);
        this.add(runButton);
        this.add(ssButton);
    }
}
