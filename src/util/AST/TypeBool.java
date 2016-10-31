package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class TypeBool extends Type {
	
	public TypeBool(String spelling) {
		super(spelling);
	}

	public String toString() {
		return "[TypeBool " +spelling + " TypeBool]";
	}

	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitTypeBool(this, o);
	}
}
