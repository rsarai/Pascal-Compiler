package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class ID extends Terminal{
	private String type = null;
	private AST declaration = null;
	
	
	public ID(String spelling) {
		this.spelling = spelling;
	}

	@Override
	public String toString() {
		return "[ID  " + spelling + " ID]";
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitID(this, o);
	}

	public String getSpelling() {
		return spelling;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AST getDeclaration() {
		return declaration;
	}

	public void setDeclaration(AST declaration) {
		this.declaration = declaration;
	}
	

}
