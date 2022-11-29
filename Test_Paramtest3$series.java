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
public class Paramtest3$seriesDump implements Opcodes {

public static byte[] dump () throws Exception {

ClassWriter classWriter = new ClassWriter(0);
FieldVisitor fieldVisitor;
RecordComponentVisitor recordComponentVisitor;
MethodVisitor methodVisitor;
AnnotationVisitor annotationVisitor0;

classWriter.visit(V18, ACC_SUPER, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", null, "java/lang/Object", new String[] { "java/lang/Runnable" });

classWriter.visitSource("Paramtest3.java", null);

classWriter.visitNestHost("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3");

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "series", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "fib", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "printOut", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$incI", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "incI", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$printMessage", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "printMessage", 0);

{
fieldVisitor = classWriter.visitField(ACC_FINAL, "n", "I", null, new Integer(10));
fieldVisitor.visitEnd();
}
{
fieldVisitor = classWriter.visitField(ACC_FINAL, "start", "I", null, new Integer(1));
fieldVisitor.visitEnd();
}
{
fieldVisitor = classWriter.visitField(0, "i", "I", null, null);
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
methodVisitor.visitLineNumber(49, label0);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitVarInsn(ALOAD, 1);
methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;");
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLineNumber(50, label1);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitIntInsn(BIPUSH, 10);
methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "n", "I");
Label label2 = new Label();
methodVisitor.visitLabel(label2);
methodVisitor.visitLineNumber(51, label2);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitInsn(ICONST_1);
methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "start", "I");
methodVisitor.visitInsn(RETURN);
Label label3 = new Label();
methodVisitor.visitLabel(label3);
methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series;", null, label0, label3, 0);
methodVisitor.visitLocalVariable("this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;", null, label0, label3, 1);
methodVisitor.visitMaxs(2, 2);
methodVisitor.visitEnd();
}
{
methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(71, label0);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;");
methodVisitor.visitLdcInsn("10");
methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "count", "Ljava/lang/String;");
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLineNumber(72, label1);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitInsn(ICONST_1);
methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "i", "I");
Label label2 = new Label();
methodVisitor.visitLabel(label2);
methodVisitor.visitLineNumber(73, label2);
methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "i", "I");
methodVisitor.visitIntInsn(BIPUSH, 10);
Label label3 = new Label();
methodVisitor.visitJumpInsn(IF_ICMPGT, label3);
Label label4 = new Label();
methodVisitor.visitLabel(label4);
methodVisitor.visitLineNumber(74, label4);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;");
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "i", "I");
methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "in", "I");
Label label5 = new Label();
methodVisitor.visitLabel(label5);
methodVisitor.visitLineNumber(75, label5);
methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib");
methodVisitor.visitInsn(DUP);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;");
methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "<init>", "(Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;)V", false);
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "run", "()V", false);
Label label6 = new Label();
methodVisitor.visitLabel(label6);
methodVisitor.visitLineNumber(76, label6);
methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut");
methodVisitor.visitInsn(DUP);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut", "<init>", "(Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series;)V", false);
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut", "run", "()V", false);
Label label7 = new Label();
methodVisitor.visitLabel(label7);
methodVisitor.visitLineNumber(77, label7);
methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$incI");
methodVisitor.visitInsn(DUP);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$incI", "<init>", "(Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series;)V", false);
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$incI", "run", "()V", false);
methodVisitor.visitJumpInsn(GOTO, label2);
methodVisitor.visitLabel(label3);
methodVisitor.visitLineNumber(79, label3);
methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$printMessage");
methodVisitor.visitInsn(DUP);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;");
methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$printMessage", "<init>", "(Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;)V", false);
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$printMessage", "run", "()V", false);
Label label8 = new Label();
methodVisitor.visitLabel(label8);
methodVisitor.visitLineNumber(80, label8);
methodVisitor.visitInsn(RETURN);
Label label9 = new Label();
methodVisitor.visitLabel(label9);
methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series;", null, label0, label9, 0);
methodVisitor.visitMaxs(3, 1);
methodVisitor.visitEnd();
}
classWriter.visitEnd();

return classWriter.toByteArray();
}
}
