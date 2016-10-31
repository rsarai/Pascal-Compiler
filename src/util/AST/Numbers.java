package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class Numbers extends AST{
	private String spelling;
	public Numbers(String spelling) {
		this.spelling = spelling;
	}
	
	public String toString() {
		return " [NUMBERS " + spelling + " NUMBERS]";
	}

	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitNumbers(this, o);
	}

	public String getSpelling() {
		return spelling;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

}
