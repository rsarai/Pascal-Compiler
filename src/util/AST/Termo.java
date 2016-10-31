package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class Termo extends AST{
	private Fator f;
	private ArrayList<Tuple> factors;
	
	public Termo(Fator f, ArrayList<Tuple> factors) {
		this.f = f;
		this.factors = factors;
	}

	@Override
	public String toString() {
		String str = "F[" + f.toString() + "]"; 
		for(int i=0; i<factors.size(); i++) 
			str += "TupleF[" + factors.get(i).toString() + "]";	
		return str;
	}

	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitTermo(this, o);
	}

	public Fator getF() {
		return f;
	}

	public void setF(Fator f) {
		this.f = f;
	}

	public ArrayList<Tuple> getFactors() {
		return factors;
	}

	public void setFactors(ArrayList<Tuple> factors) {
		this.factors = factors;
	}

}