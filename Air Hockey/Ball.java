import java.awt.Color;
import java.awt.Graphics;

class Ball // the ball of the game
{
    private Color ballColor;
    private double radius;
    private double speedLoss;
    private Vector2D velocity;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int centreX;
    private int centreY;
    private int width;
    private int height;

    public int getDx()
    {
        return dx;
    }
    public int getDy()
    {
        return dy;          //getters
    }

    public Vector2D getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vector2D vec2d)
    {
        velocity = vec2d;
    }

    public double getSpeedLoss()
    {
        return speedLoss;
    }

    public void lowerSpeedLoss(double value)    //lowering the speedLoss (friction) 0<=speedLoss <=1
    {
        speedLoss -= value;
    }

    public void resetSpeedLoss() //resetting it after a player hit the ball
    {
        speedLoss = 1;
    }

    public void setX(int value) //setters which change the position (relocation)
    {
        x += value;     
        if (x < 0)
            x = 0;
        else if(x > 753) // we do not let the ball go out of bounds (borders)
        {
            getVelocity().setX(getVelocity().getX()*(-1));     //reflect ball's vector speed again (otherwise ball will still get out of bounds)
            x += getVelocity().getX(); 
            setY((int) getVelocity().getY());    
            setCentreX(); 
            setCentreY();
        }   
    }

    public void setY(int value)
    {
        y += value;
        if( y < 65 && (x < 250 || x > 510))
        {
            getVelocity().setY(getVelocity().getY()*(-1));    //reflect ball's vector speed again (otherwise ball will still get out of bounds)
            setX((int) getVelocity().getX());  
            y += getVelocity().getY();    
            setCentreX(); 
            setCentreY();
        }
    }

    public double getRadius()       //radius useful for the itersection mechanism
    {
        return radius;
    }

    public void setRadius()
    {
        radius = width/2;
    }

    public int getCentreX() //centre getter
    {
        return centreX;
    }

    public int getCentreY()
    {
        return centreY;
    }
    
    public void setCentreX() //updating centre setter
    {
        centreX = x + (width/2);
    }

    public void setCentreY()
    {
        centreY = y + (height/2); 
    }

    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Ball(Color ballColor, int x, int y, int width, int height) //constructor
    {
        this.ballColor = ballColor;
        this.speedLoss = 1;
        this.dx = 0;
        this.dy = 0;
        this.x = x;
        this.y = y;
        setCentreX();
        setCentreY();
        this.width = width;
        this.height = height;
    }
}