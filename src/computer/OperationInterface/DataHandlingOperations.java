/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer.OperationInterface;

import computer.ComputerExceptions.DeviceFailureException;
import computer.ComputerExceptions.InterruptException;
import computer.ComputerExceptions.MemoryAddressException;
import computer.ISA;
import computer.Register;

/**
 *
 * @author Administrator
 */
public interface DataHandlingOperations {

    public Register LDR(ISA instruction);

    public Register STR(ISA instruction);

    public Register LDA(ISA instruction);

    public Register LDX(ISA instruction);

    public Register STX(ISA instruction);

    /**
     * Customized Operation. Opcode 043. LIX x, immed: Assign X with an
     * immediate value.
     *
     * @param instruction
     * @return X[ix]
     */
    public Register LIX(ISA instruction);

    public Register IN(ISA instruction) throws InterruptException, DeviceFailureException;

    public Register OUT(ISA instruction);

    public Register CHK(ISA instruction);

    /**
     * Customized Operation. Opcode 064. GETS r, devid: Input a string from
     * device and store its initial address into GPR[r].
     *
     * @param instruction
     * @return GPR[r]
     * @throws computer.ComputerExceptions.DeviceFailureException
     */
    public Register GETS(ISA instruction) throws DeviceFailureException;

    /**
     * Customized Operation. Opcode 065. PUTS r, devid: Output a string to
     * device whose initial address is in GPR[r].
     *
     * @param instruction
     * @return GPR[r]
     * @throws computer.ComputerExceptions.MemoryAddressException
     */
    public Register PUTS(ISA instruction) throws MemoryAddressException;
}
