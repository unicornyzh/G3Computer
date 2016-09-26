/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import computer.Register;
import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionEvent;

/**
 *
 * @author Administrator
 */
public class RegisterGUI extends JPanel {
    private final String name;
    private final int index;
    private final Register register;
    private JRadioButton[] radioButtons;
    private JPanel lightPanel;
    
    public RegisterGUI(Register register, String name) {
        super();
        this.name = name;
        this.register = register;
        this.index = -1;
        this.initComponents();
    }
    
    public RegisterGUI(Register register, int index) {
        super();
        this.name = null;
        this.register = register;
        this.index = index;
        this.initComponents();
    }
    
    public RegisterGUI(Register register, String name, int index) {
        super();
        this.name = name;
        this.register = register;
        this.index = index;
        this.initComponents();
    }
    
    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        if (this.name != null)
            this.setupName();
        // Pack the values and buttons together into a panel
        this.lightPanel = new JPanel();
        if (this.index != -1)
            this.setupIndex();
        this.setupValues();
    }
    
    private void setupName() {
        // Register name
        JLabel label = new JLabel(this.name);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(label);
    }
    
    private void setupIndex() {
        // Register index (for GPR and IX)
        lightPanel.add(new JLabel(Integer.toString(this.index)));
    }
    
    private void setupValues() {
        // Register value
        this.radioButtons = new JRadioButton[this.register.getLength()];
        for (int i = 0; i < this.radioButtons.length; ++i) {
            this.radioButtons[i] = new JRadioButton();
            lightPanel.add(this.radioButtons[i]);
        }
        // Should add a listener
        JButton button = new JButton("Set");
        lightPanel.add(button);
        button.addActionListener((ActionEvent ae) -> {
            int value = 0;
            for (int i = 0; i < radioButtons.length; ++i)
                if (radioButtons[i].isSelected())
                    value |= 1 << radioButtons.length - i - 1;
            register.setContentByGUI(value);
        });
        // Add to the parent panel
        this.add(lightPanel);
    }
    
    public void setRadioButtons(int value) {
        for (int i = this.radioButtons.length - 1; i >=0; --i) {
            if ((value & 1) == 0)
                this.radioButtons[i].setSelected(false);
            else
                this.radioButtons[i].setSelected(true);
            value >>= 1;
        }
    }
}

