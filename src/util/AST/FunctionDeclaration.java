package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class FunctionDeclaration extends DeclarationCommand{
	private ID id;
	private ParameterList pl;
	private Type type;
	private ArrayList<DeclarationCommand> declarations;
	private BeginCommand commands;
	
	public FunctionDeclaration(ID id, ParameterList pl, Type type,
			ArrayList<DeclarationCommand> declarations, BeginCommand commands) {
		this.commands = commands;
		this.declarations = declarations;
		this.id = id;
		this.pl = pl;
		this.type = type;
	}

	@Override
	public String toString() {
		String str = "\n[ FunctioDeclaration ";
		

		str += id.toString();
		
		if (pl!=null){
			str +=  pl.toString() + type.toString();
		}
		
		if(commands!= null){
			str += commands.toString();
		}
		
		
		for(int i = 0; i<declarations.size(); i++){
			if(declarations.get(i).toString() != null){
				str +=  " " + declarations.get(i).toString();
			}
		}
		
		
		str+= " FunctioDeclaration]"+ "\n";
		return str;
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitFunctionDeclaration(this, o);
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public ParameterList getPl() {
		return pl;
	}

	public void setPl(ParameterList pl) {
		this.pl = pl;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
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
	
}

