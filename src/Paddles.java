import java.awt.Graphics;
import java.awt.Color;

public class Paddles 
{
    protected int x,y,width,height; // Position and dimensions of paddle
    protected int score = 0;
    public Paddles()
    {

    }
    
    public Paddles (int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawPaddle(Graphics g)
    {
        g.setColor(Color.GREEN); // Makes paddle Green
        g.drawRect(x,y,width,height); // Draws outline of paddle
        g.fillRect(x,y,width,height); // Fills it in
    }
}