/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer.OperationInterface;

import computer.ISA;
import computer.Register;

/**
 *
 * @author Administrator
 */
public interface ArithmeticLogicOperations {

    public Register AMR(ISA instruction);

    public Register SMR(ISA instruction);

    public Register AIR(ISA instruction);

    public Register SIR(ISA instruction);

    public Register MLT(ISA instruction);

    public Register DVD(ISA instruction);

    public Register TRR(ISA instruction);

    public Register AND(ISA instruction);

    public Register ORR(ISA instruction);

    public Register NOT(ISA instruction);

    public Register SRC(ISA instruction);

    public Register RRC(ISA instruction);

    /**
     * Customized Operation. Opcode 026. CMB rx, ry: Compare c(rx) and c(ry),
     * then return the bigger one to c(rx).
     *
     * @param instruction
     * @return GPR[rx]
     */
    public Register CMT(ISA instruction);

    /**
     * Customized Operation. Opcode 027. CMB rx, ry: Compare c(rx) and c(ry),
     * then return the smaller one to c(rx).
     *
     * @param instruction
     * @return GPR[rx]
     */
    public Register CMB(ISA instruction);

    /**
     * Customized Operation. Opcode 033. LE rx, ry, rz: c(rz) = 1 if c(rx) is
     * less than or equal to c(ry) else 0. rz is the last two digits in address.
     *
     * @param instruction
     * @return GPR[rz]
     */
    public Register LE(ISA instruction);

    /**
     * Customized Operation. Opcode 034. GE rx, ry, rz: c(rz) = 1 if c(rx) is
     * greater than or equal to c(ry) else 0. rz is the last two digits in
     * address.
     *
     * @param instruction
     * @return GPR[rz]
     */
    public Register GE(ISA instruction);

    /**
     * Customized Operation. Opcode 035. ET rx, ry, rz: c(rz) = 1 if c(rx) is
     * equal to c(ry) else 0. rz is the last two digits in address.
     *
     * @param instruction
     * @return GPR[rz]
     */
    public Register ET(ISA instruction);
}
