package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class IfCommand extends Command {
	private Expression exp;
	private ArrayList<Command> commands;
	private ArrayList<Command> elsecommands;
	
	
	public IfCommand(Expression exp, ArrayList<Command> commands, ArrayList<Command> elsecommands) {
		this.exp = exp;
		this.commands = commands;
		this.elsecommands = elsecommands;
	}


	@Override
	public String toString() {
		String str = "\n[IF " + exp.toString();
		
		if(commands.size() > 0){
			for(int i=0; i<commands.size(); i++)
				str += " " + commands.get(i).toString();
		}
		
		
		str += " IF]"+ "\n";
		str += "[ELSE "; 
		
		if(elsecommands.size() > 0){
			for(int i=0; i<elsecommands.size(); i++)
				str += " " + elsecommands.get(i).toString();
		}
		
		return str+= " ELSE] \n";
	}


	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitIfCommand(this, o);
	}


	public Expression getExp() {
		return exp;
	}


	public void setExp(Expression exp) {
		this.exp = exp;
	}


	public ArrayList<Command> getCommands() {
		return commands;
	}


	public void setCommands(ArrayList<Command> commands) {
		this.commands = commands;
	}


	public ArrayList<Command> getElsecommands() {
		return elsecommands;
	}


	public void setElsecommands(ArrayList<Command> elsecommands) {
		this.elsecommands = elsecommands;
	}

}
