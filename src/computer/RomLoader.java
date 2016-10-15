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
public class RomLoader {
    private UI ui;
    
    private final Memory memory;
    private final CPU cpu;
    
    public RomLoader(Memory memory, CPU cpu) {
        this.memory = memory;
        this.cpu = cpu;
    }
    
    public void setUI(UI ui) {
        this.ui = ui;
    }
    
    public void load() {
        if (this.cpu.isInterrupted()) {
            JOptionPane.showMessageDialog(this.ui, "Disabled by interrupt.", "Switch Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Load R[3] from Memory[7] whose data is 256
        this.memory.write(0, 1799); //0000011100000111
        // Store R[3] whose data is 256 to Memory[8]
        this.memory.write(1, 2824); //0000101100001000
        // AMR Add R[2] and Memory[8] to R[2]
        this.memory.write(2, 4616); //0001001000001000	
        // SMR Sub R[2] and Memory[9] to R[2]
        this.memory.write(3, 5641);  //0001011000001001
        // AIR R[1] + 4       
        this.memory.write(4, 6404); //0001100100000100	
        // SIR R[1] - 2
        this.memory.write(5, 7426); //0001110100000010

        // Data
        this.memory.write(7, 256);
        this.memory.write(8, 0);
        this.memory.write(9, 140);
        
        // IN 0, 0
        this.memory.write(16, 0x0000c400);
        // OUT 0, 1
        this.memory.write(17, 0x0000c801);
    }
}
