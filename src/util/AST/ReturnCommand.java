package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class ReturnCommand extends Command {
	private Expression exp;
	
	public ReturnCommand(Expression exp) {
		this.exp = exp;
	}

	public String toString() {
		String str = "<RETURN>" + exp.toString() + "</RETURN>"+ "\n";
		return str;
	}
	
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitReturnCommand(this, o);
	}

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		this.exp = exp;
	}
}
