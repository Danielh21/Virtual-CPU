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
        movATooB(instr);
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
    else if(instr == 0b0001_0101){
        // `0001 0101` | `MOV B A` | A ← B; IP++
        moveBtooA(instr);
    }
    else if ((instr & 0b1111_0000) == 0b0010_0000) {
        // `MOV r o` `0010 rooo`    
        moveRtooO(instr);
      }
    else if((instr & 0b1111_1110) == 0b0001_0000 ){
        //`0001 000r` | `PUSH r` | [--SP] ← r; IP++
        pushrOnStack(instr);
    }
    else if((instr & 0b1111_1110) ==0b0001_0010){
        //`0001 001r` | `POP r` | r ← [SP++]; IP++
        popStack(instr);
    }
    else if((instr & 0b1111_1000) == 0b0001_1000){
        //`0001 1ooo` | `RTN +o` | IP ← [SP++]; SP += o; IP++
        notSure(instr);
    }
    else if((instr & 0b1111_0000) == 0b0011_0000){
        //`0011 ooor` | `MOV o r` | r ← [SP + o]; IP++
        moveSpOffsetTOr(instr);
    }
    else if((instr & 0b1100_0000) == 0b0100_0000){
        //`01vv vvvr` | `MOV v r` | r ← v; IP++
        valueToR(instr);
    }
    else if((instr & 0b1100_0000) == 0b1000_0000){
        //`10aa aaaa` | `JMP #a` | 
        //**if** F **then** IP ← a **else** IP++
        jumpToAddresse(instr);
    }
    else if((instr & 0b1100_0000) == 0b1100_0000){
        //`11aa aaaa` | `CALL #a` | 
        //**if** F **then** [--SP] ← IP; IP ← a **else** IP++
        callAddresse(instr);
    }
    else{
        System.out.println("Error! Not Supported");
        throw new UnsupportedOperationException();
     }
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

    private void movATooB(int instr) {
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

    private void moveBtooA(int instr) {
        cpu.setA(cpu.getB());
        cpu.incIp();
    }
     private void moveRtooO(int instr) {
        // `MOV r o` `0010 rooo`
        //[SP + o] ← r; IP++
        
        int o = instr & 0b0000_0111;
        int r = (instr & 0b0000_1000) >> 3;
        if (r == Cpu.A) memory.set(cpu.getSp() + o, cpu.getA());
        else memory.set(cpu.getSp() + o, cpu.getB());
        cpu.incIp();
    }

    private void pushrOnStack(int instr) {
        //`0001 000r` | `PUSH r` | [--SP] ← r; IP++
        int r = (instr & 0b0000_0001);
        cpu.decSp();
        if(r == Cpu.A) memory.set(cpu.getSp(), cpu.getA());
        else memory.set(cpu.getSp(), cpu.getB());
        
        cpu.incIp();
        
    }

    private void popStack(int instr) {
        // `0001 001r` | `POP r` | r ← [SP++]; IP++
        int r = (instr & 0b0000_0001);
        if(r==Cpu.A)cpu.setA(memory.get(cpu.getSp()));
        else cpu.setB(memory.get(cpu.getSp()));
        
        cpu.incSp();
        cpu.incIp();
    }

    private void notSure(int instr) {
        // `0001 1ooo` | `RTN +o` | IP ← [SP++]; SP += o; IP++
        /*
        Im not a 100 % sure what the assigment want me to do here
        but what im doing:
        SP++
        IP <= SP
        SP += offset
        IP++
        */
        int offset = instr & 0b0000_0111;
        cpu.incSp();
        cpu.setIp(cpu.getSp());
        for (int i = 0; i < offset; i++) {
            cpu.incSp();
        }
        cpu.incIp();
    }

    /**
     * Moves the value at SP + Offset into r 
     * @param instr Instructions
     */
    private void moveSpOffsetTOr(int instr) {
        //`0011 ooor` | `MOV o r` | r ← [SP + o]; IP++
       
        int r = instr & 0b0000_0001;
        int o = (instr & 0b0000_1110) >> 1;
        
        if(r == Cpu.A) cpu.setA(memory.get(cpu.getSp() + o));
        else cpu.setB(memory.get(cpu.getSp() + o));
        
        cpu.incIp();
    }

    private void valueToR(int instr) {
        //`01vv vvvr` | `MOV v r` | r ← v; IP++
        
        int r = instr & 0b0000_0001;
        int v;
        int sign = (instr & 0b0010_0000) >>5; // will Either be one(negative number) or zero(positive number)
        if(sign != 1){
            //Means it's a positive number
        v = (instr & 0b0001_1110) >>1;
        }
        else{
            String binery = Integer.toBinaryString(instr);
            binery = "0" + binery; // because ^^ that method ignores zeros, but we know we have one infront of
            binery = binery.substring(2, 7);
            short res = (short)Integer.parseInt(binery, 2);
            v= -res;
        }
        
        if(r == Cpu.A)cpu.setA(v);
        else cpu.setB(v);
        cpu.incIp();
        
        
    }

    private void jumpToAddresse(int instr) {
        //`10aa aaaa` | `JMP #a` | 
        //**if** F **then** IP ← a **else** IP++
        int adresse = instr & 0b0011_1111;
        if(cpu.isFlag()) cpu.setIp(adresse);
        else cpu.incIp();
    }

    /**
     * If flag = true
     * Sets Sp too Ip's adresse and then Sp--
     * Ip then jumps to adresse!
     * @param instr Instructions
     */
    private void callAddresse(int instr) {
        //`11aa aaaa` | `CALL #a` | 
        //**if** F **then** [--SP] ← IP; IP ← a **else** IP++
        int adresse = instr & 0b0011_1111;
        if(cpu.isFlag()){
            cpu.setSp(cpu.getIp());
            cpu.decSp();
            cpu.setIp(adresse);
        }
        else cpu.incIp();
    }
  }
