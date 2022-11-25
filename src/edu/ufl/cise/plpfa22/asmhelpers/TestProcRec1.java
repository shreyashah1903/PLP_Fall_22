package edu.ufl.cise.plpfa22.asmhelpers;

public class TestProcRec1 implements Runnable {
    int a;

    public TestProcRec1() {
        super();
    }

    class p implements Runnable {

        @Override
        public void run() {

            System.out.println("in p, a=");
            System.out.println(a);
            a = a - 1;
            if (a >= 0) {
                new p().run();
            }
        }
    }

    public static void main(String[] args) {
        new TestProcRec1().run();
    }

    @Override
    public void run() {
         a = 6;
         new p().run();
    }
}
