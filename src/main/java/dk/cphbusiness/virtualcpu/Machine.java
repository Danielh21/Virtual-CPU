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
      System.out.println("Running Instruction: " + Integer.toBinaryString(instr));
    if (instr == 0b0000_0000) {
        // 0000 0000  NOP
            NOP(instr);
      }
    else if (instr == 0b0000_0001) {
        // 0000 0001 ADD A B
            addAB(instr);
      }
    else if(instr == 0b0000_1111 ){
       // 0000 1111 HALT - Stop the program!
            HALT();
    }
    // ..
    else if ((instr & 0b1111_0000) == 0b0010_0000) {
      // `MOV r o` `0010 rooo`    
        MOVro(instr);
      }
    else{
        System.out.println("Error! Not Supported");
        throw new UnsupportedOperationException();
     }
    }

    private void MOVro(int instr) {
        // `MOV r o` `0010 rooo`
        //[SP + o] ← r; IP++
        
        int o = instr & 0b0000_0111;
        int r = (instr & 0b0000_1000) >> 3;
        if (r == Cpu.A) memory.set(cpu.getSp() + o, cpu.getA());
        else memory.set(cpu.getSp() + o, cpu.getB());
        cpu.setIp(cpu.getIp() + 1);
    }

    private void HALT() {
        // 0000 1111 HALT - Stop the program!
        keepRunning = false;
    }

    private void addAB(int instr) {
        // 0000 0001 ADD A B
        cpu.setA(cpu.getA() + cpu.getB());
        cpu.setIp(cpu.getIp() + 1);
    }

    private void NOP(int instr) {
        // 0000 0000  NOP
        cpu.incIp();
        // cpu.setIp(cpu.getIp() + 1);
    }
  
  public void print(PrintStream out) {
    memory.print(out);
    out.println("-------------");
    cpu.print(out);
    }
  
  }
