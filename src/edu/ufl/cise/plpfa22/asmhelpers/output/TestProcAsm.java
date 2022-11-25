package edu.ufl.cise.plpfa22.asmhelpers.output;

import org.objectweb.asm.*;

public class TestProcAsm implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        RecordComponentVisitor recordComponentVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V18, ACC_PUBLIC | ACC_SUPER, "edu/ufl/cise/plpfa22/TestProc", null, "java/lang/Object", new String[]{"java/lang/Runnable"});

        classWriter.visitSource("TestProc.java", null);

        classWriter.visitNestMember("edu/ufl/cise/plpfa22/TestProc$p");

        classWriter.visitInnerClass("edu/ufl/cise/plpfa22/TestProc$p", "edu/ufl/cise/plpfa22/TestProc", "p", 0);

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
            methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/TestProc;", null, label0, label2, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(26, label0);
            methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/TestProc");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/TestProc", "<init>", "()V", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/TestProc", "run", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(27, label1);
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
            methodVisitor.visitLineNumber(31, label0);
            methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/TestProc$p");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/TestProc$p", "<init>", "(Ledu/ufl/cise/plpfa22/TestProc;)V", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/TestProc$p", "run", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(32, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/TestProc;", null, label0, label2, 0);
            methodVisitor.visitMaxs(3, 1);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
