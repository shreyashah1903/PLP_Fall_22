package edu.ufl.cise.plpfa22.asmhelpers;

public class TestProcMultiple implements Runnable {
    int a;
    String b;
    boolean c;

    public TestProcMultiple() {
        super();
    }

    class p implements Runnable {


        class q implements Runnable {
            @Override
            public void run() {
                a = 45;
                b = "hello";
                c = true;
                System.out.println(a);
                System.out.println(b);
                System.out.println(c);
            }
        }

        @Override
        public void run() {
            new q().run();
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
