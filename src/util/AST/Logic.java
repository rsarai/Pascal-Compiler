package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class Logic extends AST{
	private String spelling;
	
	public Logic(String spelling) {
		this.spelling = spelling;
	}
	
	public String toString() {
		return  "[LOGIC " + spelling + " LOGIC]";
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitLogic(this, o);
	}

	public String getSpelling() {
		return spelling;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}
}
