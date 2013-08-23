public class DoublePoint{
    //this class is made because regular java points are not doubles for some reason
    
    //these are the two coordinates:
    private double x;
    private double y;
    
    //constructor
    public DoublePoint(double xcoord, double ycoord){
        x = xcoord;
        y = ycoord;
    }
    
    //accessors
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    //mutator. specifically used at the end to take all my points and move them from the top left
    //corner to the center of the screen or wherever the ship will be.
    public void translate( double dx, double dy){
        x+=dx;
        y+=dy;
    }
    
    //mutator to make one pixel be less than one unit in the graph:
    public void stretch( double sx, double sy){
        x = x*sx;
        y = y*sy;
    }
    
    //to change the points:
    public void changeX(double newX){
        x = newX;
    }
    public void changeY(double newY){
        y= newY;
    }
}