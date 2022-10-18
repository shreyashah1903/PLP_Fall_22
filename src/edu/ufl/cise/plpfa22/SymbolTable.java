package edu.ufl.cise.plpfa22;

import edu.ufl.cise.plpfa22.ast.Declaration;

import java.util.*;

public class SymbolTable {

    private int currentScope = 0;
    private Stack<Integer> stack = new Stack<>();
    private HashMap<String, ArrayList<IdentInfo>> map = new HashMap<>();

    public SymbolTable() {
        stack.push(currentScope);
    }


    public void enterScope() {
        currentScope++;
        stack.push(currentScope);
        LogHelper.printOutput("Enter scope currentscope:" + currentScope);
        LogHelper.printOutput("Map size:" + map.size());
    }

    public void leaveScope() {
        stack.pop();
        currentScope = stack.peek();
        LogHelper.printOutput("Leave scope currentscope:" + currentScope);
    }


    public int getCurrentScope() {
        return currentScope;
    }

    public boolean insert(String ident, Declaration dec) {

        IdentInfo info = new IdentInfo(currentScope, dec);
        ArrayList<IdentInfo> list = new ArrayList<>();

        if (map.containsKey(ident)) {
            list = map.get(ident);
            for (IdentInfo identInfo : list) {
                if (identInfo.getScope() == currentScope) {
                    LogHelper.printOutput("insert:identinfo:" + Arrays.toString(identInfo.getDeclaration().getFirstToken().getText()));
                    return false;
                }
            }
        }
        list.add(info);
        map.put(ident, list);
        return true;
    }


    public Declaration lookup(String ident) {

        if (!map.containsKey(ident)) {
            return null;
        }
        ArrayList<IdentInfo> list = map.get(ident);
        for (int i = list.size() - 1; i >= 0; i--) {
            if (stack.contains(list.get(i).getScope())) {
                return list.get(i).getDeclaration();
            }
        }
        return null;
    }

    public void clearProcVariables() {
        for (Map.Entry<String, ArrayList<IdentInfo>> entry : map.entrySet()) {
            ArrayList<IdentInfo> list = entry.getValue();

            Iterator<IdentInfo> iterator = list.iterator();
            while (iterator.hasNext()) {
                IdentInfo identInfo = iterator.next();
                if (identInfo.getScope() == currentScope) {
                    iterator.remove();
                }
            }
        }
    }
}
