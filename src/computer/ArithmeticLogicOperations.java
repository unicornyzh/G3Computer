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
public interface ArithmeticLogicOperations {
    public Register AMR(ISA instruction);
    public Register SMR(ISA instruction);
    public Register AIR(ISA instruction);
    public Register SIR(ISA instruction);
}
