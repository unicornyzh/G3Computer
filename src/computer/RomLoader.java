/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import gui.UI;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
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
    
    public void loadFromFile(int radix) {
        if (this.cpu.isInterrupted())
            return;
        
        // File chooser to read a file
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this.ui) != JFileChooser.APPROVE_OPTION) {
            System.out.println("Canceled.");
            return;
        }
        
        // Construct a list for instructions
        List<Integer> instructions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Accept data in given radix (currently it could be 2 or 8 or 16).
                instructions.add(Integer.parseInt(line, radix));
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        } catch (NumberFormatException nfx) {
            JOptionPane.showMessageDialog(this.ui, "Wrong format.", "File Error", JOptionPane.ERROR_MESSAGE);
        }

        int start = this.memory.allocate(instructions);
        if (start == -1)
            JOptionPane.showMessageDialog(this.ui, "Memory full.", "Memory Error", JOptionPane.ERROR_MESSAGE);
        else
            JOptionPane.showMessageDialog(this.ui, "Loading succeeded. Your program starts at address " + start);
    }
    
    public void load() {
        if (this.cpu.isInterrupted())
            return;
        
        // Data
        this.memory.write(20, 21);
        this.memory.write(24, 120);
        this.memory.write(26, 200);
        this.memory.write(25, 300);

        this.memory.write(31, 20);
        this.memory.write(29, 65535);
        this.memory.write(30, 100);
        // in：
        // 8 LDX 3, 26 1000010011011010 
        this.memory.write(8, 34010);
        // 9 JSR 3, 0  0011000011000000
        this.memory.write(9, 12480);

        // LDR 3,0,20
        this.memory.write(200, 1812); //0000011100010100
        // LDX 1, 24  1000010001011000
        this.memory.write(201, 33880);
        // IN  2, 0   1100011000000000
        this.memory.write(202, 50688);
        
        // OUT 2, 1   1100101000000001
        this.memory.write(203, 51713);
        
        // STR 2, 1, 0  0000101001000000
        this.memory.write(204, 2624);
        // LDA 2, 1, 0  0000111001000000
        this.memory.write(205, 3648);
        // SIR 2, 1    0001111000000001
        this.memory.write(206, 7681);
        // STR 2,0,24 0000101000011000
        this.memory.write(207, 2584);
        // LIX 1, 0    1000110001000000
        this.memory.write(208, 35904);
        // SOB 3, 3, 1  0011101111000001 
        this.memory.write(209, 15297);
        // AIR 3, 10             
        this.memory.write(210, 6922); // 0001101100001010 
        // RFS 0      0011010000000000
        this.memory.write(211, 13312);
        
//        // STR 2, 1, 0  0000101001000000
//        this.memory.write(203, 2624);
//        // LDA 2, 1, 0  0000111001000000
//        this.memory.write(204, 3648);
//        // SIR 2, 1    0001111000000001
//        this.memory.write(205, 7681);
//        // STR 2,0,24 0000101000011000
//        this.memory.write(206, 2584);
//        // LIX 1, 0    1000110001000000
//        this.memory.write(207, 35904);
//        // SOB 3, 3, 1  0011101111000001 
//        this.memory.write(208, 15297);
//        // AIR 3, 10             
//        this.memory.write(209, 6922); // 0001101100001010 
//        // RFS 0      0011010000000000
//        this.memory.write(210, 13312);

        // M[25]=300
        // compare:
        // 10 LIX 1, 0   1000110001000000
        this.memory.write(10, 35904);
        // 11 LIX 3, 0   1000110011000000
        this.memory.write(11, 36032);
        // 12 LDX 3, 25  1000010011011001
        this.memory.write(12, 34009);
        // 13 JSR 3, 0   0011000011000000
        this.memory.write(13, 12480);

        // LDR 3,0,31
        this.memory.write(300, 1823); //0000011100011111
        // LDX 1, 30  
        this.memory.write(301, 33886); //1000010001011110
        // LDA 2, 1, 1
        this.memory.write(302, 3649); //0000111001000001
        // STR 2,0,30 
        this.memory.write(303, 2590);  //0000101000011110
        // LDX 2, 30     
        this.memory.write(304, 33950); //1000010010011110	
        // LDR 0, 2, 0
        this.memory.write(305, 1152); //0000010010000000
        // LDR 1, 1, 0
        this.memory.write(306, 1344); //0000010101000000
        // CMT 1, 0
        this.memory.write(307, 22784); //0101100100000000
        // LDR 0, 1, 0
        this.memory.write(308, 1088); //0000010001000000
        // LDR 2, 2, 0
        this.memory.write(309, 1664); //0000011010000000
        // CMB 0, 2 
        this.memory.write(310, 23680); //0101110010000000
        // STR 0,0,27
        this.memory.write(311, 2075); //0000100000011011
        // LDR 0, 2, 0
        this.memory.write(312, 1152); //0000010010000000
        // SMR 1,0,27
        this.memory.write(313, 5403); //0001010100011011
        // STR 1,0,27
        this.memory.write(314, 2331); //0000100100011011
        // LDR 2,0,29
        this.memory.write(315, 1565); //0000011000011101
        // CMB 1, 2
        this.memory.write(316, 23936); //0101110110000000
        // TRR 1, 2
        this.memory.write(317, 18816); //0100100110000000
        // JCC 4, 3, 21
        this.memory.write(318, 11221); //0010101111010101
        // STR 0, 0, 28
        this.memory.write(319, 2076); //0000100000011100
        // STR 1, 0, 29
        this.memory.write(320, 2333); //0000100100011101
        // LDR 2, 0 ,30
        this.memory.write(321, 1566); //0000011000011110
        // AIR 2, 1
        this.memory.write(322, 6657); //0001101000000001
        // STR 2, 0, 30
        this.memory.write(323, 2590); //0000101000011110
        // LIX 2, 0     
        this.memory.write(324, 35968); //1000110010000000	
        // SOB 3, 3, 4
        this.memory.write(325, 15300); //0011101111000100
        // AIR 3, 14               
        this.memory.write(326, 6926); // 0001101100001110
        // RFS 0
        this.memory.write(327, 13312); //0011010000000000

        // out：
        // 14 LDR 1, 1, 0   0000010101000000
        this.memory.write(14, 1344);
        // 15 LDR 0, 0, 28  0000010000011100
        this.memory.write(15, 1052);
        // 16 OUT 1, 1     1100100100000001
        this.memory.write(16, 51457);
        // 17 OUT 0, 1     1100100000000001
        this.memory.write(17, 51201);

        JOptionPane.showMessageDialog(this.ui, "Loading initial program succeeded.");
    }
}
