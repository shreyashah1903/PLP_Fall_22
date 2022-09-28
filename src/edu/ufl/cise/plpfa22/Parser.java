package edu.ufl.cise.plpfa22;

import edu.ufl.cise.plpfa22.ast.*;

import javax.naming.OperationNotSupportedException;
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

    private Statement getStatement(IToken token) throws LexicalException {
        if (token.getKind() == IToken.Kind.EOF) {
            return new StatementEmpty(firstToken);
        }
        consume();
        switch (token.getKind()) {
            case BANG -> {
                Expression expression;
                try {
                    expression = getExpression(this.token);
                } catch (OperationNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                return new StatementOutput(token, expression);
            }
            case KW_VAR, KW_CONST, KW_PROCEDURE -> {
                varDecs.add(new VarDec(firstToken, this.token));
                return new StatementEmpty(token);
            }
            case KW_BEGIN -> {
                List<Statement> statements = new ArrayList<>();
                while (this.token.getKind() != IToken.Kind.EOF || this.token.getKind() != IToken.Kind.KW_END) {
                    statements.add(getStatement(this.token));
                }
                return new StatementBlock(firstToken, statements);
            }
            case QUESTION -> {
                return new StatementInput(firstToken, new Ident(this.token));
            }
            case STRING_LIT, BOOLEAN_LIT, NUM_LIT, IDENT -> {
                try {
                    return new StatementOutput(token, getExpression(token));
                } catch (OperationNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> {
                return new StatementEmpty(token);
            }
        }
    }

    private void consume() throws LexicalException {
        token = lexer.next();
    }

    private Expression getExpression(IToken token) throws OperationNotSupportedException {
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
            default -> throw new OperationNotSupportedException();
        }
    }
}