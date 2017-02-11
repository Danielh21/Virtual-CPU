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
    
   
    // if XX at end, it needs adjusting
    private ArrayList<String[]> list = new ArrayList<>();
    private String[] nOP = {"NO operation", "00000000"};
    private String[] addAB = {"A <= A +B", "00000001"};
    private String[] halt = {"Halt", "00001111"};
    private String[] incA = {"A++", "00010110"};
    private String[] decA = {"A--", "00010111"};
    private String[] mul = {"A <= A*B", "00000010"};
    private String[] divi = {"A <= A/B", "00000011"};
    private String[] always = {"F <= True", "00001100"};
    private String[] zero = {"F <= A=0", "00000100"};
    private String[] negA = {"F <= A<0", "00000101"};
    private String[] posA = {"F <=  A>0", "00000110"};
    private String[] notZeroA = {"F <=  A!=0", "00000111"};
    private String[] aEQb = {"F <=  A=B", "00001000"};
    private String[] aSmall = {"F <=  A<B", "00001001"};
    private String[] aBigger = {"F <=  A>B", "00001010"};
    private String[] aNotEQb = {"F <= A!=B", "00001011"};
    private String[] movAtoB = {"B <= A", "00010100"};
    private String[] movBtoA = {"A <= B", "00010101"};
    
    private String[] movroXX = {"Move r into o", "0010 rooo"}; 
    
    public BineryCodeConstructor(){
        setBineryList();
    }
    
    
    private void setBineryList(){
        list.add(nOP);
        list.add(incA);
        list.add(incA);
        list.add(movAtoB);
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
       out.println("Running Program: ");
       for (String[] command : list) {
           int index = Integer.parseInt(command[1], 2);
           out.printf("%20s %s %3s %s\n",command[0], ": ", index, "Binery(" + command[1]+")");
       }
   }
   
   }
