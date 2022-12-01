package edu.ufl.cise.plpfa22.asmhelpers;

// Add test case in this class and run Asmifier(ByteCodeTest.java) to get the bytecode instruction
public class TestConstants implements Runnable {
    int a = 3;
    String b = "HELLO";
    boolean c = false;

    public TestConstants() {
        super();
    }
    @Override
    public void run() {
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
    public static void main(String[] args) {
        new TestConstants().run();
    }
}
