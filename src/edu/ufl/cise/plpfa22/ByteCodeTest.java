package edu.ufl.cise.plpfa22;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

public class ByteCodeTest {

    public static void main(String[] args) throws IOException {
        new ClassReader(ByteCodeTest.class.getResourceAsStream("Test2.class"))
                .accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(System.out)), 0);
    }

}
