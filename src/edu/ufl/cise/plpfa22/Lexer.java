package edu.ufl.cise.plpfa22;

import java.util.*;

public class Lexer implements ILexer {
    private int startPos;
    private int tokenPos;

    private int lineNum = 1;
    private int colNum = 1;
    private State state = State.START;
    private List<IToken> tokens = new ArrayList<>();

    private final char EOF = '\0';

    private final char[] chars;


    enum State {
        START,
        IN_IDENT,
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
            createToken(IToken.Kind.EOF);
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
                        case '+' -> {
                            createToken(IToken.Kind.PLUS);
                            startPos++;
                            colNum++;
                        }
                        case '-' -> {
                            createToken(IToken.Kind.MINUS);
                            startPos++;
                            colNum++;
                        }
                        case '?' -> {
                            createToken(IToken.Kind.QUESTION);
                            startPos++;
                            colNum++;
                        }

                        case '=' -> {
                            state = State.HAVE_EQ;
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
                                switch (ch) {
                                    case '"' -> {
                                        state = State.IN_STRING;
                                        startPos++;
                                        colNum++;
                                    }
                                    case '\n' -> {
                                        lineNum++;
                                        colNum = 1;
                                    }
                                    case EOF -> {
                                        createToken(IToken.Kind.EOF);
                                        startPos++;
                                        colNum++;
                                    }
                                }
                                startPos++;
                            }
                        }
                    }

                }

                case IN_NUM -> {
                    int numDigits = 1;
                    while (Character.isDigit(chars[startPos])) {
                        startPos++;
                        colNum++;
                    }
                    createToken(IToken.Kind.NUM_LIT);
                    try {
                        Integer.parseInt(String.valueOf(chars, startPos - numDigits, numDigits));
                    } catch (NumberFormatException e) {
                        throw new LexicalException("Number format exception at", lineNum, colNum - numDigits);
                    }
                    state = State.START;
                }
                case IN_IDENT -> {
                    int len = 1;
                    while (startPos < chars.length && (Character.isLetterOrDigit(chars[startPos]) || chars[startPos] == '$' || chars[startPos] == '_')) {
                        startPos++;
                        len++;
                    }
                    createToken(IToken.Kind.IDENT);
                    state = State.START;
                }

            }
        }
    }

    private void createToken(IToken.Kind kind) {
        //TODO: Pass input, position, length
        IToken token = new Token(kind, (new String()).toCharArray(), 0, 0, new IToken.SourceLocation(lineNum, colNum));
        tokens.add(token);
    }

    @Override
    public IToken next() throws LexicalException {
        return tokens.remove(tokenPos);
    }

    @Override
    public IToken peek() throws LexicalException {
        return tokens.get(tokenPos);
    }
}
