package edu.ufl.cise.plpfa22.asmhelpers;

import org.objectweb.asm.*;
public class TestProcRec1$pDump implements Opcodes {

public static byte[] dump () throws Exception {

ClassWriter classWriter = new ClassWriter(0);
FieldVisitor fieldVisitor;
RecordComponentVisitor recordComponentVisitor;
MethodVisitor methodVisitor;
AnnotationVisitor annotationVisitor0;

classWriter.visit(V18, ACC_SUPER, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p", null, "java/lang/Object", new String[] { "java/lang/Runnable" });

classWriter.visitSource("TestProcRec1.java", null);

classWriter.visitNestHost("edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1");

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p", "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1", "p", 0);

{
fieldVisitor = classWriter.visitField(ACC_FINAL | ACC_SYNTHETIC, "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1;", null, null);
fieldVisitor.visitEnd();
}
{
methodVisitor = classWriter.visitMethod(0, "<init>", "(Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1;)V", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(10, label0);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitVarInsn(ALOAD, 1);
methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1;");
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
methodVisitor.visitInsn(RETURN);
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p;", null, label0, label1, 0);
methodVisitor.visitLocalVariable("this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1;", null, label0, label1, 1);
methodVisitor.visitMaxs(2, 2);
methodVisitor.visitEnd();
}
{
methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(15, label0);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitLdcInsn("in p, a=");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLineNumber(16, label1);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1;");
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1", "a", "I");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
Label label2 = new Label();
methodVisitor.visitLabel(label2);
methodVisitor.visitLineNumber(17, label2);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1;");
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1;");
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1", "a", "I");
methodVisitor.visitInsn(ICONST_1);
methodVisitor.visitInsn(ISUB);
methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1", "a", "I");
Label label3 = new Label();
methodVisitor.visitLabel(label3);
methodVisitor.visitLineNumber(18, label3);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1;");
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1", "a", "I");
Label label4 = new Label();
methodVisitor.visitJumpInsn(IFLT, label4);
Label label5 = new Label();
methodVisitor.visitLabel(label5);
methodVisitor.visitLineNumber(19, label5);
methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p");
methodVisitor.visitInsn(DUP);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1;");
methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p", "<init>", "(Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1;)V", false);
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p", "run", "()V", false);
methodVisitor.visitLabel(label4);
methodVisitor.visitLineNumber(21, label4);
methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
methodVisitor.visitInsn(RETURN);
Label label6 = new Label();
methodVisitor.visitLabel(label6);
methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/asmhelpers/TestProcRec1$p;", null, label0, label6, 0);
methodVisitor.visitMaxs(3, 1);
methodVisitor.visitEnd();
}
classWriter.visitEnd();

return classWriter.toByteArray();
}
}
