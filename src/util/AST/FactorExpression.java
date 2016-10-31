package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class FactorExpression extends Fator {
	private Expression exp;
	
	public FactorExpression(Expression exp) {
		this.exp = exp;
	}

	@Override
	public String toString() {
		String str = "<FactorExpression>";
		
		str += exp.toString() + "</FactorExpression>"+ "\n";
		
		return str;
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitFactorExpression(this, o);
	}

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		this.exp = exp;
	}
	
	
}
