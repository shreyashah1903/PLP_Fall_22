package edu.ufl.cise.plpfa22;

public class TestProcMultiple implements Runnable {
    int a;
    String b;
    boolean c;

    public TestProcMultiple() {
        super();
    }

    class p implements Runnable {

        @Override
        public void run() {
            a = 42;
            b = "hello";
            c = true;
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
        }
    }

    public static void main(String[] args) {
        new TestProcMultiple().run();
    }

    @Override
    public void run() {
        new p().run();
    }
}
