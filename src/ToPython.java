import java.io.*;
import java.util.ArrayList;

//This class will pass the equation to my Python program which will evaluate it
//the Python program returns a list of points that this class reads 
//this class then returns the list of points to the Equation class 
public class ToPython {
 
       public static ArrayList getPoints(String equation) {
            //ArrayList that will store the raw input from the python program
            ArrayList<String> theLines = new ArrayList();
            //ArrayList that will be returned to Equation
            ArrayList<DoublePoint> thePoints = new ArrayList();
            try {
                //This is what the internet said to do to run things through the command line
                Runtime rt = Runtime.getRuntime();
                //the commands[] stores the commands I am sending to the command line
                String[] commands = new String[3];
                //cmd /c is required to run programs that do not end in *.exe
                //commands[0] = "cmd";
                //commands[1] = "/c";
                //this is the location of the python program
                //NOTE THIS ONLY RUNS ON COMPUTERS WITH A PYTHON INTERPRETER INSTALLED
                //LOOK INTO THIS
                commands[0] = "dist\\CAPSTONE.exe";
                //This is the equation passed by Equation class
                commands[1] = equation;
                //This is the interval at which Python will evaluate the equation it is given
                commands[2] = ".05";
                //This executes the above list of commands
                Process pr = rt.exec(commands);
 
                BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
 
                String line=null;
 
                while((line=input.readLine()) != null) {
                    //System.out.println(line);
                    if(line.equals("")){
                    }else{
                        theLines.add(line);
                    }
                }
 
                int exitVal = pr.waitFor();
                //System.out.println("Exited with error code "+exitVal);
                
                //convert the raw Strings from the Python code into individual points
                for( String theline : theLines){
                    String xcoord = "";
                    String ycoord = "";
                    boolean isxcoord = true;
                    for(int k = 0; k < theline.length(); k++){
                        if(theline.charAt(k) == ' '){
                            isxcoord = false;
                        }
                        if(isxcoord){
                            xcoord += theline.charAt(k);
                        } else if( theline.charAt(k) == ' ' ){
                        } else {
                            ycoord += theline.charAt(k);
                        }
                    }
                    DoublePoint toAdd = new DoublePoint(Double.parseDouble(xcoord), Double.parseDouble(ycoord));
                    thePoints.add(toAdd);
                }
                return thePoints;
            } catch(Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }
            return thePoints;
        }
}