/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computer;

import gui.UI;

/**
 *
 * @author Administrator
 */
public class G3Computer {

    private final UI ui;
    private final Computer computer;

    public G3Computer() {
        this.computer = new Computer();
        this.ui = new UI(this.computer);
        this.computer.setUI(ui);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new G3Computer();
    }

}
