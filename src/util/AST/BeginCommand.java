package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class BeginCommand extends Command{
	private ArrayList<Command> commands;
	
	public BeginCommand(ArrayList<Command> commands) {
		this.commands = commands;
	}

	@Override
	public String toString() {
		String str = "\n[BeginCommand \n" ;
		
		if(commands.size() > 0){
			for(int i = 0; i< commands.size(); i++){
				if(commands.get(i) != null)
					str += " " + commands.get(i).toString() ; 
			}
		}
		str += " \nBeginCommand]"+ "\n";
		return str;
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitBeginCommand(this, o);
	}

	public ArrayList<Command> getCommands() {
		return commands;
	}

	public void setCommands(ArrayList<Command> commands) {
		this.commands = commands;
	}

}
