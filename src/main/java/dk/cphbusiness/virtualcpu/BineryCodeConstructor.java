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
    private String[] movrAoffset1 = {"Move r into o", "00100001"}; 
    private String[] pushrXX = {"Push r on Stack", "0001 000r"}; 
    private String[] pushrA = {"Push A on Stack", "00010000"}; 
    private String[] pushrB = {"Push B on Stack", "00010001"}; 
    private String[] popXX = {"Removes r from Stack", "0001 001r"}; 
    private String[] popToA = {" A <- from Stack", "00010010"}; 
    private String[] popToB = {" B <- from Stack", "00010011"}; 
    private String[] ipResetX = {"* ip<-sp++; sp+= off", "0001 1ooo"}; 
    private String[] ipResetoff3 = {"* ip<-sp++; sp+= 0", "00011011"}; 
    private String[] ipResetoff0 = {"* ip<-sp++; sp+= off", "00011000"}; 
    private String[] spOffToorXX = {"r <- [SP + o]", "0011 ooor"}; 
    private String[] spOff1TooA = {"A <- [SP + 1]", "00110010"};
    private String[] valueVtooRXX = {"r <- v;", "01vv vvvr"};
    private String[] valuepos5tooA = {"A <- 5;", "01001010"};
    private String[] valuepos1tooA = {"A <- 1;", "01000010"};
    private String[] valuepos15tooB = {"B <- 15;", "01011111"};
    private String[] jumpToAdresseXX = {"if F=true, IP <- a", "10aa aaaa"};
    private String[] jumpToAdresse30 = {"if F=true, IP <- 30", "10011110"};
    private String[] jumpToAdresse12 = {"if F=true, IP <- 12", "10001100"};
    private String[] callAdresseXX = {"--SP <-IP, IP <-a ", "11aa aaaa"};
    private String[] callAdresse6 = {"--SP <-IP, IP <-6 ", "11000110"};
    
    
    
    public BineryCodeConstructor(){
        setBineryList();
    }
    
    
    private void setBineryList(){
        list.add(valuepos5tooA);
        list.add(pushrA);
        list.add(always);
        list.add(callAdresse6);
        list.add(popToA);
        list.add(halt);
        list.add(spOff1TooA);
        list.add(notZeroA);
        list.add(jumpToAdresse12);
        list.add(valuepos1tooA); //MOV 1 A
        list.add(movrAoffset1); // MOV A +1
        list.add(ipResetoff0);
        list.add(pushrA);
        list.add(decA);
        list.add(pushrA);
        list.add(always);
        list.add(callAdresse6);
        list.add(popToB);
        list.add(popToA);
        list.add(mul);
        list.add(movrAoffset1); // MOV A +1
        list.add(ipResetoff0);
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
       int num = 0;
       for (String[] command : list) {
           int index = Integer.parseInt(command[1], 2);
           out.printf("%20s %s %3s %s\n",command[0], ": ", index, "Binery(" + command[1]+")#" + num );
           num++;
       }
   }
   
   }
