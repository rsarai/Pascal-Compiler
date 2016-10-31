package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class LocalVariableDeclaration extends DeclarationCommand{
	private VariableDeclaration vd;
	
	public LocalVariableDeclaration(Type type, ID id) {
		vd = new VariableDeclaration(type,id);
	}
	public String toString() {
		String str = "<LocalVariableDeclaration>" + vd.toString()+ "</LocalVariableDeclaration>"+ "\n";
		return str;
	}
	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitLocalVariableDeclaration(this, o);
	}
	public VariableDeclaration getVd() {
		return vd;
	}
	public void setVd(VariableDeclaration vd) {
		this.vd = vd;
	}
}
