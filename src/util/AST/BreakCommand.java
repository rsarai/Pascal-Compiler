package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class BreakCommand extends Command {
	public String toString(){
		return "</BREAK>"+ "\n";
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitBreakCommand(this, o);
	}
}
