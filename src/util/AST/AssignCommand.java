package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class AssignCommand extends Command {
	private ID id;
	private Expression exp;
	
	public AssignCommand(ID id, Expression exp) {
		this.id = id;
		this.exp = exp;
	}
	
	public String toString(){
		String assign = "\n[AssignCommand " + id.toString() +  exp.toString() + " AssignCommand]\n";
		return assign;
	}

	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitAssignCommand(this, o);
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		this.exp = exp;
	}
}
