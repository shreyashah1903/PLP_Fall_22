package edu.ufl.cise.plpfa22.asmhelpers;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ByteCodeTest {

    public static void main(String[] args) throws IOException {
        FileWriter file = new FileWriter("TestProcRec1$p.java");

        PrintWriter output = new PrintWriter(file, true);

        new ClassReader(ByteCodeTest.class.getResourceAsStream("TestProcRec1$p.class"))
                .accept(new TraceClassVisitor(null, new ASMifier(), output), 0);
    }

}
