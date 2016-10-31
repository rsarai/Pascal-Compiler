package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;


public class ArgumentList extends AST{
	ArrayList<Expression> exps;

	@Override
	public String toString() {
		
		return "\n[Argument List " + exps.toString() + "Argument List]" + "\n";
	}
	public ArgumentList(ArrayList<Expression> expressions){
		this.exps = expressions;
	}
	
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitArgumentList(this, o);
	}
	public ArrayList<Expression> getExps() {
		return exps;
	}
	public void setExps(ArrayList<Expression> exps) {
		this.exps = exps;
	}
	
	
	
}