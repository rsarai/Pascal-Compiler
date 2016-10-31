package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class FunctionCall extends Command {
	private ID id;
	private ArgumentList al;
	
	public FunctionCall(ID id, ArgumentList al) {
		this.al = al;
		this.id = id;
	}

	@Override
	public String toString() {
		String str = "[FunctionCall ";
		
		if(id != null ){
			str += id.toString();
		}
		
		if(al != null){
			str += al.toString(); 
		}
		
		
		return str + " FunctionCall]";
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitFunctionCall(this, o);
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public ArgumentList getAl() {
		return al;
	}

	public void setAl(ArgumentList al) {
		this.al = al;
	}
	
	
}
