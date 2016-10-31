package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class FatorInteger extends Fator{
	private Numbers number;
	
	public FatorInteger(Numbers n) {
		this.number = n;
	}

	@Override
	public String toString() {
		return " [FatorInteger " + number.toString() + " FatorInteger] ";
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitFatorInteger(this, o);
	}

	public Numbers getNumber() {
		return number;
	}

	public void setNumber(Numbers number) {
		this.number = number;
	}
	
	

}
