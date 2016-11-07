/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer.OperationInterface;

import computer.ComputerExceptions.HaltException;
import computer.ComputerExceptions.MemoryAddressException;
import computer.ComputerExceptions.UnexpectedInstructionException;
import computer.ISA;
import computer.Register;

/**
 *
 * @author Administrator
 */
public interface ControlFlowOperations {

    public Register JZ(ISA instruction);

    public Register JNE(ISA instruction);

    public Register JCC(ISA instruction);

    public Register JMA(ISA instruction);

    public Register JSR(ISA instruction);

    public Register RFS(ISA instruction);

    public Register SOB(ISA instruction);

    public Register JGE(ISA instruction);

    public Register HLT(ISA instruction) throws HaltException, UnexpectedInstructionException;

    public Register TRAP(ISA instruction) throws MemoryAddressException;
}
