package edu.ufl.cise.plpfa22;

public class Token implements IToken {
    private final Kind kind;
    private final String input;
    private final int position;
    private final int len;
    private final SourceLocation sourceLocation;

    public Token(Kind kind, String input, int position, int len, SourceLocation sourceLocation) {
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
        return input.substring(position, len).toCharArray();
    }

    @Override
    public SourceLocation getSourceLocation() {
        return sourceLocation;
    }

    @Override
    public int getIntValue() {
        assert getKind() == Kind.NUM_LIT;
        return Integer.parseInt(input.substring(position, len));
    }

    @Override
    public boolean getBooleanValue() {
        assert getKind() == Kind.BOOLEAN_LIT;
        return Boolean.parseBoolean(input.substring(position, len));
    }

    @Override
    public String getStringValue() {
        assert getKind() == Kind.STRING_LIT;
        return input.substring(position, len);
    }
}
