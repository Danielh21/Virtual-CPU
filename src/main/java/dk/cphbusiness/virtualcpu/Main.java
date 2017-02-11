package dk.cphbusiness.virtualcpu;

import java.util.Scanner;

public class Main {
  
  public static void main(String[] args) {
    System.out.println("Welcome to the CPU program");
    BineryCodeConstructor bcc = new BineryCodeConstructor();
    bcc.printProgram(System.out);
    String[] commands = bcc.getCommands();
    Program program = new Program(commands);
    Machine machine = new Machine();
    machine.load(program);
    machine.print(System.out);
    System.out.println("Length of Program: " + program.getLengthOfProgram());    
    Scanner scan = new Scanner(System.in);
    
    while(Machine.keepRunning){   
        System.out.println(">Enter To Contiune ");
        scan.nextLine();
        machine.tick();
        machine.print(System.out);
    }
    
    
  }
}
