package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class FunctionCall2 extends Command {
	private ID id;
	public FunctionCall2(ID id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "<FunctionCall>" +id.toString() + "</FunctionCall>"+ "\n";
	}

	

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> list) throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

}
