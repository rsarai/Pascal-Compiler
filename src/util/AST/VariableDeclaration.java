package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class VariableDeclaration extends DeclarationCommand{
	private Type type;
	private ID id;
	private int scope;

	public VariableDeclaration(Type type, ID id) {
		this.type = type;
		this.id = id;
	}

	@Override
	public String toString() {
		String str = "\n[VariableDeclaration " + type.toString();
		str += id.toString() + " VariableDeclaration]"+ "\n";
		return str;
	}
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitVariableDeclaration(this, o);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

}
