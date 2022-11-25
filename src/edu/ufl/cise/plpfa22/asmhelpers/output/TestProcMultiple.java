package edu.ufl.cise.plpfa22.asmhelpers.output;

import org.objectweb.asm.*;

public class TestProcMultiple implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        RecordComponentVisitor recordComponentVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V18, ACC_PUBLIC | ACC_SUPER, "edu/ufl/cise/plpfa22/TestProcMultiple", null, "java/lang/Object", new String[]{"java/lang/Runnable"});

        classWriter.visitSource("TestProcMultiple.java", null);

        classWriter.visitNestMember("edu/ufl/cise/plpfa22/TestProcMultiple$p");

        classWriter.visitNestMember("edu/ufl/cise/plpfa22/TestProcMultiple$p$q");

        classWriter.visitInnerClass("edu/ufl/cise/plpfa22/TestProcMultiple$p", "edu/ufl/cise/plpfa22/TestProcMultiple", "p", 0);

        classWriter.visitInnerClass("edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "edu/ufl/cise/plpfa22/TestProcMultiple$p", "q", 0);

        {
            fieldVisitor = classWriter.visitField(0, "a", "I", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(0, "b", "Ljava/lang/String;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            fieldVisitor = classWriter.visitField(0, "c", "Z", null, null);
            fieldVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(9, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(10, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/TestProcMultiple;", null, label0, label2, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(34, label0);
            methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/TestProcMultiple");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/TestProcMultiple", "<init>", "()V", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/TestProcMultiple", "run", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(35, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("args", "[Ljava/lang/String;", null, label0, label2, 0);
            methodVisitor.visitMaxs(2, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(39, label0);
            methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/TestProcMultiple$p");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/TestProcMultiple$p", "<init>", "(Ledu/ufl/cise/plpfa22/TestProcMultiple;)V", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/TestProcMultiple$p", "run", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(40, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/TestProcMultiple;", null, label0, label2, 0);
            methodVisitor.visitMaxs(3, 1);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
