package edu.ufl.cise.plpfa22;

public class TestProcMultiple implements Runnable {
    int a;
    String b = "abc";
    boolean c;
    int e;

    public TestProcMultiple() {
        super();
    }

    class p implements Runnable {

        int d = 10;
        String f;
        class q implements Runnable {
            @Override
            public void run() {
                a = 45;
                b = "inq";
                c = false;
                d = 25;
                System.out.println(a);
                System.out.println(b);
                System.out.println(c);
            }
        }

        @Override
        public void run() {
            a = 42;
            b = "hello";
            c = true;
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
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
