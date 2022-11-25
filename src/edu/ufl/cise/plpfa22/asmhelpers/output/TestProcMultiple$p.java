package edu.ufl.cise.plpfa22.asmhelpers.output;

import org.objectweb.asm.*;

public class TestProcMultiple$p implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        RecordComponentVisitor recordComponentVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V18, ACC_SUPER, "edu/ufl/cise/plpfa22/TestProcMultiple$p", null, "java/lang/Object", new String[]{"java/lang/Runnable"});

        classWriter.visitSource("TestProcMultiple.java", null);

        classWriter.visitNestHost("edu/ufl/cise/plpfa22/TestProcMultiple");

        classWriter.visitInnerClass("edu/ufl/cise/plpfa22/TestProcMultiple$p", "edu/ufl/cise/plpfa22/TestProcMultiple", "p", 0);

        classWriter.visitInnerClass("edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "edu/ufl/cise/plpfa22/TestProcMultiple$p", "q", 0);

        {
            fieldVisitor = classWriter.visitField(ACC_FINAL | ACC_SYNTHETIC, "this$0", "Ledu/ufl/cise/plpfa22/TestProcMultiple;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(0, "<init>", "(Ledu/ufl/cise/plpfa22/TestProcMultiple;)V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(12, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p", "this$0", "Ledu/ufl/cise/plpfa22/TestProcMultiple;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(RETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p;", null, label0, label1, 0);
            methodVisitor.visitLocalVariable("this$0", "Ledu/ufl/cise/plpfa22/TestProcMultiple;", null, label0, label1, 1);
            methodVisitor.visitMaxs(2, 2);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(29, label0);
            methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/TestProcMultiple$p$q");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "<init>", "(Ledu/ufl/cise/plpfa22/TestProcMultiple$p;)V", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "run", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(30, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p;", null, label0, label2, 0);
            methodVisitor.visitMaxs(3, 1);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
