package checker;

import java.util.ArrayList;

import util.AST.AST;
import util.AST.ArgumentList;
import util.AST.AssignCommand;
import util.AST.BeginCommand;
import util.AST.BreakCommand;
import util.AST.Command;
import util.AST.ContinueCommand;
import util.AST.DeclarationCommand;
import util.AST.Expression;
import util.AST.ExpressionA;
import util.AST.FactorExpression;
import util.AST.FatorBoolean;
import util.AST.FatorFunctionCall;
import util.AST.FatorID;
import util.AST.FatorInteger;
import util.AST.FunctionCall;
import util.AST.FunctionDeclaration;
import util.AST.GlobalVariableDeclaration;
import util.AST.ID;
import util.AST.IfCommand;
import util.AST.LocalVariableDeclaration;
import util.AST.Logic;
import util.AST.Numbers;
import util.AST.Operator;
import util.AST.ParameterList;
import util.AST.PrintCommand;
import util.AST.ProcedureCommand;
import util.AST.Program;
import util.AST.ReturnCommand;
import util.AST.Termo;
import util.AST.Tuple;
import util.AST.TypeBool;
import util.AST.TypeInteger;
import util.AST.VariableDeclaration;
import util.AST.WhileCommand;
import util.symbolsTable.IdentificationTable;

public class Checker implements Visitor {
	private IdentificationTable idTable = new IdentificationTable();

	public Checker() {

	}

	// OK
	public AST check(Program ast) {
		try {
			return (AST) ast.visit(this, new ArrayList<AST>());
		} catch (SemanticException e) {
			System.err.println(e);
		}
		return null;
	}

	public Object visitFatorBoolean(FatorBoolean p, ArrayList<AST> list) throws SemanticException {
		return p.getBool().visit(this, list);
	}

	public Object visitFatorFunctionCall(FatorFunctionCall p, ArrayList<AST> list) throws SemanticException {
		return p.getFc().visit(this, list);
	}

	public Object visitArgumentList(ArgumentList al, ArrayList<AST> list) throws SemanticException {
		String functionId = null;
		FunctionDeclaration functionDec = null;

		// Recuperar as funcoes declaradas
		for (AST a : list) {
			if (a instanceof FunctionCall) {
				functionId = ((FunctionCall) a).getId().getSpelling();
				break;
			}
		}

		functionDec = (FunctionDeclaration) idTable.retrieve(functionId);

		ArrayList<VariableDeclaration> varDecList = functionDec.getPl().getVarDecList();
		ArrayList<Expression> expList = al.getExps();
		String id = functionDec.getId().getSpelling();

		// REGRA 3 - Ao chamar uma função os tipos dos argumentos devem ser
		// iguais ao dos parâmetros.
		if (expList.size() != varDecList.size()) {
			throw new SemanticException("Função " + id + "tem número errado de argumentos");
		}

		for (int i = 0; i < expList.size(); i++) {
			if (!varDecList.get(i).getType().getSpelling().equals(expList.get(i).visit(this, list))) {
				throw new SemanticException("Função " + id + "tem o tipo errado de argumentos");
			}
		}
		return null;
	}

	public Object visitFunctionCall(FunctionCall functionCall, ArrayList<AST> list) throws SemanticException {
		list.add(functionCall);

		String id = functionCall.getId().getSpelling();
		functionCall.getId().visit(this, list);
		ArrayList<VariableDeclaration> varDec = null;
		// REGRA 1 - Todos os identificadores precisam ser declarados antes de
		// serem utilizados.
		if (!idTable.containsKey(id)) {
			throw new SemanticException("Função " + id + " não foi declarada");
		}

		if (functionCall.getAl() != null) {
			functionCall.getAl().visit(this, list);
		} else {
			if (idTable.retrieve(id) instanceof FunctionDeclaration) {
				FunctionDeclaration fDec = (FunctionDeclaration) idTable.retrieve(id);

				if (fDec.getPl() != null) {
					varDec = fDec.getPl().getVarDecList();
				}
			}
			// REGRA 3 - Ao chamar uma função os tipos dos argumentos devem ser
			// iguais ao dos parâmetros.
			if (varDec != null) {
				throw new SemanticException("Função com número errado de argumentos");
			}
		}

		// Recupera a declaracao de uma funcao da lista de objetos
		String funcID = null;
		FunctionDeclaration funcDec = null;
		for (AST ast : list) {
			if (ast instanceof FunctionCall) {
				funcID = ((FunctionCall) ast).getId().getSpelling();
				break;
			}
		}

		String retorno = null;
		if (idTable.retrieve(funcID) instanceof FunctionDeclaration) {
			funcDec = (FunctionDeclaration) idTable.retrieve(funcID);
			retorno = funcDec.getType().getSpelling();
			;
		}
		list.remove(functionCall);

		// Retorna o tipo da chamada da funcao baseado no tipo declarado na
		// funcao
		return "void";
	}

	public Object visitAssignCommand(AssignCommand ac, ArrayList<AST> list) throws SemanticException {
		String type = (String) ac.getId().visit(this, list);
		String id = ac.getId().getSpelling();

		// REGRA 8 - Em A = B, A deve ser uma variável.
		if ((idTable.retrieve(id) instanceof VariableDeclaration) == false
				&& (idTable.retrieve(id) instanceof GlobalVariableDeclaration) == false
				&& (idTable.retrieve(id) instanceof LocalVariableDeclaration) == false) {
			throw new SemanticException(id + " deve ser uma variavel");
		}

		// REGRA 8 - Em A = B, o tipo de B precisa ser igual ao tipo de A
		if (!type.equals(ac.getExp().visit(this, list))) {
			throw new SemanticException("Assign Error: o tipo de \"" + id + "\" é " + type);
		}
		return null;
	}

	public Object visitLocalVariableDeclaration(LocalVariableDeclaration lv, ArrayList<AST> list)
			throws SemanticException {
		return lv.getVd().visit(this, list);
	}

	public Object visitGlobalVariavelDeclaration(GlobalVariableDeclaration g, ArrayList<AST> list)
			throws SemanticException {
		AST ast = idTable.retrieve(g.getId().getSpelling());

		if (ast != null) {
			throw new SemanticException("REGRA 2");
		} else {
			// Adiciona a variavel na tabela de simbolos
			idTable.enter(g.getId().getSpelling(), g);
			g.setScope(idTable.getScope());
			g.getType().visit(this, list);
			g.getId().visit(this, list);
		}
		return g.getId().getSpelling();
	}

	public Object visitID(ID id, ArrayList<AST> list) throws SemanticException {
		String id2 = id.getSpelling();
		VariableDeclaration varDec = null;
		FunctionDeclaration fd;

		// REGRA 1 - SE A VARIAVEL NÃO TIVER SIDO DECLARADA
		if (!idTable.containsKey(id2)) {
			throw new SemanticException(id + " deve ser declarado");
		}

		// Recupera declarações do id na tabela e decora a AST de ID com o tipo,
		// salvando a AST no ID
		// Checa se o id veio de um declaração de variavel ou de funcao.
		// Decora a AST de ID com o tipo
		AST ast = idTable.retrieve(id.getSpelling());
		id.setDeclaration(ast);

		if (ast instanceof VariableDeclaration) {
			varDec = (VariableDeclaration) idTable.retrieve(id2);
			id.setType(varDec.getType().getSpelling());
		} else if (ast instanceof FunctionDeclaration) {
			fd = (FunctionDeclaration) idTable.retrieve(id2);
			id.setType(fd.getType().getSpelling());
		} else if (ast instanceof GlobalVariableDeclaration) {
			GlobalVariableDeclaration gvarDec = (GlobalVariableDeclaration) idTable.retrieve(id2);
			id.setType(gvarDec.getType().getSpelling());
		} else if (ast instanceof LocalVariableDeclaration) {
			LocalVariableDeclaration lvarDec = (LocalVariableDeclaration) idTable.retrieve(id2);
			id.setType(lvarDec.getVd().getType().getSpelling());
		}

		return id.getType();
	}

	public Object visitParameterList(ParameterList p, ArrayList<AST> list) throws SemanticException {
		for (VariableDeclaration v : p.getVarDecList()) {
			v.visit(this, list);
		}
		return null;
	}

	public Object visitProcedureCommand(ProcedureCommand pr, ArrayList<AST> list) throws SemanticException {
		list.add(pr);
		String id = pr.getId().getSpelling();
		idTable.openScope();
		idTable.enter(id, pr);

		pr.getId().visit(this, list);

		if (pr.getPl() != null) {
			pr.getPl().visit(this, list);
		}

		for (DeclarationCommand d : pr.getDeclarations()) {
			if (d != null) {
				d.visit(this, list);
			}
		}

		boolean hasReturn = false;
		if (!pr.getCommands().getCommands().isEmpty()) {
			hasReturn = (Boolean) pr.getCommands().visit(this, list);
		}

		if (hasReturn) {
			throw new SemanticException("A função " + id + " não deve ter retorno");
		}

		/*
		 * // REGRA 9 - Verifica se na tabela de simbolos tem alguma funcao main
		 * if (idTable.retrieve("main") == null || !(idTable.retrieve("main")
		 * instanceof ProcedureCommand)){ throw new SemanticException(
		 * "A função \"main\" deve ser declarada."); }
		 */

		idTable.closeScope();
		list.remove(pr);
		return pr.getId().getSpelling();
	}

	public Object visitFunctionDeclaration(FunctionDeclaration f, ArrayList<AST> list) throws SemanticException {
		list.add(f);
		String id = f.getId().getSpelling();

		idTable.openScope(); // abre escopo interno para parametros da funcao
		idTable.enter(id, f); // adiciona nome da funcao na tabela de simbolos

		f.getId().visit(this, list);

		if (f.getPl() != null) {
			f.getPl().visit(this, list);
		}

		idTable.openScope(); // abre escopo para variaveis locais
		for (DeclarationCommand d : f.getDeclarations()) {
			if (d != null) {
				d.visit(this, list);
			}
		}

		boolean hasReturn = (Boolean) f.getCommands().visit(this, list);

		// REGRA 4 - Toda função com retorno diferente de void, precisa ter um
		// ou mais return deste tipo
		if (!hasReturn) {
			throw new SemanticException("Pelo menos um comando de retorno deve ser definido por função " + id);
		}

		/*
		 * // REGRA 9 - Verifica se na tabela de simbolos tem alguma funcao main
		 * if (idTable.retrieve("main") == null || !(idTable.retrieve("main")
		 * instanceof FunctionDeclaration) ){ throw new SemanticException(
		 * "A função \"main\" deve ser declarada."); }
		 */

		idTable.closeScope();
		idTable.closeScope();

		list.remove(f);
		return null;
	}

	public Object visitProgram(Program p, ArrayList<AST> list) throws SemanticException {
		idTable.enter(p.getId().getSpelling(), p);
		p.getId().visit(this, list);

		if (!p.getDeclarations().isEmpty()) {
			for (DeclarationCommand dCmd : p.getDeclarations()) {
				dCmd.visit(this, list);

				if (dCmd instanceof ProcedureCommand) {
					// REGRA 9 - Verifica se na tabela de simbolos tem alguma
					// funcao main
					idTable.enter(((ProcedureCommand) dCmd).getId().getSpelling(), dCmd);
				} else if (dCmd instanceof FunctionDeclaration) {
					idTable.enter(((FunctionDeclaration) dCmd).getId().getSpelling(), dCmd);
				}else if(dCmd instanceof VariableDeclaration){
					//idTable.enter(((VariableDeclaration) dCmd).getId().getSpelling(), dCmd);
				}

				
			  if (idTable.retrieve("main") == null 
					  || ( !(idTable.retrieve("main") instanceof ProcedureCommand)
					  && !(idTable.retrieve("main") instanceof FunctionDeclaration)) ){
				  throw new SemanticException("A função \"main\" deve ser declarada."); 
			  }
			  
			  
				 // TIRAR OS COMENTARIOS
			}
		}

		idTable.openScope();
		p.getCommands().visit(this, list);
		idTable.closeScope();

		return p;
	}

	public Object visitBeginCommand(BeginCommand bc, ArrayList<AST> list) throws SemanticException {
		boolean hasWhileReturn = false;
		boolean hasIfReturn = false;
		boolean hasReturn = false;

		for (Command c : bc.getCommands()) {
			if (c != null) {
				c.visit(this, list);
				if (c instanceof ReturnCommand) {
					hasReturn = true;
				}
				if (c instanceof WhileCommand) {
					hasWhileReturn = hasWhileReturn || ((Boolean) c.visit(this, list));
				}
				if (c instanceof IfCommand) {
					hasIfReturn = hasIfReturn || ((Boolean) c.visit(this, list));
				}

				hasReturn = hasReturn || hasWhileReturn || hasIfReturn;
			}
		}
		return hasReturn;
	}

	public Object visitFatorInteger(FatorInteger p, ArrayList<AST> list) throws SemanticException {
		return p.getNumber().visit(this, list);
	}

	public Object visitFatorID(FatorID p, ArrayList<AST> list) throws SemanticException {
		return p.getId().visit(this, list);
	}

	public Object visitFactorExpression(FactorExpression p, ArrayList<AST> list) throws SemanticException {
		return p.getExp().visit(this, list);
	}

	public Object visitPrintCommand(PrintCommand print, ArrayList<AST> list) throws SemanticException {
		print.getExp().visit(this, list);
		return null;
	}

	public Object visitVariableDeclaration(VariableDeclaration vd, ArrayList<AST> list) throws SemanticException {
		AST ast = idTable.retrieve(vd.getId().getSpelling());

		if (ast instanceof VariableDeclaration) {
			throw new SemanticException("REGRA 2");
		} else {
			idTable.enter(vd.getId().getSpelling(), vd); // Adiciona variavel na
															// tabela de
															// simbolos
			vd.setScope(idTable.getScope());
			vd.getType().visit(this, list);
			vd.getId().visit(this, list);
		}
		return vd.getId().getSpelling();
	}

	public Object visitContinueCommand(ContinueCommand cc, ArrayList<AST> list) throws SemanticException {
		// REGRA 6 - Verifica se o continue foi declarado dentro de um while
		for (Object o : list) {
			if (o instanceof WhileCommand) {
				return null;
			}
		}
		throw new SemanticException("Comando BREAK deve estar dentro de um loop");
	}

	public Object visitBreakCommand(BreakCommand br, ArrayList<AST> list) throws SemanticException {
		// REGRA 6 - Verifica se o break foi declarado dentro de um while
		for (Object o : list) {
			if (o instanceof WhileCommand) {
				return null;
			}
		}
		throw new SemanticException("Comando BREAK deve estar dentro de um loop");
	}

	public Object visitLogic(Logic l, ArrayList<AST> list) throws SemanticException {
		return "boolean";
	}

	public Object visitNumbers(Numbers n, ArrayList<AST> list) throws SemanticException {
		return "integer";
	}

	public Object visitOperator(Operator op, ArrayList<AST> list) {
		return op.getSpelling();
	}

	public Object visitTypeBool(TypeBool tb, ArrayList<AST> list) throws SemanticException {
		return "boolean";
	}

	public Object visitTypeInteger(TypeInteger ti, ArrayList<AST> list) throws SemanticException {
		return "integer";
	}

	public Object visitIfCommand(IfCommand c, ArrayList<AST> list) throws SemanticException {
		list.add(c);
		String type = (String) c.getExp().visit(this, list);

		boolean validReturn, hasIfReturn, hasElse, hasElseReturn;
		validReturn = hasIfReturn = hasElse = hasElseReturn = false;

		if ((type.equals("boolean")) && c.getExp().getArithExp() == null) {
			if (c.getExp().getAe().getT().getFactors().toString().contains("true")) {
				validReturn = true;
			}
		}

		for (Command cmd : c.getCommands()) {
			cmd.visit(this, list);
			if (cmd instanceof ReturnCommand) {
				hasIfReturn = true;
			} else if (cmd instanceof IfCommand || cmd instanceof WhileCommand) {
				hasIfReturn = hasIfReturn || (Boolean) c.visit(this, list);
			}
		}

		for (Command cmd : c.getElsecommands()) {
			hasElse = true;
			cmd.visit(this, list);
			if (cmd instanceof ReturnCommand) {
				hasElseReturn = true;
			} else if (cmd instanceof IfCommand || cmd instanceof WhileCommand) {
				hasElseReturn = hasElseReturn || (Boolean) cmd.visit(this, list);
			}
		}

		list.remove(c);

		// Se não houver else, no if deve ter um retorno e sua condicao tem que
		// ser sempre atendida.
		// Se houver um else, deve haver um retorno nele e no if
		return (!hasElse && hasIfReturn && validReturn) || (hasElse && hasIfReturn && hasElseReturn);
	}

	public Object visitReturnCommand(ReturnCommand r, ArrayList<AST> list) throws SemanticException {
		String functionId = null;
		DeclarationCommand functionDec = null;
		String type = "void";

		for (AST a : list) {
			if (a instanceof FunctionDeclaration) {
				functionId = ((FunctionDeclaration) a).getId().getSpelling();
				functionDec = (FunctionDeclaration) idTable.retrieve(functionId);
				String id = (String) ((FunctionDeclaration) functionDec).getId().getSpelling();
				type = (String) ((FunctionDeclaration) functionDec).getType().getSpelling();
				break;
			}

			if (a instanceof ProcedureCommand) {
				functionId = ((ProcedureCommand) a).getId().getSpelling();
				functionDec = (ProcedureCommand) idTable.retrieve(functionId);
				String id = (String) ((ProcedureCommand) functionDec).getId().getSpelling();
				break;
			}
		}

		String exp = null;

		if (r.getExp() != null) {
			exp = (String) r.getExp().visit(this, list);
		}

		// REGRA 5 - Ao retornar um valor, a função em questão deve ter como
		// retorno o mesmo tipo.
		if (!exp.equals(type)) {
			throw new SemanticException("A função em questão deve ter como retorno o tipo " + type);
		}

		// retorna o retorno da função
		return exp;
	}

	public Object visitWhileCommand(WhileCommand wc, ArrayList<AST> list) throws SemanticException {
		list.add(wc);

		String type = (String) wc.getExp().visit(this, list);
		boolean validReturn, hasWhileReturn;
		validReturn = hasWhileReturn = false;

		if ((type.equals("boolean")) && wc.getExp().getArithExp() == null) {
			if (wc.getExp().getAe().getT().getFactors().toString().contains("true")) {
				validReturn = true;
			}
		}

		for (Command c : wc.getBc().getCommands()) {
			c.visit(this, list);
			if (c instanceof ReturnCommand) {
				hasWhileReturn = true;
			} else if (c instanceof IfCommand || c instanceof WhileCommand) {
				hasWhileReturn = hasWhileReturn || (Boolean) c.visit(this, list);
			}
		}

		list.remove(wc);
		return hasWhileReturn && validReturn;
	}

	public Object visitTermo(Termo te, ArrayList<AST> list) throws SemanticException {
		String fator1, resultFator;
		fator1 = resultFator = (String) te.getF().visit(this, list);

		// REGRA 7 - Operadores +, -, * e / devem ser aplicados a operandos int
		if (!te.getFactors().isEmpty()) {
			if (fator1.equals("boolean")) {
				throw new SemanticException("Operadores devem ser aplicados a operandos int");
			}

			String fator2 = null;
			for (Tuple t : te.getFactors()) {
				t.getOp().visit(this, list);
				fator2 = (String) t.getAst().visit(this, list);
				if (fator2.equals("boolean")) {
					throw new SemanticException("Operadores devem ser aplicados a operandos int");
				}
			}

			resultFator = "integer";
		}
		return resultFator;
	}

	public Object visitExpression(Expression e, ArrayList<AST> list) throws SemanticException {
		String expA1, resultExp;
		expA1 = resultExp = (String) e.getAe().visit(this, list);

		if (e.getArithExp() != null) {
			String op = (String) e.getArithExp().getOp().visit(this, list);
			String expA2 = (String) e.getArithExp().getAst().visit(this, list);

			/*
			 * ok Todos os operadores devem ser aplicados a operandos do mesmo
			 * tipo. ok Operadores == e != devem ser aplicados a operandos int,
			 * ou boolean. ok Operadores >, <, >=, <= devem ser aplicados a
			 * operandos int ok Operadores ==, !=, >, <, >=, <= retornam valor
			 * de tipo boolean.
			 */

			// REGRA 7 - Todos os operadores devem ser aplicados a operandos do
			// mesmo tipo.
			if (!expA1.equals(expA2)) {
				throw new SemanticException("Operandos devem ser do mesmo tipo");
			}

			// REGRA 7 - Operadores >, <, >=, <= devem ser aplicados a operandos
			// int
			if ((op.equals(">") || op.equals("<") || op.equals(">=") || op.equals("<=")) && expA1.equals("boolean")) {
				throw new SemanticException("Operadores >, <, >=, <= só podem ser aplicados a operandos int");
			}

			if (op.equals(">") || op.equals("<") || op.equals(">=") || op.equals("<=") || op.equals("<>")
					|| op.equals("=")) {
				resultExp = "boolean";
			}
		}
		// retorna tipo da expressão
		e.setType(resultExp);
		return e.getType();
	}

	public Object visitExpressionA(ExpressionA ea, ArrayList<AST> list) throws SemanticException {
		String term1, resultTerm;
		term1 = resultTerm = (String) ea.getT().visit(this, list);

		if (!ea.getTerms().isEmpty()) {
			/*
			 * Operadores +, -, * e / devem ser aplicados a operandos int
			 * Operadores +, -, * e / retornam o tipo dos seus operandos.
			 */

			// REGRA 7 - Operadores +, -, * e / devem ser aplicados a operandos
			// int
			if (!term1.equals("integer")) {
				throw new SemanticException("Operador definido apenas para elementos tipo int");
			}

			String term2 = null;
			for (Tuple t : ea.getTerms()) {
				t.getOp().visit(this, list);
				term2 = (String) t.getAst().visit(this, list);
				if (!term2.equals("integer")) {
					throw new SemanticException("Operador definido apenas para elementos tipo int");
				}
			}
			resultTerm = "integer";
		}
		return resultTerm;
	}
}