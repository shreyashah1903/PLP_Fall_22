package edu.ufl.cise.plpfa22;

import java.util.*;

public class Lexer implements ILexer {
    private int startPos;
    private int tokenPos;

    private int lineNum = 1;
    private int colNum = 1;
    private State state = State.START;
    private final List<IToken> tokens = new ArrayList<>();
    public HashMap<String, IToken.Kind> reservedWords = new HashMap<>();

    private final char EOF = '\0';

    private final char[] chars;

    private final List<Character> allowedStringLit = Arrays.asList('b', 't', 'n', 'f', 'r', '\"', '\'', '\\');

    private static final boolean SHOW_OUTPUT = false;

    enum State {
        START,
        IN_IDENT,
        IN_NUM,
        IN_STRING,
        HAVE_EQ,
        HAVE_GT,
        HAVE_LT,
        HAVE_ASSIGN
    }

    public Lexer(String input) {
        int len = input.length();
        chars = Arrays.copyOf(input.toCharArray(), len + 1);
        chars[len] = EOF;
        addReservedWords();
        if (input.isEmpty()) {
            createToken(IToken.Kind.EOF, 0, 0, colNum);
        } else {
            handleInput(chars);
        }
    }

    private void printOutput(Object text) {
        if (SHOW_OUTPUT) {
            System.out.println(text);
        }
    }

    private void addReservedWords() {
        reservedWords.put("CONST", IToken.Kind.KW_CONST);
        reservedWords.put("VAR", IToken.Kind.KW_VAR);
        reservedWords.put("PROCEDURE", IToken.Kind.KW_PROCEDURE);
        reservedWords.put("CALL", IToken.Kind.KW_CALL);
        reservedWords.put("BEGIN", IToken.Kind.KW_BEGIN);
        reservedWords.put("END", IToken.Kind.KW_END);
        reservedWords.put("IF", IToken.Kind.KW_IF);
        reservedWords.put("THEN", IToken.Kind.KW_THEN);
        reservedWords.put("WHILE", IToken.Kind.KW_WHILE);
        reservedWords.put("DO", IToken.Kind.KW_DO);
    }


    private void handleInput(char[] chars) {
        while (startPos < chars.length) {
            char ch = chars[startPos];
            printOutput(ch);

            switch (state) {
                case START -> {
                    switch (ch) {
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
                            startPos++;
                            colNum++;
                            if(startPos < chars.length && chars[startPos] == '/') {
                                startPos++;
                                colNum++;
                                while(startPos < chars.length && (chars[startPos] != '\r' && chars[startPos] != '\n' && chars[startPos] != EOF)) {
                                    startPos++;
                                    colNum++;
                                }
                                if (chars[startPos] == '\r' && chars[startPos+1] == '\n') {
                                    colNum += 2;
                                    startPos +=2;
                                    lineNum++;
                                }
                            } else {
                                createToken(IToken.Kind.DIV, startPos-1, 1, colNum-1);
                            }
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
                            createToken(IToken.Kind.ERROR, startPos, 1, colNum, "Invalid character:"+ch);
                            colNum++;
                            startPos++;
                        }
                        case '=' -> {
                            createToken(IToken.Kind.EQ, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case '#' -> {
                            createToken(IToken.Kind.NEQ, startPos, 1, colNum);
                            startPos++;
                            colNum++;
                        }
                        case '<' -> {
                            state = State.HAVE_LT;
                            startPos++;
                            colNum++;
                        }
                        case '>' -> {
                            state = State.HAVE_GT;
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
                        case '\t', ' ' -> {
                            startPos++;
                            colNum++;
                        }
                        case '\r' -> {
                            startPos++;
                            colNum++;
                            if (startPos < chars.length && chars[startPos] == '\n') {
                                lineNum++;
                                startPos++;
                                colNum = 1;
                            }
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
                                if (ch == '0') createToken(IToken.Kind.NUM_LIT, startPos, 1, colNum);
                                else state = State.IN_NUM;
                                startPos++;
                                colNum++;
                            }
                            else {
                                createToken(IToken.Kind.ERROR, startPos, 1, colNum, "Invalid character:"+ch);
                                startPos++;
                                colNum++;
//                                throw new LexicalException("Invalid character.", lineNum, colNum);
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
                    try {
                        Integer.parseInt(String.valueOf(chars, startPos - numDigits, numDigits));
                    } catch (NumberFormatException e) {
                        createToken(IToken.Kind.ERROR, startPos - numDigits, numDigits, colNum, "Number format exception trying to parse: "+Arrays.toString(chars));
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
                    String token = String.copyValueOf(chars, startPos-len, len);
                    if (reservedWords.containsKey(token)) {
                        createToken(reservedWords.get(token), startPos - len, len, colNum - len);
                    }
                    else if (token.equals("TRUE") || token.equals("FALSE")) {
                        createToken(IToken.Kind.BOOLEAN_LIT, startPos - len, len, colNum - len);
                    }
                    else {
                        createToken(IToken.Kind.IDENT, startPos - len, len, colNum - len);
                    }
                    state = State.START;
                }
                case IN_STRING -> {
                    // TODO Put String handling in a new function for readability
                    int len = 1;
                    int line = lineNum;
                    int charsOnNewline = colNum;
                    while (startPos < chars.length) {
                       ch = chars[startPos];
                       if (ch == '\\') {
                           startPos++;
                           colNum++;
                           charsOnNewline++;
                           len++;
                           if (allowedStringLit.contains(chars[startPos])) {
                               len++;
                           }
                           else {
                               createToken(IToken.Kind.ERROR, startPos, len, colNum);
                           }
                           startPos++;
                           colNum++;
                           charsOnNewline++;
                       }
                       else if (ch == '"') {
                           startPos++;
                           colNum++;
                           len++;
                           charsOnNewline++;
                           state = State.START;
                           break;
                       }
                       else {
                           len++;
                           startPos++;
                           colNum++;
                           charsOnNewline++;
                           // \n within a "" counts as a new line and not \\n
                           if (ch == '\n') {
                               line++;
                               charsOnNewline = 1;
                           }
                       }
                    }
                    if (state != State.START) {
                        createToken(IToken.Kind.ERROR, startPos - len, len, colNum - len - 1, "Unterminated String.");
                    }
                    else {
                        createToken(IToken.Kind.STRING_LIT, startPos - len, len, colNum - len);
                    }
                    if (line - lineNum > 0) {
                        colNum = charsOnNewline;
                    }
                    lineNum = line;
                }
                case HAVE_EQ -> {
                    startPos++;
                    colNum++;
                    createToken(IToken.Kind.EQ, startPos - 2, 2, colNum - 2);
                }
                case HAVE_LT -> {
                    if (chars[startPos] == '=') {
                        createToken(IToken.Kind.LE, startPos - 1, 2, colNum - 1);
                        startPos++;
                        colNum++;
                    }
                    else {
                        createToken(IToken.Kind.LT, startPos - 1, 1, colNum - 1);
                    }
                    state = State.START;
                }
                case HAVE_GT -> {
                    if (chars[startPos] == '=') {
                        createToken(IToken.Kind.GE, startPos - 1, 2, colNum - 1);
                        startPos++;
                        colNum++;
                    }
                    else {
                        createToken(IToken.Kind.GT, startPos - 1, 1, colNum - 1);
                    }
                    state = State.START;
                }
                case HAVE_ASSIGN -> {
                    startPos++;
                    colNum++;
                    if (ch == '=') {
                        createToken(IToken.Kind.ASSIGN, startPos - 2, 2, colNum - 2);
                    }
                    else {
                        createToken(IToken.Kind.ERROR, startPos - 2, 2, colNum - 2);
                    }
                    state = State.START;
                }
            }
        }
    }

    private void createToken(IToken.Kind kind, int pos, int len, int col, String... errorMsg) {
        IToken token = new Token(kind, chars, pos, len, new IToken.SourceLocation(lineNum, col), errorMsg);
        String text = "kind = " + kind + ", pos = " + pos + ", len = " + len + ", col = " + col + " input = " + String.valueOf(chars, pos, len)
                + ", lineNum = " + lineNum + ", col = " + col;
        printOutput(text);
        tokens.add(token);
    }

    @Override
    public IToken next() throws LexicalException {
        Token eofToken = getEOFToken();
        if (eofToken != null) {
            return eofToken;
        }

        Token token = (Token) tokens.get(tokenPos++);
        if (token.getKind() == IToken.Kind.ERROR) {
            throw new LexicalException(String.valueOf(token.getText()));
        }
        return token;
    }

    @Override
    public IToken peek() throws LexicalException {
        Token eofToken = getEOFToken();
        if (eofToken != null) {
            return eofToken;
        }

        Token token = (Token) tokens.get(tokenPos);
        if (token.getKind() == IToken.Kind.ERROR) {
            throw new LexicalException(String.valueOf(token.getText()));
        }
        return token;
    }

    private Token getEOFToken() {
        if (tokenPos >= tokens.size()) {
            return new Token(IToken.Kind.EOF, chars, startPos, 0, new IToken.SourceLocation(lineNum, colNum));
        }
        return null;
    }
}
