package encoder;

import java.util.ArrayList;
import java.util.Hashtable;

import checker.SemanticException;
import checker.Visitor;
import parser.GrammarSymbols;
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

public class Encoder implements Visitor{
	private ObjectCode objectCode;
	private Hashtable<String, Integer> vars;
	private int localVarPosition;
	private int countIf;
	private int countWhile;
	private int countCmp;
	
	public Encoder(){
		objectCode = new ObjectCode();
	}
	
	public void emit(String section, String instruction) {
		objectCode.addInstruction(section, new Instruction(instruction));
	}

	public void emit(String instruction) {
		emit("text", instruction);
	}
	
	public void encode(Program p){
		try {
			p.visit(this, new ArrayList<AST>());
			objectCode.generateCode();
		} catch (SemanticException e) {
			System.err.println(e);
		}
	}
	
	public Object visitProgram(Program p, ArrayList<AST> list) throws SemanticException {
		emit ("extern", "extern _printf");
		
		for(DeclarationCommand dc : p.getDeclarations()){
			dc.visit(this, list);
		}
		
		emit("data", "intFormat: db \"%d\", 10, 0");
		
		
		if(p.getCommands().getCommands() != null){
			p.getCommands().visit(this, list);
		}
		
		return null;
	}

	public Object visitFunctionDeclaration(FunctionDeclaration f, ArrayList<AST> list) throws SemanticException {
		vars = new Hashtable<String, Integer>();
		localVarPosition = 0;
		countIf = countWhile = 1;
		countCmp = 1;
		
		if(f.getPl() != null){
			f.getPl().visit(this, list);
		}
		
		emit("_" + f.getId().getSpelling() + ":");
		list.add(f);
		
		for(DeclarationCommand dc : f.getDeclarations()){
			dc.visit(this, list);
		}
		
		f.getCommands().visit(this, list);
		
		list.remove(f);
		return null;
	}
	
	public Object visitProcedureCommand(ProcedureCommand pr, ArrayList<AST> list) throws SemanticException {
		vars = new Hashtable<String, Integer>();
		localVarPosition = 0;
		countIf = countWhile = 1;
		countCmp = 1;
		
		if(pr.getPl() != null){
			pr.getPl().visit(this, list);
		}
		
		emit("_" + pr.getId().getSpelling() + ":");
		list.add(pr);
		
		for(DeclarationCommand dc : pr.getDeclarations()){
			if(dc != null){
				dc.visit(this, list);
			}
		}
		
		pr.getCommands().visit(this, list);
		
		list.remove(pr);
		return null;
	}
	
	public Object visitFunctionCall(FunctionCall c, ArrayList<AST> list) throws SemanticException {
		list.add(c);
		if(c.getAl() != null){
			c.getAl().visit(this, list);
			emit("call _" + c.getId().getSpelling());
			emit("add esp" + c.getAl().getExps().size() * 4);
		}
		emit("call _" + c.getId().getSpelling());

		if(list.get(list.size() - 1) instanceof FatorFunctionCall) {
			emit("push eax");
		}
		list.remove(c);
		return null;
	}
	
	public Object visitParameterList(ParameterList parList, ArrayList<AST> list) throws SemanticException {
		int size = parList.getVarDecList().size();
		for (int i = 0; i < size; i++) {
			vars.put(parList.getVarDecList().get(i).getId().getSpelling(), (size - i + 1) * 4);
		}
		return null;
	}
	
	public Object visitBeginCommand(BeginCommand bc, ArrayList<AST> list) throws SemanticException {
		if(bc.getCommands() != null){
			emit("push ebp");
			emit("mov ebp, esp");
			for(Command cmd : bc.getCommands()){
				cmd.visit(this, list);
			}
			emit("mov esp, ebp");
			emit("pop ebp");
			emit("ret");
			emit("");
		}
		return null;
	}
	
	public Object visitArgumentList(ArgumentList al, ArrayList<AST> list) throws SemanticException {
		for(Expression exp : al.getExps()){
			exp.visit(this, list);
		}
		return null;
	}

	public Object visitReturnCommand(ReturnCommand r, ArrayList<AST> list) throws SemanticException {
		if(r.getExp() != null){
			r.getExp().visit(this, list);
			emit("pop eax");
		}
		
		return null;
	}

	public Object visitLogic(Logic l, ArrayList<AST> list) throws SemanticException {
		emit("push dword " + (l.getSpelling().equals("true") ? "1" : "0"));
		return null;
	}

	public Object visitNumbers(Numbers n, ArrayList<AST> list) throws SemanticException {
		emit("push dword " + n.getSpelling());
		return null;
	}
	
	public Object visitOperator(Operator op, ArrayList<AST> list) {
		return null;
	}
	
	public Object visitTypeBool(TypeBool tb, ArrayList<AST> list) throws SemanticException {
		return null;
	}

	public Object visitTypeInteger(TypeInteger ti, ArrayList<AST> list) throws SemanticException {
		return null;
	}
	
	public Object visitFatorID(FatorID p, ArrayList<AST> list) throws SemanticException {
		return p.getId().visit(this, list);
	}
	
	public Object visitFatorBoolean(FatorBoolean p, ArrayList<AST> list) throws SemanticException {
		return p.getBool().visit(this, list);
	}
	

	public Object visitFatorInteger(FatorInteger p, ArrayList<AST> list) throws SemanticException {
		return p.getNumber().visit(this, list);
	}
	
	public Object visitFactorExpression(FactorExpression p, ArrayList<AST> list) throws SemanticException {
		p.getExp().visit(this, list);
		return null;
	}
	
	public Object visitFatorFunctionCall(FatorFunctionCall p, ArrayList<AST> list) throws SemanticException {
		list.add(p);
		p.getFc().visit(this, list);
		list.remove(p);
		return null;
	}
	

	public Object visitVariableDeclaration(VariableDeclaration vd, ArrayList<AST> list) throws SemanticException {
		return null;
	}
	

	public Object visitPrintCommand(PrintCommand print, ArrayList<AST> list) throws SemanticException {
		print.getExp().visit(this, list);
		emit("push dword intFormat");
		emit("call _printf");
		emit("add esp, 8");
		return null;
	}
	
	public Object visitGlobalVariavelDeclaration(GlobalVariableDeclaration g, ArrayList<AST> list) throws SemanticException {
		emit("data", g.getId().getSpelling() + ": dd 0");
		return null;
	}

	public Object visitAssignCommand(AssignCommand asgn, ArrayList<AST> list) throws SemanticException {
		asgn.getExp().visit(this, list);
		switch (((VariableDeclaration) asgn.getId().getDeclaration()).getScope()) {
		case 0:
			emit("pop dword [" + asgn.getId().getSpelling() + "]");
			break;
		case 1:
			emit("pop dword [ebp+" + vars.get(asgn.getId().getSpelling()) + "]");
			break;
		default:
			emit("pop dword [ebp" + vars.get(asgn.getId().getSpelling()) + "]");
		}
		return null;
	}
	

	public Object visitLocalVariableDeclaration(LocalVariableDeclaration localVarDec, ArrayList<AST> list) throws SemanticException {
		vars.put(localVarDec.getVd().getId().getSpelling(), localVarPosition -= 4);
		emit("sub esp, 4");
		emit("push dword 0");
		emit("pop dword [ebp" + vars.get(localVarDec.getVd().getId().getSpelling()) + "]");
		return null;
	}
	

	public Object visitID(ID id, ArrayList<AST> list) throws SemanticException {
		switch (((VariableDeclaration) id.getDeclaration()).getScope()) {
		case 0:
			emit("push dword [" + id.getSpelling() + "]");
			break;
		case 1:
			emit("push dword [ebp+" + vars.get(id.getSpelling()) + "]");
			break;
		default:
			emit("push dword [" + vars.get(id.getSpelling()) + "]");
		}
		return id.getDeclaration();
	}
	
	public Object visitBreakCommand(BreakCommand br, ArrayList<AST> list) throws SemanticException {
		String labelWhileEnd = null;
		int countWhile = 0;
		
		for (AST ast : list) {
			if (ast instanceof FunctionDeclaration) {
				labelWhileEnd = ((FunctionDeclaration) ast).getId().getSpelling();
			}
			if (ast instanceof WhileCommand) {
				countWhile++;
			}
		}
		labelWhileEnd += "_while_" + countWhile + "_end"; 
		emit("jmp " + labelWhileEnd);
		return null;
	}
	
	public Object visitContinueCommand(ContinueCommand cc, ArrayList<AST> list) throws SemanticException {
		String labelWhileBegin = null;
		int countWhile = 0;
		for (AST ast : list) {
			if (ast instanceof FunctionDeclaration) {
				labelWhileBegin = ((FunctionDeclaration) ast).getId().getSpelling();
			}
			if (ast instanceof WhileCommand) {
				countWhile++;
			}
		}
		labelWhileBegin += "_while_" + countWhile + "_begin";
		emit("jmp " + labelWhileBegin);
		return null;
	}
	
	private int retornavalor(String str){
		
		if(str == ">"){
			return GrammarSymbols.MAIOR;
		}else if(str == "<"){
			return GrammarSymbols.MENOR;
		}else if(str == ">="){
			return GrammarSymbols.MAIORQ;
		}else if(str == "<="){
			return GrammarSymbols.MENORQ;
		}else if(str == "="){
			return GrammarSymbols.EQ;
		}else if(str == "<>"){
			return GrammarSymbols.DIF;
		}else if(str == "+"){
			return GrammarSymbols.SOM;
		}else if(str == "-"){
			return GrammarSymbols.SUB;
		}else if(str == "/"){
			return GrammarSymbols.DIV;
		}else if(str == "*"){
			return GrammarSymbols.MULT;
		}
		return 0;
	}

	public Object visitExpression(Expression exp, ArrayList<AST> list) throws SemanticException {
		String functionId = "";

		for (AST ast : list) {
			if (ast instanceof FunctionDeclaration) {
				functionId = ((FunctionDeclaration) ast).getId().getSpelling();
				break;
			}
		}
		
		if (exp.getArithExp() == null) {
			exp.getAe().visit(this, list);
		} else {
			exp.getAe().visit(this, list);
			exp.getArithExp().getAst().visit(this, list);
			emit("pop ebx");
			emit("pop eax");
			emit("cmp eax, ebx");
			switch (retornavalor(exp.getArithExp().getOp().getSpelling())) {
			case 13:
				emit(functionId + "jne _false_cmp_" + countCmp);
				break;
			case 18:
				emit(functionId + "je _false_cmp_" + countCmp);
				break;
			case 17:
				emit(functionId + "jge _false_cmp_" + countCmp);
				break;
			case 15:
				emit(functionId + "jg _false_cmp_" + countCmp);
				break;
			case 16:
				emit(functionId + "jle _false_cmp_" + countCmp);
				break;
			case 14:
				emit(functionId + "jl _false_cmp_" + countCmp);
				break;
			}
			emit("push dword 1");
			emit("jmp" + functionId + "_end_cmp_" + countCmp);
			emit(functionId + "_false_cmp_" + countCmp + ":");
			emit("push dword 0");
			emit(functionId + "_end_cmp_" + countCmp + ":");
			countCmp++;
		}
		return null;
	}

	public Object visitExpressionA(ExpressionA ea, ArrayList<AST> list) throws SemanticException {
		if (ea.getTerms().isEmpty()) {
			ea.getT().visit(this, list);
		} else {
			int i = 0;
			for (Tuple t : ea.getTerms()) {
				if (i == 0) {
					ea.getT().visit(this, list);
					i++;
				}
				t.getAst().visit(this, list);
				emit("pop ebx");
				emit("pop eax");
				if (t.getOp().getSpelling().equals("+")) {
					emit("add eax, ebx");
				} else {
					emit("sub eax, ebx");
				}
				emit("push eax");
			}
		}
		return null;
	}

	public Object visitIfCommand(IfCommand cmd, ArrayList<AST> list) throws SemanticException {
		String labelEndIf = null;
		String labelElse = null;
		String functionId = "";
		for (AST ast : list) {
			if (ast instanceof FunctionDeclaration) {
				functionId = ((FunctionDeclaration) ast).getId().getSpelling();
				break;
			}
		}
		labelEndIf = functionId + "_if_" + countIf + "_end";
		labelElse = functionId + "_else_" + countIf;
		cmd.getExp().visit(this, list);
		emit("push dword 0");
		emit("pop ebx");
		emit("pop eax");
		emit("cmp eax, ebx");
		countIf++;
		if (cmd.getElsecommands().size() != 0) {
			emit("je " + labelElse);
			for (Command c : cmd.getCommands()) {
				c.visit(this, list);
			}
			emit("jmp " + labelEndIf);
			emit(labelElse + ":");
			for (Command c : cmd.getElsecommands()) {
				c.visit(this, list);
			}
		} else {
			emit("je " + labelEndIf);
			for (Command c : cmd.getCommands()) {
				c.visit(this, list);
			}
		}
		emit(labelEndIf + ":");
		return null;
	}

	public Object visitTermo(Termo te, ArrayList<AST> list) throws SemanticException {
		if (te.getFactors().isEmpty()) {
			te.getF().visit(this, list);
		} else {
			int i = 0;
			for (Tuple t : te.getFactors()) {
				if (i == 0) {
					te.getF().visit(this, list);
					i++;
				}
				t.getAst().visit(this, list);
				emit("pop ebx");
				emit("pop eax");
				if (t.getOp().getSpelling().equals("*")) {
					emit("imul eax, ebx");
				} else {
					emit("div eax, ebx");
				}
				emit("push eax");
			}
		}
		return null;
	}

	public Object visitWhileCommand(WhileCommand wc, ArrayList<AST> list) throws SemanticException {
		String labelWhileBegin = null;
		String labelWhileEnd = null;
		for (AST ast : list) {
			if (ast instanceof FunctionDeclaration) {
				labelWhileBegin = ((FunctionDeclaration) ast).getId().getSpelling();
				labelWhileEnd = labelWhileBegin;
				break;
			}
		}
		labelWhileBegin += "_while_" + countWhile + "_begin";
		labelWhileEnd += "_while_" + countWhile + "_end";		
		countWhile++;
		
		emit(labelWhileBegin + ":");
		wc.getExp().visit(this, list);
		emit("push 0");
		emit("pop ebx");
		emit("pop eax");
		emit("cmp eax, ebx");
		emit("je " + labelWhileEnd);
		list.add(wc);
		for (Command c : wc.getBc().getCommands()) {
			c.visit(this, list);
		}
		list.remove(wc);
		emit("jmp " + labelWhileBegin);
		emit(labelWhileEnd + ":");
		
		return null;
	}
}
