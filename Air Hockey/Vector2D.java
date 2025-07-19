public class Vector2D {
    private double x, y;

    public Vector2D(double x, double y) { //vector, useful for the ball moving mechanism
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;           //getters
    }

    public double getY()
    {
        return y;
    }
    public double length() { // the length
        return Math.sqrt(x * x + y * y);
    }


    public void setX(double value)
    {
        x = value;                      //setters
    }

    public void setY(double value)
    {
        y = value;
    }
    
    public Vector2D normalize() {       // normalization (length == 1)
        double len = length();
        return new Vector2D(x / len, y / len);
    }


    public Vector2D multiply(double value) { // multiplication of vector with a number
        return new Vector2D(this.x * value, this.y * value);
    }
}