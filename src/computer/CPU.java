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
    
    private InterruptException interrupt;
    private boolean isRunning;
    
    public ProcessorRegisters registers;
    
    public CPU(int wordLength, Memory memory) {
        this.wordLength = wordLength;
        this.memory = memory;
        this.registers = new ProcessorRegisters();
        this.alu = new ALU(this.registers);
        this.cu = new CU(this.memory, this.registers, this.alu);
        this.interrupt = null;
        this.isRunning = false;
    }
    
    // Continuously working until error or end of program
    public void run() {
        this.isRunning = true;
        while (this.isRunning && this.interrupt == null)
            this.singleStep();
    }
    
    // Execute the instruction cycle (only one at each call)
    public void singleStep() {
        if (this.isInterrupted())
            return;
        
        try {
            this.cu.instuctionCycle();
        } catch (InterruptException iex) {
            this.interrupt = iex;
        } catch (HaltException hx) {
            hx.showAlert();
            this.cu.nextInstruction();
            this.isRunning = false;
        } catch (UnexpectedInstructionException uix) {
            uix.showAlert();
            this.reboot();
            this.isRunning = false;
        } catch (MemoryAddressException maex) {
            maex.showAlert();
            this.reboot();
            this.isRunning = false;
        } catch (Exception ex) {
            // Program bugs would be caught here.
            ex.printStackTrace();
            this.isRunning = false;
        }
    }
    
    public void recover() {
        try {
            this.cu.recover(this.interrupt.getInstruction());
        } catch (Exception ex) {
            // Never expect to get here.
            ex.printStackTrace();
        }

        this.interrupt = null;
        if (this.isRunning)
            this.run();
    }
    
    public void setUI(UI ui) {
        this.ui = ui;
        this.registers.setUI(this.ui);
        this.cu.setUI(this.ui);
    }
    
    public boolean isInterrupted() {
        if (this.interrupt != null) {
            this.interrupt.showAlert();
            this.ui.ioPanel.focusOnInputAndSelectAll();
            return true;
        }
        return false;
    }
    
    public boolean isInterrupted(boolean checkOnly) {
        return this.interrupt != null;
    }
    
    private void reboot() {
        // Meaning the boot program starts at octal 10.
        this.registers.pc.setContent(010);
        
        JOptionPane.showMessageDialog(this.ui, "System rebooted.", "Warning", JOptionPane.WARNING_MESSAGE);
    }
}
