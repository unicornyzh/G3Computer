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
public class CU implements DataHandlingOperations, ControlFlowOperations {
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
        this.registers.mbr.setContent(this.memory.read(this.registers.mar.getContent()));
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
                this.registers.iar.setContent(memory.read(instruction.getAddress()));
            // Indexing
            else
                // IAR = Memory[Instruction.address + X[ix]]
                this.registers.iar.setContent(memory.read(this.registers.x[instruction.getIX()].getContent() + instruction.getAddress()));
        }
        // MAR = IAR
        this.registers.mar.setContent(this.registers.iar.getContent());
        // MBR = Memory[MAR]
        this.registers.mbr.setContent(this.memory.read(this.registers.mar.getContent()));
    }
    
    public Register execute(ISA instruction) throws Exception {
        return instruction.operate(this, this.alu, this);
    }
    
    public void store(Register target) {
        // Store into memory
        if (target == null) {
            // MBR = IRR
            this.registers.mbr.setContent(this.registers.irr.getContent());
            // Memory[MAR] = MBR
            this.memory.write(this.registers.mar.getContent(), this.registers.mbr.getContent());
        }
        else
            // Target register = IRR
            target.setContent(this.registers.irr.getContent());
    }
    
    public void nextInstruction() {
        // Increment by 1 (Branch has been implemented in previous steps by changing PC).
        this.registers.pc.setContent(this.registers.pc.getContent() + 1);
    }

    @Override
    public Register LDR(ISA instruction) {
        // IRR = MBR
        this.registers.irr.setContent(this.registers.mbr.getContent());
        // GPR[r]
        return this.registers.gpr[instruction.getR()];
    }

    @Override
    public Register STR(ISA instruction) {
        // IRR = R[r]
        this.registers.irr.setContent(this.registers.gpr[instruction.getR()].getContent());
        // Means the result will be stored into memory
        return null;
    }

    @Override
    public Register LDA(ISA instruction) {
        // IRR = MAR
        this.registers.irr.setContent(this.registers.mar.getContent());
        // GPR[r]
        return this.registers.gpr[instruction.getR()];
    }

    @Override
    public Register LDX(ISA instruction) {
        // IRR = MBR
        this.registers.irr.setContent(this.registers.mbr.getContent());
        // X[ix]
        // ix can't be 0 here. Confirmation is needed (later).
        return this.registers.x[instruction.getIX()];
    }

    @Override
    public Register STX(ISA instruction) {
        // IRR = X[ix]
        this.registers.irr.setContent(this.registers.x[instruction.getIX()].getContent());
        // Means the result will be stored into memory
        return null;
    }

    @Override
    public Register JZ(ISA instruction) {
        // IRR = MAR - 1 if GPR[r] == 0 else PC
        if (this.registers.gpr[instruction.getR()].getContent() == 0)
            this.registers.irr.setContent(this.registers.mar.getContent() - 1);
        else
            this.registers.irr.setContent(this.registers.pc.getContent());
        // PC
        return this.registers.pc;
    }

    @Override
    public Register JNE(ISA instruction) {
        // IRR = MAR - 1 if GPR[r] != 0 else PC
        if (this.registers.gpr[instruction.getR()].getContent() != 0)
            this.registers.irr.setContent(this.registers.mar.getContent() - 1);
        else
            this.registers.irr.setContent(this.registers.pc.getContent());
        // PC
        return this.registers.pc;
    }

    @Override
    public Register JCC(ISA instruction) {
        int cc = instruction.getR();
        // IRR = MAR - 1 if If cc bit == 1 else PC
        if ((this.registers.cc.getContent() >> cc & 1) == 1)
            this.registers.irr.setContent(this.registers.mar.getContent() - 1);
        else
            this.registers.irr.setContent(this.registers.pc.getContent());
        // PC
        return this.registers.pc;
    }

    @Override
    public Register JMA(ISA instruction) {
        // IRR = MAR - 1
        this.registers.irr.setContent(this.registers.mar.getContent() - 1);
        // PC
        return this.registers.pc;
    }

    @Override
    public Register JSR(ISA instruction) {
        // GPR[3] = PC + 1
        this.registers.gpr[3].setContent(this.registers.pc.getContent() + 1);
        // IRR = MAR - 1
        this.registers.irr.setContent(this.registers.mar.getContent() - 1);
        // PC
        return this.registers.pc;
    }

    @Override
    public Register RFS(ISA instruction) {
        // GPR[0] = Immed
        this.registers.gpr[0].setContent(instruction.getAddress());
        // IRR = GPR[3] - 1
        this.registers.irr.setContent(this.registers.gpr[3].getContent() - 1);
        // PC
        return this.registers.pc;
    }

    @Override
    public Register SOB(ISA instruction) {
        // GPR[r] = GPR[r] - 1
        this.registers.gpr[instruction.getR()].setContent(this.registers.gpr[instruction.getR()].getContent() - 1);
        // IRR = MAR - 1 if If GPR[r] > 0 else PC
        if (this.registers.gpr[instruction.getR()].getContent() > 0)
            this.registers.irr.setContent(this.registers.mar.getContent() - 1);
        else
            this.registers.irr.setContent(this.registers.pc.getContent());
        // PC
        return this.registers.pc;
    }

    @Override
    public Register JGE(ISA instruction) {
        // IRR = MAR - 1 if If GPR[r] >= 0 else PC
        if (this.registers.gpr[instruction.getR()].getContent() >= 0)
            this.registers.irr.setContent(this.registers.mar.getContent() - 1);
        else
            this.registers.irr.setContent(this.registers.pc.getContent());
        // PC
        return this.registers.pc;
    }
}
