package edu.ufl.cise.plpfa22;

import java.util.Stack;

public class Lexer implements ILexer {
    private int startPos;
    private State state = State.START;
    private Stack<IToken> tokens = new Stack<>();

    enum State {
        START
    }

    public Lexer(String input) {
        char[] chars = input.toCharArray();
        handleInput(chars);

    }

    private void handleInput(char[] chars) {
        int pos = 0;
        if (chars.length == 0) {
            IToken token = new Token(IToken.Kind.EOF);
            addToken(token);
            return;
        }
        while (true) {
            char ch = chars[pos];

            switch (state) {
                case START -> {
                    startPos = pos;

                }
            }
        }
    }

    private void addToken(IToken token) {
        tokens.push(token);
    }

    @Override
    public IToken next() throws LexicalException {
        return tokens.pop();
    }

    @Override
    public IToken peek() throws LexicalException {
        return tokens.peek();
    }
}
