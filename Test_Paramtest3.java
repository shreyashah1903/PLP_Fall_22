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
public class Paramtest3Dump implements Opcodes {

public static byte[] dump () throws Exception {

ClassWriter classWriter = new ClassWriter(0);
FieldVisitor fieldVisitor;
RecordComponentVisitor recordComponentVisitor;
MethodVisitor methodVisitor;
AnnotationVisitor annotationVisitor0;

classWriter.visit(V18, ACC_PUBLIC | ACC_SUPER, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", null, "java/lang/Object", new String[] { "java/lang/Runnable" });

classWriter.visitSource("Paramtest3.java", null);

classWriter.visitNestMember("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$printMessage");

classWriter.visitNestMember("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series");

classWriter.visitNestMember("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut");

classWriter.visitNestMember("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$incI");

classWriter.visitNestMember("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib");

classWriter.visitNestMember("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$baseCase");

classWriter.visitNestMember("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$recursiveCall");

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "series", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$printMessage", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "printMessage", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "fib", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "printOut", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$incI", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "incI", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$baseCase", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "baseCase", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib$recursiveCall", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$fib", "recursiveCall", 0);

{
fieldVisitor = classWriter.visitField(0, "in", "I", null, null);
fieldVisitor.visitEnd();
}
{
fieldVisitor = classWriter.visitField(0, "out", "I", null, null);
fieldVisitor.visitEnd();
}
{
fieldVisitor = classWriter.visitField(0, "count", "Ljava/lang/String;", null, null);
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
methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;", null, label0, label2, 0);
methodVisitor.visitMaxs(1, 1);
methodVisitor.visitEnd();
}
{
methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(91, label0);
methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3");
methodVisitor.visitInsn(DUP);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "<init>", "()V", false);
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "run", "()V", false);
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLineNumber(92, label1);
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
methodVisitor.visitLineNumber(96, label0);
methodVisitor.visitTypeInsn(NEW, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series");
methodVisitor.visitInsn(DUP);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "<init>", "(Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;)V", false);
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "run", "()V", false);
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLineNumber(97, label1);
methodVisitor.visitInsn(RETURN);
Label label2 = new Label();
methodVisitor.visitLabel(label2);
methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;", null, label0, label2, 0);
methodVisitor.visitMaxs(3, 1);
methodVisitor.visitEnd();
}
classWriter.visitEnd();

return classWriter.toByteArray();
}
}
