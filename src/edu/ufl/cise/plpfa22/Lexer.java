package edu.ufl.cise.plpfa22;

import java.util.*;

public class Lexer implements ILexer {
    private int startPos;
    private int tokenPos = 0;

    private int lineNum = 1;
    private int colNum = 1;
    private State state = State.START;
    private List<IToken> tokens = new ArrayList<>();

    private final char EOF = '\0';

    private final char[] chars;


    enum State {
        START,
        IN_IDENT,
        IN_GT_OR_LT,
        HAVE_ZERO,
        HAVE_DOT,
        IN_FLOAT,
        IN_NUM,
        IN_STRING,
        HAVE_EQ,
        HAVE_MINUS
    }

    public Lexer(String input) {
        int len = input.length();
        chars = Arrays.copyOf(input.toCharArray(), len + 1);
        chars[len] = EOF;
        if (input.isEmpty()) {
            createToken(IToken.Kind.EOF, 0, 0, colNum);
        } else {
            try {
                handleInput(chars);
            } catch (LexicalException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private void handleInput(char[] chars) throws LexicalException {
        while (startPos < chars.length) {
            char ch = chars[startPos];
            System.out.println(ch);

            switch (state) {
                case START -> {
                    switch (ch) {
//                        case ' ', '\t', '\n', '\r' -> {
//                            startPos++;
//                            colNum++;
//                        }
                        case '.' -> {
                            createToken(IToken.Kind.DOT, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case ',' -> {
                            createToken(IToken.Kind.COMMA, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case ';' -> {
                            createToken(IToken.Kind.SEMI, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case '(' -> {
                            createToken(IToken.Kind.LPAREN, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case ')' -> {
                            createToken(IToken.Kind.RPAREN, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case '+' -> {
                            createToken(IToken.Kind.PLUS, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case '-' -> {
                            createToken(IToken.Kind.MINUS, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case '*' -> {
                            createToken(IToken.Kind.TIMES, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case '/' -> {
                            createToken(IToken.Kind.DIV, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case '%' -> {
                            createToken(IToken.Kind.MOD, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case '?' -> {
                            createToken(IToken.Kind.QUESTION, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case '!' -> {
                            createToken(IToken.Kind.BANG, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case ':' -> {
                            startPos++;
                            colNum++;
                            if (chars[startPos] == '=') {
                                createToken(IToken.Kind.ASSIGN, startPos - 1, 2, colNum - 1);
                                startPos++;
                                colNum++;
                            } else {
                                createToken(IToken.Kind.ERROR, startPos-1, 1, colNum-1);
                            }
                        }
                        case '@' -> {
                            createToken(IToken.Kind.ERROR, startPos, 1, colNum);
                            colNum++;
                            startPos++;
                        }
                        case '=' -> {
                              createToken(IToken.Kind.EQ, startPos, 1, colNum);
//                            state = State.HAVE_EQ;
//
//                            // TODO catch IOB Exception
//                            if (chars[startPos + 1] == '=') {
//                                createToken(IToken.Kind.EQ);
//                            }
//                            else {
//                                createToken(IToken.Kind.ASSIGN);
//                            }
//                            startPos++;
                            startPos++;
                            colNum++;
                        }
                        case '#' -> {
                            createToken(IToken.Kind.NEQ, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case '<', '>' -> {
                            state = State.IN_GT_OR_LT;
                            startPos++;
                            colNum++;
                        }
                        case '"' -> {
                            state = State.IN_STRING;
                            startPos++;
                            colNum++;
                        }
                        case '\n' -> {
                            lineNum++;
                            startPos++;
                            colNum = 1;
                        }
                        case ' ' -> {
                            startPos++;
                            colNum++;
                        }
                        case EOF -> {
                            createToken(IToken.Kind.EOF, startPos, 0, colNum);
                            startPos++;
                            colNum++;
                        }
                        default -> {
                            if (Character.isJavaIdentifierStart(ch)) {
                                state = State.IN_IDENT;
                                startPos++;
                                colNum++;
                            } else if (Character.isDigit(ch)) {
                                state = State.IN_NUM;
                                startPos++;
                                colNum++;
                            }
                            else {
                                createToken(IToken.Kind.ERROR, startPos, 1, colNum);
                                throw new LexicalException("Invalid character.", lineNum, colNum);
                            }
                        }
                    }

                }

                case IN_NUM -> {
                    int numDigits = 1;
                    while (Character.isDigit(chars[startPos])) {
                        startPos++;
                        colNum++;
                        numDigits++;
                    }
                    System.out.println(numDigits);
                    try {
                        Integer.parseInt(String.valueOf(chars, startPos - numDigits, numDigits));
                    } catch (NumberFormatException e) {
                        createToken(IToken.Kind.ERROR, startPos - numDigits, numDigits, colNum);
                        throw new LexicalException("Number format exception.", lineNum, colNum - numDigits);
                    }
                    createToken(IToken.Kind.NUM_LIT, startPos - numDigits, numDigits, colNum - numDigits);
                    state = State.START;
                }
                case IN_IDENT -> {
                    int len = 1;
                    while (startPos < chars.length && (Character.isJavaIdentifierStart(chars[startPos]) || Character.isDigit(chars[startPos]))) {
                        startPos++;
                        len++;
                        colNum++;
                    }
                    createToken(IToken.Kind.IDENT, startPos - len, len, colNum - len);
                    state = State.START;
                }
                case IN_GT_OR_LT -> {
                    int len = 1;
                    if (startPos < chars.length && chars[startPos] == '=') {
                        len++;
                        if (chars[startPos-1] == '>') createToken(IToken.Kind.GE, startPos-1, len, colNum-1);
                        if (chars[startPos-1] == '<') createToken(IToken.Kind.LE, startPos-1, len, colNum-1);
                        startPos++;
                        colNum++;
                    } else {
                        if (chars[startPos - 1] == '>') createToken(IToken.Kind.GT, startPos - 1, len, colNum-1);
                        if (chars[startPos - 1] == '<') createToken(IToken.Kind.LT, startPos - 1, len, colNum-1);
                        state = state.START;
                    }
                }
                case IN_STRING -> {
                    startPos++;
                }
            }
        }
    }

    private void createToken(IToken.Kind kind, int pos, int len, int col) {
        IToken token = new Token(kind, chars, pos, len, new IToken.SourceLocation(lineNum, col));
        System.out.println("kind = " + kind + ", pos = " + pos + ", len = " + len + ", col = " + col + " input:"+String.valueOf(chars, pos, len));

        tokens.add(token);
    }

    @Override
    public IToken next() throws LexicalException {
        if (tokenPos >= tokens.size()) {
            throw new IndexOutOfBoundsException();
        }

        Token token = (Token) tokens.get(tokenPos++);
        if (token.getKind() == IToken.Kind.ERROR) {
            throw new LexicalException("Token "+ Arrays.toString(token.getText()) + " not allowed");
        }
        return token;
    }

    @Override
    public IToken peek() throws LexicalException {
        if (tokenPos >= tokens.size()) {
            throw new IndexOutOfBoundsException();
        }

        Token token = (Token) tokens.get(tokenPos);
        if (token.getKind() == IToken.Kind.ERROR) {
            throw new LexicalException("Token "+ Arrays.toString(token.getText()) + " not allowed");
        }
        return token;
    }
}
