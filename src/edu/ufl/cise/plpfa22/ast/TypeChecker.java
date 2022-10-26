package edu.ufl.cise.plpfa22.ast;

import edu.ufl.cise.plpfa22.IToken;
import edu.ufl.cise.plpfa22.PLPException;
import edu.ufl.cise.plpfa22.TypeCheckException;

import static edu.ufl.cise.plpfa22.LogHelper.printOutput;

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
        //TODO Check if assignment is correctly typed
        Types.Type expressionType;
        if (statementAssign.expression instanceof ExpressionIdent) {
            expressionType = ((ExpressionIdent) statementAssign.expression).getDec().getType();
        }
        else {
            expressionType = statementAssign.expression.getType();
        }

        Types.Type identDecType = statementAssign.ident.getDec().getType();
        if (isTreeTraversedOnce && identDecType != null && identDecType != expressionType) {
            throw new TypeCheckException("Type mismatch: Expected:"+identDecType + " but found "+expressionType);
        }

        if (isTreeTraversedOnce && statementAssign.ident.getDec() instanceof ConstDec) {
            throw new TypeCheckException("Cannot assign again to CONST");
        }

        // Type can be inferred from the RHS or LHS. If RHS type unknown, infer from LHS (if known). If LHS type unknown, infer from RHS (if known).
        if (expressionType != null && identDecType == null) {
            statementAssign.ident.dec.setType(expressionType);
        }
        if (identDecType != null && statementAssign.expression instanceof ExpressionIdent && ((ExpressionIdent) statementAssign.expression).getDec().getType() == null) {
            ((ExpressionIdent) statementAssign.expression).getDec().setType(identDecType);
        }
        printOutput("Typechecker visitStatementAssign identType:" + statementAssign.ident.getDec().getType() + " expression type:"+statementAssign.expression.getType());
        return null;
    }

    @Override
    public Object visitVarDec(VarDec varDec, Object arg) throws PLPException {
        return null;
    }

    @Override
    public Object visitStatementCall(StatementCall statementCall, Object arg) throws PLPException {
        Types.Type type = statementCall.ident.getDec().getType();
        if (isTreeTraversedOnce && type != Types.Type.PROCEDURE) {
            throw new TypeCheckException("Expected PROCEDURE type but found "+type);
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
        printOutput("TypeChecker - visitStatementInput: Type:"+type);
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
            printOutput("Typechecker - visitStatementOutput type:"+type);
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
        statementIf.expression.visit(this, arg);
        statementIf.statement.visit(this, arg);
        return null;
    }

    @Override
    public Object visitStatementWhile(StatementWhile statementWhile, Object arg) throws PLPException {
        return null;
    }

    @Override
    public Object visitExpressionBinary(ExpressionBinary expressionBinary, Object arg) throws PLPException {
        Object result1 = expressionBinary.e0.visit(this, arg);
        Object result2 = expressionBinary.e1.visit(this, arg);
        // TODO Handle more cases
        if (isExpression(result1) && isExpression(result2)) {
            Types.Type type1 = (Types.Type) result1;
            Types.Type type2 = (Types.Type) result2;

            IToken.Kind kind = expressionBinary.op.getKind();

            if (type1.equals(Types.Type.NUMBER) && type2.equals(Types.Type.NUMBER) &&
                    (kind.equals(IToken.Kind.MINUS) || kind.equals(IToken.Kind.DIV) || kind.equals(IToken.Kind.MOD))) {
                expressionBinary.setType(Types.Type.NUMBER);
            }
        }
        return null;
    }

    private boolean isExpression(Object obj) {
        return obj instanceof ExpressionIdent || obj instanceof ExpressionBooleanLit || obj instanceof ExpressionNumLit;
    }

    @Override
    public Object visitExpressionIdent(ExpressionIdent expressionIdent, Object arg) throws PLPException {
        return null;
    }

    @Override
    public Object visitExpressionNumLit(ExpressionNumLit expressionNumLit, Object arg) throws PLPException {
        return null;
    }

    @Override
    public Object visitExpressionStringLit(ExpressionStringLit expressionStringLit, Object arg) throws PLPException {
        return null;
    }

    @Override
    public Object visitExpressionBooleanLit(ExpressionBooleanLit expressionBooleanLit, Object arg) throws PLPException {
        return null;
    }

    @Override
    public Object visitProcedure(ProcDec procDec, Object arg) throws PLPException {
        procDec.setType(Types.Type.PROCEDURE);
        procDec.block.visit(this, arg);
        return procDec.getType();
    }

    @Override
    public Object visitConstDec(ConstDec constDec, Object arg) throws PLPException {
        if (constDec.val instanceof Integer) {
            constDec.setType(Types.Type.NUMBER);
        } else if (constDec.val instanceof String) {
            constDec.setType(Types.Type.STRING);
        } else if (constDec.val instanceof Boolean) {
            constDec.setType(Types.Type.BOOLEAN);
        }
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
