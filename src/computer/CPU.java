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
    
    public ProcessorRegisters registers;
    
    public CPU(int wordLength, Memory memory) {
        this.wordLength = wordLength;
        this.memory = memory;
        this.registers = new ProcessorRegisters();
        this.alu = new ALU(this.registers);
        this.cu = new CU(this.memory, this.registers, this.alu);
        this.interrupt = null;
    }
    
    // Continuously working until error or end of program
    public void run() {
        if (this.isInterrupted())
            return;
        
        try {
            while (true)
                this.cu.instuctionCycle();
        } catch (InterruptException ix) {
            this.interrupt = ix;
            this.interrupt.setIsSingleStep(false);
        } catch (HaltException hx) {
            hx.showAlert();
            this.cu.nextInstruction();
        } catch (UnexpectedInstructionException uix) {
            uix.showAlert();
            this.reboot();
        } catch (Exception ex) {
            // Never expect to get here.
            ex.printStackTrace();
        }
    }
    
    // Execute the instruction cycle (only one at each call)
    public void singleStep() {
        if (this.isInterrupted())
            return;
        
        try {
            this.cu.instuctionCycle();
        } catch (InterruptException iex) {
            this.interrupt = iex;
            this.interrupt.setIsSingleStep(true);
        } catch (HaltException hx) {
            hx.showAlert();
            this.cu.nextInstruction();
        } catch (UnexpectedInstructionException uix) {
            uix.showAlert();
            this.reboot();
        } catch (Exception ex) {
            // Never expect to get here.
            ex.printStackTrace();
        }
    }
    
    public void recover() {
        try {
            this.cu.recover(this.interrupt.getInstruction());
        } catch (Exception ex) {
            // Never expect to get here.
            ex.printStackTrace();
        }
        boolean keepRunning = !this.interrupt.getIsSingleStep();
        // Remember to clear the interrupt (after getting the flag).
        this.interrupt = null;
        if (keepRunning)
            this.run();
    }
    
    public void setUI(UI ui) {
        this.ui = ui;
        this.registers.setUI(this.ui);
        this.cu.setUI(this.ui);
    }
    
    public boolean isInterrupted() {
        if (this.interrupt != null) {
            JOptionPane.showMessageDialog(this.ui, "Disabled by interrupt.", "CPU Error", JOptionPane.ERROR_MESSAGE);
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
    }
}
