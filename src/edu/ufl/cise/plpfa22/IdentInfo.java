package edu.ufl.cise.plpfa22;

import edu.ufl.cise.plpfa22.ast.Declaration;
import edu.ufl.cise.plpfa22.ast.Ident;

import java.util.Comparator;

public class IdentInfo {
    int scope;
    Declaration declaration;

    public IdentInfo(int scope, Declaration declaration) {
        this.scope = scope;
        this.declaration = declaration;
    }

    public int getScope() {
        return scope;
    }

    public Declaration getDeclaration() {
        return declaration;
    }
}

class customComparator implements Comparator<IdentInfo>
{
    public int compare(IdentInfo s1, IdentInfo s2)
    {
        return s2.getScope()-s1.getScope();
    }
}
