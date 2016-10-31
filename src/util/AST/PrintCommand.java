package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class PrintCommand extends Command {
	private Expression exp;
	
	public PrintCommand(Expression exp) {
		this.exp = exp;
	}

	public String toString() {
		String str = "<PRINT>" + exp.toString() + "</PRINT>"+ "\n";
		return str;
	}

	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitPrintCommand(this, o);
	}

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		this.exp = exp;
	}
}
