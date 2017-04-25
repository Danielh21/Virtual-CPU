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
    ArrayList<String[]> active = new ArrayList<>();
    private String[] nOP = {"NO operation", "00000000"};
    private String[] addAB = {"A <= A +B", "00000001"};
    private String[] halt = {"HALT", "00001111", ""};
    private String[] incA = {"A++", "00010110"};
    private String[] decA = {"DEC", "00010111", ""};
    private String[] mul = {"MUL", "00000010", ""};
    private String[] always = {"ALWAYS", "00001100", ""};
    private String[] notZeroA = {"NZERO", "00000111", ""};
    
    private String[] movrAoffset1 = {"MOV A +1", "00100001", ""}; 
    private String[] pushrA = {"PUSH A", "00010000", ""}; 
    private String[] popToA = {"POP A", "00010010", ""}; 
    private String[] popToB = {"POP B", "00010011", ""}; 
    private String[] ipResetoff0 = {"RTN", "00011000", ""}; 
    private String[] spOff1TooA = {"MOV +1 A", "00110010", ""};
    private String[] valueVtooRXX = {"r <- v;", "01vv vvvr"};
    private String[] valuepos5tooA = {"MOV 5 A", "01001010", ""};
    private String[] valuepos1tooA = {"MOV 1 A", "01000010", ""};
    private String[] jumpToAdresse12 = {"JMP RECUR", "10001100", ""};
    private String[] callAdresse6 = {"CALL 6 ", "11000110", ""};
    
    
    
    
    
    public String[] getFacFiveCommands(){
        active.add(valuepos5tooA);
        active.add(pushrA);
        active.add(always);
        active.add(callAdresse6);
        active.add(popToA);
        active.add(halt);
        active.add(spOff1TooA);
        active.add(notZeroA);
        active.add(jumpToAdresse12);
        active.add(valuepos1tooA); //MOV 1 A
        active.add(movrAoffset1); // MOV A +1
        active.add(ipResetoff0);
        active.add(pushrA);
        active.add(decA);
        active.add(pushrA);
        active.add(always);
        active.add(callAdresse6);
        active.add(popToB);
        active.add(popToA);
        active.add(mul);
        active.add(movrAoffset1); // MOV A +1
        active.add(ipResetoff0);
        return getCommands(active);
    }
    
    
    private String[] move4A = {"MOV 4 A" , "01001000" , ""};
    private String[] move5A = {"MOV 5 A" , "01001010" , ""};
    private String[] move1B = {"MOV 1 B" , "01000011" , ""};
    private String[] jumto11 = {"JMP End" , "10001011" , ""}; // remeber to set the right addresse!
    private String[] moveAB = {"MOV A B" , "00010100" , ""};
    private String[] jmpto2 = {"JMP 2" , "10000010" , ""}; // remeber to set the right addresse!
    private String[] pushrB = {"PUSH B" , "00010001" , ""};
    private String[] setFtrueonpA = {"NZERO", "00000100", ""};
    
    
   
    
    public String[] getFacFiveTailCommands(){
       active.add(move5A);
       active.add(move1B);
       active.add(setFtrueonpA);
       active.add(jumto11);
       active.add(pushrA);
       active.add(mul);
       active.add(moveAB);
       active.add(popToA);
       active.add(decA);
       active.add(always);
       active.add(jmpto2);
       active.add(pushrB);
       active.add(halt);
       
       return getCommands(active);
    }
    
    private String[] getCommands(ArrayList<String[]> list){
        ArrayList<String> temp = new ArrayList<>();
        for (String[] commands : list) {
            temp.add(commands[1]);
        }
        makeBineryReadable();
        return temp.toArray(new String[0]);
    }
    
   public void printProgram(PrintStream out){
       out.println("Running Program: ");
       int num = 0;
       for (String[] command : active) {
           int index = Integer.parseInt(command[1], 2);
           out.printf("%20s %s %3s %s\n",command[0], ": ", index, "Binery(" + command[2]+")#" + num );
           num++;
       }
   }

    private void makeBineryReadable() {
        for (String[] instructions : active) {
            String readable = instructions[1].substring(0, 4) + " " + instructions[1].substring(4);
            instructions[2] = readable;
        }
        
    }
   
   }
