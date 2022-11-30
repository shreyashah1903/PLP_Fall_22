package edu.ufl.cise.plpfa22;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ClassAndMethodWriter {

    private final ClassWriter classWriter;
    private final MethodVisitor methodVisitor;

    public ClassAndMethodWriter(ClassWriter classWriter, MethodVisitor methodVisitor) {
        this.classWriter = classWriter;
        this.methodVisitor = methodVisitor;
    }

    public ClassWriter getClassWriter() {
        return classWriter;
    }

    public MethodVisitor getMethodVisitor() {
        return methodVisitor;
    }
}
