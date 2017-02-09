package dk.cphbusiness.virtualcpu;

import java.util.Scanner;

public class Main {
  
  public static void main(String[] args) {
    System.out.println("Welcome to the CPU program");
    Program program = new Program("00101001", "00001111", "00101001", "00001111");
    Machine machine = new Machine();
    machine.load(program);
    machine.print(System.out);
    System.out.println("Length of Program: " + program.getLengthOfProgram());    
    Scanner scan = new Scanner(System.in);
    
    while(Machine.keepRunning){   
        System.out.println(">Enter To Contiune ");
        String s = scan.nextLine();
        machine.tick();
        machine.print(System.out);
    }
    
    
  }
}
