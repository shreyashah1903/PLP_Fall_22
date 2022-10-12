package edu.ufl.cise.plpfa22;

import edu.ufl.cise.plpfa22.ast.Declaration;

import java.util.HashMap;
import java.util.Stack;
import java.util.TreeSet;

public class SymbolTable {

    int currentScope = 0;
    Stack<Integer> stack = new Stack<>();
    HashMap<String, TreeSet<IdentInfo>> map = new HashMap<>();
    public SymbolTable() {
        this.stack.push(currentScope);
    }


    void enterScope() {
        stack.push(currentScope++);
    }


    void leaveScope() {
        stack.pop();
        currentScope--;
    }


    boolean insert(String ident, Declaration dec) {

        IdentInfo info = new IdentInfo(currentScope, dec);
        TreeSet<IdentInfo> identSet = new TreeSet<>(new customComparator());
        if (map.containsKey(ident)) {
            identSet = map.get(ident);
            if (identSet.contains(info)) {
                return false;
            }
        }
        identSet.add(info);
        map.put(ident, identSet);
        return true;
    }


    public Declaration lookup(String ident) {

        if(!map.containsKey(ident)) return null;

        for(IdentInfo info: map.get(ident)) {
            if (info.getScope() <= currentScope) {
                return info.getDeclaration();
            }
        }
        return null;
    }


}
