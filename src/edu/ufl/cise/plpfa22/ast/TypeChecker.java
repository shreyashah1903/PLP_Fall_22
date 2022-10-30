package edu.ufl.cise.plpfa22.ast;

import edu.ufl.cise.plpfa22.PLPException;
import edu.ufl.cise.plpfa22.TypeCheckException;

import static edu.ufl.cise.plpfa22.IToken.Kind;
import static edu.ufl.cise.plpfa22.LogHelper.printOutput;
import static edu.ufl.cise.plpfa22.ast.Types.Type;

//TODO 1. Check if/while guard cases
//     2. Check BinaryExpression and handle all operators

public class TypeChecker implements ASTVisitor {
    private boolean isTreeTraversedOnce = false;

    @Override
    public Object visitBlock(Block block, Object arg) throws PLPException {
        for (ConstDec dec : block.constDecs) {
            dec.visit(this, arg);
        }
        for (VarDec dec : block.varDecs) {
            dec.visit(this, arg);
        }
        for (ProcDec dec : block.procedureDecs) {
            dec.visit(this, arg);
        }
        Statement statement = block.statement;
        statement.visit(this, arg);
        return null;
    }

    @Override
    public Object visitProgram(Program program, Object arg) throws PLPException {
        program.block.visit(this, arg);
        isTreeTraversedOnce = true;
        program.block.visit(this, arg);
        return null;
    }

    @Override
    public Object visitStatementAssign(StatementAssign statementAssign, Object arg) throws PLPException {
        Types.Type expressionType = (Type) statementAssign.expression.visit(this, arg);
        Declaration identDec = statementAssign.ident.getDec();
        Types.Type identType = identDec.getType();

        if (identType != null && expressionType != null && identType != expressionType) {
            throw new TypeCheckException("Type mismatch: Expected:" + identType + " but found " + expressionType);
        }

        if (identDec instanceof ConstDec) {
            throw new TypeCheckException("Cannot assign again to CONST");
        }

        // Type can be inferred from the RHS or LHS. If RHS type unknown, infer from LHS (if known).
        // If LHS type unknown, infer from RHS (if known).
        if (expressionType != null && identType == null) {
            identDec.setType(expressionType);
        } else if (identType != null && expressionType == null) {
            statementAssign.expression.setType(identType);
        }

        printOutput("Typechecker visitStatementAssign identType:" + identDec.getType() + " expression type:" + statementAssign.expression.getType());
        return statementAssign.expression.getType();
    }

    @Override
    public Object visitVarDec(VarDec varDec, Object arg) throws PLPException {
        return null;
    }

    @Override
    public Object visitStatementCall(StatementCall statementCall, Object arg) throws PLPException {
        Types.Type type = statementCall.ident.getDec().getType();
        if (type != Types.Type.PROCEDURE) {
            throw new TypeCheckException("Expected PROCEDURE type but found " + type);
        }
        checkNullType(type);
        return null;
    }

    private void checkNullType(Types.Type type) throws TypeCheckException {
        if (isTreeTraversedOnce && type == null) {
            throw new TypeCheckException("Type is null");
        }
    }

    @Override
    public Object visitStatementInput(StatementInput statementInput, Object arg) throws PLPException {
        Types.Type type = statementInput.ident.dec.getType();
        printOutput("TypeChecker - visitStatementInput: Type:" + type);
        if (isTreeTraversedOnce && !isStatementInputType(type)) {
            throw new TypeCheckException("StatementInput type should be either Number, Boolean or String");
        }
        checkNullType(type);
        return null;
    }

    private boolean isStatementOutputType(Types.Type type) {
        return type == Types.Type.NUMBER || type == Types.Type.STRING || type == Types.Type.BOOLEAN;
    }

    private boolean isStatementInputType(Types.Type type) {
        return type == Types.Type.NUMBER || type == Types.Type.STRING || type == Types.Type.BOOLEAN;
    }

    @Override
    public Object visitStatementOutput(StatementOutput statementOutput, Object arg) throws PLPException {
        if (statementOutput.expression instanceof ExpressionIdent) {
            Declaration declaration = ((ExpressionIdent) statementOutput.expression).getDec();

            Types.Type type = declaration.getType();
            printOutput("Typechecker - visitStatementOutput type:" + type);
            if (isTreeTraversedOnce && !isStatementOutputType(type)) {
                throw new TypeCheckException("StatementOutput type should be Number, String or Boolean");
            }
            checkNullType(type);
        }
        return statementOutput.expression.getType();
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
        Type type = (Type) statementIf.expression.visit(this, arg);
        printOutput("visitStatementIf -- type:"+type);
        checkGuardCondition(type);
        statementIf.statement.visit(this, arg);
        return null;
    }

    @Override
    public Object visitStatementWhile(StatementWhile statementWhile, Object arg) throws PLPException {
        Type type = (Type) statementWhile.expression.visit(this, arg);
        printOutput("visitStatementWhile -- type:"+type);
        //FIXME Shrey Fix this :D
        checkGuardCondition(type);
        statementWhile.statement.visit(this, arg);
        return null;
    }

    private void checkGuardCondition(Type type) throws TypeCheckException {
        if (isTreeTraversedOnce && !type.equals(Type.BOOLEAN)) {
            throw new TypeCheckException("Guard condition should be BOOLEAN but found "+type);
        }
    }

    @Override
    public Object visitExpressionBinary(ExpressionBinary expressionBinary, Object arg) throws PLPException {
        Types.Type type1 = (Types.Type) expressionBinary.e0.visit(this, arg);
        Types.Type type2 = (Types.Type) expressionBinary.e1.visit(this, arg);
        Kind kind = expressionBinary.op.getKind();

        if (type1 != null && type2 != null && !type1.equals(type2)) {
            throw new TypeCheckException("Types should be same");
        }

        if (type1 == null && type2 != null) {
            Expression expression = expressionBinary.e0;
            setExpressionType(type2, expression, expressionBinary);
        } else if (type1 != null && type2 == null) {
            Expression expression = expressionBinary.e1;
            setExpressionType(type1, expression, expressionBinary);
        }

        // TODO Handle more cases
        if (type1 != null && type2 != null) {
            if (isAnyEqualOperator(kind)) {
                expressionBinary.setType(Type.BOOLEAN);
            }
            else if (type1.equals(Types.Type.NUMBER)) {
                expressionBinary.setType(Types.Type.NUMBER);
            }
        }
        printOutput("TypeChecker- visitExpressionBinary type1:" + type1 + " Type2:" + type2);
        return expressionBinary.getType();
    }

    private void setExpressionType(Type type, Expression expression, ExpressionBinary expressionBinary) {
        if (expression instanceof ExpressionIdent) {
            ((ExpressionIdent) expression).getDec().setType(type);
        } else if (expression instanceof ExpressionBinary) {
            Expression e0 = ((ExpressionBinary) expression).e0;
            Expression e1 = ((ExpressionBinary) expression).e1;

            // Set expression type for child expressions
            setExpressionType(type, e0, expressionBinary);
            setExpressionType(type, e1, expressionBinary);
        }
        expressionBinary.setType(type);
    }

    private boolean isAnyEqualOperator(Kind kind) {
        return kind == Kind.EQ || kind == Kind.NEQ || kind == Kind.LT || kind == Kind.LE || kind == Kind.GT
                || kind == Kind.GE;
    }

    @Override
    public Object visitExpressionIdent(ExpressionIdent expressionIdent, Object arg) throws PLPException {
        return expressionIdent.getDec().getType();
    }

    @Override
    public Object visitExpressionNumLit(ExpressionNumLit expressionNumLit, Object arg) throws PLPException {
        return expressionNumLit.getType();
    }

    @Override
    public Object visitExpressionStringLit(ExpressionStringLit expressionStringLit, Object arg) throws PLPException {
        return expressionStringLit.getType();
    }

    @Override
    public Object visitExpressionBooleanLit(ExpressionBooleanLit expressionBooleanLit, Object arg) throws PLPException {
        return expressionBooleanLit.getType();
    }

    @Override
    public Object visitProcedure(ProcDec procDec, Object arg) throws PLPException {
        procDec.block.visit(this, arg);
        return procDec.getType();
    }

    @Override
    public Object visitConstDec(ConstDec constDec, Object arg) throws PLPException {
        return constDec.getType();
    }

    @Override
    public Object visitStatementEmpty(StatementEmpty statementEmpty, Object arg) throws PLPException {
        return null;
    }

    @Override
    public Object visitIdent(Ident ident, Object arg) throws PLPException {
        return null;
    }
}
