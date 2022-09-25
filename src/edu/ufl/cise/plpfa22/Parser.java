package edu.ufl.cise.plpfa22;

import edu.ufl.cise.plpfa22.ast.*;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Parser implements IParser {

    private IToken firstToken;
    private final ILexer lexer;
    private final List<ConstDec> constDecs = new ArrayList<>();
    private final List<VarDec> varDecs = new ArrayList<>();
    private final List<ProcDec> procedureDecs = new ArrayList<>();

    public Parser(ILexer lexer) {
        this.lexer = lexer;
        try {
            firstToken = lexer.next();
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
         if (token.getKind() == IToken.Kind.BANG) {
             consume();
             Expression expression;
             try {
                 expression = getExpression(firstToken);
             } catch (OperationNotSupportedException e) {
                 throw new RuntimeException(e);
             }
             return new StatementOutput(token, expression);
         }
         else {
             return new StatementEmpty(token);
         }
    }

    private void consume() throws LexicalException {
        firstToken = lexer.next();
    }

    private Expression getExpression(IToken token) throws OperationNotSupportedException {
        switch (token.getKind()) {
            case NUM_LIT -> {
                return new ExpressionNumLit(token);
            }
            default -> throw new OperationNotSupportedException();
        }
    }
}
