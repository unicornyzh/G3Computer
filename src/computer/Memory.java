/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
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
        this.traceToFile("Cache Log\n", false);
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
        else
            this.writeThrough(line, address, datum, memory);
    }
    
    private void traceMiss(int address) {
        String msg = String.format("Cache miss for memory address %d.\n", address);
        System.out.print(msg);
        this.traceToFile(msg, true);
    }
    
    private void traceHit(int address) {
        String msg = String.format("Cache hit for memory address %d.\n", address);
        System.out.print(msg);
        this.traceToFile(msg, true);
    }
    
    private void traceAdd(int address) {
        String msg = String.format("Added a new cache line with tag %d. Current number of lines: %d.\n", address, this.lines.size());
        System.out.print(msg);
        this.traceToFile(msg, true);
    }
    
    private void traceRemove(int address) {
        String msg = String.format("Removed a cache line with tag %d. Current number of lines: %d.\n", address, this.lines.size());
        System.out.print(msg);
        this.traceToFile(msg, true);
    }
    
    private void traceToFile(String msg, boolean toAppend) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(this.filename, toAppend));
            writer.append(msg);
        } catch (IOException ex) {
            Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

class CacheLine {
    private final int tag;
    private int[] data;
    
    public CacheLine(int size, int address, Memory memory) {
        this.tag = address;
        this.data = new int[size];
        for (int i = 0; i < this.data.length; ++i)
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
