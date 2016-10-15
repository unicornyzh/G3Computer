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
    public Register operate (DataHandlingOperations dho, ArithmeticLogicOperations alo, ControlFlowOperations cfo) throws Exception {
        switch (this.opcode) {
            case 01:
                return dho.LDR(this);
            case 02:
                return dho.STR(this);
            case 03:
                return dho.LDA(this);
            case 041:
                return dho.LDX(this);
            case 042:
                return dho.STX(this);
            case 043:
                return dho.LIX(this);
            case 061:
                return dho.IN(this);
            case 062:
                return dho.OUT(this);
                
            case 04:
                return alo.AMR(this);
            case 05:
                return alo.SMR(this);
            case 06:
                return alo.AIR(this);
            case 07:
                return alo.SIR(this);
            case 020:
                return alo.MLT(this);
            case 021:
                return alo.DVD(this);
            case 022:
                return alo.TRR(this);
            case 023:
                return alo.AND(this);
            case 024:
                return alo.ORR(this);
            case 025:
                return alo.NOT(this);
            case 026:
                return alo.CMT(this);
            case 027:
                return alo.CMB(this);
            case 031:
                return alo.SRC(this);
            case 032:
                return alo.RRC(this);
                
            case 010:
                return cfo.JZ(this);
            case 011:
                return cfo.JNE(this);
            case 012:
                return cfo.JCC(this);
            case 013:
                return cfo.JMA(this);
            case 014:
                return cfo.JSR(this);
            case 015:
                return cfo.RFS(this);
            case 016:
                return cfo.SOB(this);
            case 017:
                return cfo.JGE(this);
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
