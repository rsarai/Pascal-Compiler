package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class Expression extends AST{
	private ExpressionA ae;
	private Tuple arithExp;
	private String type;
	
	public Expression(ExpressionA ae1, Tuple arithExp) {
		this.ae = ae1;
		this.arithExp = arithExp;
	}

	@Override
	public String toString() {
		String str = "AE[" + ae.toString() + "]"; 
		if (arithExp != null)
			str += "TupleAE[" + arithExp.toString() + "]"; 			
		return str;
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitExpression(this, o);
	}

	public ExpressionA getAe() {
		return ae;
	}

	public void setAe(ExpressionA ae) {
		this.ae = ae;
	}

	public Tuple getArithExp() {
		return arithExp;
	}

	public void setArithExp(Tuple arithExp) {
		this.arithExp = arithExp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}