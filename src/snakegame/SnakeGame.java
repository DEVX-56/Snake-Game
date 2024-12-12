
package snakegame;

import javax.swing.*;

/*
 * @author: ROHAN PRAMANIK
 * @E-Mail: devx.rohan@gmail.com
 * @github: https://github.com/DEVX-56
 */
public class SnakeGame extends JFrame{
    
    SnakeGame(){   //Creating the class Constructor 
        super("Snake Game");  //Super will call the parent class constructor and this will be heading 
        add(new Board());   //using add we can add board function itmes here
        pack();   //reload
        setLocationRelativeTo(null);  //This function will analyze the screen location and set the frame in middle
        setResizable(false);  // screen resizable disable
        
    }
    
 
    public static void main(String[] args) {
        new SnakeGame().setVisible(true);
    }
    
}
 