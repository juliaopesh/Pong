import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball 
{
    int counter = 0; // Counter used for buffering flicker animation
    Random rand = new Random();
    protected int x,y,width,height; // Positions and dimensions of balls
    protected double realx, realy; // Decimal positions
    int[] startingX = {1,-1}; // Possible starting x velocities
    int[] startingY = {1,-1}; // Possible starting y velocities
    double velocityX = startingX[rand.nextInt(2)]; // Randomly chooses x-velocities 
    double velocityY = startingY[rand.nextInt(2)]; // Randomly chooses y-velocities

    public Ball()
    {

    }

    public Ball (int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.realx = x;
        this.realy = y;

    }

    public void drawBall(Graphics g) // Draw function
    {
        g.setColor(Color.GREEN); // Makes colour of ball green
        x = (int) Math.round(realx);
        y = (int) Math.round(realy);
        g.drawOval(x,y,width,height); // Draws outline
        g.fillOval(x,y,width,height); // Fills in the circle
    }

    public void drawBallFlicker(Graphics g) // Ball flicker function
    {
        //Alternates between white and green colours, counter used as delay.
        if (counter < 10) 
        {
            g.setColor(Color.WHITE);
        }
        else
        {
            g.setColor(Color.GREEN);
        }
        counter += 1;

        if (counter == 20)
        {
            counter = 0;
        }
        // Rounds decimal coordinates

        x = (int) Math.round(realx);
        y = (int) Math.round(realy);
        g.drawOval(x,y,width,height); // Draws outline
        g.fillOval(x,y,width,height); // Fills in the circle
    }

    public void reset()
    {
        // Resets ball's position to center
        this.x= 340;
        this.realx = 340;
        this.y = 227;
        this.realy = 227;
        
        // Randomizes ball starting velocities anew
        this.velocityX = startingX[rand.nextInt(2)];
        this.velocityY = startingY[rand.nextInt(2)];
    }

    // Middle border
    public void drawCenter(Graphics g)
    {
        for (int i = 0; i<500; i+=50)
        {
            g.setColor(Color.GREEN);
            g.fillRect(346,i,8,25);
        }
    }
}