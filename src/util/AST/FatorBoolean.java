package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class FatorBoolean extends Fator {
	private Logic bool;
	
	public FatorBoolean(Logic n) {
		this.bool = n;
	}

	@Override
	public String toString() {
		return "<Logic>" + bool.toString() + "</Logic>"+ "\n";
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitFatorBoolean(this, o);
	}

	public Logic getBool() {
		return bool;
	}

	public void setBool(Logic number) {
		this.bool = number;
	}

	
}
