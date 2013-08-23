//this is the abstract class for all the objects that will be part of the game
//as of right now, the only actor is going to be asteroids
//but I like to have good programming habits

//what's so cool about having this class is that in the game class I can just have a list
//of actors and it doesn't matter what they are, and just update their locations based on 
//their speed every step without worrying about what kind of actor it is

//I don't actually need to import all of this
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.*;

public abstract class Actor{
    //this is what the actor looks like
    private Image pic;
    //this is the actor's location
    private double Xloc;
    private double Yloc;
    //this is the actor's speed (in pixel's per step
    private double Xspeed;
    private double Yspeed;
    //should I be visible?
    private boolean visible;
    //the size, etc. specifically for explosions
    private double size;
    private boolean beginFade = false;
    private float compositeFade;
    
    
    public Actor(Image img, double initialX, double initialY, double speedX, double speedY, boolean showme){
        pic = img;
        Xloc = initialX;
        Yloc = initialY;
        Xspeed = speedX;
        Yspeed = speedY;
        visible = showme;
    }
    public Actor(Image img, double initialX, double initialY, double speedX, double speedY){
        pic = img;
        Xloc = initialX;
        Yloc = initialY;
        Xspeed = speedX;
        Yspeed = speedY;
        visible = true;
    }
    public Actor(double initialX, double initialY, double radius){
        Xloc = initialX;
        Yloc = initialY;
        Xspeed = 0;
        Yspeed = 0;
        visible = true;
        size = radius;
    }
    
    //accessors:
    public double getX(){
        return Xloc;
    }
    public double getY(){
        return Yloc;
    }
    public double getXspeed(){
        return Xspeed;
    }
    public double getYspeed(){
        return Yspeed;
    }
    public Image getPic(){
        return pic;
    }
    public boolean isVisible(){
        return visible;
    }
    public double getRadius(){
        return size;    
    }
    public boolean getFade(){
        return beginFade;
    }
    public float getCompositeFade(){
        return compositeFade;
    }
    
    //mutators:
    public void setX(double loc){
        Xloc = loc;
    }
    public void setY(double loc){
        Yloc = loc;
    }
    public void setXspeed(double speed){
        Xspeed = speed;
    }
    public void setYspeed(double speed){
        Yspeed = speed;   
    }
    public void setPic(Image img){
        pic = img;
    }
    public void setVisible(boolean showme){
        visible = showme;
    }
    public void setRadius(double value){
        size = value;
    }
    public void setCompositeFade(Float value){
        compositeFade = value;
    }
    
    //A useful method to save some time later
    //updates both X and Y positions based on speed:
    public void updateLoc(){
        //wrapping around
        
        if(Xspeed > 0){
            Xloc = ((Xloc+575) % 575) + Xspeed;
        }
        
        if(Yspeed > 0 ){
            Yloc = ((575+Yloc) % 575) + Yspeed;
        }
    }
}
