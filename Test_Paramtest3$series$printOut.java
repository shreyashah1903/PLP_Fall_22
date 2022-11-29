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
public class Paramtest3$series$printOutDump implements Opcodes {

public static byte[] dump () throws Exception {

ClassWriter classWriter = new ClassWriter(0);
FieldVisitor fieldVisitor;
RecordComponentVisitor recordComponentVisitor;
MethodVisitor methodVisitor;
AnnotationVisitor annotationVisitor0;

classWriter.visit(V18, ACC_SUPER, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut", null, "java/lang/Object", new String[] { "java/lang/Runnable" });

classWriter.visitSource("Paramtest3.java", null);

classWriter.visitNestHost("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3");

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "series", 0);

classWriter.visitInnerClass("edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut", "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "printOut", 0);

{
fieldVisitor = classWriter.visitField(ACC_FINAL | ACC_SYNTHETIC, "this$1", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series;", null, null);
fieldVisitor.visitEnd();
}
{
methodVisitor = classWriter.visitMethod(0, "<init>", "(Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series;)V", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(62, label0);
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitVarInsn(ALOAD, 1);
methodVisitor.visitFieldInsn(PUTFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut", "this$1", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series;");
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
methodVisitor.visitInsn(RETURN);
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut;", null, label0, label1, 0);
methodVisitor.visitLocalVariable("this$1", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series;", null, label0, label1, 1);
methodVisitor.visitMaxs(2, 2);
methodVisitor.visitEnd();
}
{
methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "run", "()V", null, null);
methodVisitor.visitCode();
Label label0 = new Label();
methodVisitor.visitLabel(label0);
methodVisitor.visitLineNumber(65, label0);
methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
methodVisitor.visitVarInsn(ALOAD, 0);
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut", "this$1", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series;");
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series", "this$0", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3;");
methodVisitor.visitFieldInsn(GETFIELD, "edu/ufl/cise/plpfa22/asmhelpers/Paramtest3", "out", "I");
methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
Label label1 = new Label();
methodVisitor.visitLabel(label1);
methodVisitor.visitLineNumber(66, label1);
methodVisitor.visitInsn(RETURN);
Label label2 = new Label();
methodVisitor.visitLabel(label2);
methodVisitor.visitLocalVariable("this", "Ledu/ufl/cise/plpfa22/asmhelpers/Paramtest3$series$printOut;", null, label0, label2, 0);
methodVisitor.visitMaxs(2, 1);
methodVisitor.visitEnd();
}
classWriter.visitEnd();

return classWriter.toByteArray();
}
}
