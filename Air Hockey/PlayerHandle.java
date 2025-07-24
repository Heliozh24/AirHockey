
class PlayerHandle // the player handle/paddle
{
    private double radius;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private boolean passedDownBorders;
    private boolean passedLeftBorders;
    private boolean passedUpBorders;
    private boolean passedRightBorders;
    private int upKey;
    private int downKey;
    private int leftKey;
    private int rightKey;
    private int topLimit;
    private int downLimit;
    private int leftLimit;
    private int rightLimit;
    private int centreX;
    private int centreY;
    private int width;
    private int height;
    //Getters/setters
    public boolean getDownBorderPass()
    {
        return passedDownBorders;
    }


    public boolean getLeftBorderPass()
    {
        return passedLeftBorders;
    }

    public boolean getRightBorderPass()
    {
        return passedRightBorders;
    }

    public boolean getUpBorderPass()
    {
        return passedUpBorders;
    }

    public void setDownBorder(boolean value)
    {
        passedDownBorders = value;
    }

    public void setLeftBorder(boolean value)
    {
        passedLeftBorders = value;
    }

    public void setUpBorder(boolean value)
    {
        passedUpBorders = value;
    }

    public void setRightBorder(boolean value)
    {
        passedRightBorders = value;
    }

    public int getUpKey()
    {
        return upKey;
    }

    public int getDownKey()
    {
        return downKey;
    }

    public int getLeftKey()
    {
        return leftKey;
    }

    public int getRightKey()
    {
        return rightKey;
    }

    public int getTopLimit()
    {
        return topLimit;
    }

    public int getDownLimit()
    {
        return downLimit;
    }

    public int getLeftLimit()
    {
        return leftLimit;
    }

    public int getRightLimit()
    {
        return rightLimit;
    }

    public int getDx()
    {
        return dx;
    }

    public int getDy()
    {
        return dy;
    }

    public void setDx(int delta_x)
    {
        dx = delta_x;
    }

    public void setDy(int delta_y)
    {
        dy = delta_y;
    }

    public double getRadius()
    {
        return radius;
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

    public int getCentreX()
    {
        return centreX;
    }

    public int getCentreY()
    {
        return centreY;
    }
    
    public void setRadius()
    {
        radius = width/2;
    }
    
    public void setCentreX()
    {
        centreX = x + (width/2);
    }

    public void setCentreY()
    {
        centreY = y + (height/2);
    }
    
    public void setX(int dx)
    {
        x = x + dx;
    }

     public void setY(int dy)
    {
        y = y + dy;
    }
    //constructor
    public PlayerHandle( int x, int y, int width, int height, int upKey, int downKey, int leftKey, int rightKey, int topLimit, int downLimit, int leftLimit, int rightLimit)
    {
        this.dx = 0;
        this.dy = 0;
        this.passedDownBorders = false;
        this.passedLeftBorders = false;
        this.passedRightBorders = false;
        this.passedUpBorders = false;
        this.topLimit = topLimit;
        this.downLimit = downLimit;
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        setRadius();
        setCentreX();
        setCentreY();
    }
}