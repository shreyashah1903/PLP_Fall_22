package edu.ufl.cise.plpfa22;

import edu.ufl.cise.plpfa22.ast.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser implements IParser {

    private final IToken firstToken;

    private IToken token;

    private final ILexer lexer;

    private static final boolean SHOW_OUTPUT = false;

    public Parser(ILexer lexer) throws LexicalException {
        this.lexer = lexer;
        try {
            firstToken = lexer.next();
            token = firstToken;
        } catch (LexicalException e) {
            throw new LexicalException();
        }
    }

    private void printOutput(Object text) {
        if (SHOW_OUTPUT) {
            System.out.println(text);
        }
    }

    @Override
    public ASTNode parse() throws PLPException {
        return handleProgram(token);
    }

    private Program handleProgram(IToken startToken) throws LexicalException, SyntaxException {
        Block block = handleBlock(startToken);
        match(IToken.Kind.DOT);
        consume();
        match(IToken.Kind.EOF);
        consume();
        return new Program(startToken, block);
    }

    private Block handleBlock(IToken startToken) throws LexicalException, SyntaxException {
        List<ConstDec> constDecs = new ArrayList<>();
        List<VarDec> varDecs = new ArrayList<>();
        List<ProcDec> procedureDecs = new ArrayList<>();
        Statement statement = null;
        while (token.getKind() != IToken.Kind.DOT && token.getKind() != IToken.Kind.EOF) {
            switch (token.getKind()) {
                case KW_CONST -> {
                    while(token.getKind() == IToken.Kind.KW_CONST) {
                        consume();
                        IToken ident;
                        do {
                            if (token.getKind() == IToken.Kind.IDENT) {
                                ident = token;
                                consume();
                                match(IToken.Kind.EQ);
                                consume();
                                switch (token.getKind()) {
                                    case NUM_LIT -> constDecs.add(new ConstDec(firstToken, ident, token.getIntValue()));
                                    case STRING_LIT ->
                                            constDecs.add(new ConstDec(firstToken, ident, token.getStringValue()));
                                    case BOOLEAN_LIT ->
                                            constDecs.add(new ConstDec(firstToken, ident, token.getBooleanValue()));
                                    default -> throw new SyntaxException();
                                }
                                consume();
                                if (token.getKind() == IToken.Kind.COMMA) consume();
                            } else {
                                throw new SyntaxException();
                            }
                        } while (token.getKind() != IToken.Kind.SEMI);
                        consume();
                    }
                }
                case KW_VAR -> {
                    while(token.getKind() == IToken.Kind.KW_VAR) {
                        consume();
                        if (token.getKind() != IToken.Kind.IDENT) {
                            throwSyntaxException("Expected IDENT after VAR", token);
                        }
                        while (token.getKind() != IToken.Kind.SEMI) {
                            IToken.Kind kind = token.getKind();

                            if (kind == IToken.Kind.IDENT) {
                                varDecs.add(new VarDec(firstToken, token));
                                consume();
                            }

                            if (token.getKind() != IToken.Kind.SEMI && token.getKind() != IToken.Kind.COMMA) {
                                throwSyntaxException("Expected COMMA, SEMI after VAR IDENT declaration", token);
                            }
                            if (token.getKind() != IToken.Kind.SEMI) {
                                consume();
                            }
                        }
                        consume();
                    }
                }
                case KW_PROCEDURE -> {
                    while(token.getKind() == IToken.Kind.KW_PROCEDURE) {
                        consume();
                        match(IToken.Kind.IDENT);
                        if (token.getKind() != IToken.Kind.DOT && token.getKind() != IToken.Kind.EOF) {
                            IToken ident = token;
                            consume();
                            match(IToken.Kind.SEMI);
                            consume();
                            Block block = handleBlock(token);
                            match(IToken.Kind.SEMI);
                            consume();
                            procedureDecs.add(new ProcDec(firstToken, ident, block));
                        }
                    }
                }
                default -> statement = handleStatement(startToken);
            }
            if (statement != null) {
                break;
            }
        }
        if (statement == null) {
            statement = new StatementEmpty(startToken);
        }
        return new Block(firstToken, constDecs, varDecs, procedureDecs, statement);
    }

    private Statement handleStatement(IToken startToken) throws LexicalException, SyntaxException {
        Statement statement;
        switch (token.getKind()) {
            case BANG -> {
                consume();
                Expression expression = handleExpression(startToken);
                statement = new StatementOutput(startToken, expression);
            }
            case KW_IF -> {
                consume();
                Expression expression = handleExpression(startToken);
                match(IToken.Kind.KW_THEN);
                consume();
                Statement ifStatement = handleStatement(startToken);
                statement = new StatementIf(startToken, expression, ifStatement);
            }
            case KW_WHILE -> {
                IToken token1 = token;
                consume();
                Expression expression = handleExpression(token1);
                match(IToken.Kind.KW_DO);
                token1 = token;
                consume();
                Statement whileStatement = handleStatement(token1);
                statement = new StatementWhile(token1, expression, whileStatement);
            }
            case KW_BEGIN -> {
                List<Statement> statements = new ArrayList<>();
                consume();
                while (token.getKind() != IToken.Kind.KW_END) {
                    Statement statement1 = handleStatement(token);
                    if (token.getKind() == IToken.Kind.SEMI) consume();
                    statements.add(statement1);
                }
                match(IToken.Kind.KW_END);
                consume();
                statement = new StatementBlock(firstToken, statements);
            }
            case QUESTION -> {
                consume();
                match(IToken.Kind.IDENT);
                statement = new StatementInput(firstToken, new Ident(token));
                consume();
            }
            case KW_CALL -> {
                consume();
                match(IToken.Kind.IDENT);
                statement = new StatementCall(firstToken, new Ident(token));
                consume();
            }
            case IDENT -> {
                IToken ident = token;
                consume();
                match(IToken.Kind.ASSIGN);
                consume();
                statement = new StatementAssign(startToken, new Ident(ident), handleExpression(startToken));
            }
            default -> statement = new StatementEmpty(startToken);
        }
        return statement;
    }


    private static void throwSyntaxException(String msg, IToken itoken) throws SyntaxException {
        throw new SyntaxException(msg + " Found token:" + itoken.getKind(),
                itoken.getSourceLocation().line(), itoken.getSourceLocation().line());
    }

    private Expression handlePrimaryExpression(IToken itoken) throws LexicalException, SyntaxException {
        Expression expression;
        printOutput("itoken = " + token.getKind());
        switch (token.getKind()) {
            case IDENT -> expression = new ExpressionIdent(token);
            case NUM_LIT -> expression = new ExpressionNumLit(token);
            case STRING_LIT -> expression = new ExpressionStringLit(token);
            case BOOLEAN_LIT -> expression = new ExpressionBooleanLit(token);
            case LPAREN -> {
                consume(); // LPAREN
                expression = handleExpression(itoken);
                match(IToken.Kind.RPAREN);
            }
            default -> throw new SyntaxException();
        }
        consume();
        return expression;
    }

    private Expression handleMultiplicativeExpression(IToken itoken) throws LexicalException, SyntaxException {
        Expression operand1 = handlePrimaryExpression(token);
        Expression operand2;
        IToken operator;
        while (token.getKind() == IToken.Kind.TIMES || token.getKind() == IToken.Kind.DIV
                || token.getKind() == IToken.Kind.MOD) {
            operator = token;
            switch (token.getKind()) {
                case TIMES, DIV, MOD -> consume();
            }
            operand2 = handlePrimaryExpression(token);
            operand1 = new ExpressionBinary(firstToken, operand1, operator, operand2);
        }
        return operand1;
    }


    private Expression handleAdditiveExpression(IToken itoken) throws LexicalException, SyntaxException {
        Expression operand1 = handleMultiplicativeExpression(token);
        Expression operand2;
        IToken operator;
        while (token.getKind() == IToken.Kind.PLUS || token.getKind() == IToken.Kind.MINUS) {
            operator = token;
            switch (token.getKind()) {
                case PLUS, MINUS -> consume();
            }
            operand2 = handleMultiplicativeExpression(token);
            operand1 = new ExpressionBinary(firstToken, operand1, operator, operand2);
        }
        return operand1;
    }

    private Expression handleExpression(IToken startToken) throws LexicalException, SyntaxException {
        Expression operand1 = handleAdditiveExpression(token);
        Expression operand2;
        IToken operator;
        IToken.Kind kind = token.getKind();
        printOutput("token kind= " + kind + " value: " + String.valueOf(token.getText()) + "" +
                " startToken kind:" + startToken.getKind() + " startTokenValue:" + Arrays.toString(startToken.getText()));
        if (startToken.getKind() != IToken.Kind.KW_IF && startToken.getKind() != IToken.Kind.KW_WHILE) {
            if (isInvalidExprCondition(kind))
                throwSyntaxException("Invalid expression", token);
        }
        while (isExpressionOperand(token.getKind())) {
            operator = token;
            if (isExpressionOperand(token.getKind())) consume();
            operand2 = handleAdditiveExpression(token);
            operand1 = new ExpressionBinary(firstToken, operand1, operator, operand2);
        }
        return operand1;
    }

    private boolean isInvalidExprCondition(IToken.Kind kind) {
        return !isValidOperator(kind) && kind != IToken.Kind.DOT && kind != IToken.Kind.RPAREN && kind != IToken.Kind.KW_END;
    }

    private boolean isValidOperator(IToken.Kind kind) {
        return isExpressionOperand(kind) || kind == IToken.Kind.TIMES || kind == IToken.Kind.MOD || kind == IToken.Kind.DIV ||
                kind == IToken.Kind.PLUS || kind == IToken.Kind.MINUS || kind == IToken.Kind.SEMI;
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

    private void match(IToken.Kind kind) throws SyntaxException {
        if (token.getKind() != kind) {
            throwSyntaxException("Expected " + kind, token);
        }
    }

    private Expression getExpression(IToken token) throws SyntaxException {
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
            default -> throw new SyntaxException();
        }
    }
}
