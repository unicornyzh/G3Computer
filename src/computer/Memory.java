/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class Memory {
    private int size;
    private int[] memory;
    
    public Memory(int size) {
        this.size = size;
        memory = new int[size];
        Arrays.fill(memory, 0);
    }
    
    public int getByAddress(int index) {
        return memory[index];
    }
    
    public void setByAddress(int index, int content) {
        this.memory[index] = content;
    }
}
