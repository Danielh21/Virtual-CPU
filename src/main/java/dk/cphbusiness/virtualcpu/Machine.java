package dk.cphbusiness.virtualcpu;

import java.io.PrintStream;

public class Machine {
  private Cpu cpu = new Cpu();
  private Memory memory = new Memory();
  public static boolean keepRunning = true;
  
  public void load(Program program) {
    int index = 0;
    for (int instr : program) {
      memory.set(index++, instr);
      }
    }
  
  public void tick() {
    int instr = memory.get(cpu.getIp());
    if (instr == 0b0000_0000) {
      // 0000 0000  NOP
      cpu.incIp();
      // cpu.setIp(cpu.getIp() + 1);
      }
    else if (instr == 0b0000_0001) {
      // 0000 0001 ADD A B
      cpu.setA(cpu.getA() + cpu.getB());
      cpu.setIp(cpu.getIp() + 1);
      }
    else if(instr == 0b0000_1111 ){
        // 0000 1111 HALT - Stop the program!
        keepRunning = false;
    }
    // ..
    else if ((instr & 0b1111_0000) == 0b0010_0000) { 
        
      int o = instr & 0b0000_0111;
      int r = (instr & 0b0000_1000) >> 3;
      if (r == Cpu.A) memory.set(cpu.getSp() + o, cpu.getA());
      else memory.set(cpu.getSp() + o, cpu.getB());
      cpu.setIp(cpu.getIp() + 1);
      }
    else{
        System.out.println("Error! Not Supported");
        throw new UnsupportedOperationException();
     }
    }
  
  public void print(PrintStream out) {
    memory.print(out);
    out.println("-------------");
    cpu.print(out);
    }
  
  }
