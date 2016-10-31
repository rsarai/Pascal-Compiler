package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;


public class ParameterList extends AST{
	private ArrayList<VariableDeclaration> varDecList;
	
	public ParameterList(ArrayList<VariableDeclaration> varDeclarations) {
		this.varDecList = varDeclarations;
	}

	@Override
	public String toString() {
		String str = "[ParameterList ";
		
		for(int i=0; i < varDecList.size(); i++) 
			str += " " + varDecList.get(i).toString() + ", ";
		
		str += " ParameterList]";
		return str;
	}
	
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitParameterList(this, o);
	}

	public ArrayList<VariableDeclaration> getVarDecList() {
		return varDecList;
	}

	public void setVarDecList(ArrayList<VariableDeclaration> varDecList) {
		this.varDecList = varDecList;
	}
	

}