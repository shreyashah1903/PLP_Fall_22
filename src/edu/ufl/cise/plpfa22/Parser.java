package edu.ufl.cise.plpfa22;

import edu.ufl.cise.plpfa22.ast.*;

import java.util.ArrayList;
import java.util.List;

public class Parser implements IParser {

    private IToken firstToken;

    private IToken token;

    private final ILexer lexer;
    private List<ConstDec> constDecs = new ArrayList<>();
    private List<VarDec> varDecs = new ArrayList<>();
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
//        Block block = new Block(firstToken, constDecs, varDecs, procedureDecs, handleStatement(firstToken));
//        return new Program(firstToken, block);
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
        Statement statement = new StatementEmpty(startToken);

        switch (token.getKind()) {
            case KW_CONST -> {
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
            case KW_VAR -> {
                while (this.token.getKind() != IToken.Kind.SEMI) {
                    if (this.token.getKind() == IToken.Kind.IDENT) {
                        varDecs.add(new VarDec(firstToken, this.token));
                    }
                    consume();
                }
                consume();
            }
            case KW_PROCEDURE -> {
//                procedureDecs.add(new ProcDec(firstToken, this.token));
                consume();
//                    if (token.getKind() != IToken.Kind.SEMI) {
//                        throwSyntaxException(token);
//                    }
                if (token.getKind() != IToken.Kind.DOT && token.getKind() != IToken.Kind.EOF) {
//                        if (token.getKind() != IToken.Kind.IDENT) {
//                            throwSyntaxException(startToken);
//                        }
                    constDecs = new ArrayList<>();
                    varDecs = new ArrayList<>();
                    //procedureDecs = new ArrayList<>();

                    IToken ident = token;
                    // Consuming ident
                    consume();
                    //Consuming semi
                    if (token.getKind() != IToken.Kind.SEMI) {
                        throwSyntaxException(token);
                    }
                    consume();
//                    startToken = token;
                    // Consuming any BEGIN keyword
                    //consume();
                    Block block = new Block(token, constDecs, varDecs, new ArrayList<>(), handleStatement(token));
                    System.out.println("Vardec size inside procedure:" + varDecs.size() + " proc size:" + procedureDecs.size());
                    procedureDecs.add(new ProcDec(firstToken, ident, block));
//                        consume();
                }
//                return new StatementEmpty(startToken);
            }
            default -> {
                statement = handleStatement(firstToken);
            }
        }
        Block block = new Block(firstToken, constDecs, varDecs, procedureDecs, statement);
        return block;
    }

    private Statement handleStatement(IToken startToken) throws LexicalException, SyntaxException {
        Statement statement;
        switch (token.getKind()) {
            case BANG -> {
                consume();
                Expression expression = handleExpression(token);
                statement =  new StatementOutput(startToken, expression);
            }
            case KW_IF, KW_WHILE -> {
                consume();
                statement =  handleIfStatement(startToken);
            }
            case KW_BEGIN -> {
                List<Statement> statements =  new ArrayList<>();
                consume();
                while (token.getKind() != IToken.Kind.KW_END) {
                    Statement statement1 = handleStatement(token);
                    if(token.getKind() == IToken.Kind.SEMI) consume();
                    statements.add(statement1);
                }
                match(IToken.Kind.KW_END);
                consume();
                statement =  new StatementBlock(firstToken, statements);
            }
            case QUESTION -> {
                consume();
                match(IToken.Kind.IDENT);
                statement =  new StatementInput(firstToken, new Ident(token));
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
                statement = new StatementAssign(startToken, new Ident(ident), handleExpression(token));
            }
            case STRING_LIT, BOOLEAN_LIT, NUM_LIT -> {
                statement =  new StatementOutput(startToken, getExpression(startToken));
            }
            default -> {
                statement =  new StatementEmpty(startToken);
            }
        }
        return statement;
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
                    match(IToken.Kind.IDENT);
                    return new StatementCall(firstToken, new Ident(this.token));
                }
                case KW_IF, KW_WHILE -> {
                    consume();
                    return handleIfStatement(startToken);
                }
                default -> {
                    return new StatementEmpty(startToken);
                }
            }
        }
        return new StatementEmpty(startToken);
    }

    private Statement handleIfStatement(IToken startToken) throws LexicalException, SyntaxException {
        List<IToken> tokens = new ArrayList<>();
        Statement statement;
//        while (token.getKind() != IToken.Kind.SEMI && ((startToken.getKind() == IToken.Kind.KW_IF &&
//                token.getKind() != IToken.Kind.KW_THEN) ||(startToken.getKind() == IToken.Kind.KW_WHILE &&
//                token.getKind() != IToken.Kind.KW_DO))) {
//            if (!isConditionalOperator(token)) {
//                tokens.add(token);
//            }
//            consume();
//        }
        IToken token1 = token;
        Expression expression = handleExpression(startToken);
//        while ((token1.getKind() != IToken.Kind.KW_THEN) &&(token1.getKind() != IToken.Kind.KW_DO)) {
//            if (!isConditionalOperator(token1)) {
//                tokens.add(token1);
//            }
//           token1 = lexer.next();
//        }
//        Expression expression = handleExpression(tokens.get(0));
//        consume();

        // For THEN/DO
        consume();
        statement = handleStatement(token);
//        Ident ident = new Ident(tokens.get(0));
//        ident.setDec(new VarDec(firstToken, tokens.get(0)));
//        Expression expression = new ExpressionIdent(tokens.get(0));
        //consume();
        if (startToken.getKind() == IToken.Kind.KW_IF) {
            System.out.println("Creating IF statement this.token:" + this.token.getKind());
            return new StatementIf(firstToken, expression, statement);
        } else {
            System.out.println("Creating WHILE statement");
            return new StatementWhile(firstToken, expression, statement);
        }
    }

    private boolean isConditionalOperator(IToken token) {
        IToken.Kind kind = token.getKind();
        return kind == IToken.Kind.EQ || kind == IToken.Kind.LT || kind == IToken.Kind.GT
                || kind == IToken.Kind.LE || kind == IToken.Kind.GE;
    }

    private static void throwSyntaxException(IToken token) throws SyntaxException {
        throw new SyntaxException("Expected token IDENT inside BEGIN, Found " + token.getKind(),
                token.getSourceLocation().line(), token.getSourceLocation().line());
    }

    private Expression handlePrimaryExpression(IToken token) throws LexicalException, SyntaxException {
        Expression expression;
        switch (token.getKind()) {
            case IDENT -> expression = new ExpressionIdent(token);
            case NUM_LIT -> expression = new ExpressionNumLit(token);
            case STRING_LIT -> expression = new ExpressionStringLit(token);
            case BOOLEAN_LIT -> expression = new ExpressionBooleanLit(token);
            case LPAREN -> {
                consume(); // LPAREN
                expression = handleExpression(this.token);
                match(IToken.Kind.RPAREN);
            }
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
        return operand1;
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
        return operand1;
    }

    private Expression handleExpression(IToken token) throws LexicalException, SyntaxException {
        Expression operand1 = handleAdditiveExpression(this.token);
        Expression operand2 = null;
        IToken operator = null;
        IToken.Kind kind = this.token.getKind();
        System.out.println("token kind= " + kind + " " + String.valueOf(this.token.getText()));
        if (!isValidOperator(kind) && kind != IToken.Kind.DOT && kind != IToken.Kind.RPAREN && kind != IToken.Kind.KW_END)
            throw new SyntaxException();
        while (isExpressionOperand(this.token.getKind())) {
            operator = this.token;
            if (isExpressionOperand(this.token.getKind())) consume();
            operand2 = handleAdditiveExpression(this.token);
            operand1 = new ExpressionBinary(firstToken, operand1, operator, operand2);
        }
        //if(this.token.getKind() == IToken.Kind.SEMI) consume();
        return operand1;
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
        if (this.token.getKind() != kind) throw new SyntaxException();
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
