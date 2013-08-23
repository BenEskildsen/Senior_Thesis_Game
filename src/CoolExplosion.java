import java.util.Random;

public class CoolExplosion extends Actor{
    private int stepsToEnd = 250;
    private double radius = 0;
    private Random generator = new Random();

    public CoolExplosion(double Xloc, double Yloc){
        super(Xloc, Yloc,0);
    }

    public void updateLoc(){
        stepsToEnd--;
        if(stepsToEnd <= 0){
            this.setVisible(false);
        } else if(stepsToEnd > 100) {
            double toradius = 18;
            this.setX(this.getX()-toradius/2);
            this.setY(this.getY()-toradius/2);
            radius = toradius + radius;
        } else if(stepsToEnd > 50) {
            double toradius = 6;
            this.setX(this.getX()-toradius/2);
            this.setY(this.getY()-toradius/2);
            radius = toradius + radius;
        } else{
            stepsToEnd-=3;
        }
        this.setRadius(radius);
    }

}