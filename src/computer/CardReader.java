/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import computer.ComputerExceptions.DeviceFailureException;
import gui.UI;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class CardReader {

    private UI ui;

    private final MemorySystem memory;
    private final CPU cpu;

    private int status;
    private int pointer;
    private String contents;

    public CardReader(MemorySystem memory, CPU cpu) {
        this.memory = memory;
        this.cpu = cpu;
        this.status = 0;
        this.pointer = 0;
    }

    public void setUI(UI ui) {
        this.ui = ui;
    }

    public int getStatus() {
        return this.status;
    }

    public void load() {
        if (this.cpu.isInterrupted()) {
            return;
        }

        // File chooser to read a file
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this.ui) != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this.ui, "Canceled.", "Loading Files", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Put all contents of the file into a string variable
            this.contents = new String(Files.readAllBytes(Paths.get(chooser.getSelectedFile().getPath())));
            // Requested by unicornyzh
            this.contents = this.contents.trim();
            // Requested by myself
            this.contents = this.contents.toLowerCase();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this.ui, "IO Exception occurred.", "IO Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Right now we would assume the device would be always in the card reader after it is inserted.
        // So, there is no operation for flipping the status back to 0.
        this.status = 1;
        JOptionPane.showMessageDialog(this.ui, "Card Reader is ready.");
    }

    // Return the ASCII of characters.
    public int getASCII() throws DeviceFailureException {
        if (this.status == 0) {
            throw new DeviceFailureException();
        }
        if (this.pointer < this.contents.length()) {
            return this.contents.charAt(this.pointer++);
        } else {
            this.pointer = 0;
            return '\0';
        }
    }
}
