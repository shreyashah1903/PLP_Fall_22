package edu.ufl.cise.plpfa22;

import edu.ufl.cise.plpfa22.ast.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

	ASTNode getAST(String input) throws PLPException {
		IParser parser = CompilerComponentFactory.getParser(CompilerComponentFactory.getLexer(input));
		return parser.parse();
	}

	@Test
//shortest legal program
	void test0() throws PLPException {
		String input = """
				.""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementEmpty.class));
	}

	@Test
	void test1() throws PLPException {
		String input = """
				! 0 .""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementOutput.class));
		Expression v5 = ((StatementOutput) v4).expression;
		assertThat("", v5, instanceOf(ExpressionNumLit.class));
		IToken v6 = ((ExpressionNumLit) v5).firstToken;
		assertEquals("0", String.valueOf(v6.getText()));
	}

	@Test
	void test2() throws PLPException {
		String input = """
				! "hello" .""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementOutput.class));
		Expression v5 = ((StatementOutput) v4).expression;
		assertThat("", v5, instanceOf(ExpressionStringLit.class));
		IToken v6 = ((ExpressionStringLit) v5).firstToken;
		assertEquals("hello", v6.getStringValue());
	}

	@Test
	void test3() throws PLPException {
		String input = """
				! TRUE .""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementOutput.class));
		Expression v5 = ((StatementOutput) v4).expression;
		assertThat("", v5, instanceOf(ExpressionBooleanLit.class));
		IToken v6 = ((ExpressionBooleanLit) v5).firstToken;
		assertEquals("TRUE", String.valueOf(v6.getText()));
	}

	@Test
	void test4() throws PLPException {
		String input = """
				! abc
				.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementOutput.class));
		Expression v5 = ((StatementOutput) v4).expression;
		assertThat("", v5, instanceOf(ExpressionIdent.class));
		IToken v6 = ((ExpressionIdent) v5).firstToken;
		assertEquals("abc", String.valueOf(v6.getText()));
	}

	@Test
	void test5() throws PLPException {
		String input = """
				VAR abc;
				.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(1, v2.size());
		assertThat("", v2.get(0), instanceOf(VarDec.class));
		IToken v3 = ((VarDec) v2.get(0)).ident;
		assertEquals("abc", String.valueOf(v3.getText()));
		List<ProcDec> v4 = ((Block) v0).procedureDecs;
		assertEquals(0, v4.size());
		Statement v5 = ((Block) v0).statement;
		assertThat("", v5, instanceOf(StatementEmpty.class));
	}

	@Test
	void test6() throws PLPException {
		String input = """
				BEGIN
				! "hello";
				! TRUE;
				!  33 ;
				! variable
				END
				.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementBlock.class));
		List<Statement> v5 = ((StatementBlock) v4).statements;
		assertThat("", v5.get(0), instanceOf(StatementOutput.class));
		Expression v6 = ((StatementOutput) v5.get(0)).expression;
		assertThat("", v6, instanceOf(ExpressionStringLit.class));
		IToken v7 = ((ExpressionStringLit) v6).firstToken;
		assertEquals("hello", v7.getStringValue());
		assertThat("", v5.get(1), instanceOf(StatementOutput.class));
		Expression v8 = ((StatementOutput) v5.get(1)).expression;
		assertThat("", v8, instanceOf(ExpressionBooleanLit.class));
		IToken v9 = ((ExpressionBooleanLit) v8).firstToken;
		assertEquals("TRUE", String.valueOf(v9.getText()));
		assertThat("", v5.get(2), instanceOf(StatementOutput.class));
		Expression v10 = ((StatementOutput) v5.get(2)).expression;
		assertThat("", v10, instanceOf(ExpressionNumLit.class));
		IToken v11 = ((ExpressionNumLit) v10).firstToken;
		assertEquals("33", String.valueOf(v11.getText()));
		assertThat("", v5.get(3), instanceOf(StatementOutput.class));
		Expression v12 = ((StatementOutput) v5.get(3)).expression;
		assertThat("", v12, instanceOf(ExpressionIdent.class));
		IToken v13 = ((ExpressionIdent) v12).firstToken;
		assertEquals("variable", String.valueOf(v13.getText()));
	}

	@Test
	void test7() throws PLPException {
		String input = """
				BEGIN
				? abc;
				! variable
				END
				.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementBlock.class));
		List<Statement> v5 = ((StatementBlock) v4).statements;
		assertThat("", v5.get(0), instanceOf(StatementInput.class));
		Ident v6 = ((StatementInput) v5.get(0)).ident;
		assertEquals("abc", String.valueOf(v6.getText()));
		assertThat("", v5.get(1), instanceOf(StatementOutput.class));
		Expression v7 = ((StatementOutput) v5.get(1)).expression;
		assertThat("", v7, instanceOf(ExpressionIdent.class));
		IToken v8 = ((ExpressionIdent) v7).firstToken;
		assertEquals("variable", String.valueOf(v8.getText()));
	}

	@Test
	void test8() throws PLPException {
		String input = """
				CONST a = 3, b = TRUE, c = "hello";
				.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(3, v1.size());
		assertThat("", v1.get(0), instanceOf(ConstDec.class));
		IToken v2 = ((ConstDec) v1.get(0)).ident;
		assertEquals("a", String.valueOf(v2.getText()));
		Integer v3 = (Integer) ((ConstDec) v1.get(0)).val;
		assertEquals(3, v3);
		assertThat("", v1.get(1), instanceOf(ConstDec.class));
		IToken v4 = ((ConstDec) v1.get(1)).ident;
		assertEquals("b", String.valueOf(v4.getText()));
		Boolean v5 = (Boolean) ((ConstDec) v1.get(1)).val;
		assertEquals(true, v5);
		assertThat("", v1.get(2), instanceOf(ConstDec.class));
		IToken v6 = ((ConstDec) v1.get(2)).ident;
		assertEquals("c", String.valueOf(v6.getText()));
		String v7 = (String) ((ConstDec) v1.get(2)).val;
		assertEquals("hello", v7);
		List<VarDec> v8 = ((Block) v0).varDecs;
		assertEquals(0, v8.size());
		List<ProcDec> v9 = ((Block) v0).procedureDecs;
		assertEquals(0, v9.size());
		Statement v10 = ((Block) v0).statement;
		assertThat("", v10, instanceOf(StatementEmpty.class));
	}

	@Test
	void test9() throws PLPException {
		String input = """
				BEGIN
				x := 3;
				y := "hello";
				b := FALSE
				END
				.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementBlock.class));
		List<Statement> v5 = ((StatementBlock) v4).statements;
		assertThat("", v5.get(0), instanceOf(StatementAssign.class));
		Ident v6 = ((StatementAssign) v5.get(0)).ident;
		assertEquals("x", String.valueOf(v6.getText()));
		Expression v7 = ((StatementAssign) v5.get(0)).expression;
		assertThat("", v7, instanceOf(ExpressionNumLit.class));
		IToken v8 = ((ExpressionNumLit) v7).firstToken;
		assertEquals("3", String.valueOf(v8.getText()));
		assertThat("", v5.get(1), instanceOf(StatementAssign.class));
		Ident v9 = ((StatementAssign) v5.get(1)).ident;
		assertEquals("y", String.valueOf(v9.getText()));
		Expression v10 = ((StatementAssign) v5.get(1)).expression;
		assertThat("", v10, instanceOf(ExpressionStringLit.class));
		IToken v11 = ((ExpressionStringLit) v10).firstToken;
		assertEquals("hello", v11.getStringValue());
		assertThat("", v5.get(2), instanceOf(StatementAssign.class));
		Ident v12 = ((StatementAssign) v5.get(2)).ident;
		assertEquals("b", String.valueOf(v12.getText()));
		Expression v13 = ((StatementAssign) v5.get(2)).expression;
		assertThat("", v13, instanceOf(ExpressionBooleanLit.class));
		IToken v14 = ((ExpressionBooleanLit) v13).firstToken;
		assertEquals("FALSE", String.valueOf(v14.getText()));
	}

	@Test
	void test10() throws PLPException {
		String input = """
				BEGIN
				CALL x
				END
				.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementBlock.class));
		List<Statement> v5 = ((StatementBlock) v4).statements;
		assertThat("", v5.get(0), instanceOf(StatementCall.class));
		Ident v6 = ((StatementCall) v5.get(0)).ident;
		assertEquals("x", String.valueOf(v6.getText()));
	}

	@Test
	void test11() throws PLPException {
		String input = """
				CONST a=3;
				VAR x,y,z;
				PROCEDURE p;
				  VAR j;
				  BEGIN
				     ? x;
				     IF x = 0 THEN ! y ;
				     WHILE j < 24 DO CALL z
				  END;
				! z
				.
				""";
		ASTNode ast = getAST(input);
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
		List<VarDec> v4 = ((Block) v0).varDecs;
		assertEquals(3, v4.size());
		assertThat("", v4.get(0), instanceOf(VarDec.class));
		IToken v5 = ((VarDec) v4.get(0)).ident;
		assertEquals("x", String.valueOf(v5.getText()));
		assertThat("", v4.get(1), instanceOf(VarDec.class));
		IToken v6 = ((VarDec) v4.get(1)).ident;
		assertEquals("y", String.valueOf(v6.getText()));
		assertThat("", v4.get(2), instanceOf(VarDec.class));
		IToken v7 = ((VarDec) v4.get(2)).ident;
		assertEquals("z", String.valueOf(v7.getText()));
		List<ProcDec> v8 = ((Block) v0).procedureDecs;
		assertEquals(1, v8.size());
		assertThat("", v8.get(0), instanceOf(ProcDec.class));
		IToken v9 = ((ProcDec) v8.get(0)).ident;
		assertEquals("p", String.valueOf(v9.getText()));
		Block v10 = ((ProcDec) v8.get(0)).block;
		assertThat("", v10, instanceOf(Block.class));
		List<ConstDec> v11 = ((Block) v10).constDecs;
		assertEquals(0, v11.size());
		List<VarDec> v12 = ((Block) v10).varDecs;
		assertEquals(1, v12.size());
		assertThat("", v12.get(0), instanceOf(VarDec.class));
		IToken v13 = ((VarDec) v12.get(0)).ident;
		assertEquals("j", String.valueOf(v13.getText()));
		List<ProcDec> v14 = ((Block) v10).procedureDecs;
		assertEquals(0, v14.size());
		Statement v15 = ((Block) v10).statement;
		assertThat("", v15, instanceOf(StatementBlock.class));
		List<Statement> v16 = ((StatementBlock) v15).statements;
		assertThat("", v16.get(0), instanceOf(StatementInput.class));
		Ident v17 = ((StatementInput) v16.get(0)).ident;
		assertEquals("x", String.valueOf(v17.getText()));
		assertThat("", v16.get(1), instanceOf(StatementIf.class));
		Expression v18 = ((StatementIf) v16.get(1)).expression;
		assertThat("", v18, instanceOf(ExpressionBinary.class));
		Expression v19 = ((ExpressionBinary) v18).e0;
		assertThat("", v19, instanceOf(ExpressionIdent.class));
		IToken v20 = ((ExpressionIdent) v19).firstToken;
		assertEquals("x", String.valueOf(v20.getText()));
		Expression v21 = ((ExpressionBinary) v18).e1;
		assertThat("", v21, instanceOf(ExpressionNumLit.class));
		IToken v22 = ((ExpressionNumLit) v21).firstToken;
		assertEquals("0", String.valueOf(v22.getText()));
		IToken v23 = ((ExpressionBinary) v18).op;
		assertEquals("=", String.valueOf(v23.getText()));
		Statement v24 = ((StatementIf) v16.get(1)).statement;
		assertThat("", v24, instanceOf(StatementOutput.class));
		Expression v25 = ((StatementOutput) v24).expression;
		assertThat("", v25, instanceOf(ExpressionIdent.class));
		IToken v26 = ((ExpressionIdent) v25).firstToken;
		assertEquals("y", String.valueOf(v26.getText()));
		assertThat("", v16.get(2), instanceOf(StatementWhile.class));
		Expression v27 = ((StatementWhile) v16.get(2)).expression;
		assertThat("", v27, instanceOf(ExpressionBinary.class));
		Expression v28 = ((ExpressionBinary) v27).e0;
		assertThat("", v28, instanceOf(ExpressionIdent.class));
		IToken v29 = ((ExpressionIdent) v28).firstToken;
		assertEquals("j", String.valueOf(v29.getText()));
		Expression v30 = ((ExpressionBinary) v27).e1;
		assertThat("", v30, instanceOf(ExpressionNumLit.class));
		IToken v31 = ((ExpressionNumLit) v30).firstToken;
		assertEquals("24", String.valueOf(v31.getText()));
		IToken v32 = ((ExpressionBinary) v27).op;
		assertEquals("<", String.valueOf(v32.getText()));
		Statement v33 = ((StatementWhile) v16.get(2)).statement;
		assertThat("", v33, instanceOf(StatementCall.class));
		Ident v34 = ((StatementCall) v33).ident;
		assertEquals("z", String.valueOf(v34.getText()));
		Statement v35 = ((Block) v0).statement;
		assertThat("", v35, instanceOf(StatementOutput.class));
		Expression v36 = ((StatementOutput) v35).expression;
		assertThat("", v36, instanceOf(ExpressionIdent.class));
		IToken v37 = ((ExpressionIdent) v36).firstToken;
		assertEquals("z", String.valueOf(v37.getText()));
	}

	@Test
	void test12() throws PLPException {
		String input = """
				CONST a=3;
				VAR x,y,z;
				PROCEDURE p;
				  VAR j;
				  BEGIN
				     ? x;
				     IF x = 0 THEN ! y ;
				     WHILE j < 24 DO CALL z
				  END;
				! a+b - (c/e) * 35/(3+4)
				.
				""";
		ASTNode ast = getAST(input);
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
		List<VarDec> v4 = ((Block) v0).varDecs;
		assertEquals(3, v4.size());
		assertThat("", v4.get(0), instanceOf(VarDec.class));
		IToken v5 = ((VarDec) v4.get(0)).ident;
		assertEquals("x", String.valueOf(v5.getText()));
		assertThat("", v4.get(1), instanceOf(VarDec.class));
		IToken v6 = ((VarDec) v4.get(1)).ident;
		assertEquals("y", String.valueOf(v6.getText()));
		assertThat("", v4.get(2), instanceOf(VarDec.class));
		IToken v7 = ((VarDec) v4.get(2)).ident;
		assertEquals("z", String.valueOf(v7.getText()));
		List<ProcDec> v8 = ((Block) v0).procedureDecs;
		assertEquals(1, v8.size());
		assertThat("", v8.get(0), instanceOf(ProcDec.class));
		IToken v9 = ((ProcDec) v8.get(0)).ident;
		assertEquals("p", String.valueOf(v9.getText()));
		Block v10 = ((ProcDec) v8.get(0)).block;
		assertThat("", v10, instanceOf(Block.class));
		List<ConstDec> v11 = ((Block) v10).constDecs;
		assertEquals(0, v11.size());
		List<VarDec> v12 = ((Block) v10).varDecs;
		assertEquals(1, v12.size());
		assertThat("", v12.get(0), instanceOf(VarDec.class));
		IToken v13 = ((VarDec) v12.get(0)).ident;
		assertEquals("j", String.valueOf(v13.getText()));
		List<ProcDec> v14 = ((Block) v10).procedureDecs;
		assertEquals(0, v14.size());
		Statement v15 = ((Block) v10).statement;
		assertThat("", v15, instanceOf(StatementBlock.class));
		List<Statement> v16 = ((StatementBlock) v15).statements;
		assertThat("", v16.get(0), instanceOf(StatementInput.class));
		Ident v17 = ((StatementInput) v16.get(0)).ident;
		assertEquals("x", String.valueOf(v17.getText()));
		assertThat("", v16.get(1), instanceOf(StatementIf.class));
		Expression v18 = ((StatementIf) v16.get(1)).expression;
		assertThat("", v18, instanceOf(ExpressionBinary.class));
		Expression v19 = ((ExpressionBinary) v18).e0;
		assertThat("", v19, instanceOf(ExpressionIdent.class));
		IToken v20 = ((ExpressionIdent) v19).firstToken;
		assertEquals("x", String.valueOf(v20.getText()));
		Expression v21 = ((ExpressionBinary) v18).e1;
		assertThat("", v21, instanceOf(ExpressionNumLit.class));
		IToken v22 = ((ExpressionNumLit) v21).firstToken;
		assertEquals("0", String.valueOf(v22.getText()));
		IToken v23 = ((ExpressionBinary) v18).op;
		assertEquals("=", String.valueOf(v23.getText()));
		Statement v24 = ((StatementIf) v16.get(1)).statement;
		assertThat("", v24, instanceOf(StatementOutput.class));
		Expression v25 = ((StatementOutput) v24).expression;
		assertThat("", v25, instanceOf(ExpressionIdent.class));
		IToken v26 = ((ExpressionIdent) v25).firstToken;
		assertEquals("y", String.valueOf(v26.getText()));
		assertThat("", v16.get(2), instanceOf(StatementWhile.class));
		Expression v27 = ((StatementWhile) v16.get(2)).expression;
		assertThat("", v27, instanceOf(ExpressionBinary.class));
		Expression v28 = ((ExpressionBinary) v27).e0;
		assertThat("", v28, instanceOf(ExpressionIdent.class));
		IToken v29 = ((ExpressionIdent) v28).firstToken;
		assertEquals("j", String.valueOf(v29.getText()));
		Expression v30 = ((ExpressionBinary) v27).e1;
		assertThat("", v30, instanceOf(ExpressionNumLit.class));
		IToken v31 = ((ExpressionNumLit) v30).firstToken;
		assertEquals("24", String.valueOf(v31.getText()));
		IToken v32 = ((ExpressionBinary) v27).op;
		assertEquals("<", String.valueOf(v32.getText()));
		Statement v33 = ((StatementWhile) v16.get(2)).statement;
		assertThat("", v33, instanceOf(StatementCall.class));
		Ident v34 = ((StatementCall) v33).ident;
		assertEquals("z", String.valueOf(v34.getText()));
		Statement v35 = ((Block) v0).statement;
		assertThat("", v35, instanceOf(StatementOutput.class));
		Expression v36 = ((StatementOutput) v35).expression;
		assertThat("", v36, instanceOf(ExpressionBinary.class));
		Expression v37 = ((ExpressionBinary) v36).e0;
		assertThat("", v37, instanceOf(ExpressionBinary.class));
		Expression v38 = ((ExpressionBinary) v37).e0;
		assertThat("", v38, instanceOf(ExpressionIdent.class));
		IToken v39 = ((ExpressionIdent) v38).firstToken;
		assertEquals("a", String.valueOf(v39.getText()));
		Expression v40 = ((ExpressionBinary) v37).e1;
		assertThat("", v40, instanceOf(ExpressionIdent.class));
		IToken v41 = ((ExpressionIdent) v40).firstToken;
		assertEquals("b", String.valueOf(v41.getText()));
		IToken v42 = ((ExpressionBinary) v37).op;
		assertEquals("+", String.valueOf(v42.getText()));
		Expression v43 = ((ExpressionBinary) v36).e1;
		assertThat("", v43, instanceOf(ExpressionBinary.class));
		Expression v44 = ((ExpressionBinary) v43).e0;
		assertThat("", v44, instanceOf(ExpressionBinary.class));
		Expression v45 = ((ExpressionBinary) v44).e0;
		assertThat("", v45, instanceOf(ExpressionBinary.class));
		Expression v46 = ((ExpressionBinary) v45).e0;
		assertThat("", v46, instanceOf(ExpressionIdent.class));
		IToken v47 = ((ExpressionIdent) v46).firstToken;
		assertEquals("c", String.valueOf(v47.getText()));
		Expression v48 = ((ExpressionBinary) v45).e1;
		assertThat("", v48, instanceOf(ExpressionIdent.class));
		IToken v49 = ((ExpressionIdent) v48).firstToken;
		assertEquals("e", String.valueOf(v49.getText()));
		IToken v50 = ((ExpressionBinary) v45).op;
		assertEquals("/", String.valueOf(v50.getText()));
		Expression v51 = ((ExpressionBinary) v44).e1;
		assertThat("", v51, instanceOf(ExpressionNumLit.class));
		IToken v52 = ((ExpressionNumLit) v51).firstToken;
		assertEquals("35", String.valueOf(v52.getText()));
		IToken v53 = ((ExpressionBinary) v44).op;
		assertEquals("*", String.valueOf(v53.getText()));
		Expression v54 = ((ExpressionBinary) v43).e1;
		assertThat("", v54, instanceOf(ExpressionBinary.class));
		Expression v55 = ((ExpressionBinary) v54).e0;
		assertThat("", v55, instanceOf(ExpressionNumLit.class));
		IToken v56 = ((ExpressionNumLit) v55).firstToken;
		assertEquals("3", String.valueOf(v56.getText()));
		Expression v57 = ((ExpressionBinary) v54).e1;
		assertThat("", v57, instanceOf(ExpressionNumLit.class));
		IToken v58 = ((ExpressionNumLit) v57).firstToken;
		assertEquals("4", String.valueOf(v58.getText()));
		IToken v59 = ((ExpressionBinary) v54).op;
		assertEquals("+", String.valueOf(v59.getText()));
		IToken v60 = ((ExpressionBinary) v43).op;
		assertEquals("/", String.valueOf(v60.getText()));
		IToken v61 = ((ExpressionBinary) v36).op;
		assertEquals("-", String.valueOf(v61.getText()));
	}

	@Test
	void test13() throws PLPException {
		String input = """
				CONST a * b;
				.
				""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void test14() throws PLPException {
		String input = """
				PROCEDURE 42
				.
				""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	//The error in this example should be found by the Lexer
	void test15() throws PLPException {
		String input = """
				VAR @;
				.
				""";
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	// Self added tests
	@Test
	// Test the expression with parenthesis
	void test16() throws PLPException {
		String input = """
    			! 40/((4+1)*2)
    			.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementOutput.class));
		Expression v5 = ((StatementOutput) v4).expression;
		assertThat("", v5, instanceOf(ExpressionBinary.class));
		Expression v6 = ((ExpressionBinary) v5).e0;
		assertThat("", v6, instanceOf(ExpressionNumLit.class));
		IToken v7 = ((ExpressionNumLit) v6).firstToken;
		assertEquals("40",String.valueOf(v7.getText()));
		IToken v8 = ((ExpressionBinary) v5).op;
		assertEquals("/", String.valueOf(v8.getText()));
		Expression v9 = ((ExpressionBinary) v5).e1;
		assertThat("", v9, instanceOf(ExpressionBinary.class));
		Expression v10 = ((ExpressionBinary) v9).e0;
		assertThat("", v10, instanceOf(ExpressionBinary.class));
		Expression v11 = ((ExpressionBinary) v10).e0;
		assertThat("", v11, instanceOf(ExpressionNumLit.class));
		IToken v12 = ((ExpressionNumLit) v11).firstToken;
		assertEquals("4", String.valueOf(v12.getText()));
		IToken v13 = ((ExpressionBinary) v10).op;
		assertEquals("+", String.valueOf(v13.getText()));
		Expression v14 = ((ExpressionBinary) v10).e1;
		assertThat("", v14, instanceOf(ExpressionNumLit.class));
		IToken v15 = ((ExpressionNumLit) v14).firstToken;
		assertEquals("1", String.valueOf(v15.getText()));
		IToken v16 = ((ExpressionBinary) v9).op;
		assertEquals("*", String.valueOf(v16.getText()));
		Expression v17 = ((ExpressionBinary) v9).e1;
		assertThat("", v14, instanceOf(ExpressionNumLit.class));
		IToken v18 = ((ExpressionNumLit) v17).firstToken;
		assertEquals("2", String.valueOf(v18.getText()));
	}

	@Test
	// Test the operator precedence in expression
	void test17() throws PLPException {
		String input = """
    			! 2 + 3 * 4
    			.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementOutput.class));
		Expression v5 = ((StatementOutput) v4).expression;
		assertThat("", v5, instanceOf(ExpressionBinary.class));
		Expression v6 = ((ExpressionBinary) v5).e0;
		assertThat("", v6, instanceOf(ExpressionNumLit.class));
		IToken v7 = ((ExpressionNumLit) v6).firstToken;
		assertEquals("2",String.valueOf(v7.getText()));
		IToken v8 = ((ExpressionBinary) v5).op;
		assertEquals("+", String.valueOf(v8.getText()));
		Expression v9 = ((ExpressionBinary) v5).e1;
		assertThat("", v9, instanceOf(ExpressionBinary.class));
		Expression v10 = ((ExpressionBinary) v9).e0;
		assertThat("", v10, instanceOf(ExpressionNumLit.class));
		IToken v11 = ((ExpressionNumLit) v10).firstToken;
		assertEquals("3", String.valueOf(v11.getText()));
		IToken v12 = ((ExpressionBinary) v9).op;
		assertEquals("*", String.valueOf(v12.getText()));
		Expression v13 = ((ExpressionBinary) v9).e1;
		assertThat("", v13, instanceOf(ExpressionNumLit.class));
		IToken v14 = ((ExpressionNumLit) v13).firstToken;
		assertEquals("4",String.valueOf(v14.getText()));
	}


	@Test
	// Test the Statement If
	void test18() throws PLPException {
		String input = """
    			IF (x > y) THEN ! \"Number 1 is larger than number 2\"
    			.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementIf.class));
		Expression v5 = ((StatementIf) v4).expression;
		assertEquals("x", String.valueOf(((ExpressionBinary) v5).e0.getFirstToken().getText()));
		assertEquals(">", String.valueOf(((ExpressionBinary) v5).op.getText()));
		assertEquals("y", String.valueOf(((ExpressionBinary) v5).e1.getFirstToken().getText()));
		Statement v6 = ((StatementIf) v4).statement;
		assertThat("", v6, instanceOf(StatementOutput.class));
		Expression v7 = ((StatementOutput) v6).expression;
		assertThat("", v7, instanceOf(ExpressionStringLit.class));
		assertEquals("\"Number 1 is larger than number 2\"", String.valueOf(v7.getFirstToken().getText()));
	}

	@Test
	//	Test the Statement While
	void test19() throws PLPException {
		String input = """
    			WHILE 0 < a < 2 DO a:=3*2
    			.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementWhile.class));
		Expression v5 = ((StatementWhile) v4).expression;
		Expression v6 = ((ExpressionBinary) v5).e0;
		assertThat("", v6, instanceOf(ExpressionBinary.class));
		assertThat("", ((ExpressionBinary) v6).e0, instanceOf(ExpressionNumLit.class));
		assertEquals("0", String.valueOf(((ExpressionBinary) v6).e0.getFirstToken().getText()));
		assertEquals("<", String.valueOf(((ExpressionBinary) v6).op.getText()));
		assertThat("", ((ExpressionBinary) v6).e1, instanceOf(ExpressionIdent.class));
		assertEquals("a", String.valueOf(((ExpressionBinary) v6).e1.getFirstToken().getText()));
		assertEquals("<", String.valueOf(((ExpressionBinary) v5).op.getText()));
		assertThat("", ((ExpressionBinary) v5).e1, instanceOf(ExpressionNumLit.class));
		assertEquals("2", String.valueOf(((ExpressionBinary) v5).e1.getFirstToken().getText()));
		Statement v7 = ((StatementWhile) v4).statement;
		assertThat("", v7, instanceOf(StatementAssign.class));
		assertEquals("a", String.valueOf(((StatementAssign) v7).ident.getText()));
		Expression v8 = ((StatementAssign) v7).expression;
		assertThat("", v8, instanceOf(ExpressionBinary.class));
		assertThat("", ((ExpressionBinary) v8).e0, instanceOf(ExpressionNumLit.class));
		assertEquals("3", String.valueOf(((ExpressionBinary) v8).e0.getFirstToken().getText()));
		assertEquals("*", String.valueOf(((ExpressionBinary) v8).op.getText()));
		assertThat("", ((ExpressionBinary) v8).e1, instanceOf(ExpressionNumLit.class));
		assertEquals("2", String.valueOf(((ExpressionBinary) v8).e1.getFirstToken().getText()));
	}


	@Test
	// Test the CALL statement (Should fail as CALL <ident> is correct)
	void test20() throws PLPException {
		String input = """
    			BEGIN
    			CALL a;
    			CALL 23
    			END
    			.
				""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}


	@Test
	// Test the Procedure keyword
	void test21() throws PLPException {
		String input = """
    			PROCEDURE m;
    				! "Procedure is working.";
    			.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(1, v3.size());
		assertThat("", v3.get(0), instanceOf(ProcDec.class));
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementEmpty.class));
		IToken v5 = ((ProcDec) v3.get(0)).ident;
		assertEquals("m", String.valueOf(v5.getText()));
		Block v6 = ((ProcDec) v3.get(0)).block;
		assertThat("", v6, instanceOf(Block.class));
		List<ConstDec> v7 = ((Block) v6).constDecs;
		assertEquals(0, v7.size());
		List<VarDec> v8 = ((Block) v6).varDecs;
		assertEquals(0, v8.size());
		List<ProcDec> v9 = ((Block) v6).procedureDecs;
		assertEquals(0, v9.size());
		Block v11 = ((ProcDec) v3.get(0)).block;
		Statement v12 = ((Block) v11).statement;
		assertThat("", v12, instanceOf(StatementOutput.class));
		Expression v13 = ((StatementOutput) v12).expression;
		assertThat("", v13, instanceOf(ExpressionStringLit.class));
		assertEquals("Procedure is working.", v13.getFirstToken().getStringValue());
	}

	// Test the expression with parenthesis and incorrect semicolon
	@Test
	void test22() {
		String input = """
    			! 40/((4+1)*2);
    			.
				""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void test23() throws PLPException {
		String input = """
    			WHILE (x > y) DO ! \"Number 1 is larger than number 2\"
    			.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementWhile.class));
		Expression v5 = ((StatementWhile) v4).expression;
		assertEquals("x", String.valueOf(((ExpressionBinary) v5).e0.getFirstToken().getText()));
		assertEquals(">", String.valueOf(((ExpressionBinary) v5).op.getText()));
		assertEquals("y", String.valueOf(((ExpressionBinary) v5).e1.getFirstToken().getText()));
		Statement v6 = ((StatementWhile) v4).statement;
		assertThat("", v6, instanceOf(StatementOutput.class));
		Expression v7 = ((StatementOutput) v6).expression;
		assertThat("", v7, instanceOf(ExpressionStringLit.class));
		assertEquals("\"Number 1 is larger than number 2\"", String.valueOf(v7.getFirstToken().getText()));
	}

	@Test
	void test24() throws PLPException {
		String input = """
				! 0. abc
				 """;
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void test25() throws PLPException {
		String input = """
				CONST abc = 1, bcd = 2;
				.
				 """;
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(2, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
	}

	// Invalid since CONST should end with ;
	@Test
	void test26() throws PLPException {
		String input = """
				CONST abc = 1, bcd = 2
				.
				 """;
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	// Tests added from Google doc
	@Test
	void test_1() throws PLPException{
		String input = """
           VAR x,y;
           PROCEDURE random;
               CALL random_int;
           BEGIN
           x := 10;
           y := random;
           IF (x > y) THEN ! \"Number 1 is larger than number 2\";
           IF (x < y) THEN ! \"Number 2 is larger than number 1\";
           IF (x = y) THEN ! \"Number 1 is equal to number 2\"
           END
           .
           """;
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<VarDec> v1 = ((Block) v0).varDecs;
		assertEquals(2, v1.size());
		IToken v2 = ((VarDec) v1.get(0)).ident;
		assertEquals("x", String.valueOf(v2.getText()));
		IToken v3 = ((VarDec) v1.get(1)).ident;
		assertEquals("y", String.valueOf(v3.getText()));
		List<ProcDec> v4 = ((Block) v0).procedureDecs;
		assertEquals(1, v4.size());
		IToken v5 = ((ProcDec) v4.get(0)).ident;
		assertEquals("random", String.valueOf(v5.getText()));
		Block v6 = ((ProcDec) v4.get(0)).block;
		Statement v7 = ((Block) v6).statement;
		assertThat("", v7, instanceOf(StatementCall.class));
		Ident v8 = ((StatementCall) v7).ident;
		assertEquals("random_int", String.valueOf(v8.getText()));
		Statement v9 = ((Block) v0).statement;
		assertThat("", v9, instanceOf(StatementBlock.class));
		Statement v10 = ((StatementBlock) v9).statements.get(0);
		assertThat("", v10, instanceOf(StatementAssign.class));
		Ident v11 = ((StatementAssign) v10).ident;
		assertEquals("x", String.valueOf(v11.getText()));
		Expression v12 = ((StatementAssign) v10).expression;
		assertThat("", v12, instanceOf(ExpressionNumLit.class));
		Statement v13 = ((StatementBlock) v9).statements.get(1);
		assertThat("", v13, instanceOf(StatementAssign.class));
		Ident v14 = ((StatementAssign) v13).ident;
		assertEquals("y", String.valueOf(v14.getText()));
		Expression v15 = ((StatementAssign) v13).expression;
		assertThat("", v15, instanceOf(ExpressionIdent.class));
		Statement v16 = ((StatementBlock) v9).statements.get(2);
		assertThat("", v16, instanceOf(StatementIf.class));
		Expression v17 = ((StatementIf) v16).expression;
		assertEquals("x", String.valueOf(((ExpressionBinary) v17).e0.getFirstToken().getText()));
		assertEquals(">", String.valueOf(((ExpressionBinary) v17).op.getText()));
		assertEquals("y", String.valueOf(((ExpressionBinary) v17).e1.getFirstToken().getText()));
		Statement v18 = ((StatementIf) v16).statement;
		assertThat("", v18, instanceOf(StatementOutput.class));
		Expression v19 = ((StatementOutput) v18).expression;
		assertThat("", v19, instanceOf(ExpressionStringLit.class));
		assertEquals("\"Number 1 is larger than number 2\"", String.valueOf(v19.getFirstToken().getText()));
		Statement v20 = ((StatementBlock) v9).statements.get(3);
		assertThat("", v20, instanceOf(StatementIf.class));
		Expression v21 = ((StatementIf) v20).expression;
		assertEquals("x", String.valueOf(((ExpressionBinary) v21).e0.getFirstToken().getText()));
		assertEquals("<", String.valueOf(((ExpressionBinary) v21).op.getText()));
		assertEquals("y", String.valueOf(((ExpressionBinary) v21).e1.getFirstToken().getText()));
		Statement v22 = ((StatementIf) v20).statement;
		assertThat("", v22, instanceOf(StatementOutput.class));
		Expression v23 = ((StatementOutput) v22).expression;
		assertThat("", v23, instanceOf(ExpressionStringLit.class));
		assertEquals("\"Number 2 is larger than number 1\"", String.valueOf(v23.getFirstToken().getText()));
		Statement v24 = ((StatementBlock) v9).statements.get(4);
		assertThat("", v24, instanceOf(StatementIf.class));
		Expression v25 = ((StatementIf) v24).expression;
		assertEquals("x", String.valueOf(((ExpressionBinary) v25).e0.getFirstToken().getText()));
		assertEquals("=", String.valueOf(((ExpressionBinary) v25).op.getText()));
		assertEquals("y", String.valueOf(((ExpressionBinary) v25).e1.getFirstToken().getText()));
		Statement v26 = ((StatementIf) v24).statement;
		assertThat("", v26, instanceOf(StatementOutput.class));
		Expression v27 = ((StatementOutput) v26).expression;
		assertThat("", v27, instanceOf(ExpressionStringLit.class));
		assertEquals("\"Number 1 is equal to number 2\"",
				String.valueOf(v27.getFirstToken().getText()));
	}

	@Test
	void test_2() throws PLPException{
		String input = """
               ! x = y # 10 > 7 <= 45 < 50 >= 56
               .
               """;
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		Statement v1 = ((Block) v0).statement;
		assertThat("", v1, instanceOf(StatementOutput.class));
		Expression v2 = ((StatementOutput) v1).expression;
		assertThat("", v2, instanceOf(ExpressionBinary.class));
		assertEquals(">=", String.valueOf(((ExpressionBinary) v2).op.getText()));
		assertEquals("56", String.valueOf(((ExpressionBinary) v2).e1.getFirstToken().getText()));
		Expression v3 = ((ExpressionBinary) v2).e0;
		assertThat("", v3, instanceOf(ExpressionBinary.class));
		assertEquals("<", String.valueOf(((ExpressionBinary) v3).op.getText()));
		assertEquals("50", String.valueOf(((ExpressionBinary) v3).e1.getFirstToken().getText()));
		Expression v4 = ((ExpressionBinary) v3).e0;
		assertThat("", v4, instanceOf(ExpressionBinary.class));
		assertEquals("<=", String.valueOf(((ExpressionBinary) v4).op.getText()));
		assertEquals("45", String.valueOf(((ExpressionBinary) v4).e1.getFirstToken().getText()));
		Expression v5 = ((ExpressionBinary) v4).e0;
		assertThat("", v5, instanceOf(ExpressionBinary.class));
		assertEquals(">", String.valueOf(((ExpressionBinary) v5).op.getText()));
		assertEquals("7", String.valueOf(((ExpressionBinary) v5).e1.getFirstToken().getText()));
		Expression v6 = ((ExpressionBinary) v5).e0;
		assertThat("", v6, instanceOf(ExpressionBinary.class));
		assertEquals("#", String.valueOf(((ExpressionBinary) v6).op.getText()));
		assertEquals("10", String.valueOf(((ExpressionBinary) v6).e1.getFirstToken().getText()));
		Expression v7 = ((ExpressionBinary) v6).e0;
		assertThat("", v7, instanceOf(ExpressionBinary.class));
		assertEquals("=", String.valueOf(((ExpressionBinary) v7).op.getText()));
		assertEquals("y", String.valueOf(((ExpressionBinary) v7).e1.getFirstToken().getText()));
		assertEquals("x", String.valueOf(((ExpressionBinary) v7).e0.getFirstToken().getText()));
	}

	@Test
	void test_3() throws PLPException {
		String input = """
               ! ((2-a)+4)+3
               .
               """;
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		Statement v1 = ((Block) v0).statement;
		assertThat("", v1, instanceOf(StatementOutput.class));
		Expression v2 = ((StatementOutput) v1).expression;
		assertThat("",v2, instanceOf(ExpressionBinary.class));
		Expression v3 = ((ExpressionBinary) v2).e0;
		assertThat("", v3, instanceOf(ExpressionBinary.class));
		Expression v4 = ((ExpressionBinary) v3).e0;
		assertThat("", v4, instanceOf(ExpressionBinary.class));
		Expression v5 = ((ExpressionBinary) v4).e0;
		assertThat("", v5, instanceOf(ExpressionNumLit.class));
		assertEquals("2", String.valueOf(v5.firstToken.getText()));
		Expression v6 = ((ExpressionBinary) v4).e1;
		assertThat("", v6, instanceOf(ExpressionIdent.class));
		assertEquals("a", String.valueOf(v6.firstToken.getText()));
		Expression v7 = ((ExpressionBinary) v3).e1;
		assertThat("", v7, instanceOf(ExpressionNumLit.class));
		assertEquals("4", String.valueOf(v7.firstToken.getText()));
		Expression v8 = ((ExpressionBinary) v2).e1;
		assertThat("", v8, instanceOf(ExpressionNumLit.class));
		assertEquals("3", String.valueOf(v8.firstToken.getText()));
		IToken v9 = ((ExpressionBinary) v2).op;
		assertEquals("+", String.valueOf(v9.getText()));
		IToken v10 = ((ExpressionBinary) v3).op;
		assertEquals("+", String.valueOf(v10.getText()));
		IToken v11 = ((ExpressionBinary) v4).op;
		assertEquals("-", String.valueOf(v11.getText()));
	}

	// Test if you are checking for EOF
	@Test
	void test_4() {
		String input = """
				VAR abc;
				. bruh
				""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void test_5() throws PLPException {
		String input = """
               ! \"Spooky Month\";
               ? input
               .
           """;
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void aTest() throws PLPException
	{
		String input = """
        	PROCEDURE;
        	.
    	""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void aTest2() throws PLPException
	{
		String input = """
        	PROCEDURE a;
   		 	VAR b;
   		 CONST abc = 5
        	.
    	""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void aTest3() throws PLPException
	{
		String input = """
        	VAR abc = 5;
        	! abc
        	.
    	""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void aTest4() throws PLPException
	{
		String input = """
        	CONST abc = 012;
        	.
    	""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void aTest5() throws PLPException
	{
		String input = """
        	VAR a, b, c;
        	c := a +-/ b
       	 
        	.
    	""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	// Siju -> Is this test correct??
	// Fails because the space is unexpected but our Lexer impl skips spaces and hence we get Tokens in form
	// VAR,a,b,c, and it looks correct for the Parser. Lexer impl needs to be changed to fix this
	@Test
	void aTest6() throws PLPException
	{
		String input = """
        	VAR a b c;
        	.
    	""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}


	//testing comments
	@Test
	void aTest7() throws PLPException
	{
		String input = """
        	//VAR ignore this;
        	VAR var1;
        	.
    	""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(1, v2.size());
		assertThat("", v2.get(0), instanceOf(VarDec.class));
		IToken v3 = ((VarDec) v2.get(0)).ident;
		assertEquals("var1", String.valueOf(v3.getText()));
		List<ProcDec> v4 = ((Block) v0).procedureDecs;
		assertEquals(0, v4.size());
		Statement v5 = ((Block) v0).statement;
		assertThat("", v5, instanceOf(StatementEmpty.class));

	}

	@Test
	void aTest8() throws PLPException
	{
		String input = """
        	CONST TRUE = 1;
        	.
    	""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void aTest9() throws PLPException {
		String input = """
   		 WHILE "xyz"
   		 DO
   		 .
   		 """;
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		Statement v4 = ((Block) v0).statement;
		assertThat("", v4, instanceOf(StatementWhile.class));
		Expression v5 = ((StatementWhile) v4).expression;
		assertThat("", v5, instanceOf(ExpressionStringLit.class));
		IToken v6 = ((ExpressionStringLit) v5).firstToken;
		assertEquals("xyz", v6.getStringValue());
		Statement v7 = ((StatementWhile) v4).statement;
		assertThat("", v7, instanceOf(StatementEmpty.class));
	}

    @Test
	void missingDot() throws PLPException {
		String input = """
        CONST a = 3;
        """;
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void tokenFollowingProgram() throws PLPException {
		String input = """
        .abc
        """;
		assertThrows(SyntaxException.class, () -> {
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void invalidConstDec() throws PLPException {
		String input = """
        CONST alpha = /;
        """;
		assertThrows(SyntaxException.class, () -> {
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void constAndVarDecs0() throws PLPException {
		String input = """
            CONST alpha = 0;
            VAR beta;
            .
        """;
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = v0.constDecs;
		assertEquals(1, v1.size());
		ConstDec v2 = v1.get(0);
		assertEquals(0, (Integer) v2.val);
		assertEquals("alpha", String.valueOf(v2.ident.getText()));
		List<VarDec> v3 = v0.varDecs;
		VarDec v4 = v3.get(0);
		assertEquals("beta", String.valueOf(v4.ident.getText()));
	}

	@Test
	void constAndVarDecs1() throws PLPException {
		String input = """
            CONST alpha = 0, beta = "string";
            CONST gamma = TRUE;
            VAR a, b;
            VAR c;
            .
        """;
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = v0.constDecs;
		assertEquals(3, v1.size());
		ConstDec v2 = v1.get(0);
		assertEquals("alpha", String.valueOf(v2.ident.getText()));
		assertEquals(0, (Integer) v2.val);
		ConstDec v3 = v1.get(1);
		assertEquals("beta", String.valueOf(v3.ident.getText()));
		assertEquals("string", (String) v3.val);
		ConstDec v4 = v1.get(2);
		assertEquals("gamma", String.valueOf(v4.ident.getText()));
		assertEquals(true, (Boolean) v4.val);
		List<VarDec> v5 = v0.varDecs;
		assertEquals(3, v5.size());
		VarDec v6 = v5.get(0);
		assertEquals("a", String.valueOf(v6.ident.getText()));
		VarDec v7 = v5.get(1);
		assertEquals("b", String.valueOf(v7.ident.getText()));
		VarDec v8 = v5.get(2);
		assertEquals("c", String.valueOf(v8.ident.getText()));
	}

	@Test
	void multipleProcedures() throws PLPException {
		String input = """
        PROCEDURE a;
        alpha := 1;
        PROCEDURE b;
        VAR beta;
        beta := FALSE;
        .
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		Block mainBlock = program.block;
		assertEquals(2, mainBlock.procedureDecs.size());
		ProcDec firstProc = mainBlock.procedureDecs.get(0);
		assertEquals("a", String.valueOf(firstProc.ident.getText()));
		Block firstProcBlock = firstProc.block;
		StatementAssign firstProcAssignment = (StatementAssign) firstProcBlock.statement;
		assertEquals("alpha", String.valueOf(firstProcAssignment.ident.getText()));
		assertEquals(1, firstProcAssignment.expression.firstToken.getIntValue());
		ProcDec secondProc = mainBlock.procedureDecs.get(1);
		assertEquals("b", String.valueOf(secondProc.ident.getText()));
		Block secondProcBlock = secondProc.block;
		List<VarDec> secondProcVarDecs = secondProcBlock.varDecs;
		VarDec secondProcVarDec = secondProcVarDecs.get(0);
		assertEquals("beta", String.valueOf(secondProcVarDec.ident.getText()));
		StatementAssign secondProcAssignment = (StatementAssign) secondProcBlock.statement;
		assertEquals("beta", String.valueOf(secondProcAssignment.ident.getText()));
		assertEquals(false, secondProcAssignment.expression.firstToken.getBooleanValue());
	}

	@Test
	void assignment0() throws PLPException {
		String input = """
        a := 5.
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		StatementAssign statement = (StatementAssign) program.block.statement;
		assertEquals("a", String.valueOf(statement.ident.getText()));
		assertEquals(5, statement.expression.firstToken.getIntValue());
	}

	@Test
	void assignment1() throws PLPException {
		String input = """
        a := b < 5 # FALSE
        .
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		StatementAssign statement = (StatementAssign) program.block.statement;
		assertEquals("a", String.valueOf(statement.ident.getText()));
		ExpressionBinary expression = (ExpressionBinary) statement.expression;
		assertEquals(IToken.Kind.NEQ, expression.op.getKind());
		ExpressionBinary leftBooleanExpresison = (ExpressionBinary) expression.e0;
		assertEquals(IToken.Kind.LT, leftBooleanExpresison.op.getKind());
		ExpressionIdent b = (ExpressionIdent) leftBooleanExpresison.e0;
		assertEquals("b", String.valueOf(b.firstToken.getText()));
		assertEquals(IToken.Kind.IDENT, b.firstToken.getKind());
		ExpressionNumLit five = (ExpressionNumLit) leftBooleanExpresison.e1;
		assertEquals(5, five.firstToken.getIntValue());
		ExpressionBooleanLit right = (ExpressionBooleanLit) expression.e1;
		assertEquals(false, right.firstToken.getBooleanValue());
	}

	@Test
	void assignment2() throws PLPException {
		String input = """
        a := b + c - 1
        .
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		StatementAssign statement = (StatementAssign) program.block.statement;
		assertEquals("a", String.valueOf(statement.ident.getText()));
		ExpressionBinary expression = (ExpressionBinary) statement.expression;
		assertEquals(IToken.Kind.MINUS, expression.op.getKind());
		ExpressionBinary leftBooleanExpresison = (ExpressionBinary) expression.e0;
		assertEquals(IToken.Kind.PLUS, leftBooleanExpresison.op.getKind());
		ExpressionIdent b = (ExpressionIdent) leftBooleanExpresison.e0;
		assertEquals("b", String.valueOf(b.firstToken.getText()));
		assertEquals(IToken.Kind.IDENT, b.firstToken.getKind());
		ExpressionIdent c = (ExpressionIdent) leftBooleanExpresison.e1;
		assertEquals("c", String.valueOf(c.firstToken.getText()));
		assertEquals(IToken.Kind.IDENT, c.firstToken.getKind());
		ExpressionNumLit right = (ExpressionNumLit) expression.e1;
		assertEquals(1, right.firstToken.getIntValue());
	}

	@Test
	void assignment3() throws PLPException {
		String input = """
        a := b * 12 / 1
        .
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		StatementAssign statement = (StatementAssign) program.block.statement;
		assertEquals("a", String.valueOf(statement.ident.getText()));
		ExpressionBinary expression = (ExpressionBinary) statement.expression;
		assertEquals(IToken.Kind.DIV, expression.op.getKind());
		ExpressionBinary leftBooleanExpresison = (ExpressionBinary) expression.e0;
		assertEquals(IToken.Kind.TIMES, leftBooleanExpresison.op.getKind());
		ExpressionIdent b = (ExpressionIdent) leftBooleanExpresison.e0;
		assertEquals("b", String.valueOf(b.firstToken.getText()));
		assertEquals(IToken.Kind.IDENT, b.firstToken.getKind());
		ExpressionNumLit twelve = (ExpressionNumLit) leftBooleanExpresison.e1;
		assertEquals(12, twelve.firstToken.getIntValue());
		ExpressionNumLit right = (ExpressionNumLit) expression.e1;
		assertEquals(1, right.firstToken.getIntValue());
	}

	@Test
	void assignment4() throws PLPException {
		String input = """
        a := "test".
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		StatementAssign statement = (StatementAssign) program.block.statement;
		assertEquals("a", String.valueOf(statement.ident.getText()));
		assertEquals("test", statement.expression.firstToken.getStringValue());
	}

	@Test
	void output0() throws PLPException {
		String input = """
        ! 3 + 4 * 5 < a * b
        .
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		StatementOutput statement = (StatementOutput) program.block.statement;
		ExpressionBinary expression = (ExpressionBinary) statement.expression;
		assertEquals(IToken.Kind.LT, expression.op.getKind());
		ExpressionBinary leftOfInequality = (ExpressionBinary) expression.e0;
		assertEquals(IToken.Kind.PLUS, leftOfInequality.op.getKind());
		ExpressionNumLit three = (ExpressionNumLit) leftOfInequality.e0;
		assertEquals(3, three.firstToken.getIntValue());
		ExpressionBinary leftProd = (ExpressionBinary) leftOfInequality.e1;
		assertEquals(IToken.Kind.TIMES, leftProd.op.getKind());
		ExpressionNumLit four = (ExpressionNumLit) leftProd.e0;
		assertEquals(4, four.firstToken.getIntValue());
		ExpressionNumLit five = (ExpressionNumLit) leftProd.e1;
		assertEquals(5, five.firstToken.getIntValue());
		ExpressionBinary rightOfInequality = (ExpressionBinary) expression.e1;
		assertEquals(IToken.Kind.TIMES, rightOfInequality.op.getKind());
		ExpressionIdent a = (ExpressionIdent) rightOfInequality.e0;
		assertEquals("a", String.valueOf(a.firstToken.getText()));
		ExpressionIdent b = (ExpressionIdent) rightOfInequality.e1;
		assertEquals("b", String.valueOf(b.firstToken.getText()));
	}

	@Test
	void output1() throws PLPException {
		String input = """
            ! 3 + (4 - 5)
            .
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		StatementOutput statementOutput = (StatementOutput) program.block.statement;
		ExpressionBinary mainExpr = (ExpressionBinary) statementOutput.expression;
		assertEquals(IToken.Kind.PLUS, mainExpr.op.getKind());
		ExpressionNumLit three = (ExpressionNumLit) mainExpr.e0;
		assertEquals(3, three.firstToken.getIntValue());
		ExpressionBinary parenthesisExpr = (ExpressionBinary) mainExpr.e1;
		assertEquals(IToken.Kind.MINUS, parenthesisExpr.op.getKind());
		ExpressionNumLit four = (ExpressionNumLit) parenthesisExpr.e0;
		assertEquals(4, four.firstToken.getIntValue());
		ExpressionNumLit five = (ExpressionNumLit) parenthesisExpr.e1;
		assertEquals(5, five.firstToken.getIntValue());
	}

	@Test
	void call0() throws PLPException {
		String input = """
            PROCEDURE p;
            a := 3;
        CALL p.
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		List<ProcDec> procDecs = program.block.procedureDecs;
		assertEquals(1, procDecs.size());
		ProcDec proc = procDecs.get(0);
		assertEquals("p", String.valueOf(proc.ident.getText()));
		StatementAssign assign = (StatementAssign) proc.block.statement;
		assertEquals("a", String.valueOf(assign.ident.getText()));
		assertEquals(3, assign.expression.firstToken.getIntValue());
		StatementCall call = (StatementCall) program.block.statement;
		assertEquals("p", String.valueOf(call.ident.getText()));
	}

	@Test
	void input0() throws PLPException {
		String input = """
        ? input.
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		StatementInput statement = (StatementInput) program.block.statement;
		assertEquals("input", String.valueOf(statement.ident.getText()));
	}

	@Test
	void ifThen0() throws PLPException {
		String input = """
            IF b > 5 THEN .
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		StatementIf ifStatement = (StatementIf) program.block.statement;
		ExpressionBinary condition = (ExpressionBinary) ifStatement.expression;
		assertEquals(IToken.Kind.GT, condition.op.getKind());
		ExpressionIdent b = (ExpressionIdent) condition.e0;
		assertEquals("b", String.valueOf(b.firstToken.getText()));
		assertEquals(IToken.Kind.IDENT, b.firstToken.getKind());
		ExpressionNumLit five = (ExpressionNumLit) condition.e1;
		assertEquals(5, five.firstToken.getIntValue());
		assertThat(ifStatement.statement, instanceOf(StatementEmpty.class));
	}

	@Test
	void whileDo0() throws PLPException {
		String input = """
            WHILE c DO
            b := b + 1
            .
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		StatementWhile statementWhile = (StatementWhile) program.block.statement;
		ExpressionIdent c = (ExpressionIdent) statementWhile.expression;
		assertEquals("c", String.valueOf(c.firstToken.getText()));
		assertEquals(IToken.Kind.IDENT, c.firstToken.getKind());
		StatementAssign assignmentStatement = (StatementAssign) statementWhile.statement;
		assertEquals("b", String.valueOf(assignmentStatement.ident.getText()));
		ExpressionBinary assignmentExpr = (ExpressionBinary) assignmentStatement.expression;
		assertEquals(IToken.Kind.PLUS, assignmentExpr.op.getKind());
		ExpressionIdent b = (ExpressionIdent) assignmentExpr.e0;
		assertEquals("b", String.valueOf(b.firstToken.getText()));
		ExpressionNumLit one = (ExpressionNumLit) assignmentExpr.e1;
		assertEquals(1, one.firstToken.getIntValue());
	}

	@Test
	void statementBlock0() throws PLPException {
		String input = """
            BEGIN
            ? input;
            dur := input / 3600;
            ! (dur)
            END .
        """;
		ASTNode ast = getAST(input);
		Program program = (Program) ast;
		StatementBlock statementBlock = (StatementBlock) program.block.statement;
		List<Statement> statements = statementBlock.statements;
		StatementInput statementInput = (StatementInput) statements.get(0);
		assertEquals("input", String.valueOf(statementInput.ident.getText()));
		StatementAssign statementAssign = (StatementAssign) statements.get(1);
		assertEquals("dur", String.valueOf(statementAssign.ident.getText()));
		ExpressionBinary assignmentExpr = (ExpressionBinary) statementAssign.expression;
		assertEquals(IToken.Kind.DIV, assignmentExpr.op.getKind());
		ExpressionIdent inputIdent = (ExpressionIdent) assignmentExpr.e0;
		assertEquals("input", String.valueOf(inputIdent.firstToken.getText()));
		ExpressionNumLit num = (ExpressionNumLit) assignmentExpr.e1;
		assertEquals(3600, num.firstToken.getIntValue());
		StatementOutput statementOutput = (StatementOutput) statements.get(2);
		ExpressionIdent dur = (ExpressionIdent) statementOutput.expression;
		assertEquals("dur", String.valueOf(dur.firstToken.getText()));
	}

	@Test
	void missingRightParenthesis() throws PLPException {
		String input = """
            ! (right
        """;
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void missingLeftParenthesis() throws PLPException {
		String input = """
            ! left)
            .
        """;
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void invalidStartOfExpression() throws PLPException {
		String input = """
            ! ?
            .
        """;
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void invalidConst() throws PLPException {
		String input = """
        CONST a = b;
        .
        """;
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

	@Test
	void NickTest() throws PLPException{
		String input  = """
				CONST temp = 10, temp2 = TRUE, temp3 = \"HELLO\";
				VAR temp3;
					PROCEDURE myProc;
					VAR temp4;
					temp4 := 6
					;
				temp3 := (10*12)*(4+5) + temp
				.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block first = ((Program)ast).block;
		assertEquals(first.procedureDecs.size(),1);
		assertEquals(String.valueOf(first.procedureDecs.get(0).ident.getText()),"myProc");
		assertEquals(first.constDecs.size(),3);
		assertEquals(first.constDecs.get(0).val,10);
		assertEquals((boolean)first.constDecs.get(1).val,true);
		assertEquals(String.valueOf(first.constDecs.get(2).val),"HELLO");
		assertEquals(first.varDecs.size(),1);
		assertEquals(String.valueOf(first.varDecs.get(0).ident.getText()),"temp3");
		Block second = (first.procedureDecs.get(0).block);
		assertEquals(second.varDecs.size(),1);
		assertEquals(String.valueOf(second.varDecs.get(0).ident.getText()),"temp4");
		StatementAssign firstAssignment =  (StatementAssign) second.statement;
		assertEquals(String.valueOf(firstAssignment.ident.getText()),"temp4");
		StatementAssign secondAssignment =  (StatementAssign) first.statement;
		assertEquals(String.valueOf(secondAssignment.ident.getText()),"temp3");
	}


	// Tests added after the result
	// TODO: Check the tests written below.
	@Test
	void test__14() throws PLPException {
		String input = """
			CONST a=3;
			VAR x,y,z;
			PROCEDURE p;
			  VAR j;
			  BEGIN
				? x;
				IF x = 0 THEN ! y ;
			  END;
			.
			""";
		ASTNode ast = getAST(input);
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
		List<VarDec> v4 = ((Block) v0).varDecs;
		assertEquals(3, v4.size());
		assertThat("", v4.get(0), instanceOf(VarDec.class));
		IToken v5 = ((VarDec) v4.get(0)).ident;
		assertEquals("x", String.valueOf(v5.getText()));
		assertThat("", v4.get(1), instanceOf(VarDec.class));
		IToken v6 = ((VarDec) v4.get(1)).ident;
		assertEquals("y", String.valueOf(v6.getText()));
		assertThat("", v4.get(2), instanceOf(VarDec.class));
		IToken v7 = ((VarDec) v4.get(2)).ident;
		assertEquals("z", String.valueOf(v7.getText()));
		List<ProcDec> v8 = ((Block) v0).procedureDecs;
		assertEquals(1, v8.size());
		assertThat("", v8.get(0), instanceOf(ProcDec.class));
		IToken v9 = ((ProcDec) v8.get(0)).ident;
		assertEquals("p", String.valueOf(v9.getText()));
		Block v10 = ((ProcDec) v8.get(0)).block;
		assertThat("", v10, instanceOf(Block.class));
		List<ConstDec> v11 = ((Block) v10).constDecs;
		assertEquals(0, v11.size());
		List<VarDec> v12 = ((Block) v10).varDecs;
		assertEquals(1, v12.size());
		assertThat("", v12.get(0), instanceOf(VarDec.class));
		IToken v13 = ((VarDec) v12.get(0)).ident;
		assertEquals("j", String.valueOf(v13.getText()));
		List<ProcDec> v14 = ((Block) v10).procedureDecs;
		assertEquals(0, v14.size());
		Statement v15 = ((Block) v10).statement;
		assertThat("", v15, instanceOf(StatementBlock.class));
		List<Statement> v16 = ((StatementBlock) v15).statements;
		assertThat("", v16.get(0), instanceOf(StatementInput.class));
		Ident v17 = ((StatementInput) v16.get(0)).ident;
		assertEquals("x", String.valueOf(v17.getText()));
		assertThat("", v16.get(1), instanceOf(StatementIf.class));
		Expression v18 = ((StatementIf) v16.get(1)).expression;
		assertThat("", v18, instanceOf(ExpressionBinary.class));
		Expression v19 = ((ExpressionBinary) v18).e0;
		assertThat("", v19, instanceOf(ExpressionIdent.class));
		IToken v20 = ((ExpressionIdent) v19).firstToken;
		assertEquals("x", String.valueOf(v20.getText()));
		Expression v21 = ((ExpressionBinary) v18).e1;
		assertThat("", v21, instanceOf(ExpressionNumLit.class));
		IToken v22 = ((ExpressionNumLit) v21).firstToken;
		assertEquals("0", String.valueOf(v22.getText()));
		IToken v23 = ((ExpressionBinary) v18).op;
		assertEquals("=", String.valueOf(v23.getText()));
		Statement v24 = ((StatementIf) v16.get(1)).statement;
		assertThat("", v24, instanceOf(StatementOutput.class));
		Expression v25 = ((StatementOutput) v24).expression;
		assertThat("", v25, instanceOf(ExpressionIdent.class));
		IToken v26 = ((ExpressionIdent) v25).firstToken;
		assertEquals("y", String.valueOf(v26.getText()));
	}


	@Test
	void test__25() throws PLPException {
		String input = """
			PROCEDURE x;
				 CALL x;
			PROCEDURE y;
				 !x;
			PROCEDURE z;
				 VAR y,z;
			;
			.
			""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(3, v3.size());
		assertThat("", v3.get(0), instanceOf(ProcDec.class));
		Statement v4 = ((Block) v3.get(0).block).statement;
		assertThat("", v4, instanceOf(StatementCall.class));
		Ident v5 = ((StatementCall) v4).ident;
		assertEquals("x", String.valueOf(v5.getText()));
		assertThat("", v3.get(1), instanceOf(ProcDec.class));
		Statement v6 = ((Block) v3.get(1).block).statement;
		assertThat("", v6, instanceOf(StatementOutput.class));
		Expression v7 = ((StatementOutput) v6).expression;
		assertThat("", v7, instanceOf(ExpressionIdent.class));
		assertEquals("x", String.valueOf(v7.getFirstToken().getText()));
		assertThat("", v3.get(2), instanceOf(ProcDec.class));
		Block v8 = ((Block) v3.get(2).block);
		List<VarDec> v9 = v8.varDecs;
		assertEquals(2, v9.size());
		assertThat("", v9.get(0), instanceOf(VarDec.class));
		IToken v10 = ((VarDec) v9.get(0)).ident;
		assertEquals("y", String.valueOf(v10.getText()));
		assertThat("", v9.get(1), instanceOf(VarDec.class));
		IToken v11 = ((VarDec) v9.get(1)).ident;
		assertEquals("z", String.valueOf(v11.getText()));
	}


	@Test
	void test__28() throws PLPException {
		String input = """
			PROCEDURE X;
				 PROCEDURE Y;
						 PROCEDURE Z;
								 CALL XYZ;
				 ;
			;
			.
			""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(0, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(0, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(1, v3.size());
		assertThat("", v0.statement, instanceOf(StatementEmpty.class));
		assertEquals("X", String.valueOf(v3.get(0).ident.getText()));
		assertThat("", v3.get(0), instanceOf(ProcDec.class));
		Block v4 = (Block) v3.get(0).block;
		assertEquals(0, v4.varDecs.size());
		assertEquals(0, v4.constDecs.size());
		assertThat("", v4.statement, instanceOf(StatementEmpty.class));
		assertEquals(1, v4.procedureDecs.size());
		List<ProcDec> v5 = v4.procedureDecs;
		assertEquals("Y", String.valueOf(v5.get(0).ident.getText()));
		Block v6 = (Block) v5.get(0).block;
		assertEquals(0, v6.varDecs.size());
		assertEquals(0, v6.constDecs.size());
		assertThat("", v6.statement, instanceOf(StatementEmpty.class));
		assertEquals(1, v6.procedureDecs.size());
		List<ProcDec> v7 = v6.procedureDecs;
		assertEquals("Z", String.valueOf(v7.get(0).ident.getText()));
		Block v8 = (Block) v7.get(0).block;
		assertEquals(0, v8.varDecs.size());
		assertEquals(0, v8.constDecs.size());
		assertThat("", v8.statement, instanceOf(StatementCall.class));
		assertEquals("XYZ", String.valueOf(((StatementCall) v8.statement).ident.getText()));
		assertEquals(0, v8.procedureDecs.size());
	}


	@Test
	void test__35() throws SyntaxException {
		String input = """
			CALL ident;.
			""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}


	@Test
	void test__39() throws LexicalException{
		String input = """
			<=a.
			""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}


	@Test
	void test__41() throws LexicalException {
		String input = """
			@ abc:=4.
			""";
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});

	}

	// Test to check if an empty statement is appended at the end if the last statement in
	// BEGIN...END ends with a semi-colon.
	@Test
	void test__42() throws PLPException {
		String input = """
				CONST n=42, s="this is a string", x=TRUE;
				VAR a,b,c,d,e,f,g;
				BEGIN
				a := 4;
				b := n + 4;
				c := b - a;
				d := s + s;
				e := a*b;
				f := a/b;
				g := a%b;
				END
				.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(3, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(7, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		StatementBlock statementBlock = (StatementBlock) v0.statement;
		List<Statement> statements = statementBlock.statements;
		assertEquals(8, statements.size());
		assertThat("", statements.get(7), instanceOf(StatementEmpty.class));
	}

	// If the last statement inside BEGIN..END does not end with
	// a semi-colon then no empty statement should be added
	@Test
	void test__43() throws PLPException {
		String input = """
				CONST n=42, s="this is a string", x=TRUE;
				VAR a,b,c,d,e,f,g;
				BEGIN
				a := 4;
				b := n + 4;
				c := b - a;
				d := s + s;
				e := a*b;
				f := a/b;
				g := a%b
				END
				.
				""";
		ASTNode ast = getAST(input);
		assertThat("", ast, instanceOf(Program.class));
		Block v0 = ((Program) ast).block;
		assertThat("", v0, instanceOf(Block.class));
		List<ConstDec> v1 = ((Block) v0).constDecs;
		assertEquals(3, v1.size());
		List<VarDec> v2 = ((Block) v0).varDecs;
		assertEquals(7, v2.size());
		List<ProcDec> v3 = ((Block) v0).procedureDecs;
		assertEquals(0, v3.size());
		StatementBlock statementBlock = (StatementBlock) v0.statement;
		List<Statement> statements = statementBlock.statements;
		assertEquals(7, statements.size());
		assertThat("", statements.get(6), instanceOf(StatementAssign.class));
	}

	// Should throw and exception if VAR is defined before CONST
	// TODO: SIJU Check this once
	@Test
	void test__44() throws PLPException {
		String input = """
    			VAR a,b,c,d,e,f,g;
				CONST n=42, s="this is a string", x=TRUE;
				BEGIN
				a := 4;
				END
				.
				""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

//	Fix this what if statement does not start with intended token
 //	@Test
	void test__45() throws PLPException {
		String input = """
				CONST a=3;
				VAR x,y,z;
				PROCEDURE p;
                    VAR j;
                    BEGIN
                        ! j ;
                        IF j > 24 THEN z:=5;
                    END
                    ;
                BEGIN   
                CALL p
				! z
				END
				.
				""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

//	@Test
	void test__46() throws PLPException {
		String input = """
				CONST a=3;
				VAR x,y,z;
				PROCEDURE p;
                    VAR j;
                    BEGIN
                        ! j ;
                        WHILE j > 24 DO z:=5
                    END
                    ;
                BEGIN   
                CALL p
				! z
				END
				.
				""";
		assertThrows(SyntaxException.class, () -> {
			@SuppressWarnings("unused")
			ASTNode ast = getAST(input);
		});
	}

}
