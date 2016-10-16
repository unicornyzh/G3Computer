/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import computer.Computer;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public class UI extends JFrame {

    private final Computer computer;

    public IOPanel ioPanel;
    public RegisterPanel registerPanel;
    public ControlPanel controlPanel;

    public UI(Computer computer) {
        super();
        this.computer = computer;
        this.initComponents();
    }

    private void initComponents() {
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);

        this.ioPanel = new IOPanel(this.computer);
        this.getContentPane().add(BorderLayout.NORTH, this.ioPanel);

        this.registerPanel = new RegisterPanel(this.computer);
        this.getContentPane().add(BorderLayout.CENTER, this.registerPanel);

        this.controlPanel = new ControlPanel(this.computer);
        this.getContentPane().add(BorderLayout.SOUTH, this.controlPanel);

        // If it's not set the frame would just be hidden and the Java virtual machine (VM) will not terminate.
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
