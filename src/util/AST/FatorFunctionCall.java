package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class FatorFunctionCall extends Fator{
	private FunctionCall fc;
	
	public FatorFunctionCall(FunctionCall fc) {
		this.fc = fc;
	}

	@Override
	public String toString() {
		return "\n[FatorFunctionCall " + fc.toString() + " FatorFunctionCall]"+ "\n";
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitFatorFunctionCall(this, o);
	}

	public FunctionCall getFc() {
		return fc;
	}

	public void setFc(FunctionCall fc) {
		this.fc = fc;
	}

	

}
