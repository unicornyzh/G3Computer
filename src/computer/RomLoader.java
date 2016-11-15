/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import computer.ComputerExceptions.MemoryAddressException;
import gui.UI;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class RomLoader {

    private UI ui;

    private final MemorySystem memory;
    private final CPU cpu;

    public RomLoader(MemorySystem memory, CPU cpu) {
        this.memory = memory;
        this.cpu = cpu;
    }

    public void setUI(UI ui) {
        this.ui = ui;
    }

    public void loadFromFile(int radix) {
        if (this.cpu.isInterrupted()) {
            return;
        }

        // File chooser to read a file
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this.ui) != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this.ui, "Canceled.", "Loading Files", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int count = 0;
        // Construct a list for instructions
        List<Integer> instructions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip non-parsable lines
                try {
                    // Accept data in given radix (currently it could be 2 or 8 or 16).
                    instructions.add(Integer.parseInt(line, radix));
                    ++count;
                } catch (NumberFormatException nfx) {
                }
            }
        } catch (IOException x) {
            JOptionPane.showMessageDialog(this.ui, "IO Exception occurred.", "IO Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (instructions.isEmpty()) {
            JOptionPane.showMessageDialog(this.ui, "Loading failed. Please check the content and format of your file.", "Loading Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int start = this.memory.allocate(instructions);
        if (start != -1) {
            JOptionPane.showMessageDialog(this.ui, "Loaded " + count + (count == 1 ? " instruction. " : " instructions. ") + "Your program starts at address " + start + ".");
        }
    }

    public void loadInitialProgram() throws MemoryAddressException {
        if (this.cpu.isInterrupted()) {
            return;
        }

//        // Data
//        this.memory.write(5, 0);
//        this.memory.write(6, 1300);
//        this.memory.write(7, 0);
//        this.memory.write(8, 0);
//        this.memory.write(9, 600);
//        this.memory.write(10, 13312);
//        this.memory.write(12, 32);
//        this.memory.write(13, 44);
//        this.memory.write(14, 46);
//        this.memory.write(15, 63);
//        this.memory.write(1303, 1);
//        this.memory.write(1304, 1);
        
        this.memory.write(8, 1024);
        this.memory.write(10, 13312);

        this.cpu.resetRegisters();
        JOptionPane.showMessageDialog(this.ui, "Loading initial program succeeded.");
    }
}
