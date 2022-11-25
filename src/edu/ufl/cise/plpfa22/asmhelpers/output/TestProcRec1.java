package edu.ufl.cise.plpfa22.asmhelpers.output;

import org.objectweb.asm.*;

public class TestProcRec1 implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        RecordComponentVisitor recordComponentVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V18, ACC_PUBLIC | ACC_SUPER, "edu/ufl/cise/plpfa22/TestProcRec1", null, "java/lang/Object", new String[]{"java/lang/Runnable"});

        classWriter.visitSource("TestProcRec1.java", null);

        classWriter.visitNestMember("edu/ufl/cise/plpfa22/TestProcRec1$p");

        classWriter.visitInnerClass("edu/ufl/cise/plpfa22/TestProcRec1$p", "edu/ufl/cise/plpfa22/TestProcRec1", "p", 0);

        {
            fieldVisitor = classWriter.visitField(0, "a", "I", null, null);
            fieldVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(7, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(8, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/TestProcRec1;", null, label0, label2, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(25, label0);
            methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/TestProcRec1");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/TestProcRec1", "<init>", "()V", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/TestProcRec1", "run", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(26, label1);
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
            methodVisitor.visitLineNumber(30, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitIntInsn(BIPUSH, 6);
            methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/TestProcRec1", "a", "I");
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(31, label1);
            methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/TestProcRec1$p");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/TestProcRec1$p", "<init>", "(Ledu/ufl/cise/plpfa22/TestProcRec1;)V", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/TestProcRec1$p", "run", "()V", false);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(32, label2);
            methodVisitor.visitInsn(RETURN);
            Label label3 = new Label();
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/TestProcRec1;", null, label0, label3, 0);
            methodVisitor.visitMaxs(3, 1);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
