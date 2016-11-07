/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import computer.Computer;
import computer.ComputerExceptions.MemoryAddressException;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class IOPanel extends JPanel {

    private final Computer computer;

    public JTextArea outputTextArea;
    public JTextField inputTextField;
    
    private boolean isNewLine;

    public IOPanel(Computer computer) {
        super();
        this.computer = computer;
        this.initComponents();
        
        this.isNewLine = false;
    }

    private void initComponents() {
        BoxLayout ioBoxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(ioBoxlayout);

        // Output
        this.outputTextArea = new JTextArea();
        this.outputTextArea.setEditable(false);
        this.outputTextArea.setRows(10);
        JScrollPane scrollPane = new JScrollPane(this.outputTextArea);
        TextLineNumber tln = new TextLineNumber(this.outputTextArea);
        scrollPane.setRowHeaderView(tln);
        this.add(scrollPane);

        // Input
        this.inputTextField = new JTextField();
        this.inputTextField.addActionListener((ActionEvent ae) -> {
            this.validateInputAndRecover();
        });
        this.add(inputTextField);
    }

    public void focusOnInputAndSelectAll() {
        this.inputTextField.requestFocus();
        this.inputTextField.selectAll();
    }

    private void validateInputAndRecover() {
        // Validate the timing.
        if (!this.computer.cpu.isInterrupted(true)) {
            JOptionPane.showMessageDialog(null, "Input denied: No input instruction executed.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (this.computer.memory.directRead(7) == 0) {
                // Validate the input.
                try {
                    this.getNumberInput();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Incorrect input.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    this.focusOnInputAndSelectAll();
                    return;
                }
            } else {
                if (this.getStringInput().equals("")) {
                    JOptionPane.showMessageDialog(null, "Input can't be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    this.focusOnInputAndSelectAll();
                    return;
                }
            }
        } catch (MemoryAddressException ex) {
        }

        this.computer.cpu.recover();
    }

    public int getNumberInput() {
        return Integer.parseInt(this.inputTextField.getText());
    }

    public String getStringInput() {
        return this.inputTextField.getText();
    }

    public void setOutput(int content) {
        if (this.isNewLine) {
            this.outputTextArea.append(System.lineSeparator());
            this.isNewLine = false;
        }
        this.outputTextArea.append(String.valueOf(content));
        this.isNewLine = true;
    }

    public void setOutput(char content) {
        if (this.isNewLine) {
            this.outputTextArea.append(System.lineSeparator());
            this.isNewLine = false;
        }
        this.outputTextArea.append(String.valueOf(content));
    }
}
