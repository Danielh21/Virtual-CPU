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
    private String[] pushrXX = {"Push r on Stack", "0001 000r"}; 
    private String[] pushrA = {"Push A on Stack", "00010000"}; 
    private String[] pushrB = {"Push B on Stack", "00010001"}; 
    private String[] popXX = {"Removes r from Stack", "0001 001r"}; 
    private String[] popToA = {" A <- from Stack", "00010010"}; 
    private String[] popToB = {" B <- from Stack", "00010011"}; 
    private String[] ipResetX = {"* ip<-sp++; sp+= off", "0001 1ooo"}; 
    private String[] ipResetoff3 = {"* ip<-sp++; sp+= off", "00011011"}; 
    private String[] spOffToorXX = {"r <- [SP + o]", "0011 ooor"}; 
    private String[] spOff1TooA = {"A <- [SP + 1]", "00110010"};
    private String[] valueVtooRXX = {"r <- v;", "01vv vvvr"};
    private String[] valueneg16tooA = {"A <- -16;", "01100000"};
    private String[] valuepos15tooB = {"B <- 15;", "01011111"};
    private String[] jumpToAdresseXX = {"if F=true, IP <- a", "10aa aaaa"};
    private String[] jumpToAdresse30 = {"if F=true, IP <- 30", "10011110"};
    private String[] callAdresseXX = {"--SP <-IP, IP <-a ", "11aa aaaa"};
    private String[] callAdresse62 = {"--SP <-IP, IP <-62 ", "11111110"};
    
    
    
    public BineryCodeConstructor(){
        setBineryList();
    }
    
    
    private void setBineryList(){
        list.add(nOP);
        list.add(callAdresse62);
        list.add(always);
        list.add(callAdresse62);
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
