package edu.ufl.cise.plpfa22.asmhelpers;

public class Paramtest3 implements Runnable {
    int in;
    int out;
    String count;

    public Paramtest3() {
        super();
    }

    class fib implements Runnable {

        int t;
        int a;
        int b;
        class recursiveCall implements Runnable {
            @Override
            public void run() {
                in = t-1;
                new fib().run();
                a = out;
                in = t-2;
                new fib().run();
                b = out;
                out = a+b;
            }
        }

        class baseCase implements Runnable {
            @Override
            public void run() {
                out = t;
            }
        }

        @Override
        public void run() {
            t = in;
            if (t <= 1) {
                new baseCase().run();
            }
            else if (t > 1) {
                new recursiveCall().run();
            }
        }
    }

    class series implements Runnable {
        final int n = 10;
        final int start = 1;
        int i;

        class incI implements Runnable {
            final int incVal = 1;
            @Override
            public void run() {
                i = i+1;
            }
        }

        class printOut implements Runnable {
            @Override
            public void run() {
                System.out.println(out);
            }
        }

        @Override
        public void run() {
            count = "10";
            i = start;
            while (i <= n) {
                in = i;
                new fib().run();
                new printOut().run();
                new incI().run();
            }
            new printMessage().run();
        }
    }

    class printMessage implements Runnable {
        @Override
        public void run() {
            System.out.println("printed first " + count + " fib series");
        }
    }

    public static void main(String[] args) {
        new Paramtest3().run();
    }

    @Override
    public void run() {
        new series().run();
    }
}
