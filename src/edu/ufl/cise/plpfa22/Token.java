package edu.ufl.cise.plpfa22;

public class Token implements IToken {
    private final Kind kind;
    private final SourceLocation sourceLocation;

    public Token(Kind kind, SourceLocation sourceLocation) {
        this.kind = kind;
        this.sourceLocation = sourceLocation;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    @Override
    public char[] getText() {
        return new char[0];
    }

    @Override
    public SourceLocation getSourceLocation() {
        return sourceLocation;
    }

    @Override
    public int getIntValue() {
        return 0;
    }

    @Override
    public boolean getBooleanValue() {
        return false;
    }

    @Override
    public String getStringValue() {
        return null;
    }
}
