/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import computer.Computer;
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

    private int outputCount;

    public IOPanel(Computer computer) {
        super();
        this.computer = computer;
        this.initComponents();
        this.outputCount = 0;
    }

    private void initComponents() {
        BoxLayout ioBoxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(ioBoxlayout);

        // Output
        this.outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setRows(5);
        this.add(new JScrollPane(outputTextArea));

        // Input
        this.inputTextField = new JTextField();
        inputTextField.addActionListener((ActionEvent ae) -> {
            this.inputAndRecover();
        });
        this.add(inputTextField);
    }

    public void focusOnInputAndSelectAll() {
        this.inputTextField.requestFocus();
        this.inputTextField.selectAll();
    }

    private void inputAndRecover() {
        // Validate the timing.
        if (!this.computer.cpu.isInterrupted(true)) {
            JOptionPane.showMessageDialog(null, "Input denied: No input instruction executed.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate the input.
        try {
            Integer.parseInt(this.inputTextField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Incorrect input.", "Input Error", JOptionPane.ERROR_MESSAGE);
            this.focusOnInputAndSelectAll();
            return;
        }

        this.computer.cpu.recover();
    }

    public void setOutput(int content) {
        if (!this.outputTextArea.getText().equals("")) {
            this.outputTextArea.append(System.lineSeparator());
        }
        this.outputTextArea.append("Line " + ++this.outputCount + ": " + String.valueOf(content));
    }
}
