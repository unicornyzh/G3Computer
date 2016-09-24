/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import gui.RegisterGUI;

/**
 *
 * @author Administrator
 */
public class Register {
    // Binding happens when ProcessorRegisters calling its setUI() method.
    private RegisterGUI rgui;

    private final int length;
    private int content;
    
    public Register(int length) {
        this.rgui = null;
        
        this.length = length;
        this.content = 0;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public void setContent(int content) {
        this.content = content;
        this.respondToGUI();
    }
    
    public void setContentByGUI(int content) {
        this.content = content;
    }
    
    public int getContent() {
        return this.content;
    }
    
    public void setRegisterGUI(RegisterGUI rgui) {
        this.rgui = rgui;
    }
    
    private void respondToGUI() {
        if (this.rgui != null)
            this.rgui.setRadioButtons(this.content);
    }
}
