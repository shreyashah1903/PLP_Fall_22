package edu.ufl.cise.plpfa22;

public class Token implements IToken {
    private final Kind kind;
    private final char[] input;
    private final int position;
    private final int len;
    private final SourceLocation sourceLocation;

    public Token(Kind kind, char[] input, int position, int len, SourceLocation sourceLocation) {
        this.kind = kind;
        this.input = input;
        this.position = position;
        this.len = len;
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    @Override
    public char[] getText() {
        if (getKind() == Kind.STRING_LIT) {
            return getStringValue().toCharArray();
        }
        else return String.copyValueOf(input, position, len).toCharArray();
    }

    @Override
    public SourceLocation getSourceLocation() {
        return sourceLocation;
    }

    @Override
    public int getIntValue() {
        assert getKind() == Kind.NUM_LIT;
        return Integer.parseInt(String.copyValueOf(input, position, len));
    }

    @Override
    public boolean getBooleanValue() {
        assert getKind() == Kind.BOOLEAN_LIT;
        return Boolean.parseBoolean(String.copyValueOf(input, position, len));
    }

    @Override
    public String getStringValue() {
        assert getKind() == Kind.STRING_LIT;

        StringBuilder builder = new StringBuilder();
        for(int i = position + 1; i < position + len - 1; i++) { // omit enclosing "
            char c = input[i];

            if (c == '\\') {
                i++;
                c = input[i];
                switch (c) {
                    case 'b' -> builder.append('\b');
                    case 't' -> builder.append('\t');
                    case 'n' -> builder.append('\n');
                    case 'f' -> builder.append('\f');
                    case 'r' -> builder.append('\r');
                    case '\"' -> builder.append('\"');
                    case '\'' -> builder.append('\'');
                    case '\\' -> builder.append('\\');
                    default -> {
                        assert false;
                    }
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
