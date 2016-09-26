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
    private static Map<Integer, String> OPCODE_MAP = initOperation();
    
    private static Map initOperation() {
        Map<Integer, String> operation = new HashMap<>();
        // Key is Octal integer
        operation.put(01, "LDR");
        operation.put(02, "STR");
        operation.put(04, "AMR");
        operation.put(05, "SMR");
        operation.put(06, "AIR");
        operation.put(07, "SIR");
        return operation;
    }
    
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
        if (OPCODE_MAP.containsKey(this.opcode)) {
            switch (OPCODE_MAP.get(this.opcode)) {
                case "LDR":
                    return dho.LDR(this);
                case "STR":
                    return dho.STR(this);
                case "AMR":
                    return alo.AMR(this);
                case "SMR":
                    return alo.SMR(this);
                case "AIR":
                    return alo.AIR(this);
                case "SIR":
                    return alo.SIR(this);
                // Found but no branch to execute. Hardly needs considering though.
                default:
                    throw new Exception();
            }
        }
        // Unexpected instruction
        else
            // Better customize the exception (later work)
            throw new Exception();
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
