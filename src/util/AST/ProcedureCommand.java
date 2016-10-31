package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class ProcedureCommand extends DeclarationCommand{
	private ID id;
	private ParameterList pl;
	private ArrayList<DeclarationCommand> declarations;
	private BeginCommand commands;
	
	public ProcedureCommand(ID id, ParameterList pl,
			ArrayList<DeclarationCommand> declarations, BeginCommand commands) {
		this.commands = commands;
		this.declarations = declarations;
		this.id = id;
		this.pl = pl;
	}

	@Override
	public String toString() {
		String str = "\n[ProcedureCmd ";
		
		str += id.toString();
		
		if (pl!=null){
			str = pl.toString();
		}
			
		
		str += commands.toString();
		
		if(declarations.size() < 0){
			for(int i = 0; i<declarations.size(); i++){
				if(declarations.get(i).toString() != null){
					str +=  " " + declarations.get(i).toString();
				}
			}
		}
		
		str+=" ProcedureCmd]"+ "\n";
		return str;
	}
	
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitProcedureCommand(this, o);
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