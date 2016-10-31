package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class GlobalVariableDeclaration extends VariableDeclaration{
	private int scope;
	
	public GlobalVariableDeclaration(Type type, ID id) {
		super(type, id);
	}

	@Override
	public String toString() {
		return "\n[GlobalVariableDeclaration " + getType().toString() + getId().toString() + " GlobalVariableDeclaration]"+ "\n";
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitGlobalVariavelDeclaration(this, o);
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	

}