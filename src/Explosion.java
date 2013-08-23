import java.util.Random;

public class Explosion extends Actor{
    private int stepsToEnd = 150;
    private double radius = 0;
    private boolean beginFade = false;
    private Random generator = new Random();

    public Explosion(double Xloc, double Yloc){
        super(Xloc, Yloc,0);
    }

    public void updateLoc(){
        stepsToEnd--;
        if(stepsToEnd <= 0){
            this.setVisible(false);
        } else if(stepsToEnd > 100) {
            this.setCompositeFade(.9F);
            double toradius = .5 + generator.nextInt(2);
            this.setX(this.getX()-toradius/2);
            this.setY(this.getY()-toradius/2);
            radius = toradius + radius;
        } else if(stepsToEnd > 50) {
            this.setCompositeFade(.8F);
            double toradius = stepsToEnd/200;
            this.setX(this.getX()-toradius/2);
            this.setY(this.getY()-toradius/2);
            radius = toradius + radius;
        } else{
            this.setCompositeFade((float) (1-(100-stepsToEnd)/100.0));
            beginFade = true;
            stepsToEnd-=3;
        }
        this.setRadius(radius);
    }

}