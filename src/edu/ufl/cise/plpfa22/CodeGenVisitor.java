package edu.ufl.cise.plpfa22;

import edu.ufl.cise.plpfa22.IToken.Kind;
import edu.ufl.cise.plpfa22.ast.*;
import edu.ufl.cise.plpfa22.ast.Types.Type;
import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeGenVisitor implements ASTVisitor, Opcodes {

    public static final String JAVA_LANG_STRING = "java/lang/String";
    final String packageName;
    final String className;
    final String sourceFileName;
    final String fullyQualifiedClassName;
    final String classDesc;

    ClassWriter classWriter;

    private static final String BOOLEAN_NOT_CLASS = "edu/ufl/cise/plpfa22/BooleanNotOp";
    private static final String CLASS_NAME = "edu/ufl/cise/plpfa22/prog";
    private static final String INSTANCE_NAME = "Ledu/ufl/cise/plpfa22/prog;";

    private final List<CodeGenUtils.GenClass> bytecodeList = new ArrayList<>();
    private final List<String> classNameList = new ArrayList<>();

    public CodeGenVisitor(String className, String packageName, String sourceFileName) {
        super();
        this.packageName = packageName;
        this.className = className;
        this.sourceFileName = sourceFileName;
        this.fullyQualifiedClassName = packageName + "/" + className;
        this.classDesc = "L" + this.fullyQualifiedClassName + ';';
    }

    @Override
    public Object visitBlock(Block block, Object arg) throws PLPException {
        ClassWriter classWriter = (ClassWriter) arg;
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "run", "()V", null, null);

        for (ConstDec constDec : block.constDecs) {
            constDec.visit(this, methodVisitor);
        }
        for (VarDec varDec : block.varDecs) {
            varDec.visit(this, arg);
        }
        for (ProcDec procDec : block.procedureDecs) {
            procDec.visit(this, CLASS_NAME);
        }

        methodVisitor.visitCode();

        //add instructions from statement to method
        block.statement.visit(this, methodVisitor);

        methodVisitor.visitInsn(RETURN);

        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();
        return null;

    }

    @Override
    public Object visitProgram(Program program, Object arg) throws PLPException {
        //create a classWriter and visit it
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        //Hint:  if you get failures in the visitMaxs, try creating a ClassWriter with 0
        // instead of ClassWriter.COMPUTE_FRAMES.  The result will not be a valid classfile,
        // but you will be able to print it so you can see the instructions.  After fixing,
        // restore ClassWriter.COMPUTE_FRAMES
        // Added a runnable interface
        classWriter.visit(V18, ACC_PUBLIC | ACC_SUPER, fullyQualifiedClassName, null, "java/lang/Object", new String[]{"java/lang/Runnable"});

        //Invoke a simple ASTVisitor to visit all procedure declarations and annotate them with their JVM names
        annotateProcedureDec(program.block, CLASS_NAME, "Ledu/ufl/cise/plpfa22/prog");

        visitInitBlock(classWriter);

        classNameList.add(CLASS_NAME);
        program.block.visit(this, classWriter);
        classNameList.remove(classNameList.size() - 1);

        visitMainBlock();

        //finish up the class
        classWriter.visitEnd();

        //return the bytes making up the classfile
        byte[] bytes = classWriter.toByteArray();
        bytecodeList.add(0, new CodeGenUtils.GenClass(CLASS_NAME, bytes));
        return bytecodeList;
//		return bytes;
    }

    private static void annotateProcedureDec(Block block, String className, String classDesc) {
        for (ProcDec procDec : block.procedureDecs) {
            System.out.println("ProcDec");
            String ident = String.valueOf(procDec.ident.getText());

            procDec.setParentClassName(className);
            className = className + "$" + ident;

            classDesc = classDesc + "$" + ident + ";";

            System.out.println("annotateProcedureDec ChildClass:"+className + " Parentclass:"+procDec.getParentClassName());

            procDec.setJvmType(className);
            procDec.setClassName(className);
            procDec.setClassDec(classDesc);

            Block block1 = procDec.block;
            for (ConstDec constDec : block1.constDecs) {
                constDec.setClassDec(classDesc);
                constDec.setClassName(className);
            }
            for (VarDec varDec : block1.varDecs) {
                varDec.setClassDec(classDesc);
                varDec.setClassName(className);
            }

            if (!block1.procedureDecs.isEmpty()) {
                annotateProcedureDec(block1, className, classDesc.substring(0, classDesc.length() - 1));
            }
            String[] classes = className.split("\\$");
            if (classes.length > 1) {
                className = classes[0];
                classDesc = classDesc.split("\\$")[0];
            }
        }
    }

    private static void visitInitBlock(ClassWriter classWriter, String... parameter) {
        String descriptor = parameter.length == 0 ? "()V" : "(" + parameter[0] + ")V";
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", descriptor, null, null);
        methodVisitor.visitCode();

        Label label0 = new Label();
        methodVisitor.visitLabel(label0);

        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);

        Label label1 = new Label();
        methodVisitor.visitLabel(label1);
        methodVisitor.visitInsn(RETURN);

        Label label2 = new Label();
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLocalVariable("this", INSTANCE_NAME, null, label0, label2, 0);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    private void visitMainBlock() {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        methodVisitor.visitCode();

        Label label3 = new Label();
        methodVisitor.visitLabel(label3);
        methodVisitor.visitTypeInsn(NEW, CLASS_NAME);
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, CLASS_NAME, "<init>", "()V", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, CLASS_NAME, "run", "()V", false);

        Label label4 = new Label();
        methodVisitor.visitLabel(label4);
        methodVisitor.visitInsn(RETURN);

        Label label5 = new Label();
        methodVisitor.visitLabel(label5);
        methodVisitor.visitLocalVariable("args", "[Ljava/lang/String;", null, label3, label5, 0);
        methodVisitor.visitMaxs(2, 1);
        methodVisitor.visitEnd();
    }

    @Override
    public Object visitStatementAssign(StatementAssign statementAssign, Object arg) throws PLPException {
        statementAssign.expression.visit(this, arg);
        statementAssign.ident.visit(this, arg);
        return null;
    }

    @Override
    public Object visitVarDec(VarDec varDec, Object arg) throws PLPException {
        Type type = varDec.getType();

        System.out.println("Vardec type" + type);
        if(type != null)  {
            FieldVisitor fieldVisitor = classWriter.visitField(ACC_PUBLIC, String.valueOf(varDec.ident.getText()),
                    varDec.getJvmType(), null, null);
            fieldVisitor.visitEnd();
        }

        return null;
    }

    @Override
    public Object visitStatementCall(StatementCall statementCall, Object arg) throws PLPException {
        MethodVisitor methodVisitor = (MethodVisitor)arg;
        String parentClassName = statementCall.ident.getDec().getParentClassName();

        String newClassName = statementCall.ident.getDec().getClassName();
        methodVisitor.visitTypeInsn(NEW, newClassName);
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);

        if (classNameList.size() > 1) {
            int identNestLevel = statementCall.ident.getNest();
            // If parentClassName != className at last index, then Getfield to get the enclosing class
            while (identNestLevel > 0 && !parentClassName.equals(classNameList.get(identNestLevel))) {
                methodVisitor.visitFieldInsn(GETFIELD, classNameList.get(identNestLevel), "this$" + (identNestLevel - 1), "L" + classNameList.get(identNestLevel - 1) + ";");
                identNestLevel--;
            }
        }
        methodVisitor.visitMethodInsn(INVOKESPECIAL, newClassName, "<init>", "(L" + parentClassName + ";)V", false);

        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, newClassName, "run", "()V", false);

        return null;
    }

    @Override
    public Object visitStatementInput(StatementInput statementInput, Object arg) throws PLPException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object visitStatementOutput(StatementOutput statementOutput, Object arg) throws PLPException {
        MethodVisitor mv = (MethodVisitor) arg;
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        statementOutput.expression.visit(this, arg);

        Type etype = statementOutput.expression.getType();
        String JVMType = (etype.equals(Type.NUMBER) ? "I" : (etype.equals(Type.BOOLEAN) ? "Z" : "Ljava/lang/String;"));
        String printlnSig = "(" + JVMType + ")V";

        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", printlnSig, false);
        return null;
    }

    @Override
    public Object visitStatementBlock(StatementBlock statementBlock, Object arg) throws PLPException {
        for (Statement statement : statementBlock.statements) {
            statement.visit(this, arg);
        }
        return null;
    }

    @Override
    public Object visitStatementIf(StatementIf statementIf, Object arg) throws PLPException {
        MethodVisitor mv = (MethodVisitor) arg;
        statementIf.expression.visit(this, arg);
        Label exprLabel = new Label();
        mv.visitJumpInsn(IFEQ, exprLabel);
        statementIf.statement.visit(this, arg);
        mv.visitLabel(exprLabel);
        return null;
    }

    @Override
    public Object visitStatementWhile(StatementWhile statementWhile, Object arg) throws PLPException {
        MethodVisitor mv = (MethodVisitor) arg;
        Label label1 = new Label();
        mv.visitJumpInsn(GOTO, label1);

        Label label2 = new Label();
        mv.visitLabel(label2);

        statementWhile.statement.visit(this, arg);
        mv.visitLabel(label1);
        statementWhile.expression.visit(this, arg);

        mv.visitJumpInsn(IFNE, label2);

        return null;
    }

    @Override
    public Object visitExpressionBinary(ExpressionBinary expressionBinary, Object arg) throws PLPException {
        MethodVisitor mv = (MethodVisitor) arg;
        Type argType = expressionBinary.e0.getType();
        Kind op = expressionBinary.op.getKind();
        switch (argType) {
            case NUMBER -> {
                expressionBinary.e0.visit(this, arg);
                expressionBinary.e1.visit(this, arg);
                switch (op) {
                    case PLUS -> mv.visitInsn(IADD);
                    case MINUS -> mv.visitInsn(ISUB);
                    case TIMES -> mv.visitInsn(IMUL);
                    case DIV -> mv.visitInsn(IDIV);
                    case MOD -> mv.visitInsn(IREM);
                    case EQ -> {
                        visitExpBinaryOp(mv, IF_ICMPNE);
                    }
                    case NEQ -> {
                        visitExpBinaryOp(mv, IF_ICMPEQ);
                    }
                    case LT -> {
                        visitExpBinaryOp(mv, IF_ICMPGE);
                    }
                    case LE -> {
                        visitExpBinaryOp(mv, IF_ICMPGT);
                    }
                    case GT -> {
                        visitExpBinaryOp(mv, IF_ICMPLE);
                    }
                    case GE -> {
                        visitExpBinaryOp(mv, IF_ICMPLT);
                    }
                    default -> {
                        throw new IllegalStateException("code gen bug in visitExpressionBinary NUMBER");
                    }
                }
            }
            case BOOLEAN -> {
                expressionBinary.e0.visit(this, arg);
                expressionBinary.e1.visit(this, arg);
                switch (op) {
                    case PLUS -> mv.visitInsn(IOR);
                    case TIMES -> mv.visitInsn(IAND);
                    case EQ -> {
                        visitExpBinaryOp(mv, IF_ICMPNE);
                    }
                    case NEQ -> {
                        visitExpBinaryOp(mv, IF_ICMPEQ);
                    }
                    case LT -> {
                        visitExpBinaryOp(mv, IF_ICMPGE);
                    }
                    case LE -> {
                        visitExpBinaryOp(mv, IF_ICMPGT);
                    }
                    case GT -> {
                        visitExpBinaryOp(mv, IF_ICMPLE);
                    }
                    case GE -> {
                        visitExpBinaryOp(mv, IF_ICMPLT);
                    }
                    default -> {
                        throw new IllegalStateException("code gen bug in visitExpressionBinary BOOLEAN");
                    }
                }
            }
            case STRING -> {
                Label start = new Label();
                Label end = new Label();
                expressionBinary.e0.visit(this, arg);
                expressionBinary.e1.visit(this, arg);

                switch (op) {
                    case PLUS -> {
                        mv.visitMethodInsn(INVOKEVIRTUAL, JAVA_LANG_STRING, "concat", "(Ljava/lang/String;)Ljava/lang/String;", false);
                    }
                    case EQ -> {
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z", false);
                    }
                    case NEQ -> {
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z", false);
                        mv.visitMethodInsn(INVOKESTATIC, BOOLEAN_NOT_CLASS, "not", "(Z)Z", false);
                    }
                    case LT -> {
                        mv.visitInsn(Opcodes.SWAP);
                        mv.visitInsn(DUP2);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "startsWith", "(Ljava/lang/String;)Z", false);
                        mv.visitVarInsn(ISTORE, 1);

                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z", false);
                        mv.visitMethodInsn(INVOKESTATIC, BOOLEAN_NOT_CLASS, "not", "(Z)Z", false);

                        mv.visitVarInsn(ILOAD, 1);
                        mv.visitInsn(IAND);

                    }
                    case LE -> {
                        mv.visitInsn(Opcodes.SWAP);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "startsWith", "(Ljava/lang/String;)Z", false);
                    }
                    case GT -> {
                        mv.visitInsn(DUP2);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "endsWith", "(Ljava/lang/String;)Z", false);
                        mv.visitVarInsn(ISTORE, 1);

                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z", false);
                        mv.visitMethodInsn(INVOKESTATIC, BOOLEAN_NOT_CLASS, "not", "(Z)Z", false);

                        mv.visitVarInsn(ILOAD, 1);
                        mv.visitInsn(IAND);
                    }
                    case GE -> {
                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "endsWith", "(Ljava/lang/String;)Z", false);
                    }
                    default -> {
                        throw new IllegalStateException("code gen bug in visitExpressionBinary BOOLEAN");
                    }
                }
                mv.visitJumpInsn(Opcodes.GOTO, end); //GOTO L0
                mv.visitLabel(start);

                mv.visitLdcInsn(true);//LDC 1
                mv.visitLabel(end);
            }
            default -> {
                throw new IllegalStateException("code gen bug in visitExpressionBinary");
            }
        }
        return null;
    }

    private void visitExpBinaryOp(MethodVisitor mv, int opcode) {
        Label labelNumEqFalseBr = new Label();
        mv.visitJumpInsn(opcode, labelNumEqFalseBr);
        mv.visitInsn(ICONST_1);
        Label labelPostNumEq = new Label();
        mv.visitJumpInsn(GOTO, labelPostNumEq);
        mv.visitLabel(labelNumEqFalseBr);
        mv.visitInsn(ICONST_0);
        mv.visitLabel(labelPostNumEq);
    }

    @Override
    public Object visitExpressionIdent(ExpressionIdent expressionIdent, Object arg) throws PLPException {
        System.out.println("VisitExpIdent:" + expressionIdent.getDec().getType());
        MethodVisitor methodVisitor = (MethodVisitor) arg;
        methodVisitor.visitVarInsn(ALOAD, 0);
        String name;
        if (expressionIdent.getDec() instanceof ConstDec) {
            name = String.valueOf(((ConstDec) expressionIdent.getDec()).ident.getText());
        } else {
            name = String.valueOf(((VarDec) expressionIdent.getDec()).ident.getText());
            System.out.println("ExpressionIdent Name:" + name);
        }
        int identNestLevel = expressionIdent.getNest();
        int decNestLevel = expressionIdent.getDec().getNest();

        while (identNestLevel > decNestLevel) {
            methodVisitor.visitFieldInsn(GETFIELD, classNameList.get(identNestLevel), "this$" + (identNestLevel - 1), "L" + classNameList.get(identNestLevel - 1) + ";");
            identNestLevel--;
        }

        methodVisitor.visitFieldInsn(GETFIELD, classNameList.get(identNestLevel), name, expressionIdent.getDec().getJvmType());
        return null;
    }

    @Override
    public Object visitExpressionNumLit(ExpressionNumLit expressionNumLit, Object arg) throws PLPException {
        MethodVisitor mv = (MethodVisitor) arg;
        mv.visitLdcInsn(expressionNumLit.getFirstToken().getIntValue());
        return null;
    }

    @Override
    public Object visitExpressionStringLit(ExpressionStringLit expressionStringLit, Object arg) throws PLPException {
        MethodVisitor mv = (MethodVisitor) arg;
        mv.visitLdcInsn(expressionStringLit.getFirstToken().getStringValue());
        return null;
    }

    @Override
    public Object visitExpressionBooleanLit(ExpressionBooleanLit expressionBooleanLit, Object arg) throws PLPException {
        MethodVisitor mv = (MethodVisitor) arg;
        mv.visitLdcInsn(expressionBooleanLit.getFirstToken().getBooleanValue());
        return null;
    }

    @Override
    public Object visitProcedure(ProcDec procDec, Object arg) throws PLPException {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES); //TODO Replace with COMPUTE_FRAMES
        classWriter.visit(V18, ACC_PUBLIC | ACC_SUPER, procDec.getJvmType(), null, "java/lang/Object", new String[]{"java/lang/Runnable"});

        String parentDesc = "L" + classNameList.get(classNameList.size() - 1) + ';';
        String fieldName = "this$" + procDec.getNest();
        FieldVisitor fieldVisitor = classWriter.visitField(ACC_PUBLIC, fieldName,
                parentDesc, null, null);
        fieldVisitor.visitEnd();

        String className = procDec.getJvmType();

        visitProcedureInitBlock(classWriter, parentDesc, fieldName, className, procDec.getClassDec());

        classNameList.add(procDec.getJvmType());
        procDec.block.visit(this, classWriter);
        classNameList.remove(classNameList.size() - 1);

        classWriter.visitEnd();
        byte[] bytes = classWriter.toByteArray();
        bytecodeList.add(new CodeGenUtils.GenClass(procDec.getJvmType(), bytes));
        return null;
    }

    private static void visitProcedureInitBlock(ClassWriter classWriter, String parentClassDesc, String fieldName, String fullyQualifiedClassName,
                                                String classDesc) {
        String descriptor = "(" + parentClassDesc + ")V";
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", descriptor, null, null);
        methodVisitor.visitCode();

        Label label0 = new Label();
        methodVisitor.visitLabel(label0);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitFieldInsn(PUTFIELD, fullyQualifiedClassName,
                fieldName, parentClassDesc);


        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);

        methodVisitor.visitInsn(RETURN);

        Label label2 = new Label();
        methodVisitor.visitLabel(label2);

        methodVisitor.visitLocalVariable("this", classDesc, null, label0, label2, 0);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    @Override
    public Object visitConstDec(ConstDec constDec, Object arg) throws PLPException {
        Type type = constDec.getType();
        System.out.println("Constdec type" + type);

        FieldVisitor fieldVisitor = classWriter.visitField(ACC_PUBLIC, String.valueOf(constDec.ident.getText()),
                constDec.getJvmType(), null, null);
        fieldVisitor.visitEnd();

        MethodVisitor methodVisitor = (MethodVisitor) arg;
        // TODO Verify if this is the right way. In ASMifier, this part is done in the init block
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitLdcInsn(constDec.val);
        methodVisitor.visitFieldInsn(PUTFIELD, CLASS_NAME, String.valueOf(constDec.ident.getText()), constDec.getJvmType());

        return null;
    }

    @Override
    public Object visitStatementEmpty(StatementEmpty statementEmpty, Object arg) throws PLPException {
        return null;
    }

    @Override
    public Object visitIdent(Ident ident, Object arg) throws PLPException {

        System.out.println("visitIdent:" + Arrays.toString(ident.getText()) + " nest:"+ident.getNest() + "dec nest:"+ident.getDec().getNest());
        MethodVisitor methodVisitor = (MethodVisitor) arg;

        String name = String.valueOf(((VarDec) ident.getDec()).ident.getText());
        String jvmType = ident.getDec().getJvmType();

        methodVisitor.visitVarInsn(ALOAD, 0);

        int identNestLevel = ident.getNest();
        int decNestLevel = ident.getDec().getNest();


        while (identNestLevel > decNestLevel) {
            methodVisitor.visitFieldInsn(GETFIELD, classNameList.get(identNestLevel), "this$" + (identNestLevel - 1), "L" + classNameList.get(identNestLevel - 1) + ";");
            identNestLevel--;
        }

        methodVisitor.visitInsn(SWAP);

        System.out.println("Ident Name:" + name);

        methodVisitor.visitFieldInsn(PUTFIELD, classNameList.get(identNestLevel), name, jvmType);
        return null;
    }


}
