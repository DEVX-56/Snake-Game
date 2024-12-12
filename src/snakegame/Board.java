package snakegame;


import javax.swing.*;  
import java.awt.*;    
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    
    private Image apple;  
    private Image dot;
    private Image head;
    
    private final int ALL_DOTS = 1600;  
    private final int DOT_SIZE = 10;   
    private final int RANDOM_POSITION = 29;
    
    private int apple_x;  //apple position on x cordinate
    private int apple_y;   //apple position on y cordinate
    
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;  //initially the snake will run in right direction
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean inGame = true;  // initially you will be ingame, if collison occurs then game will over
    
    
    private int dots;  
    private Timer timer;
    
    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);  
        setPreferredSize(new Dimension(400, 400));  //setting the frame size
        setFocusable(true); 
        loadImages();   // using this images we will create snake
        initGame();   //initializing game 
    }
    
    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));  
        apple = i1.getImage();
        
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        dot = i2.getImage();
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png")); 
        head = i3.getImage(); 

    }
    
    // initializing game
    public void initGame(){
        dots = 3; 
        
        for(int i=0;i<dots;i++){
            y[i] = 50;  
            x[i] = 50 - i*DOT_SIZE;         

        }
        
        locateApple();  //when game will start then we need atleast one apple
        
        timer = new Timer(140, this);
        timer.start();
    }
    
    //function for locating apple
    public void locateApple(){  
        int r = (int)(Math.random() * RANDOM_POSITION);  
        apple_x = r * DOT_SIZE;
        r = (int)(Math.random() * RANDOM_POSITION);  
        apple_y = r * DOT_SIZE;
    }
    
    //this function will show the images in frame
    public void paintComponent(Graphics g){  
        super.paintComponent(g); //call parent component
        
        draw(g);
    }
    
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
        
            for(int i=0;i<dots;i++){
                if(i==0){
                    g.drawImage(head, x[i], y[i], this);
                }else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
        
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g){
        String msg = "Game Over!";  //messeage will show when game over
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.GREEN);
        g.setFont(font);
        g.drawString(msg, (400-metrices.stringWidth(msg))/2, 400/2);  //Game over text will visible in this position
    }
    
    
    public void move(){  // function for snake movement
        for(int i=dots;i>0;i--){
            x[i] = x[i-1]; 
            y[i] = y[i-1]; 
        }
        
        if(leftDirection){  //when snake move left direction
            x[0] = x[0] - DOT_SIZE;
        }
        if(rightDirection){   //when snake move right direction
            x[0] = x[0] + DOT_SIZE;
        }
        if(upDirection){     //when snake move up direction
            y[0] = y[0] - DOT_SIZE;
        }
        if(downDirection){      //when snake down left direction
            y[0] = y[0] + DOT_SIZE;
        }
    }
    
    public void checkApple(){  //here we check is there collison between apple and snake 
        if((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;  //snake length will bve increased
            locateApple();
        }
    }
    
    public void checkCollision(){  //function to detect collicion of snake with wall
        //condition for gameover: if snake byte ifself body 
        for(int i=dots;i>0;i--){
            if((i>4) && (x[0]==x[i]) && (y[0]==y[i])){  // if size of snake is > 4 then snake can collide with it's own body
                inGame = false;
            }
        }
        //if snake coollide with boundary
        if(y[0]>=400){   //if y cordinates cross the screen width
            inGame = false;
        }
        if(x[0]>=400){   //if x cordinates cross the screen width
            inGame = false;
        }
        if(y[0]<0){   //if 
            inGame = false;
        }
        if(x[0]<0){   //if 
            inGame = false;
        }
        
        if(!inGame){  //for any type of collison the timer will stop and the snake movement will stop
            timer.stop();  // if snake collide timer will stop also the stakes movement will stop
        }
    }
    
    public void actionPerformed(ActionEvent ae){ 
        //here we take snake movement
        if(inGame){     // if we are ingame then these function will be functional
            checkApple();
            checkCollision();
            move();
        }
        repaint(); //this will refresh screen 
    }
    
    
    public class TAdapter extends KeyAdapter{  //inner class to control snake using key
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();        //detetct which key hasbeen pressed
            
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP && (!downDirection)){
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            if(key == KeyEvent.VK_DOWN && (!upDirection)){
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            
        }
    }
}