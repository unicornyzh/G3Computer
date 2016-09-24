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
public class CU implements DataHandlingOperations {
    private final Memory memory;
    private final ProcessorRegisters registers;
    private final ALU alu;
    
    public CU(Memory memory, ProcessorRegisters registers, ALU alu) {
        this.memory = memory;
        this.registers = registers;
        this.alu = alu;
    }
    
    public void instuctionCycle() throws Exception {
        this.fetchInstruction();
        ISA instruction = this.decode();
        this.fetchOperand(instruction);
        Register target = null;
        try {
            target = this.execute(instruction);
        } catch (Exception ex) {
            // At this point PC is set to the first address of the boot program, which is currently the only program in memory.
            // This step should actually hand over control back to CPU to be handled.
            // Like CPU.reboot()
            this.registers.pc.setContent(0);
            throw ex;
        }
        this.store(target);
        this.nextInstruction();
    }
    
    public void fetchInstruction() {
        // MAR = PC
        this.registers.mar.setContent(this.registers.pc.getContent());
        // MBR = Memory[MAR]
        this.registers.mbr.setContent(this.memory.getByAddress(this.registers.mar.getContent()));
        // IR = MBR
        this.registers.ir.setContent(this.registers.mbr.getContent());
    }
    
    public ISA decode() {
        return new ISA(this.registers.ir.getContent());
    }
    
    public void fetchOperand(ISA instruction) {
        // Direct addressing
        if (instruction.getI() == 0) {
            // IAR = Instruction.address
            this.registers.iar.setContent(instruction.getAddress());
            // Indexing
            if (instruction.getIX() != 0)
                // IAR += X[ix]
                this.registers.iar.setContent(this.registers.iar.getContent() + this.registers.x[instruction.getIX()].getContent());
        }
        // Indirect addressing
        else {
            if (instruction.getIX() == 0)
                // IAR = Memory[Instruction.address]
                this.registers.iar.setContent(memory.getByAddress(instruction.getAddress()));
            // Indexing
            else
                // IAR = Memory[Instruction.address + X[ix]]
                this.registers.iar.setContent(memory.getByAddress(this.registers.x[instruction.getIX()].getContent() + instruction.getAddress()));
        }
        // MAR = IAR
        this.registers.mar.setContent(this.registers.iar.getContent());
        // MBR = Memory[MAR]
        this.registers.mbr.setContent(this.memory.getByAddress(this.registers.mar.getContent()));
    }
    
    public Register execute(ISA instruction) throws Exception {
        switch (instruction.getOperation()) {
            case "LDR":
                return this.LDR(instruction);
            case "STR":
                return this.STR(instruction);
            case "AMR":
                return this.alu.AMR(instruction);
            case "SMR":
                return this.alu.SMR(instruction);
            case "AIR":
                return this.alu.AIR(instruction);
            case "SIR":
                return this.alu.SIR(instruction);
            // Error
            default:
                // Better customize the exception (later work)
                throw new Exception();
        }
    }
    
    public void store(Register target) {
        // Store into memory
        if (target == null) {
            // MBR = IRR
            this.registers.mbr.setContent(this.registers.irr.getContent());
            // Memory[MAR] = MBR
            this.memory.setByAddress(this.registers.mar.getContent(), this.registers.mbr.getContent());
        }
        else
            // Target register = IRR
            target.setContent(this.registers.irr.getContent());
    }
    
    public void nextInstruction() {
        // Increment by 1 (no brach is considered for now)
        this.registers.pc.setContent(this.registers.pc.getContent() + 1);
    }

    @Override
    public Register LDR(ISA instruction) {
        // IRR = MBR
        this.registers.irr.setContent(this.registers.mbr.getContent());
        return this.registers.gpr[instruction.getR()];
    }

    @Override
    public Register STR(ISA instruction) {
        // IRR = R[r]
        this.registers.irr.setContent(this.registers.gpr[instruction.getR()].getContent());
        // Means the result will be stored into memory
        return null;
    }
}
