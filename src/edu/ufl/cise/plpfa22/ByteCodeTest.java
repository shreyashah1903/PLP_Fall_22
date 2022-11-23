package edu.ufl.cise.plpfa22;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ByteCodeTest {

    public static void main(String[] args) throws IOException {
        // Creates a FileWriter
        FileWriter file = new FileWriter("TestProcAsm.java");

// Creates a PrintWriter
        PrintWriter output = new PrintWriter(file, true);

        new ClassReader(ByteCodeTest.class.getResourceAsStream("TestProc.class"))
                .accept(new TraceClassVisitor(null, new ASMifier(), output), 0);
    }

}
