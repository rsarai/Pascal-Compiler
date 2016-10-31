package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class FatorID extends Fator {
	private ID id;
	
	public FatorID(ID id) {
		this.id  = id;
	}

	@Override
	public String toString() {
		return "[FatorID " + id.toString() + " FatorID]";
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitFatorID(this, o);
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
	

}
