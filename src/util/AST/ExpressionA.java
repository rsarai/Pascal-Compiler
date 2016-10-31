package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;


public class ExpressionA extends AST{
	private Termo t;
	private ArrayList<Tuple> terms;
	
	public ExpressionA(Termo t, ArrayList<Tuple> terms) {
		this.t = t;
		this.terms = terms;
	}

	@Override
	public String toString() {
		String str = "Term[" + t.toString() + "]"; 
		for(int i=0; i<terms.size(); i++) 
			str += "TupleTerm[" + terms.get(i).toString() + "]";
		return str;
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitExpressionA(this, o);
	}

	public Termo getT() {
		return t;
	}

	public void setT(Termo t) {
		this.t = t;
	}

	public ArrayList<Tuple> getTerms() {
		return terms;
	}

	public void setTerms(ArrayList<Tuple> terms) {
		this.terms = terms;
	}

}