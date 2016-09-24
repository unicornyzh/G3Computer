/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import gui.UI;

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
    
    public ProcessorRegisters registers;
    
    public CPU(int wordLength, Memory memory) {
        this.wordLength = wordLength;
        this.memory = memory;
        this.registers = new ProcessorRegisters();
        this.alu = new ALU(this.registers);
        this.cu = new CU(this.memory, this.registers, this.alu);
    }
    
    // Continuously working until error or end of program
    public void run() {
        try {
            while (true)
                this.cu.instuctionCycle();
        } catch (Exception ex) {
            System.err.println("Unexpected instruction encountered. System rebooted.");
        }
    }
    
    // Execute the instruction cycle (only one at each call)
    public void singleStep() {
        try {
            this.cu.instuctionCycle();
        } catch (Exception ex) {
            System.err.println("Unexpected instruction encountered. System rebooted.");
        }
    }
    
    public void setUI(UI ui) {
        this.ui = ui;
        this.registers.setUI(this.ui);
    }
}
