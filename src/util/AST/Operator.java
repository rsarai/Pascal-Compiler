package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class Operator extends AST{
	private String spelling;
	private int numero;
	
	public Operator(String spelling) {
		this.spelling = spelling;
	}
	
	public String toString() {
		return " [OP " + spelling + " OP]";
	}
	
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitOperator(this, o);
	}

	public String getSpelling() {
		return spelling;
	}

	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
}
