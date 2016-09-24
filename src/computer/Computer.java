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
public class Computer {
    private UI ui;
    
    public CPU cpu;
    public Memory memory;
    public RomLoader romLoader;
    
    public Computer() {
        this.memory = new Memory(2048);
        // Make CPU be able to access memory through MAR
        this.cpu = new CPU(16, this.memory);
        this.romLoader = new RomLoader(this.memory);
    }
    
    public void setUI(UI ui) {
        this.ui = ui;
        this.cpu.setUI(this.ui);
    }
}
