package asm.edu.ufl.cise.plpfa22.asmhelpers;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.RecordComponentVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
public class Paramtest3$fibDump implements Opcodes {

public static byte[] dump () throws Exception {

ClassWriter classWriter = new ClassWriter(0);
FieldVisitor fieldVisitor;
RecordComponentVisitor recordComponentVisitor;
MethodVisitor methodVisitor;
AnnotationVisitor annotationVisitor0;

classWriter.visit(V18, ACC_SUPER, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", null, "java/lang/Object", new String[] { "java/lang/Runnable" });

classWriter.visitSource("Paramtest3.java", null);

classWriter.visitNestHost("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3");

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "fib", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$baseCase", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "baseCase", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$recursiveCall", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "recursiveCall", 0);

{
fieldVisitor = classWriter.visitField(0, "t", "I", null, null);
fieldVisitor.visitEnd();
}
{
fieldVisitor = classWriter.visitField(0, "a", "I", null, null);
fieldVisitor.visitEnd();
}
{
fieldVisitor = classWriter.visitField(0, "b", "I", null, null);
fieldVisitor.visitEnd();
}
{
fieldVisitor = classWriter.visitField(ACC_FINAL | ACC_SYNTHETIC, "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;", null, null);
fieldVisitor.visitEnd();
}
{
methodVisitor = classWriter.visitMethod(0, "<init>", "(Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;)V", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(12, label0);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitVarInsn(ALOAD, 1);
methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;");
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
methodVisitor.visitInsn(RETURN);
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib;", null, label0, label1, 0);
methodVisitor.visitLocalVariable("this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;", null, label0, label1, 1);
methodVisitor.visitMaxs(2, 2);
methodVisitor.visitEnd();
}
{
methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(39, label0);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;");
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "in", "I");
methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "t", "I");
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLineNumber(40, label1);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "t", "I");
methodVisitor.visitInsn(ICONST_1);
Label label2 = new Label();
methodVisitor.visitJumpInsn(IF_ICMPGT, label2);
Label label3 = new Label();
methodVisitor.visitLabel(label3);
methodVisitor.visitLineNumber(41, label3);
methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$baseCase");
methodVisitor.visitInsn(DUP);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$baseCase", "<init>", "(Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib;)V", false);
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$baseCase", "run", "()V", false);
Label label4 = new Label();
methodVisitor.visitJumpInsn(GOTO, label4);
methodVisitor.visitLabel(label2);
methodVisitor.visitLineNumber(43, label2);
methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "t", "I");
methodVisitor.visitInsn(ICONST_1);
methodVisitor.visitJumpInsn(IF_ICMPLE, label4);
Label label5 = new Label();
methodVisitor.visitLabel(label5);
methodVisitor.visitLineNumber(44, label5);
methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$recursiveCall");
methodVisitor.visitInsn(DUP);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$recursiveCall", "<init>", "(Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib;)V", false);
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$recursiveCall", "run", "()V", false);
methodVisitor.visitLabel(label4);
methodVisitor.visitLineNumber(46, label4);
methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
methodVisitor.visitInsn(RETURN);
Label label6 = new Label();
methodVisitor.visitLabel(label6);
methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib;", null, label0, label6, 0);
methodVisitor.visitMaxs(3, 1);
methodVisitor.visitEnd();
}
classWriter.visitEnd();

return classWriter.toByteArray();
}
}
