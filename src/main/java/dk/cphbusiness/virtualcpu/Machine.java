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
      System.out.println("Running Instruction: " + instr);
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
    else if(instr == 0b0001_0110){
        //`0001 0110` | `INC` | A++; IP++
        incA(instr);
    }
    else if(instr == 0b0001_0111){
        //`0001 0111` | `DEC` | A--; IP++
        decA(instr);
    }
    else if(instr == 0b0000_0010){
       // `0000 0010` | `MUL` | A ← A*B; IP++
       multi(instr);
    }
    else if(instr == 0b0000_0011){
       // `0000 0011` | `DIV` | A ← A/B; IP++
       divi(instr);
    }
    else if(instr == 0b0001_0100){
        //`0001 0100` | `MOV A B` | B ← A; IP++
        movAB(instr);
    }
    else if(instr == 0b0000_1100 ){
        //`0000 1100` | `ALWAYS` | F ← **true**; IP++
        setFTrueAlways(instr);
    }
    else if(instr == 0b0000_0100){
        //`0000 0100` | `ZERO` | F ← A = 0; IP++
        zeroChangeF(instr);
    }
    else if(instr == 0b0000_0101){
        //0000 0101` | `NEG` | F ← A < 0; IP++
        aNegChangeF(instr);
    }
    else if(instr == 0b0000_0110){
        //`0000 0110` | `POS` | F ← A > 0; IP++
        aPosChangeF(instr);
    }
    else if(instr == 0b0000_0111){
        //`0000 0111` | `NZERO` | F ← A ≠ 0; IP++
        aNotZeroChangeF(instr);
    }
    else if(instr == 0b0000_1000){
        //`0000 1000` | `EQ` | F ← A = B; IP++
        aEqualBChangeF(instr);
    }
    else if(instr == 0b0000_1001){
        //|| `0000 1001` | `LT` | F ← A < B; IP++
        aSmallerBChangeF(instr);
    }
    else if(instr == 0b0000_1010){
        //0000 1010` | `GT` | F ← A > B; IP++
        aBiggerBChangeF(instr);
    }
    else if(instr == 0b0000_1011){
        //`0000 1011` | `NEQ` | F ← A ≠ B; IP++
        aNotEqualBChangeF(instr);
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
  

    private void incA(int instr) {
        //`0001 0110` | `INC` | A++; IP++
        cpu.setA(cpu.getA()+1);
        cpu.incIp();
    }
  
  public void print(PrintStream out) {
    memory.print(out);
    out.println("-------------");
    cpu.print(out);
    }

    private void decA(int instr) {
        cpu.setA(cpu.getA()-1);
        cpu.incIp();
    }

    private void multi(int instr) {
       // `0000 0010` | `MUL` | A ← A*B; IP++
       cpu.setA(cpu.getA()* cpu.getB());
       cpu.incIp();
    }

    private void divi(int instr) {
        //`0000 0011` | `DIV` | A ← A/B; IP++
        cpu.setA(cpu.getA()/ cpu.getB());
        cpu.incIp();
    }

    private void movAB(int instr) {
        //`0001 0100` | `MOV A B` | B ← A; IP++
        cpu.setB(cpu.getA());
        cpu.incIp();
    }

    private void setFTrueAlways(int instr) {
        //`0000 1100` | `ALWAYS` | F ← **true**; IP++
        cpu.setFlag(true);
        cpu.incIp();
    }

    private void zeroChangeF(int instr) {
        //`0000 0100` | `ZERO` | F ← A = 0; IP++
        cpu.setFlag(cpu.getA() == 0);
        cpu.incIp();
    }

    private void aNegChangeF(int instr) {
          //0000 0101` | `NEG` | F ← A < 0; IP++
          cpu.setFlag(cpu.getA() < 0);
          cpu.incIp();
    }

    private void aPosChangeF(int instr) {
        //`0000 0110` | `POS` | F ← A > 0; IP++
        cpu.setFlag(cpu.getA() > 0);
        cpu.incIp();
    }

    private void aNotZeroChangeF(int instr) {
        //`0000 0111` | `NZERO` | F ← A ≠ 0; IP++
        cpu.setFlag(cpu.getA() != 0);
        cpu.incIp();
    }

    private void aEqualBChangeF(int instr) {
        //`0000 1000` | `EQ` | F ← A = B; IP++
        cpu.setFlag(cpu.getA() == cpu.getB());
        cpu.incIp();
    }

    private void aSmallerBChangeF(int instr) {
        //|| `0000 1001` | `LT` | F ← A < B; IP++
        cpu.setFlag(cpu.getA() < cpu.getB());
        cpu.incIp();
    }

    private void aBiggerBChangeF(int instr) {
        //0000 1010` | `GT` | F ← A > B; IP++
        cpu.setFlag(cpu.getA() > cpu.getB());
        cpu.incIp();
    }

    private void aNotEqualBChangeF(int instr) {
        //`0000 1011` | `NEQ` | F ← A ≠ B; IP++
        cpu.setFlag(cpu.getA() != cpu.getB());
        cpu.incIp();
    }
  }
