/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import computer.ComputerExceptions.MemoryAddressException;
import gui.UI;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class MemorySystem {

    private UI ui;

    // For outer devices' use only.
    private CPU cpu;

    // Size of main memory
    private final int size;
    private int[] mainMemory;
    private final Cache cache;

    // Decided when initialize CPU
    private int initialProgramAddress;

    // Outer devices that are integrated into memory system.
    public RomLoader romLoader;
    public CardReader cardReader;

    public MemorySystem(int size) {
        this.size = size;
        this.mainMemory = new int[size];
        Arrays.fill(this.mainMemory, 0);

        this.cache = new Cache(8, 16);
    }

    public void setCPU(CPU cpu) {
        this.cpu = cpu;
        this.romLoader = new RomLoader(this, this.cpu);
        this.cardReader = new CardReader(this, this.cpu);
    }

    public void setUI(UI ui) {
        this.ui = ui;
        this.romLoader.setUI(this.ui);
        this.cardReader.setUI(this.ui);
    }

    public int getSize() {
        return this.size;
    }

    private void check(int index) throws MemoryAddressException {
        if (index < 0 || index >= this.size) {
            throw new MemoryAddressException();
        }
    }

    // Direct access
    public int directRead(int index) throws MemoryAddressException {
        this.check(index);
        return mainMemory[index];
    }

    // Direct access
    public void directWrite(int index, int content) throws MemoryAddressException {
        this.check(index);
        this.mainMemory[index] = content;
    }

    public int read(int address) throws MemoryAddressException {
        return this.cache.read(address, this);
    }

    public void write(int address, int content) throws MemoryAddressException {
        this.cache.write(address, content, this);
    }

    /**
     * Find a sequentially empty memory space for the program to be stored.
     *
     * @param instructions
     * @return -1 if it fails else the beginning of the program address.
     */
    public int allocate(List<Integer> instructions) {
        int start = this.initialProgramAddress;
        while (start < this.mainMemory.length) {
            int i;
            // Leave an empty memory in order to halt or not to mess up with other programs.
            for (i = 0; i < instructions.size() + 1; ++i) {
                if (this.mainMemory[start + i] != 0) {
                    break;
                }
            }
            if (i < instructions.size() + 1) {
                start = start + i + 1;
            } else {
                break;
            }
        }

        // Means no such space.
        if (start >= this.mainMemory.length) {
            return -1;
        }
        for (int i = 0; i < instructions.size(); ++i) {
            this.mainMemory[start + i] = instructions.get(i);
        }
        return start;
    }

    public void setInitialProgramAddress(int address) {
        this.initialProgramAddress = address;
    }
}
