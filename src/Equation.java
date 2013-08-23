import java.util.ArrayList;

public class Equation{
    private String formula;        

    public Equation(String UserEntered){
        formula = UserEntered;
    }
    
    public String getFormula(){ //shouldn't this just be a toString()?
        return formula;
    }
    
    public String toString(){
        return formula;
    }

    //This will be overriden for each type of equation the user can enter (which will be its own class)
    //and that will create the list of points.
    public ArrayList<DoublePoint> toPoints(){
        //domain should be [-10,10]
        ArrayList<DoublePoint> thePoints = ToPython.getPoints(formula);
        return thePoints;
    }
    
    //Convert to polar:
    public static ArrayList<DoublePoint> toPolarCoords(Equation equ){
        ArrayList<DoublePoint> thePoints = equ.toPoints();
        for( DoublePoint point : thePoints){
            double theta = point.getX();
            double r = point.getY();
            //by definition of polar:
            point.changeY(r*Math.sin(theta));
            point.changeX(r*Math.cos(theta));
        }
        return thePoints;
    }
    public static ArrayList<DoublePoint> toPolarCoords(ArrayList<DoublePoint> thePoints){
        for( DoublePoint point : thePoints){
            double theta = point.getX();
            double r = point.getY();
            //by definition of polar:
            point.changeY(r*Math.sin(theta));
            point.changeX(r*Math.cos(theta));
        }
        return thePoints;
    }
}