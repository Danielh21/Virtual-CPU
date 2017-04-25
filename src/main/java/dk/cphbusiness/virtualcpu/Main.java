package dk.cphbusiness.virtualcpu;

import java.util.Scanner;

public class Main {
  
  public static void main(String[] args) {
    System.out.println("Welcome to the CPU program");
    BineryCodeConstructor bcc = new BineryCodeConstructor();
    String[] commands = bcc.getFacFiveTailCommands();
    bcc.printProgram(System.out);
    Program program = new Program(commands);
    Machine machine = new Machine();
    machine.load(program);
    machine.print(System.out);
    System.out.println("Length of Program: " + program.getLengthOfProgram());    
    Scanner scan = new Scanner(System.in);
    
        int times = 0;
    while(Machine.keepRunning){
        times ++;
        System.out.println(">Enter To Contiune " + "Times Runned: " + times);
        //scan.nextLine();
        machine.tick();
        bcc.printProgram(System.out);
        machine.print(System.out);
    }
    
    
  }
}
