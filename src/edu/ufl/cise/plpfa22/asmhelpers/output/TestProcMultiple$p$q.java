package edu.ufl.cise.plpfa22.asmhelpers.output;

import org.objectweb.asm.*;

public class TestProcMultiple$p$q implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        RecordComponentVisitor recordComponentVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V18, ACC_SUPER, "edu/ufl/cise/plpfa22/TestProcMultiple$p$q", null, "java/lang/Object", new String[]{"java/lang/Runnable"});

        classWriter.visitSource("TestProcMultiple.java", null);

        classWriter.visitNestHost("edu/ufl/cise/plpfa22/TestProcMultiple");

        classWriter.visitInnerClass("edu/ufl/cise/plpfa22/TestProcMultiple$p", "edu/ufl/cise/plpfa22/TestProcMultiple", "p", 0);

        classWriter.visitInnerClass("edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "edu/ufl/cise/plpfa22/TestProcMultiple$p", "q", 0);

        {
            fieldVisitor = classWriter.visitField(ACC_FINAL | ACC_SYNTHETIC, "this$1", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(0, "<init>", "(Ledu/ufl/cise/plpfa22/TestProcMultiple$p;)V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(15, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "this$1", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(RETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p$q;", null, label0, label1, 0);
            methodVisitor.visitLocalVariable("this$1", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p;", null, label0, label1, 1);
            methodVisitor.visitMaxs(2, 2);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(18, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "this$1", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p;");
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p", "this$0", "Ledu/ufl/cise/plpfa22/TestProcMultiple;");
            methodVisitor.visitIntInsn(BIPUSH, 45);
            methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple", "a", "I");
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(19, label1);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "this$1", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p;");
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p", "this$0", "Ledu/ufl/cise/plpfa22/TestProcMultiple;");
            methodVisitor.visitLdcInsn("hello");
            methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple", "b", "Ljava/lang/String;");
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(20, label2);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "this$1", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p;");
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p", "this$0", "Ledu/ufl/cise/plpfa22/TestProcMultiple;");
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple", "c", "Z");
            Label label3 = new Label();
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLineNumber(21, label3);
            methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "this$1", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p;");
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p", "this$0", "Ledu/ufl/cise/plpfa22/TestProcMultiple;");
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple", "a", "I");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            Label label4 = new Label();
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLineNumber(22, label4);
            methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "this$1", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p;");
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p", "this$0", "Ledu/ufl/cise/plpfa22/TestProcMultiple;");
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple", "b", "Ljava/lang/String;");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Label label5 = new Label();
            methodVisitor.visitLabel(label5);
            methodVisitor.visitLineNumber(23, label5);
            methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p$q", "this$1", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p;");
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple$p", "this$0", "Ledu/ufl/cise/plpfa22/TestProcMultiple;");
            methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/TestProcMultiple", "c", "Z");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Z)V", false);
            Label label6 = new Label();
            methodVisitor.visitLabel(label6);
            methodVisitor.visitLineNumber(24, label6);
            methodVisitor.visitInsn(RETURN);
            Label label7 = new Label();
            methodVisitor.visitLabel(label7);
            methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/TestProcMultiple$p$q;", null, label0, label7, 0);
            methodVisitor.visitMaxs(2, 1);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
