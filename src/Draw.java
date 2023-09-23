// Eric Nemrodov, Julia Opeshansky
// Mr. Naccarato
// ICS 4U2
// Pong Assignment, Module 4
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class Draw extends JPanel implements KeyListener
{

    protected Boolean paused = false;
    protected Boolean p1 = true;
    Paddles Paddle1 = new Paddles (30,200,10,70);
    Paddles Paddle2 = new Paddles (660,200,10,70);

    Ball ball = new Ball (340, 227, 20, 20);

    int[] current_keys = {0,0,0,0}; // List for states of keys, 1 means currently pressed, 0 means not pressed.

    Boolean inBounds = true;
    int paddleSpeed = 2; // Pixel increment
    int refresh_period = 3; // Time in milliseconds before each refresh.

    public Draw()
    {
        super();
        super.setFocusable(true);
        super.addKeyListener(this);
    }
    
    @Override
    public void keyPressed(KeyEvent arg0)
    {
        if (paused == false)
        {
            // Below are if statements for every combination of key presses
            // Activates keys by changing current_keys list
            if(arg0.getKeyCode()== KeyEvent.VK_S && arg0.getKeyCode() == KeyEvent.VK_I){
                current_keys[0] = 1;
                current_keys[2] = 1;
            }
            else if(arg0.getKeyCode()== KeyEvent.VK_S && arg0.getKeyCode() == KeyEvent.VK_K)
            {
                current_keys[0] = 1;
                current_keys[3] = 1;
                
            }
            else if(arg0.getKeyCode()== KeyEvent.VK_W && arg0.getKeyCode()== KeyEvent.VK_I)
            {
                current_keys[1] = 1;
                current_keys[2] = 1;
            }   
            else if(arg0.getKeyCode()== KeyEvent.VK_W && arg0.getKeyCode()== KeyEvent.VK_K)
            {
                current_keys[1] = 1;
                current_keys[3] = 1;
            }
            else if(arg0.getKeyCode()== KeyEvent.VK_W)
            {
                current_keys[1] = 1;
            }
            else if(arg0.getKeyCode()== KeyEvent.VK_S)
            {
                current_keys[0] = 1;
            }
            else if(arg0.getKeyCode()== KeyEvent.VK_I)
            {
                current_keys[2] = 1;
            }
            else if(arg0.getKeyCode()== KeyEvent.VK_K)
            {
                current_keys[3] = 1;
            }
        }        
    }
    
    @Override
    public void keyReleased(KeyEvent arg0)
    {
        // When a key is released, key state is reverted to 0 in current_key list.

        if(arg0.getKeyCode()== KeyEvent.VK_S){
            current_keys[0] = 0;
        }
        else if(arg0.getKeyCode()== KeyEvent.VK_W)
        {
            current_keys[1] = 0;
        }
        else if(arg0.getKeyCode()== KeyEvent.VK_I)
        {
            current_keys[2] = 0;
        }   
        else if(arg0.getKeyCode()== KeyEvent.VK_K)
        {
            current_keys[3] = 0;
        }
    }
    @Override
    public void keyTyped(KeyEvent arg0)
    {
    }
    
    public void updateMultiPlayer()
    {
        // Moves paddles depending on key activation state from current_key list
        try
        {
            Thread.sleep(refresh_period); // Waits for a few miliseconds before updating
            
            if (paused == false)
            {
                if (Paddle1.y < 400) // If first paddle hasn't reached bottom of screen, moves paddle down if DOWN is pressed
                {
                    Paddle1.y += current_keys[0]*paddleSpeed;
                }

                if (Paddle1.y > 1) // If first paddle hasn't reached top of screen, moves paddle up if UP is pressed
                {
                    Paddle1.y -= current_keys[1]*paddleSpeed;
                }
            
                if (Paddle2.y > 0) // If second paddle hasn't reached top of the screen, moves paddle up if UP is pressed
                {
                    Paddle2.y -= current_keys[2]*paddleSpeed;
                }
            
                if (Paddle2.y < 400) // If second paddle hasn't reached bottom of the screen, moves paddle down if DOWN is pressed
                {
                    Paddle2.y += current_keys[3]*paddleSpeed;
                }

                // Adjusts ball's coordinates
            
                ball.realx += ball.velocityX;
                ball.realy += ball.velocityY;
            }
            
            // If the ball is behind the paddle collision lines (far to the left or far to the right), checks for paddle collision
            if (ball.realx <= Paddle1.x || ball.realx >= Paddle2.x-ball.width)
            {
                paddleCollision();
                paddleSpeed = 1; // Lowers paddle speed to 1 for slow-motion
            }
            else
            {
                paddleSpeed = 2; // Resets paddle speed in case it was slow-motion before
            }
            if (ball.realx < Paddle1.x-3 || ball.realx > Paddle2.x-ball.width+3) // Identifies if ball is in bounds for flicker animation
            {
                inBounds = false;
            }
            else
            {
                inBounds = true;
            }
            if (ball.realy >= 470-ball.height||ball.realy < 0) // Checks if ball is outside of ceiling boundaries, initiates wall collision if true.
            {
                wallCollision();
            }

            
            repaint(); // After all updates are made to variables, repaints on screen
    
        } catch(Exception e)
        {

        }
    }

    public void updateSinglePlayer()
    {
        // Moves paddles depending on key activation state from current_key list
        try
        {
            Thread.sleep(refresh_period); // Waits for a few miliseconds before updating
            
            if (paused == false)
            {
                if (Paddle1.y < 400) // If first paddle hasn't reached bottom of screen, moves paddle down if DOWN is pressed
                {
                    Paddle1.y += current_keys[0]*paddleSpeed;
                }

                if (Paddle1.y > 1) // If first paddle hasn't reached top of screen, moves paddle up if UP is pressed
                {
                    Paddle1.y -= current_keys[1]*paddleSpeed;
                }

                // Adjusts ball's coordinates

                ball.realx += ball.velocityX;
                ball.realy += ball.velocityY;
        
                if(ball.x>340 && ball.velocityX>0)
                {
                if (ball.y < Paddle2.y+(Paddle2.height/2) && Paddle2.y+(Paddle2.height/2)>paddleSpeed && Paddle2.y >0)
                {
                    Paddle2.y -= paddleSpeed;
                }
                else if (ball.y > Paddle2.y && Paddle2.y<400)
                {
                    Paddle2.y += paddleSpeed;
                }
                }
                else
                {
                if(Paddle2.y<199)
                {
                    Paddle2.y += paddleSpeed;
                }
                else if(Paddle2.y>201)
                {
                    Paddle2.y -= paddleSpeed;
                }
                }
            }
            // If the ball is behind the paddle collision lines (far to the left or far to the right), checks for paddle collision
            if (ball.realx <= Paddle1.x || ball.realx >= Paddle2.x-ball.width)
            {
                paddleCollision();
                paddleSpeed = 1; // Lowers paddle speed to 1 for slow-motion
            }
            else
            {
                paddleSpeed = 2; // Resets paddle speed in case it was slow-motion before
            }
            if (ball.realx < Paddle1.x-3 || ball.realx > Paddle2.x-ball.width+3) // Identifies if ball is in bounds for flicker animation
            {
                inBounds = false;
            }
            else
            {
                inBounds = true;
            }
            if (ball.realy >= 470-ball.height||ball.realy < 0) // Checks if ball is outside of ceiling boundaries, initiates wall collision if true.
            {
                wallCollision();
            }

            repaint(); // After all updates are made to variables, repaints on screen
        }
        catch(Exception e)
        {}
    }
    
    public void paint(Graphics g) // Paints all game objects
    {
        if (p1)
        {
            updateSinglePlayer(); // Updates variables first
        } else 
        {
            updateMultiPlayer();
        }
    
        // Checks win conditions
        if (Paddle1.score == 3)
        {   
            Paddle1.score = 0;
            Paddle2.score = 0;
            paused = true;
            menuFnc(1);
        }
        else if (Paddle2.score == 3)
        {
            Paddle1.score = 0;
            Paddle2.score = 0;
            paused = true;
            menuFnc(2);
        }
        else
        {
            // If win conditions aren't met, draws all elements on screen
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            
            // When not in bounds, draw ball with flicker
            if (inBounds)
            {
                ball.drawBall(g);
            }
            else 
            {
                ball.drawBallFlicker(g);
            }
            ball.drawCenter(g);
            Paddle1.drawPaddle(g);
            Paddle2.drawPaddle(g);

            // Displays scores
            g.setFont(new Font("Arial", Font.PLAIN, 30)); 
            g.drawString(Integer.toString(Paddle1.score), 310, 30); 
            g.drawString(Integer.toString(Paddle2.score), 370, 30); 
        }
        
    }

    public void paddleCollision()
    {
        // paddleLoc is the y-coordinate of the paddle on the side the ball is going to
        
        int paddleLoc = 1000; // Arbitrary initial paddleLoc
        String side = ""; // The side the ball is going to, choice between "l"eft and "r"ight. Starts blank.

        if(ball.realx <= 30 && ball.realx >= 27) // If ball is on the on the left inside the paddle collision zone, program tracks Paddle1's y-coord.
        {
            paddleLoc = Paddle1.y;
            side = "l";
        }
        else if (ball.realx >= 660 -ball.width && ball.realx <= 665 - ball.width) // Same but right side
        {
            paddleLoc = Paddle2.y;
            side = "r";
        }

        if(ball.realy >= paddleLoc-15 && ball.realy <= paddleLoc+Paddle1.height-3) // Checks if ball is within paddle's y-coordinate zone
        {
            if (side == "l")
            {
                ball.realx = 30; // Snaps ball to right in front of left paddle
            }
            else if (side == "r")
            {
                ball.realx = 660-ball.width; // Snaps ball to right in front of right paddle
            }

            ball.velocityX = ball.velocityX * -1.05; // Increments x-velocity
            ball.velocityY = ball.velocityY * 1.05; // Increments y-velocity
        }
        
        else
        {
            if (ball.realx < -20) // When the ball has completely left the screen on the left
            {
                Paddle2.score += 1; // Paddle 2 gains score
                ball.reset(); // Ball variables are reset
            }
            else if (ball.realx > 700) // Same thing but from the right
            {
                Paddle1.score += 1;
                ball.reset();
            }
            try
            {
                Thread.sleep(10); // Adjusts update rate to make ball move in slow-motion when out of bounds.
            }
            catch(Exception e)
            {
                
            }
        }
    }
    public void wallCollision()
    {
        // Snaps ball's y-coordinate to right above or below the walls
        if (ball.realy <= 0)
        {
            ball.realy = 0;
        }
        else if (ball.realy >= 470 - ball.height)
        {
            ball.realy = 470 - ball.height;
        }
        
        // Flips y velocity for bounce
        ball.velocityY = ball.velocityY * -1;
    }
    public void menuFnc(int i)
    {
        paused = true;
        JDialog menuFrame = new JDialog ();
        //menuFrame.setModal (true);
        menuFrame.setAlwaysOnTop (true);

        menuFrame.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        menuFrame.setSize(300, 75);
        menuFrame.setResizable(false);
        menuFrame.setLocation(200,210);
        menuFrame.setUndecorated(true);

        JPanel menuPan = new JPanel();
        menuPan.setBackground(Color.GREEN);

        JButton single = new JButton("Single Player");
        single.setBackground(Color.BLACK);

        single.setOpaque(true);
        single.setBorderPainted(false);
        single.setForeground(Color.GREEN);

        JButton multi = new JButton("Multi Player");
        multi.setBackground(Color.BLACK);

        multi.setOpaque(true);
        multi.setBorderPainted(false);
        multi.setForeground(Color.GREEN);

        //Event listener for all buttons
        ActionListener buttonListener = new ActionListener() 
        {
            public void actionPerformed(ActionEvent ae) 
            {
                if (ae.getSource() == single) 
                {
                    menuFrame.setVisible(false);
                    updateSinglePlayer();
                    paused = false;
                    p1 = true;
                }
                else if (ae.getSource() == multi)
                {
                    menuFrame.setVisible(false);
                    updateMultiPlayer();
                    paused = false;
                    p1 = false;
                }
                try 
                    {
                        // Resets paddle positions, waits one second before starting again.
                        Paddle1.y = Paddle2.y = 200;
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) 
                    {
                    }
            
                menuFrame.dispose();
            }
        }; 

        single.addActionListener(buttonListener);
        multi.addActionListener(buttonListener);
        
        if (i == 0)
        {
            menuPan.add( new JLabel ("Welcome to Pong! Select an option:"));
        }
        else if (i == 1)
        {
            menuPan.add( new JLabel ("Player 1 won! Select an option:"));
        }
        else if (i == 2)
        {
            menuPan.add( new JLabel ("Player 2 won! Select an option:"));
        }
         
        menuPan.add(single);
        menuPan.add(multi);
        menuFrame.add(menuPan);
        menuFrame.setVisible(true);
    }

    public void keysReset() // Resets key activation states
    {
        for (int x = 0; x<4; x++)
        {
            current_keys[x] = 0;
        }
    }
}