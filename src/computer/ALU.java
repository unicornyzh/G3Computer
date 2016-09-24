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
public class ALU implements ArithmeticLogicOperations {
    private final ProcessorRegisters registers;
    
    public ALU(ProcessorRegisters registers) {
        this.registers = registers;
    }

    @Override
    public Register AMR(ISA instruction) {
        // GPR[r] = c(r) + c(EA), while c(EA) == MBR
        this.registers.irr.setContent(this.registers.gpr[instruction.getR()].getContent() + this.registers.mbr.getContent());
        return this.registers.gpr[instruction.getR()];
    }

    @Override
    public Register SMR(ISA instruction) {
        // GPR[r] = c(r) - c(EA), while c(EA) == MBR
        this.registers.irr.setContent(this.registers.gpr[instruction.getR()].getContent() - this.registers.mbr.getContent());
        return this.registers.gpr[instruction.getR()];
    }

    @Override
    public Register AIR(ISA instruction) {
        this.registers.irr.setContent(this.registers.gpr[instruction.getR()].getContent() + instruction.getAddress());
        return this.registers.gpr[instruction.getR()];
    }

    @Override
    public Register SIR(ISA instruction) {
        this.registers.irr.setContent(this.registers.gpr[instruction.getR()].getContent() - instruction.getAddress());
        return this.registers.gpr[instruction.getR()];
    }
}
