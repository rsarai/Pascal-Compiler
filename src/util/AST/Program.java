package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class Program extends AST{
	private ArrayList<DeclarationCommand> declarations;
	private BeginCommand commands;
	private ID id;
	
	public Program(ID id, ArrayList<DeclarationCommand> declarations, BeginCommand commands) {
		this.declarations = declarations;
		this.commands = commands;
		this.id = id;
	}
	
	public String toString() {
		String str = "[Program " + id.toString();
		
		for(int i=0; i<declarations.size(); i++)
			str +=  " " + declarations.get(i).toString();
		
		str += commands.toString();
		
		
		return str += "Program]"+ "\n";
	}
	
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitProgram(this, o);
	}

	public ArrayList<DeclarationCommand> getDeclarations() {
		return declarations;
	}

	public void setDeclarations(ArrayList<DeclarationCommand> declarations) {
		this.declarations = declarations;
	}

	public BeginCommand getCommands() {
		return commands;
	}

	public void setCommands(BeginCommand commands) {
		this.commands = commands;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
	
}