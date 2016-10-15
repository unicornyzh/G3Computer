/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import gui.UI;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class CPU {
    private UI ui;
    
    private final int wordLength;
    private final Memory memory;
    private final ALU alu;
    private final CU cu;
    
    private InterruptException iex;
    
    public ProcessorRegisters registers;
    
    public CPU(int wordLength, Memory memory) {
        this.wordLength = wordLength;
        this.memory = memory;
        this.registers = new ProcessorRegisters();
        this.alu = new ALU(this.registers);
        this.cu = new CU(this.memory, this.registers, this.alu);
        this.iex = null;
    }
    
    // Continuously working until error or end of program
    public void run() {
        if (this.iex != null) {
            JOptionPane.showMessageDialog(this.ui, "Disabled by interrupt.", "Switch Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            while (true)
                this.cu.instuctionCycle();
        } catch (InterruptException iex) {
            this.iex = iex;
            this.iex.setIsSingleStep(false);
        } catch (Exception ex) {
            // At this point PC is set to the first address of the boot program, which is currently the only program in memory.
            this.reboot();
            System.err.println("Unexpected instruction encountered. System rebooted.");
        }
    }
    
    // Execute the instruction cycle (only one at each call)
    public void singleStep() {
        if (this.iex != null) {
            JOptionPane.showMessageDialog(this.ui, "Disabled by interrupt.", "Switch Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            this.cu.instuctionCycle();
        } catch (InterruptException iex) {
            this.iex = iex;
            this.iex.setIsSingleStep(true);
        } catch (Exception ex) {
            // At this point PC is set to the first address of the boot program, which is currently the only program in memory.
            this.reboot();
            System.err.println("Unexpected instruction encountered. System rebooted.");
        }
    }
    
    public void recover() {
        try {
            this.cu.recover(this.iex.getInstruction());
        } catch (Exception ex) {
            // Never expect to get here.
            ex.printStackTrace();
        }
        boolean keepRunning = !this.iex.getIsSingleStep();
        // Remember to clear the interrupt (after getting the flag).
        this.iex = null;
        if (keepRunning)
            this.run();
    }
    
    public void setUI(UI ui) {
        this.ui = ui;
        this.registers.setUI(this.ui);
        this.cu.setUI(this.ui);
    }
    
    public boolean isInterrupted() {
        return this.iex != null;
    }
    
    private void reboot() {
        this.registers.pc.setContent(0);
    }
}
