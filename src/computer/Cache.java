/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import computer.ComputerExceptions.MemoryAddressException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Cache {

    private final int lineSize;
    private final int cacheSize;
    private final Queue<computer.CacheLine> lines;
    private final String filename;

    public Cache(int lineSize, int cacheSize) {
        this.lineSize = lineSize;
        this.cacheSize = cacheSize;
        this.lines = new ArrayDeque<>();
        this.filename = "trace.txt";
        this.traceToFile("Cache Log", false);
    }

    private computer.CacheLine find(int address) {
        for (computer.CacheLine line : this.lines) {
            if (line.matches(address)) {
                return line;
            }
        }
        return null;
    }

    private computer.CacheLine addLine(int address, Memory memory) throws MemoryAddressException {
        if (this.lines.size() >= this.cacheSize) {
            computer.CacheLine removed = this.lines.remove();
            this.traceRemove(removed.getTag());
        }
        computer.CacheLine newLine = new computer.CacheLine(this.lineSize, address, memory);
        this.lines.add(newLine);
        this.traceAdd(address);
        return newLine;
    }

    private void writeThrough(computer.CacheLine line, int address, int datum, Memory memory) throws MemoryAddressException {
        // Update cache
        line.setData(address, datum);
        // Update memory
        memory.directWrite(address, datum);
    }

    public int read(int address, Memory memory) throws MemoryAddressException {
        computer.CacheLine line = this.find(address);
        if (line == null) {
            this.traceMiss(address);
            line = this.addLine(address, memory);
        } else {
            this.traceHit(address);
        }
        return line.getData(address);
    }

    public void write(int address, int datum, Memory memory) throws MemoryAddressException {
        computer.CacheLine line = this.find(address);
        if (line == null) {
            this.traceMiss(address);
            // Write no-allocate
            memory.directWrite(address, datum);
        } else {
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
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename, toAppend))) {
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

    public CacheLine(int size, int address, Memory memory) throws MemoryAddressException {
        super();
        this.tag = address;
        this.data = new int[size];
        for (int i = 0; i < this.data.length; ++i) {
            this.data[i] = memory.directRead(address + i);
        }
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
