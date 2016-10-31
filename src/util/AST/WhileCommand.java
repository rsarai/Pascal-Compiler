package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class WhileCommand extends Command {
	private Expression exp;
	private BeginCommand bc;
	public WhileCommand(Expression exp, BeginCommand bc) {
		this.exp = exp;
		this.bc = bc;
	}
	
	public String toString() {
		String str = "\n[WHILE " + exp.toString()  + bc.toString() + " WHILE]"+ "\n";
		return str;
	}

	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitWhileCommand(this, o);
	}

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		this.exp = exp;
	}

	public BeginCommand getBc() {
		return bc;
	}

	public void setBc(BeginCommand bc) {
		this.bc = bc;
	}
}
