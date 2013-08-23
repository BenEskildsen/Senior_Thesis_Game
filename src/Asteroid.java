//Look how freaking easy this class is with an Actor class!
import java.awt.*;
public class Asteroid extends Actor{
    public Asteroid(Image pic, double x, double y, double xsp, double ysp, boolean visible){
        super (pic,x,y,xsp,ysp,visible);
    }
    public Asteroid(Image pic, double x, double y, double xsp, double ysp){
        super (pic,x,y,xsp,ysp);
    }
}
