package checker;

import java.util.ArrayList;

import util.AST.AST;
import util.AST.ArgumentList;
import util.AST.AssignCommand;
import util.AST.BeginCommand;
import util.AST.BreakCommand;
import util.AST.ContinueCommand;
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
import util.AST.TypeBool;
import util.AST.TypeInteger;
import util.AST.VariableDeclaration;
import util.AST.WhileCommand;

public interface Visitor {

	public Object visitProgram(Program p, ArrayList<AST> list) throws SemanticException;
	public Object visitArgumentList(ArgumentList al, ArrayList<AST> list) throws SemanticException;  
	public Object visitAssignCommand(AssignCommand ac, ArrayList<AST> list) throws SemanticException;
	public Object visitBeginCommand(BeginCommand bc, ArrayList<AST> list) throws SemanticException;
	public Object visitBreakCommand(BreakCommand br, ArrayList<AST> list) throws SemanticException;
	public Object visitContinueCommand(ContinueCommand cc, ArrayList<AST> list) throws SemanticException;
	public Object visitExpression(Expression e, ArrayList<AST> list) throws SemanticException; 
	public Object visitExpressionA(ExpressionA ea, ArrayList<AST> list) throws SemanticException; 
	public Object visitFactorExpression(FactorExpression p, ArrayList<AST> list) throws SemanticException; 
	public Object visitFatorBoolean(FatorBoolean p, ArrayList<AST> list) throws SemanticException;  
	public Object visitFatorFunctionCall(FatorFunctionCall p, ArrayList<AST> list) throws SemanticException; 
	public Object visitFatorID(FatorID p, ArrayList<AST> list) throws SemanticException;
	public Object visitFatorInteger(FatorInteger p, ArrayList<AST> list) throws SemanticException;
	public Object visitFunctionCall(FunctionCall c, ArrayList<AST> list) throws SemanticException;
	public Object visitFunctionDeclaration(FunctionDeclaration f, ArrayList<AST> list) throws SemanticException;
	public Object visitGlobalVariavelDeclaration(GlobalVariableDeclaration g, ArrayList<AST> list) throws SemanticException;
	public Object visitID(ID id, ArrayList<AST> list) throws SemanticException;
	public Object visitIfCommand(IfCommand c, ArrayList<AST> list) throws SemanticException;  
	public Object visitLocalVariableDeclaration(LocalVariableDeclaration lv, ArrayList<AST> list) throws SemanticException;  
	public Object visitLogic(Logic l, ArrayList<AST> list) throws SemanticException; 
	public Object visitNumbers(Numbers n, ArrayList<AST> list)throws SemanticException;
	public Object visitOperator(Operator op, ArrayList<AST> list);
	public Object visitParameterList(ParameterList p, ArrayList<AST> list) throws SemanticException;
	public Object visitPrintCommand(PrintCommand print, ArrayList<AST> list) throws SemanticException;
	public Object visitProcedureCommand(ProcedureCommand pr, ArrayList<AST> list) throws SemanticException;
	public Object visitReturnCommand(ReturnCommand r, ArrayList<AST> list) throws SemanticException;
	public Object visitTermo(Termo te, ArrayList<AST> list) throws SemanticException;
	public Object visitTypeBool(TypeBool tb, ArrayList<AST> list) throws SemanticException;
	public Object visitTypeInteger(TypeInteger ti, ArrayList<AST> list) throws SemanticException;
	public Object visitVariableDeclaration(VariableDeclaration vd, ArrayList<AST> list) throws SemanticException;
	public Object visitWhileCommand(WhileCommand wc, ArrayList<AST> list) throws SemanticException;  
	
	
	
	
}
