/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Memory {
    private final int size;
    private int[] mainMemory;
    private final Cache cache;
    
    public Memory(int size) {
        this.size = size;
        this.mainMemory = new int[size];
        Arrays.fill(this.mainMemory, 0);
        this.cache = new Cache(8, 16);
    }
    
    public int getSize() {
        return this.size;
    }
    
    // Direct access
    public int directRead(int index) {
        return mainMemory[index];
    }
    
    // Direct access
    public void directWrite(int index, int content) {
        this.mainMemory[index] = content;
    }
    
    public int read(int address) {
        return this.cache.read(address, this);
    }
    
    public void write(int address, int content) {
        this.cache.write(address, content, this);
    }

    /**
     * Find a sequentially empty memory space for the program to be stored.
     * @param instructions
     * @return -1 if it fails else the beginning of the program address.
     */
    public int allocate(List<Integer> instructions) {
        // Start of PC
        int start = 010;
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
}

class Cache {
    private final int lineSize;
    private final int cacheSize;
    private final Queue<CacheLine> lines;
    
    private final String filename;
    
    public Cache(int lineSize, int cacheSize) {
        this.lineSize = lineSize;
        this.cacheSize = cacheSize;
        this.lines = new ArrayDeque<>();
        
        this.filename = "trace.txt";
        this.traceToFile("Cache Log", false);
    }
    
    private CacheLine find(int address) {
        for (CacheLine line : this.lines)
            if (line.matches(address))
                return line;
        return null;
    }
    
    private CacheLine addLine(int address, Memory memory) {
        if (this.lines.size() >= this.cacheSize) {
            CacheLine removed = this.lines.remove();
            this.traceRemove(removed.getTag());
        }
        CacheLine newLine = new CacheLine(this.lineSize, address, memory);
        this.lines.add(newLine);
        this.traceAdd(address);
        return newLine;
    }
    
    private void writeThrough(CacheLine line, int address, int datum, Memory memory) {
        // Update cache
        line.setData(address, datum);
        // Update memory
        memory.directWrite(address, datum);
    }
    
    public int read(int address, Memory memory) {
        CacheLine line = this.find(address);
        if (line == null) {
            this.traceMiss(address);
            line = this.addLine(address, memory);
        }
        else
            this.traceHit(address);
        return line.getData(address);
    }
    
    public void write(int address, int datum, Memory memory) {
        CacheLine line = this.find(address);
        if (line == null) {
            this.traceMiss(address);
            // Write no-allocate
            memory.directWrite(address, datum);
        }
        else {
            this.traceHit(address);
            this.writeThrough(line, address, datum, memory);
        }
    }
    
    private void traceMiss(int address) {
        String msg = String.format("Cache miss for memory address %d.", address);
        System.out.println(msg);
        this.traceToFile(msg, true);
    }
    
    private void traceHit(int address) {
        String msg = String.format("Cache hit for memory address %d.", address);
        System.out.println(msg);
        this.traceToFile(msg, true);
    }
    
    private void traceAdd(int address) {
        String msg = String.format("Added a new cache line with tag %d. Current number of lines: %d.", address, this.lines.size());
        System.out.println(msg);
        this.traceToFile(msg, true);
    }
    
    private void traceRemove(int address) {
        String msg = String.format("Removed a cache line with tag %d. Current number of lines: %d.", address, this.lines.size());
        System.out.println(msg);
        this.traceToFile(msg, true);
    }
    
    private void traceToFile(String msg, boolean toAppend) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename, toAppend))) {
            writer.append(msg);
            writer.newLine();
        } catch (IOException ex) {
            Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class CacheLine {
    private final int tag;
    private int[] data;
    
    public CacheLine(int size, int address, Memory memory) {
        this.tag = address;
        this.data = new int[size];
        for (int i = 0; i < this.data.length && address + i < memory.getSize(); ++i)
            this.data[i] = memory.directRead(address + i);
    }
    
    public boolean matches(int address) {
        int pos = address - tag;
        // Return the position where the datum should be in the line if a match is found, else -1.
        return pos >= 0 && pos < this.data.length;
    }
    
    public int getTag() {
        return this.tag;
    }
    
    public void setData(int address, int datum) {
        int pos = address - tag;
        this.data[pos] = datum;
    }
    
    public int getData(int address) {
        int pos = address - tag;
        return this.data[pos];
    }
}
