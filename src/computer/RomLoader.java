/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

/**
 *
 * @author Administrator
 */
public class RomLoader {
    private final Memory memory;
    
    public RomLoader(Memory memory) {
        this.memory = memory;
    }
    
    public void load() {
        // Load R[3] from Memory[7] whose data is 256
        this.memory.setByAddress(0, 1799); //0000011100000111
        // Store R[3] whose data is 256 to Memory[8]
        this.memory.setByAddress(1, 2824); //0000101100001000
        // AMR Add R[2] and Memory[8] to R[2]
        this.memory.setByAddress(2, 4616); //0001001000001000	
        // SMR Sub R[2] and Memory[9] to R[2]
        this.memory.setByAddress(3, 5641);  //0001011000001001
        // AIR R[1] + 4       
        this.memory.setByAddress(4, 6404); //0001100100000100	
        // SIR R[1] - 2
        this.memory.setByAddress(5, 7426); //0001110100000010

        // Data
        this.memory.setByAddress(7, 256);
        this.memory.setByAddress(8, 0);
        this.memory.setByAddress(9, 140);
    }
}
