/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.virtualcpu;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class BineryCodeConstructor {
    
   
    // if AA at end, it needs adjusting
    private ArrayList<String[]> list = new ArrayList<>();
    private String[] nOP = {"NO operation", "00000000"};
    private String[] addAB = {"ADD A B", "00000001"};
    private String[] halt = {"Halt", "00001111"};
    private String[] movroAA = {"Move r into o", "0010 rooo"}; 
    
    public BineryCodeConstructor(){
        setBineryList();
    }
    
    
    private void setBineryList(){
        list.add(nOP);
        list.add(nOP);
        list.add(addAB);
        list.add(halt);
    }
    
    public String[] getCommands(){
        ArrayList<String> temp = new ArrayList<>();
        for (String[] commands : list) {
            temp.add(commands[1]);
        }
        return temp.toArray(new String[0]);
    }
    
   public void printProgram(PrintStream out){
       out.println("Program To Be Runned: ");
       for (String[] command : list) {
           out.printf("%20s %s %s\n",command[0], ": ", command[1]);
       }
   }
    
}
