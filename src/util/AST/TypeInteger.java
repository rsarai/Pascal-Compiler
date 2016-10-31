package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class TypeInteger extends Type {
	public TypeInteger(String spelling) {
		super(spelling);
	}

	public String toString() {
		return "[TypeInteger " +spelling + " TypeInteger] ";
	}
	
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitTypeInteger(this, o);
	}

	public String getSpelling() {
		return spelling;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}
}
