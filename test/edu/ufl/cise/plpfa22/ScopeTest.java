package edu.ufl.cise.plpfa22;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.ufl.cise.plpfa22.ast.ASTNode;
import edu.ufl.cise.plpfa22.ast.ASTVisitor;
import edu.ufl.cise.plpfa22.ast.Block;
import edu.ufl.cise.plpfa22.ast.ConstDec;
import edu.ufl.cise.plpfa22.ast.Declaration;
import edu.ufl.cise.plpfa22.ast.Expression;
import edu.ufl.cise.plpfa22.ast.ExpressionIdent;
import edu.ufl.cise.plpfa22.ast.Ident;
import edu.ufl.cise.plpfa22.ast.ProcDec;
import edu.ufl.cise.plpfa22.ast.Program;
import edu.ufl.cise.plpfa22.ast.Statement;
import edu.ufl.cise.plpfa22.ast.StatementBlock;
import edu.ufl.cise.plpfa22.ast.StatementCall;
import edu.ufl.cise.plpfa22.ast.StatementEmpty;
import edu.ufl.cise.plpfa22.ast.StatementInput;
import edu.ufl.cise.plpfa22.ast.StatementOutput;
import edu.ufl.cise.plpfa22.ast.VarDec;

class ScopeTest {
	
	ASTNode getDecoratedAST(String input) throws PLPException {
		IParser parser = CompilerComponentFactory.getParser(CompilerComponentFactory.getLexer(input));
		ASTNode ast = parser.parse();
		ASTVisitor scopes = CompilerComponentFactory.getScopeVisitor();
		CompilerComponentFactory.getScopeVisitor();
		ast.visit(scopes, null);
		return ast;
	}

	@Test
	void test0() throws PLPException {
		String input = """
				CONST a = 3;
				! a
				.
				""";
		ASTNode ast = getDecoratedAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(1, v1.size());
		assertThat("", v1.get(0), instanceOf(ConstDec.class));
		IToken v2 = ((ConstDec) v1.get(0)).ident;
		assertEquals("a", String.valueOf(v2.getText()));
		Integer v3 = (Integer) ((ConstDec) v1.get(0)).val;
		assertEquals(3, v3);
		int v4 = ((ConstDec) v1.get(0)).getNest();
		assertEquals(0, v4);
		List<VarDec> v5 = ((Block) v0).varDecs;
		assertEquals(0, v5.size());
		List<ProcDec> v6 = ((Block) v0).procedureDecs;
		assertEquals(0, v6.size());
		Statement v7 = ((Block) v0).statement;
		assertThat("", v7, instanceOf(StatementOutput.class));
		Expression v8 = ((StatementOutput) v7).expression;
		assertThat("", v8, instanceOf(ExpressionIdent.class));
		IToken v9 = ((ExpressionIdent) v8).firstToken;
		assertEquals("a", String.valueOf(v9.getText()));
		int v10 = ((ExpressionIdent) v8).getNest();
		assertEquals(0, v10);
		Declaration v11 = ((ExpressionIdent) v8).getDec();
		assertThat("", v11, instanceOf(ConstDec.class));
		IToken v12 = ((ConstDec) v11).ident;
		assertEquals("a", String.valueOf(v12.getText()));
		Integer v13 = (Integer) ((ConstDec) v11).val;
		assertEquals(3, v13);
		int v14 = ((ConstDec) v11).getNest();
		assertEquals(0, v14);
	}

	@Test
	void test1() throws PLPException {
		String input = """
				VAR a;
				BEGIN
				?a ;
				!a
				END
				.
				""";
		ASTNode ast = getDecoratedAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(1, v2.size());
		assertThat("", v2.get(0), instanceOf(VarDec.class));
		IToken v3 = ((VarDec) v2.get(0)).ident;
		assertEquals("a", String.valueOf(v3.getText()));
		int v4 = ((VarDec) v2.get(0)).getNest();
		assertEquals(0, v4);
		List<ProcDec> v5 = ((Block) v0).procedureDecs;
		assertEquals(0, v5.size());
		Statement v6 = ((Block) v0).statement;
		assertThat("", v6, instanceOf(StatementBlock.class));
		List<Statement> v7 = ((StatementBlock) v6).statements;
		assertThat("", v7.get(0), instanceOf(StatementInput.class));
		Ident v8 = ((StatementInput) v7.get(0)).ident;
		assertThat("", v8, instanceOf(Ident.class));
		IToken v9 = ((Ident) v8).firstToken;
		assertEquals("a", String.valueOf(v9.getText()));
		int v10 = ((Ident) v8).getNest();
		assertEquals(0, v10);
		Declaration v11 = ((Ident) v8).getDec();
		assertThat("", v11, instanceOf(VarDec.class));
		IToken v12 = ((VarDec) v11).ident;
		assertEquals("a", String.valueOf(v12.getText()));
		int v13 = ((VarDec) v11).getNest();
		assertEquals(0, v13);
		assertThat("", v7.get(1), instanceOf(StatementOutput.class));
		Expression v14 = ((StatementOutput) v7.get(1)).expression;
		assertThat("", v14, instanceOf(ExpressionIdent.class));
		IToken v15 = ((ExpressionIdent) v14).firstToken;
		assertEquals("a", String.valueOf(v15.getText()));
		int v16 = ((ExpressionIdent) v14).getNest();
		assertEquals(0, v16);
		Declaration v17 = ((ExpressionIdent) v14).getDec();
		assertThat("", v17, instanceOf(VarDec.class));
		IToken v18 = ((VarDec) v17).ident;
		assertEquals("a", String.valueOf(v18.getText()));
		int v19 = ((VarDec) v17).getNest();
		assertEquals(0, v19);
	}

	@Test
	void test2() throws PLPException {
		String input = """
				CONST a = 4;
				CALL a  //remember, no type checking yet
				.

				""";
		ASTNode ast = getDecoratedAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(1, v1.size());
		assertThat("", v1.get(0), instanceOf(ConstDec.class));
		IToken v2 = ((ConstDec) v1.get(0)).ident;
		assertEquals("a", String.valueOf(v2.getText()));
		Integer v3 = (Integer) ((ConstDec) v1.get(0)).val;
		assertEquals(4, v3);
		int v4 = ((ConstDec) v1.get(0)).getNest();
		assertEquals(0, v4);
		List<VarDec> v5 = ((Block) v0).varDecs;
		assertEquals(0, v5.size());
		List<ProcDec> v6 = ((Block) v0).procedureDecs;
		assertEquals(0, v6.size());
		Statement v7 = ((Block) v0).statement;
		assertThat("", v7, instanceOf(StatementCall.class));
		Ident v8 = ((StatementCall) v7).ident;
		assertThat("", v8, instanceOf(Ident.class));
		IToken v9 = ((Ident) v8).firstToken;
		assertEquals("a", String.valueOf(v9.getText()));
		int v10 = ((Ident) v8).getNest();
		assertEquals(0, v10);
		Declaration v11 = ((Ident) v8).getDec();
		assertThat("", v11, instanceOf(ConstDec.class));
		IToken v12 = ((ConstDec) v11).ident;
		assertEquals("a", String.valueOf(v12.getText()));
		Integer v13 = (Integer) ((ConstDec) v11).val;
		assertEquals(4, v13);
		int v14 = ((ConstDec) v11).getNest();
		assertEquals(0, v14);
	}

	@Test
	void test3() throws PLPException {
		String input = """
				VAR x;
				PROCEDURE p;
				   !x;
				.
				""";
		ASTNode ast = getDecoratedAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(1, v2.size());
		assertThat("", v2.get(0), instanceOf(VarDec.class));
		IToken v3 = ((VarDec) v2.get(0)).ident;
		assertEquals("x", String.valueOf(v3.getText()));
		int v4 = ((VarDec) v2.get(0)).getNest();
		assertEquals(0, v4);
		List<ProcDec> v5 = ((Block) v0).procedureDecs;
		assertEquals(1, v5.size());
		assertThat("", v5.get(0), instanceOf(ProcDec.class));
		IToken v6 = ((ProcDec) v5.get(0)).ident;
		assertEquals("p", String.valueOf(v6.getText()));
		int v7 = ((ProcDec) v5.get(0)).getNest();
		assertEquals(0, v7);
		Block v8 = ((ProcDec) v5.get(0)).block;
		assertThat("", v8, instanceOf(Block.class));
		List<ConstDec> v9 = ((Block) v8).constDecs;
		assertEquals(0, v9.size());
		List<VarDec> v10 = ((Block) v8).varDecs;
		assertEquals(0, v10.size());
		List<ProcDec> v11 = ((Block) v8).procedureDecs;
		assertEquals(0, v11.size());
		Statement v12 = ((Block) v8).statement;
		assertThat("", v12, instanceOf(StatementOutput.class));
		Expression v13 = ((StatementOutput) v12).expression;
		assertThat("", v13, instanceOf(ExpressionIdent.class));
		IToken v14 = ((ExpressionIdent) v13).firstToken;
		assertEquals("x", String.valueOf(v14.getText()));
		int v15 = ((ExpressionIdent) v13).getNest();
		assertEquals(1, v15);
		Declaration v16 = ((ExpressionIdent) v13).getDec();
		assertThat("", v16, instanceOf(VarDec.class));
		IToken v17 = ((VarDec) v16).ident;
		assertEquals("x", String.valueOf(v17.getText()));
		int v18 = ((VarDec) v16).getNest();
		assertEquals(0, v18);
		Statement v19 = ((Block) v0).statement;
		assertThat("", v19, instanceOf(StatementEmpty.class));
	}

	@Test
	void test4() throws PLPException {
		String input = """

				VAR x;
				PROCEDURE p;
				   CONST x = 2;
				   !x;
				!x
				.
				""";
		ASTNode ast = getDecoratedAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(1, v2.size());
		assertThat("", v2.get(0), instanceOf(VarDec.class));
		IToken v3 = ((VarDec) v2.get(0)).ident;
		assertEquals("x", String.valueOf(v3.getText()));
		int v4 = ((VarDec) v2.get(0)).getNest();
		assertEquals(0, v4);
		List<ProcDec> v5 = ((Block) v0).procedureDecs;
		assertEquals(1, v5.size());
		assertThat("", v5.get(0), instanceOf(ProcDec.class));
		IToken v6 = ((ProcDec) v5.get(0)).ident;
		assertEquals("p", String.valueOf(v6.getText()));
		int v7 = ((ProcDec) v5.get(0)).getNest();
		assertEquals(0, v7);
		Block v8 = ((ProcDec) v5.get(0)).block;
		assertThat("", v8, instanceOf(Block.class));
		List<ConstDec> v9 = ((Block) v8).constDecs;
		assertEquals(1, v9.size());
		assertThat("", v9.get(0), instanceOf(ConstDec.class));
		IToken v10 = ((ConstDec) v9.get(0)).ident;
		assertEquals("x", String.valueOf(v10.getText()));
		Integer v11 = (Integer) ((ConstDec) v9.get(0)).val;
		assertEquals(2, v11);
		int v12 = ((ConstDec) v9.get(0)).getNest();
		assertEquals(1, v12);
		List<VarDec> v13 = ((Block) v8).varDecs;
		assertEquals(0, v13.size());
		List<ProcDec> v14 = ((Block) v8).procedureDecs;
		assertEquals(0, v14.size());
		Statement v15 = ((Block) v8).statement;
		assertThat("", v15, instanceOf(StatementOutput.class));
		Expression v16 = ((StatementOutput) v15).expression;
		assertThat("", v16, instanceOf(ExpressionIdent.class));
		IToken v17 = ((ExpressionIdent) v16).firstToken;
		assertEquals("x", String.valueOf(v17.getText()));
		int v18 = ((ExpressionIdent) v16).getNest();
		assertEquals(1, v18);
		Declaration v19 = ((ExpressionIdent) v16).getDec();
		assertThat("", v19, instanceOf(ConstDec.class));
		IToken v20 = ((ConstDec) v19).ident;
		assertEquals("x", String.valueOf(v20.getText()));
		Integer v21 = (Integer) ((ConstDec) v19).val;
		assertEquals(2, v21);
		int v22 = ((ConstDec) v19).getNest();
		assertEquals(1, v22);
		Statement v23 = ((Block) v0).statement;
		assertThat("", v23, instanceOf(StatementOutput.class));
		Expression v24 = ((StatementOutput) v23).expression;
		assertThat("", v24, instanceOf(ExpressionIdent.class));
		IToken v25 = ((ExpressionIdent) v24).firstToken;
		assertEquals("x", String.valueOf(v25.getText()));
		int v26 = ((ExpressionIdent) v24).getNest();
		assertEquals(0, v26);
		Declaration v27 = ((ExpressionIdent) v24).getDec();
		assertThat("", v27, instanceOf(VarDec.class));
		IToken v28 = ((VarDec) v27).ident;
		assertEquals("x", String.valueOf(v28.getText()));
		int v29 = ((VarDec) v27).getNest();
		assertEquals(0, v29);
	}

	@Test
	void test5() throws PLPException {
		String input = """
				PROCEDURE p;
				     CALL q;
				PROCEDURE q;
				;
				CALL p
				.
				""";
		ASTNode ast = getDecoratedAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(2, v3.size());
		assertThat("", v3.get(0), instanceOf(ProcDec.class));
		IToken v4 = ((ProcDec) v3.get(0)).ident;
		assertEquals("p", String.valueOf(v4.getText()));
		int v5 = ((ProcDec) v3.get(0)).getNest();
		assertEquals(0, v5);
		Block v6 = ((ProcDec) v3.get(0)).block;
		assertThat("", v6, instanceOf(Block.class));
		List<ConstDec> v7 = ((Block) v6).constDecs;
		assertEquals(0, v7.size());
		List<VarDec> v8 = ((Block) v6).varDecs;
		assertEquals(0, v8.size());
		List<ProcDec> v9 = ((Block) v6).procedureDecs;
		assertEquals(0, v9.size());
		Statement v10 = ((Block) v6).statement;
		assertThat("", v10, instanceOf(StatementCall.class));
		Ident v11 = ((StatementCall) v10).ident;
		assertThat("", v11, instanceOf(Ident.class));
		IToken v12 = ((Ident) v11).firstToken;
		assertEquals("q", String.valueOf(v12.getText()));
		int v13 = ((Ident) v11).getNest();
		assertEquals(1, v13);
		Declaration v14 = ((Ident) v11).getDec();
		assertThat("", v14, instanceOf(ProcDec.class));
		IToken v15 = ((ProcDec) v14).ident;
		assertEquals("q", String.valueOf(v15.getText()));
		int v16 = ((ProcDec) v14).getNest();
		assertEquals(0, v16);
		assertThat("", v3.get(1), instanceOf(ProcDec.class));
		IToken v17 = ((ProcDec) v3.get(1)).ident;
		assertEquals("q", String.valueOf(v17.getText()));
		int v18 = ((ProcDec) v3.get(1)).getNest();
		assertEquals(0, v18);
		Block v19 = ((ProcDec) v3.get(1)).block;
		assertThat("", v19, instanceOf(Block.class));
		List<ConstDec> v20 = ((Block) v19).constDecs;
		assertEquals(0, v20.size());
		List<VarDec> v21 = ((Block) v19).varDecs;
		assertEquals(0, v21.size());
		List<ProcDec> v22 = ((Block) v19).procedureDecs;
		assertEquals(0, v22.size());
		Statement v23 = ((Block) v19).statement;
		assertThat("", v23, instanceOf(StatementEmpty.class));
		Statement v24 = ((Block) v0).statement;
		assertThat("", v24, instanceOf(StatementCall.class));
		Ident v25 = ((StatementCall) v24).ident;
		assertThat("", v25, instanceOf(Ident.class));
		IToken v26 = ((Ident) v25).firstToken;
		assertEquals("p", String.valueOf(v26.getText()));
		int v27 = ((Ident) v25).getNest();
		assertEquals(0, v27);
		Declaration v28 = ((Ident) v25).getDec();
		assertThat("", v28, instanceOf(ProcDec.class));
		IToken v29 = ((ProcDec) v28).ident;
		assertEquals("p", String.valueOf(v29.getText()));
		int v30 = ((ProcDec) v28).getNest();
		assertEquals(0, v30);
	}

	@Test
	void test6() throws PLPException {
		String input = """
				PROCEDURE p;
					CALL q;
				PROCEDURE q;
				    CALL p;
				.
				""";
		ASTNode ast = getDecoratedAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(2, v3.size());
		assertThat("", v3.get(0), instanceOf(ProcDec.class));
		IToken v4 = ((ProcDec) v3.get(0)).ident;
		assertEquals("p", String.valueOf(v4.getText()));
		int v5 = ((ProcDec) v3.get(0)).getNest();
		assertEquals(0, v5);
		Block v6 = ((ProcDec) v3.get(0)).block;
		assertThat("", v6, instanceOf(Block.class));
		List<ConstDec> v7 = ((Block) v6).constDecs;
		assertEquals(0, v7.size());
		List<VarDec> v8 = ((Block) v6).varDecs;
		assertEquals(0, v8.size());
		List<ProcDec> v9 = ((Block) v6).procedureDecs;
		assertEquals(0, v9.size());
		Statement v10 = ((Block) v6).statement;
		assertThat("", v10, instanceOf(StatementCall.class));
		Ident v11 = ((StatementCall) v10).ident;
		assertThat("", v11, instanceOf(Ident.class));
		IToken v12 = ((Ident) v11).firstToken;
		assertEquals("q", String.valueOf(v12.getText()));
		int v13 = ((Ident) v11).getNest();
		assertEquals(1, v13);
		Declaration v14 = ((Ident) v11).getDec();
		assertThat("", v14, instanceOf(ProcDec.class));
		IToken v15 = ((ProcDec) v14).ident;
		assertEquals("q", String.valueOf(v15.getText()));
		int v16 = ((ProcDec) v14).getNest();
		assertEquals(0, v16);
		assertThat("", v3.get(1), instanceOf(ProcDec.class));
		IToken v17 = ((ProcDec) v3.get(1)).ident;
		assertEquals("q", String.valueOf(v17.getText()));
		int v18 = ((ProcDec) v3.get(1)).getNest();
		assertEquals(0, v18);
		Block v19 = ((ProcDec) v3.get(1)).block;
		assertThat("", v19, instanceOf(Block.class));
		List<ConstDec> v20 = ((Block) v19).constDecs;
		assertEquals(0, v20.size());
		List<VarDec> v21 = ((Block) v19).varDecs;
		assertEquals(0, v21.size());
		List<ProcDec> v22 = ((Block) v19).procedureDecs;
		assertEquals(0, v22.size());
		Statement v23 = ((Block) v19).statement;
		assertThat("", v23, instanceOf(StatementCall.class));
		Ident v24 = ((StatementCall) v23).ident;
		assertThat("", v24, instanceOf(Ident.class));
		IToken v25 = ((Ident) v24).firstToken;
		assertEquals("p", String.valueOf(v25.getText()));
		int v26 = ((Ident) v24).getNest();
		assertEquals(1, v26);
		Declaration v27 = ((Ident) v24).getDec();
		assertThat("", v27, instanceOf(ProcDec.class));
		IToken v28 = ((ProcDec) v27).ident;
		assertEquals("p", String.valueOf(v28.getText()));
		int v29 = ((ProcDec) v27).getNest();
		assertEquals(0, v29);
		Statement v30 = ((Block) v0).statement;
		assertThat("", v30, instanceOf(StatementEmpty.class));
	}

	@Test
	void test7() throws PLPException {
		String input = """
				CONST a * b;
				""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getDecoratedAST(input);
		});
	}

	@Test
	void test8() throws PLPException {
		String input = """
				PROCEDURE 42
				""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getDecoratedAST(input);
		});
	}

	@Test
	void test9() throws PLPException {
		String input = """
				VAR @;
				""";
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getDecoratedAST(input);
		});
	}

	@Test
	void test10() throws PLPException {
		String input = """
				! abc
				.
				""";
		assertThrows(ScopeException.class, () -> { // abc not declared
			@SuppressWarnings("unused")
			ASTNode ast = getDecoratedAST(input);
		});
	}

	@Test
	void test11() throws PLPException {
		String input = """
						VAR p;
						PROCEDURE p;
						;
						.
				""";
		assertThrows(ScopeException.class, () -> { // p declared twice in same scope
			@SuppressWarnings("unused")
			ASTNode ast = getDecoratedAST(input);
		});
	}

	@Test
	void test12() throws PLPException {
		String input = """
						CONST a=43;
						VAR a;
											.
				""";
		assertThrows(ScopeException.class, () -> {// a declared twice in same scope
			@SuppressWarnings("unused")
			ASTNode ast = getDecoratedAST(input);
		});
	}

}
