import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.*;

public class MainMenu extends JFrame{
    //this panel handles the main menu    
    private JPanel MenuPanel;
    //this button starts the trig grapher
    private JButton TrigButton;
    //this button starts the challenge mode
    private JButton ChallengeButton;
    //this button starts the polar grapher
    private JButton PolarButton;
    //this button shows a JOptionPane of the directions
    private JButton DirectionsButton;
    //paintSurfacePanel
    private PaintSurfacePanel paintSurfacePanel;

    //background image:
    private Image backgroundPic;
    
    public static void main(String[] args){
        new MainMenu();
    }

    public MainMenu(){
        //setting the size to be the same as the grapher
        this.setSize(560,620);
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //title
        this.setTitle("MATHstroids Menu");
        
        //initialize PaintSurfacePanel
        paintSurfacePanel = new PaintSurfacePanel();
        paintSurfacePanel.setSize(560,575);
        this.add(paintSurfacePanel);
        
        //Create the ActionListener c1:
        ClickListener c1 = new ClickListener();
        
        //initialize the MenuPanel;
        MenuPanel = new JPanel();
        MenuPanel.setSize(560,50);
        //add the MenuPanel to the menu frame
        this.add(MenuPanel, BorderLayout.SOUTH);
        
        //initialize the DirectionsButton
        DirectionsButton = new JButton("Directions");
        DirectionsButton.addActionListener(c1);
        MenuPanel.add(DirectionsButton);
        
        //initialize TrigButton
        TrigButton = new JButton("Trig Practice");
        TrigButton.addActionListener(c1);
        MenuPanel.add(TrigButton);
        
        //initialize ChallengeButton
        ChallengeButton = new JButton("Challenge Mode");
        ChallengeButton.addActionListener(c1);
        MenuPanel.add(ChallengeButton);
        
        //I'm taking out the polar frame
        //initialize PolarButton
        //PolarButton = new JButton("Polar Practice");
        //PolarButton.addActionListener(c1);
        //MenuPanel.add(PolarButton);
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        backgroundPic = kit.getImage("newasteroids2.png");
        
        //last thing is make it all visible
        this.setVisible(true);
    }
    
    //ActionListener class
    private class ClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == TrigButton){
                //"close" the main menu frame
                getRootPane().getParent().setVisible(false);
                
                //This starts all the thread-y stuff to make the game frame actually show up
                GameFrame g = new GameFrame();
                Thread t = new Thread(g);
                t.start();
                
            } else if(e.getSource() == ChallengeButton){
                //"close" the main menu frame
                getRootPane().getParent().setVisible(false);
                
                //Start the challenge window
                ChallengeFrame g = new ChallengeFrame();
                Thread t = new Thread(g);
                t.start();
            } else if(e.getSource() == PolarButton){
                //"close" the main menu frame
                getRootPane().getParent().setVisible(false);
                
                //Start the polar window
                PolarFrame g = new PolarFrame();
                Thread t = new Thread(g);
                t.start();
            } else if(e.getSource() == DirectionsButton){
                String lineZero = "Click \"Trig Practice\" to practice trig functions.";
                String lineOne = "\n\nEnter an equation in the text field and click ";
                String lineTwo = "\"Enter Equation\" to generate an equation that ";
                String lineThree =  "will destroy the MATHstroids it comes in contact with. ";
                String lineFour =  "\nAlmost any mathematically legal equation can be entered ";
                String lineFive = "but you must put the \"*\" multiplication sign between ";
                String lineSix = "a number and a variable being multiplied (like 4.5*x) ";
                String lineSeven = "and between a number and a function (like 2*sin(x)). ";
                String lineEight = "\nUse \"x\" as the variables in your equations. ";
                String lineNine = "Type \"x**2\" for x to the power of 2.";
                String lineTen = "\nClick \"Add Asteroids\" to add more MATHstroids ";
                String lineEleven = "to clarify the function. ";
                String lineTwelve = "\nTry to destroy all the MATHstroids in one try ";
                String lineThirteen = "While adding as few extras as possible.";
                String lineFourteen = "\nGood Luck!";
                
                String firstFiveLines = lineOne+"\n"+lineTwo+"\n"+lineThree+"\n"+lineFour+"\n"+lineFive+"\n";
                String secondFiveLines = lineSix+"\n"+lineSeven+"\n"+lineEight+"\n"+lineNine+"\n"+lineTen+"\n";
                String lastLines = lineEleven+"\n"+lineTwelve+"\n"+lineThirteen+"\n"+lineFourteen;
                JOptionPane.showMessageDialog(null, lineZero+firstFiveLines+secondFiveLines+lastLines);
            }
        }
    }
    
    public class PaintSurfacePanel extends JComponent{

        private ArrayList<DoublePoint> parpoints = new ArrayList();
        
        public void paint(Graphics g){   
            g.drawImage(backgroundPic, 0, 0, this);
        }
    }
}