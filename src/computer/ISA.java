/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class ISA {
    // Components of the intruction
    private final int opcode;
    private final int r;
    private final int ix;
    private final int i;
    private final int address;
    
    // Decode
    public ISA(int instruction) {
        this.opcode = (instruction & 0x0000fc00) >> 10;
        this.r = (instruction & 0x00000300) >> 8;
        this.ix = (instruction & 0x000000c0) >> 6;
        this.i = (instruction & 0x00000020) >> 5;
        this.address = instruction & 0x0000001f;
    }
    
    // Execute
    public Register operate (DataHandlingOperations dho, ArithmeticLogicOperations alo) throws Exception {
        switch (this.opcode) {
            case 01:
                return dho.LDR(this);
            case 02:
                return dho.STR(this);
            case 04:
                return alo.AMR(this);
            case 05:
                return alo.SMR(this);
            case 06:
                return alo.AIR(this);
            case 07:
                return alo.SIR(this);
            // Unexpected instruction occurred.
            default:
                // Better customize this exception (later work).
                throw new Exception();
        }
    }
    
    public int getR() {
        return this.r;
    }
    
    public int getIX() {
        return this.ix;
    }
    
    public int getI() {
        return this.i;
    }
    
    public int getAddress() {
        return this.address;
    }
}
