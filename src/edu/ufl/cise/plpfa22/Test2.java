package edu.ufl.cise.plpfa22;

// Add test case in this class and run Asmifier(ByteCodeTest.java) to get the bytecode instruction
public class Test2 implements Runnable {
    int a = 3;
    int x;
    public Test2() {
        super();
    }
    @Override
    public void run() {
        x = a;
        System.out.println(x);
    }
    public static void main(String[] args) {
        new Test2().run();
    }
}
