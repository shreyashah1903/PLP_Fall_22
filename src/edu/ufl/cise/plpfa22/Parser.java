package edu.ufl.cise.plpfa22;

import edu.ufl.cise.plpfa22.ast.*;

import java.util.ArrayList;
import java.util.List;

public class Parser implements IParser {

    private IToken firstToken;

    private IToken token;

    private final ILexer lexer;
    private final List<ConstDec> constDecs = new ArrayList<>();
    private final List<VarDec> varDecs = new ArrayList<>();
    private final List<ProcDec> procedureDecs = new ArrayList<>();

    public Parser(ILexer lexer) {
        this.lexer = lexer;
        try {
            firstToken = lexer.next();
            token = firstToken;
        } catch (LexicalException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ASTNode parse() throws PLPException {
        Block block = new Block(firstToken, constDecs, varDecs, procedureDecs, getStatement(firstToken));
        return new Program(firstToken, block);
    }

    private Statement getStatement(IToken token) throws LexicalException, SyntaxException {
        if (token.getKind() == IToken.Kind.EOF) {
            return new StatementEmpty(firstToken);
        }
        consume();
        switch (token.getKind()) {
            case BANG -> {
                Expression expression = getExpression(this.token);
                return new StatementOutput(token, expression);
            }
            case KW_VAR -> {
                varDecs.add(new VarDec(firstToken, this.token));
                return new StatementEmpty(token);
            }
            case KW_PROCEDURE -> {
//                procedureDecs.add(new ProcDec(firstToken, this.token));
                consume();
                while (token.getKind() != IToken.Kind.SEMI) {
                    if (token.getKind() != IToken.Kind.IDENT) {
                        throwSyntaxException(token);
                    }
                }
                return new StatementEmpty(token);
            }
            case KW_BEGIN -> {
                List<Statement> statements = new ArrayList<>();
                while (this.token.getKind() != IToken.Kind.EOF && this.token.getKind() != IToken.Kind.KW_END) {
                    Statement statement = handleBeginStatement(this.token);
                    if (!(statement instanceof StatementEmpty)) {
                        statements.add(statement);
                    }
                    consume();
                }
                return new StatementBlock(firstToken, statements);
            }
            case KW_CONST -> {
                IToken ident;
                do {
                    if (this.token.getKind() == IToken.Kind.IDENT) {
                        ident = this.token;
                        consume();
                        if (this.token.getKind() == IToken.Kind.EQ) {
                            consume();
                        } else {
                            throw new SyntaxException();
                        }
                        switch (this.token.getKind()) {
                            case NUM_LIT -> constDecs.add(new ConstDec(firstToken, ident, this.token.getIntValue()));
                            case STRING_LIT ->
                                    constDecs.add(new ConstDec(firstToken, ident, this.token.getStringValue()));
                            case BOOLEAN_LIT ->
                                    constDecs.add(new ConstDec(firstToken, ident, this.token.getBooleanValue()));
                            default -> throw new SyntaxException();
                        }
                        consume();
                        if (this.token.getKind() == IToken.Kind.COMMA) consume();
                    } else {
                        throw new SyntaxException();
                    }
                } while (this.token.getKind() != IToken.Kind.SEMI);
                return new StatementEmpty(firstToken);
            }
            case QUESTION -> {
                return new StatementInput(firstToken, new Ident(this.token));
            }
            case STRING_LIT, BOOLEAN_LIT, NUM_LIT, IDENT -> {
                return new StatementOutput(token, getExpression(token));
            }
            default -> {
                return new StatementEmpty(token);
            }
        }
    }

    private Statement handleBeginStatement(IToken startToken) throws LexicalException, SyntaxException {
        List<IToken> tokens = new ArrayList<>();

        if (startToken.getKind() == IToken.Kind.IDENT) {
            while (token.getKind() != IToken.Kind.SEMI && token.getKind() != IToken.Kind.KW_END) {
                if (token.getKind() != IToken.Kind.ASSIGN) {
                    tokens.add(token);
                }
                consume();
            }
            Ident ident = new Ident(tokens.get(0));
            ident.setDec(new VarDec(firstToken, tokens.get(0)));
            return new StatementAssign(firstToken, ident, getExpression(tokens.get(1)));
        } else if (startToken.getKind() == IToken.Kind.BANG) {
            consume();
            if (token.getKind() != IToken.Kind.SEMI && token.getKind() != IToken.Kind.KW_END) {
                return new StatementOutput(startToken, getExpression(token));
            }
        } else {
            switch (startToken.getKind()) {
                case QUESTION -> {
                    consume();
                    return new StatementInput(firstToken, new Ident(this.token));
                }
                case KW_CALL -> {
                    consume();
                    return new StatementCall(firstToken, new Ident(this.token));
                }
                default -> {
                    return new StatementEmpty(startToken);
                }
            }
        }
        return new StatementEmpty(startToken);
    }

    private static void throwSyntaxException(IToken token) throws SyntaxException {
        throw new SyntaxException("Expected token ident inside BEGIN, Found " + token.getKind(),
                token.getSourceLocation().line(), token.getSourceLocation().line());
    }


    private Expression handlePrimaryExpression(IToken token) throws LexicalException, SyntaxException {
        // TODO: Handle ( <expression> )
        Expression expression;
        switch (token.getKind()) {
            case IDENT -> expression = new ExpressionIdent(token);
            case NUM_LIT -> expression = new ExpressionNumLit(token);
            case STRING_LIT -> expression = new ExpressionStringLit(token);
            case BOOLEAN_LIT -> expression = new ExpressionBooleanLit(token);
            default -> throw new SyntaxException();
        }
        consume();
        return expression;
    }

    private Expression handleMultiplicativeExpression(IToken token) throws LexicalException, SyntaxException {
        Expression operand1 = handlePrimaryExpression(token);
        Expression operand2 = null;
        IToken operator = null;
        while (this.token.getKind() == IToken.Kind.TIMES || this.token.getKind() == IToken.Kind.DIV
                || this.token.getKind() == IToken.Kind.MOD) {
            operator = this.token;
            switch (this.token.getKind()) {
                case TIMES, DIV, MOD -> consume();
            }
            operand2 = handlePrimaryExpression(this.token);
            operand1 = new ExpressionBinary(firstToken, operand1, operator, operand2);
        }
        if (operator == null || operand2 == null) throw new SyntaxException();
        return new ExpressionBinary(firstToken, operand1, operator, operand2);
    }


    private Expression handleAdditiveExpression(IToken token) throws LexicalException, SyntaxException {
        Expression operand1 = handleMultiplicativeExpression(token);
        Expression operand2 = null;
        IToken operator = null;
        while (this.token.getKind() == IToken.Kind.PLUS || this.token.getKind() == IToken.Kind.MINUS) {
            operator = this.token;
            switch (this.token.getKind()) {
                case PLUS, MINUS -> consume();
            }
            operand2 = handleMultiplicativeExpression(this.token);
            operand1 = new ExpressionBinary(firstToken, operand1, operator, operand2);
        }
        if (operator == null || operand2 == null) throw new SyntaxException();
        return new ExpressionBinary(firstToken, operand1, operator, operand2);
    }

    private Expression handleExpression(IToken token) throws LexicalException, SyntaxException {
        Expression operand1 = handleAdditiveExpression(token);
        Expression operand2 = null;
        IToken operator = null;
        while (isExpressionOperand(this.token.getKind())) {
            operator = this.token;
            if (isExpressionOperand(this.token.getKind())) consume();
            operand2 = handleAdditiveExpression(this.token);
            operand1 = new ExpressionBinary(firstToken, operand1, operator, operand2);
        }
        if (operator == null || operand2 == null) throw new SyntaxException();
        return new ExpressionBinary(firstToken, operand1, operator, operand2);
    }

    private boolean isExpressionOperand(IToken.Kind kind) {
        switch (kind) {
            case LT, GT, EQ, NEQ, LE, GE -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private void consume() throws LexicalException {
        token = lexer.next();
    }

    private Expression getExpression(IToken token) {
        switch (token.getKind()) {
            case NUM_LIT -> {
                return new ExpressionNumLit(token);
            }
            case BOOLEAN_LIT -> {
                return new ExpressionBooleanLit(token);
            }
            case STRING_LIT -> {
                return new ExpressionStringLit(token);
            }
            case IDENT -> {
                return new ExpressionIdent(token);
            }
            default -> throw new RuntimeException();
        }
    }
}
