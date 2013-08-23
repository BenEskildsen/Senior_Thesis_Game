import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.util.Random;
import java.lang.Math;

public class PolarFrame extends JFrame implements Runnable{
    //SWING COMPONENTS
    //this panel handles the whole game and is in border layout:
    private JPanel GamePanel;
    //this is the textfield where the user enters the equation:
    private JTextField EquationField;
    //this is the button the user presses when the equation is entered:
    private JButton GoButton;
    //this is the button to add more asteroids to an unclear equation:
    private JButton MoreButton;
    //the back button
    private JButton BackButton;
    //Takes care of both the textfield and the button:
    private JPanel SouthPanel;
    //is the panel for the graph to be drawn:
    private PaintSurfacePanel paintSurfacePanel;
    
    //EQUATION-RELATED 
    //this is the equation the user entered, it is initialized after GoButton has been pressed:
    private String Formula = "";
    //the Equation of the Formula
    private Equation equ;
    //the list of points to be graphed:
    private ArrayList<DoublePoint> points = new ArrayList();
    //the coefficients:
    private double a;
    private double b;
    private double phi;
    private double c;
    
    //IMAGES
    private Image asteroidPic;
    
    //SETTINGS
    //will we be graphing in cartesian or in polar?
    private boolean polar = true;
    //window dimensions
    private int width = 575;
    private int height = 625;
    //canvas dimensions:
    private int cwidth = width;
    private int cheight = height-150;
    //wait time for animation speed (milli/step):
    private int waitTime = 30;
    //fraction of the list of points to decide how much of the graph is displayed at once:
    private double fractionOfList = 1/5.0;
    //0 = none, 1 = linear, 2 = quadratic, 3 = sin/cos/tan
    private int equationType = 3;
    //atleast how many asteroids do we want?
    private int minNumberOfAsteroids = 10;
    //at most how many asteroids do we want?
    private int maxNumberOfAsteroids = 20;
    
    //ACTOR INFO
    private ArrayList<Actor> theActors = new ArrayList();
    boolean goodToUpdate = true;
    private int numAsteroids = 0;
    
    Random generator = new Random();
    
    
    public static void main(String[] args) {
        new PolarFrame();
    }
    
    //probably will never need this
    public PolarFrame(int minA, int maxA){
        //SETTINGS
        //atleast how many asteroids do we want?
        minNumberOfAsteroids = minA;
        //at most how many asteroids do we want?
        maxNumberOfAsteroids = maxA;
        
        new PolarFrame();
    }
    
    public PolarFrame(){
        
        //Need a good size:
        this.setSize(width,height);
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //The title
        this.setTitle("MATHstroids");
        
        //Create the ActionLister c1:
        ClickListener c1 = new ClickListener();
        
        //initialize the GamePanel in borderlayout:
        GamePanel = new JPanel();
        GamePanel.setLayout(new BorderLayout());
        //Add it to the PolarFrame:
        //borderlayout is a change
        this.add(GamePanel, BorderLayout.SOUTH);
        
        //intialize the SouthPanel:
        SouthPanel = new JPanel();
        SouthPanel.setSize(600,150);
        GamePanel.add(SouthPanel, BorderLayout.SOUTH);
        
        //Add the label to the panel:
        if(polar){
            SouthPanel.add(new JLabel("r= "));
        } else {
            SouthPanel.add(new JLabel("y= "));
        }
        //Initialize the textfield and add it to the panel:
        EquationField = new JTextField(20);
        SouthPanel.add(EquationField);
        
        //Initialize the button and add it to the panel:
        GoButton = new JButton("Enter Equation");
        GoButton.addActionListener(c1);
        SouthPanel.add(GoButton);
        
        //Initialize the button and add it to the panel:
        MoreButton = new JButton("Add Asteroids");
        MoreButton.addActionListener(c1);
        SouthPanel.add(MoreButton);
        
        //the back button
        BackButton = new JButton("Back");
        BackButton.addActionListener(c1);
        SouthPanel.add(BackButton);
        
        //Adding it to the North Might be a better idea
        paintSurfacePanel = new PaintSurfacePanel();
        //GamePanel.add(paintSurfacePanel, BorderLayout.CENTER);
        
        //adding the paintsurface directly to the frame seems to be the only way to get animation to work properly
        this.add(paintSurfacePanel);
        
        //paint thread
        Thread p = new Thread(paintSurfacePanel);
        p.start();
        
        
        //setting the background color to black
        Container content = this.getContentPane();
        content.setBackground(Color.black);
        
        //loading the asteroid picture
        Toolkit kit = Toolkit.getDefaultToolkit();
        asteroidPic = kit.getImage("asteroid.png");
        
        //Last thing I do is make it all visible:
        this.setVisible(true);
        
        //Game theGame = new Game();
        //theGame.runGame(paintSurfacePanel);
        
    }
    
    //here we're implementing runnable by writing the run method
    public void run(){
        //outer game loop
        while(true){
            
            //Setting up gamestate
            //These ifs each correspond to a different kind of equation
            if(equationType == 0){
                //0 is just random placement
                for (int k = 0; k<generator.nextInt(maxNumberOfAsteroids)+minNumberOfAsteroids; k++){
                    int xpos = generator.nextInt(cwidth-20);
                    int ypos = generator.nextInt(cheight-20);
                    double xsp = (cwidth/2-xpos)/1000.0;
                    double ysp = (cheight/2-ypos)/1000.0;
                    Actor ast1 = new Asteroid(asteroidPic,xpos,ypos,xsp,ysp);
                    theActors.add(ast1);
                    numAsteroids++;
                }
            } else if(equationType == 1){
                //1 is linear
                int m = generator.nextInt(6)-3;
                int b = generator.nextInt(10)-5;
                
                System.out.println("m= " + m);
                System.out.println("b= " + b);
                
                for (int k = 0; k<generator.nextInt(maxNumberOfAsteroids)+minNumberOfAsteroids; k++){
                    int xpos = generator.nextInt(cwidth-20);
                    //THIS CAN'T STAY LIKE THIS IF THERE ARE ADJUSTMENTS
                    double transformedX = (xpos-275)/50.0;
                    double transformedY = m * transformedX + b;
                    int ypos = (int) (transformedY * -50 + 275)-10;
                    
                    //System.out.println("transformedX= " + transformedX);
                    //System.out.println("transformedY= " + transformedY);
                    //System.out.println("xpos= " + xpos);
                    //System.out.println("ypos= " + ypos);
                    
                    double xsp = 0;
                    double ysp = 0;
                    Actor ast1 = new Asteroid(asteroidPic,xpos-10,ypos,xsp,ysp);
                    theActors.add(ast1);
                    numAsteroids++;
                }
                
            }else if(equationType == 2){
                //IF THIS GETS USED:
                //gotta adjust a,b,c values so that asteroids always appear on-screen
                
                //2 is quadratic
                double a = (generator.nextInt(9)-4)/2.0;
                int b = generator.nextInt(7)-3;
                int c = generator.nextInt(5)-2;
                
                System.out.println("a= " + a);
                System.out.println("b= " + b);
                System.out.println("c= " + c);
                
                for (int k = 0; k<generator.nextInt(maxNumberOfAsteroids)+minNumberOfAsteroids; k++){
                    int xpos = generator.nextInt(cwidth-20);
                    //THIS CAN'T STAY LIKE THIS IF THERE ARE ADJUSTMENTS
                    double transformedX = (xpos-275)/50.0;
                    double transformedY = a * transformedX * transformedX + b * transformedX + c;
                    int ypos = (int) (transformedY * -50 + 275)-10;
                    
                    //System.out.println("transformedX= " + transformedX);
                    //System.out.println("transformedY= " + transformedY);
                    //System.out.println("xpos= " + xpos);
                    //System.out.println("ypos= " + ypos);
                    
                    double xsp = 0;
                    double ysp = 0;
                    Actor ast1 = new Asteroid(asteroidPic,xpos-10,ypos,xsp,ysp);
                    theActors.add(ast1);
                    numAsteroids++;
                }
            } else if(equationType == 3){
                //3 is sin/cos/tan
                // makin it all sin baby
                //GOTTA ADJUST THESE FOR DIFFICULTY!
               
                
                a = generator.nextInt(9)-4;
                //we don't want a or b to equal 0
                while(a ==0){
                    a = generator.nextInt(9)-4;
                }
                b = generator.nextInt(5)-2;
                while(b ==0){
                    b = generator.nextInt(5)-2;
                }
                phi = (generator.nextInt(4)-1)*Math.PI/2.0;
                c = generator.nextInt(7)-3;
                
                System.out.println("a= " + a);
                System.out.println("b= " + b);
                System.out.println("phi = " + phi);
                System.out.println("c= " + c);
                
                for (int k = 0; k<generator.nextInt(maxNumberOfAsteroids)+minNumberOfAsteroids; k++){
                    int xpos = generator.nextInt(13)-6;
                    int ypos = (int) (a * Math.sin(b*xpos + phi) + c);
                    
                    //Converting to polar:
                    double theta = xpos;
                    double r = ypos;
                    
                    double polaredY = r * Math.sin(theta);
                    double polaredX = r * Math.cos(theta);
                    
                    double transformedX = (polaredX * 50.0 + 275) - 10;
                    double transformedY = (polaredY * -50.0 + 275);
                    
                    xpos = (int)transformedX;
                    ypos = (int)transformedY;
                    
                    //System.out.println("transformedX= " + transformedX);
                    //System.out.println("transformedY= " + transformedY);
                    System.out.println("xpos= " + xpos);
                    System.out.println("ypos= " + ypos);
                    
                    double xsp = 0;
                    double ysp = 0;
                    Actor ast1 = new Asteroid(asteroidPic,xpos,ypos,xsp,ysp);
                    theActors.add(ast1);
                    numAsteroids++;
                }
            }
            
            
            
            //the game loop:
            //this is a bad way to keep this loop up
            do{
                equ = new Equation(Formula);
                Formula= "";
                
                //re-initialize the graph
                points = equ.toPoints();
                
                //print the formula I just wrote
                //System.out.println("formula: " + Formula);
                //System.out.println("num of points: " + points.size());
                for(int i = 0; i<points.size()-points.size()*fractionOfList; i++){
                    ArrayList<DoublePoint> partialpoints = new ArrayList();
                    for(int n = i; n<points.size()*fractionOfList+i; n++){
                        double toX = points.get(n).getX();
                        double toY = points.get(n).getY();
                        DoublePoint toAdd = new DoublePoint(toX, toY);
                        partialpoints.add(toAdd);
                        //System.out.println("from points: " + points.get(n).getX() + ", " + points.get(n).getY());
                        //System.out.println("from partial: " + toAdd.getX() + ", " + toAdd.getY());
                    }
                    paintSurfacePanel.setPartialPoints(partialpoints);
                    paintSurfacePanel.adjustPoints();
                    //paintSurfacePanel.repaint();
                    
                    //wait for waitTime milliseconds
                    try{
                        Thread.sleep(waitTime);
                    } catch(InterruptedException e){
                        
                    }
                    //System.out.println("waiting...");
                    
                }
                
                //System.out.println("num of asteroids: " + numAsteroids);
                
                //paintSurfacePanel.repaint();
            } while(numAsteroids>3);
            
            JOptionPane.showMessageDialog(null, "You destroyed all the MATHstroids \n Click OK to play again");
            
            //reset the screen and get rid of old explosions, missed asteroids etc.
            theActors = new ArrayList();
            numAsteroids = 0;
        }
    }
    
    //Here's the ActionListener class:
    private class ClickListener implements ActionListener{
        
        
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == GoButton){
                
                //Making sure the textfield is not just totally empty:
                if(EquationField.getText().equals("") == false){
                    //initializing the Equation
                    Formula = EquationField.getText();
                }
            } else if(e.getSource() == MoreButton){
                for (int k = 0; k<4; k++){
                    int xpos = generator.nextInt(13)-6;
                    int ypos = (int) (a * Math.sin(b*xpos + phi) + c);
                    
                    //Converting to polar:
                    double theta = xpos;
                    double r = ypos;
                    
                    double polaredY = r * Math.sin(theta);
                    double polaredX = r * Math.cos(theta);
                    
                    double transformedX = (polaredX * 50.0 + 275) - 10;
                    double transformedY = (polaredY * -50.0 + 275);
                    
                    xpos = (int)transformedX;
                    ypos = (int)transformedY;
                    
                    //System.out.println("transformedX= " + transformedX);
                    //System.out.println("transformedY= " + transformedY);
                    System.out.println("xpos= " + xpos);
                    System.out.println("ypos= " + ypos);
                    
                    double xsp = 0;
                    double ysp = 0;
                    Actor ast1 = new Asteroid(asteroidPic,xpos,ypos,xsp,ysp);
                    theActors.add(ast1);
                    numAsteroids++;
                }
            } else if(e.getSource() == BackButton){
                //"close" the game frame
                getRootPane().getParent().setVisible(false);
                new MainMenu();
            }
            
        }
    }
    
    public class PaintSurfacePanel extends JComponent implements Runnable{

        private ArrayList<DoublePoint> parpoints = new ArrayList();
        
        
        //This is the painting loop to jsut repaint everything every however-many milliseconds.
        //SOOOO much faster than the way I was doing it.
        public void run(){
            while (true){
                repaint();
                try{
                    Thread.sleep(waitTime);
                } catch (Exception e) {
                }
            }
        }
        
        
        
        public void paint(Graphics g){
            //doing the thing the book said to cast g as a graphics2d:
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
                
            Graphics2D g3 = (Graphics2D)g;
            g3.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            
            //this is just a test:
            //System.out.println("we made it to the paintsurface");
            
            ArrayList<Actor> tempActors = new ArrayList();
            for(Actor actor : theActors){
               tempActors.add(actor); 
            }
            
            //drawing the asteroids:
            for(DoublePoint point : parpoints){
                for(Actor actor : theActors){
                    if(actor instanceof Asteroid){
                        //testing for a collision
                        //GOTTA CHANGE THE 20'S !!!
                        if((point.getX() <= actor.getX() + 20 && point.getX() >= actor.getX()) && (point.getY() >= actor.getY() && point.getY() <=actor.getY()+20)){
                            actor.setVisible(false);
                            Actor destroyedAsteroid = new Explosion(actor.getX()+10, actor.getY()+10);
                            Actor destroyedAsteroidtwo = new CoolExplosion(actor.getX()+10, actor.getY()+10);
                            try {
                                tempActors.add(destroyedAsteroid);
                                tempActors.add(destroyedAsteroidtwo);
                                tempActors.remove(actor);
                                numAsteroids--;
                            } catch (Exception e){
                                
                            }
                        }
                    }
                }
            }
            
            for(Actor actor : theActors){
                if(actor.isVisible()){
                    int currentX = (int)(actor.getX() + actor.getXspeed());
                    int currentY = (int)(actor.getY() + actor.getYspeed());
                    
                    actor.updateLoc();
                    
                    if(actor instanceof Asteroid){
                        g.drawImage(asteroidPic, currentX, currentY, this);
                    }else if (actor instanceof Explosion){
                        Shape circle = new Ellipse2D.Float((float)actor.getX(), (float)actor.getY(), (float)actor.getRadius(), (float)actor.getRadius());
                        g3.setColor(Color.WHITE);
                        g3.fill(circle);
                        g3.draw(circle);
                        //fading just doesn't work for some reason. It's fading everything BUT the explosion
                        //g3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, actor.getCompositeFade()));
                    }else if (actor instanceof CoolExplosion){
                        Shape ring = new Ellipse2D.Float((float)actor.getX(), (float)actor.getY(), (float)actor.getRadius(), (float)actor.getRadius());
                        g3.setColor(Color.CYAN);
                        g3.draw(ring);
                    }
                }
            }
            
            theActors = tempActors;
            
            //drawing the coordinate axes:
            g2.setColor(Color.GREEN);
            Shape Xaxis = new Line2D.Double(275.0,0.0,275.0,550.0);
            Shape Yaxis = new Line2D.Double(0.0,275.0,550.0,275.0);
            g2.draw(Xaxis);
            g2.draw(Yaxis);
            
            //drawing the horizontal ticks:
            for (double k = -9; k <= 9; k++){
                Shape Xtick = new Line2D.Double((k+275)+(k*50),270,(k+275)+(k*50),280);
                g2.draw(Xtick);
            }
            //drawing the vertical ticks:
            for (double k = -5; k <= 5; k++){
                Shape Ytick = new Line2D.Double(270,(k+275)+(k*50),280,(k+275)+(k*50));
                g2.draw(Ytick);
            }
            
            //labeling the axes:
            g2.drawString("-1", (int)(275-40), 295);
            g2.drawString("1", (int)(275+40), 295);
            g2.drawString("-1", (int)(275+15), 330);
            g2.drawString("1", (int)(275+15), 230);
            
            //setting the color, red is nice:
            g2.setColor(Color.RED);             
            
            //draw the points
            for(int k = 0; k<parpoints.size()-1; k++){
                Shape L = new Line2D.Double(parpoints.get(k).getX(), parpoints.get(k).getY(), parpoints.get(k+1).getX(), parpoints.get(k+1).getY());
                g2.draw(L);
            }
        }
        
        public void setPartialPoints(ArrayList<DoublePoint> somepoints){
            parpoints = somepoints;
        }
        
        public void adjustPoints(){
            if(polar){
                Equation.toPolarCoords(parpoints);
            }
            //translating all the points to the center of the panel:
            for(DoublePoint point : parpoints){
                //This translation makes the coordinate axis be 5 by 5 (for on quadrant)
                //to get an axis that goes from -10 to 10, then use
                //point.stretch(25, -25)
                //but then the tick marks will only count every other number
                point.stretch(50, -50);
                point.translate(275.0,275.0);
            }
        }
    }
}