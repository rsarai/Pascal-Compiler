package util.AST;

import java.util.ArrayList;

import checker.SemanticException;
import checker.Visitor;

public class ContinueCommand extends Command {

	@Override
	public String toString() {
		return "</CONTINUE>"+ "\n";
	}

	@Override
	public Object visit(Visitor v, ArrayList<AST> o) throws SemanticException {
		return v.visitContinueCommand(this, o);
	}

}
