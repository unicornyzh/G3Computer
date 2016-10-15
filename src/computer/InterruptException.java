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
public class InterruptException extends Exception {
    private final ISA instruction;
    private boolean isSingleStep;
    
    public InterruptException(ISA instruction) {
        super();
        this.instruction = instruction;
    }
    
    public InterruptException(ISA instruction, String msg) {
        super(msg);
        this.instruction = instruction;
    }
    
    public ISA getInstruction() {
        return this.instruction;
    }
    
    public void setIsSingleStep(boolean b) {
        this.isSingleStep = b;
    }
    
    public boolean getIsSingleStep() {
        return this.isSingleStep;
    }
}
