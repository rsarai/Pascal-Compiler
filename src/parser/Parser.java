package parser;

import java.util.ArrayList;

import scanner.Scanner;
import scanner.Token;
import util.AST.AST;
import util.AST.ArgumentList;
import util.AST.AssignCommand;
import util.AST.BeginCommand;
import util.AST.BreakCommand;
import util.AST.Command;
import util.AST.ContinueCommand;
import util.AST.DeclarationCommand;
import util.AST.Expression;
import util.AST.ExpressionA;
import util.AST.FactorExpression;
import util.AST.Fator;
import util.AST.FatorBoolean;
import util.AST.FatorFunctionCall;
import util.AST.FatorID;
import util.AST.FatorInteger;
import util.AST.FunctionCall;
import util.AST.FunctionDeclaration;
import util.AST.GlobalVariableDeclaration;
import util.AST.ID;
import util.AST.IfCommand;
import util.AST.LocalVariableDeclaration;
import util.AST.Logic;
import util.AST.Numbers;
import util.AST.Operator;
import util.AST.ParameterList;
import util.AST.PrintCommand;
import util.AST.ProcedureCommand;
import util.AST.Program;
import util.AST.ReturnCommand;
import util.AST.Termo;
import util.AST.Tuple;
import util.AST.Type;
import util.AST.TypeBool;
import util.AST.TypeInteger;
import util.AST.VariableDeclaration;
import util.AST.WhileCommand;


/**
 * Parser class
 * @version 2010-august-29
 * @discipline Projeto de Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Parser {
	private Token currentToken = null;			// The current token
	private Scanner scanner = null;				// The scanner
	
	
	
	/**
	 * Parser constructor
	 */
	public Parser() {
		this.scanner = new Scanner();			// Initializes the scanner object
		this.currentToken = scanner.getNextToken();
	}
	
	
	
	
	/**
	 * Veririfes if the current token kind is the expected one
	 * @param kind
	 * @throws SyntacticException
	 */
	private void accept(int kind) throws SyntacticException {
		if(currentToken.getKind() == kind){					// If the current token kind is equal to the expected
			acceptIt();
		}else{												// If not
			throw new SyntacticException("Token not Expected", currentToken);// Raises an exception
		}
	}
	
	
	
	/**
	 * Gets next token
	 */
	private void acceptIt() {
		currentToken = scanner.getNextToken();
	}

	
	
	
	
	/**
	 * Verifies if the source program is syntactically correct
	 * @throws SyntacticException
	 */ 
	public AST parse() throws SyntacticException {
		Program p = null;
		
		try{
			p = parseProgram();
			accept(GrammarSymbols.EOT);
		}catch(SyntacticException e){
			System.err.println(e.toString());
			System.exit(0);			
		}
		
		return p;
	}
	
	/**
	 * Parece certo
	 */
	public Program parseProgram(){
		Program p = null;
		Command cmd = null;
		try {
			
			accept(GrammarSymbols.PROGAM);
			ID id = new ID(currentToken.getSpelling());
			accept(GrammarSymbols.ID);
			accept(GrammarSymbols.SC);
			
			ArrayList<DeclarationCommand> declarations = new ArrayList<DeclarationCommand>();
			DeclarationCommand dcmmd = null;
			
			while(currentToken.getKind() != GrammarSymbols.BEGIN){
				
				if(currentToken.getKind() == GrammarSymbols.VAR){
					dcmmd = parseGlobalVariableDeclaration();
					accept(GrammarSymbols.SC);
					declarations.add(dcmmd);
				}else if(currentToken.getKind() == GrammarSymbols.FUNC){
					dcmmd = parseFunctionDeclaration();
					accept(GrammarSymbols.SC);
					declarations.add(dcmmd);
				}else{
					dcmmd = parseProcedureCommand();
					accept(GrammarSymbols.SC);
					declarations.add(dcmmd);
				}
				
			}
			if(currentToken.getKind() == GrammarSymbols.BEGIN){
				BeginCommand commands = parseBeginCommand();
				p = new Program(id, declarations, commands);
			}
			
		} catch (SyntacticException e) {
			System.err.println(e.toString());
			System.exit(0);
		}
		
		return p;
	}
	
	/**
	 * Parece certo
	 */
	public BeginCommand parseBeginCommand(){
		BeginCommand bc = null;
		ArrayList<Command> commands = new ArrayList<Command>();
		try{
			accept(GrammarSymbols.BEGIN);
			while(true){
				if(currentToken.getKind() == GrammarSymbols.END){
					break;
				}
				Command cmd = parseCommand();
				commands.add(cmd);
			}
			acceptIt(); //ACEITA END
			bc = new BeginCommand(commands);
		}catch(SyntacticException e){
			System.err.println(e.toString());
			System.exit(0);
		}
		
		return bc;
	}
	
	/**
	 * Parece certo
	 * Falta o if
	 */
	public Command parseCommand(){
		Command cmd = null;
		
		ArrayList<Command> elseCommands = new ArrayList<Command>();
		ArrayList<Command> commandsif = new ArrayList<Command>();
		ArrayList<Command> commandselse = new ArrayList<Command>();
		
		try{
			if(currentToken.getKind() == GrammarSymbols.VAR){
				cmd = parseLocalVariableDeclaration();
				accept(GrammarSymbols.SC);
			}else if(currentToken.getKind() == GrammarSymbols.IF){		
				accept(GrammarSymbols.IF);
				accept(GrammarSymbols.LP);
				Expression exp = parseExpression();
				accept(GrammarSymbols.RP);
				accept(GrammarSymbols.THEN);
				
				//esse if é só pra se tiver apenas if then  
				if(currentToken.getKind() == GrammarSymbols.BEGIN){
					BeginCommand bc = parseBeginCommand();
					commandsif.add(bc);
					
				}else{//se o if tiver não tiver begin 
					Command com = parseCommand();
					commandsif.add(com);
				}
				
				if(currentToken.getKind() == GrammarSymbols.ELSE){
					acceptIt();
					if(currentToken.getKind() == GrammarSymbols.BEGIN){
						BeginCommand bc = parseBeginCommand();
						commandselse.add(bc);
						accept(GrammarSymbols.ENDIF);
					}else{//se o if tiver não tiver begin 
						while(currentToken.getKind() != GrammarSymbols.ENDIF){
							Command com = parseCommand();
							commandselse.add(com);
						}
						acceptIt();
						accept(GrammarSymbols.SC);
					}
				}else{
					accept(GrammarSymbols.ENDIF);
					accept(GrammarSymbols.SC);
				}
				
				return cmd = new IfCommand(exp, commandsif, commandselse);
			}else if (currentToken.getKind() == GrammarSymbols.WHILE){
				acceptIt();
				accept(GrammarSymbols.LP);
				Expression exp = parseExpression();
				accept(GrammarSymbols.RP);
				accept(GrammarSymbols.DO);
				BeginCommand bc = parseBeginCommand();
				cmd = new WhileCommand(exp, bc);
			}else if(currentToken.getKind() == GrammarSymbols.CONTINUE){
				acceptIt();
				accept(GrammarSymbols.SC);
				cmd = new ContinueCommand();
			}else if (currentToken.getKind() == GrammarSymbols.BREAK) {
				acceptIt();
				accept(GrammarSymbols.SC);
				cmd = new BreakCommand();
			}else if (currentToken.getKind() == GrammarSymbols.WRITELN) {
				acceptIt();
				accept(GrammarSymbols.LP);
				Expression exp = parseExpression();
				accept(GrammarSymbols.RP);
				accept(GrammarSymbols.SC);
				cmd = new PrintCommand(exp);
			}else if (currentToken.getKind() == GrammarSymbols.RETURN) {
				acceptIt();
				Expression exp = parseExpression();	
				accept(GrammarSymbols.SC);
				cmd = new ReturnCommand(exp);
			}else if(currentToken.getKind() == GrammarSymbols.ID){
				ID id = new ID(currentToken.getSpelling());
				accept(GrammarSymbols.ID);
				ArgumentList al = null;
				if(currentToken.getKind() == GrammarSymbols.LP){
					acceptIt();	
						if (currentToken.getKind() == GrammarSymbols.RP) {
							acceptIt();
							accept(GrammarSymbols.SC);
							cmd = new FunctionCall(id, null);
						} else {
							al = parseArgumentList();
							accept(GrammarSymbols.RP);
							accept(GrammarSymbols.SC);
							cmd = new FunctionCall(id, al);
						}
					
					
				}else{
					accept(GrammarSymbols.ASG);
					Expression exp = parseExpression();
					accept(GrammarSymbols.SC);
					cmd = new AssignCommand(id, exp);
				}
			}else{
				throw new SyntacticException("Token invalido", currentToken);
			}
			
		}catch(SyntacticException e){
			System.err.println(e.toString());
			System.exit(0);
		}
		
		return cmd;
	}
	
	/**
	 * Parece certo
	 */
	public FunctionDeclaration parseFunctionDeclaration() {
		FunctionDeclaration fd = null;
		Type type = null;
		try{
			accept(GrammarSymbols.FUNC);
			ID id = new ID(currentToken.getSpelling());
			accept(GrammarSymbols.ID);
			accept(GrammarSymbols.LP);
			ParameterList pl = null;
			if (currentToken.getKind() == GrammarSymbols.RP) {
				acceptIt();
			} else {
				pl = parseParameterList();
				accept(GrammarSymbols.RP);
			}
			accept(GrammarSymbols.COLON);
			if(currentToken.getKind() == GrammarSymbols.BOOLEAN || currentToken.getKind() == GrammarSymbols.INTEGER){
				if(currentToken.getKind() == GrammarSymbols.BOOLEAN){
					type = new TypeBool(currentToken.getSpelling());
				}else if(currentToken.getKind() == GrammarSymbols.INTEGER){
					type = new TypeInteger(currentToken.getSpelling());
				}
				acceptIt();
			}
			accept(GrammarSymbols.SC);
			
			ArrayList<DeclarationCommand> declarations = new ArrayList<DeclarationCommand>();
			DeclarationCommand dcmmd = null;
			
			while(currentToken.getKind() != GrammarSymbols.BEGIN){
				if(currentToken.getKind() == GrammarSymbols.VAR){
					dcmmd = parseLocalVariableDeclaration();
					accept(GrammarSymbols.SC);
					declarations.add(dcmmd);
				}
				
			}
			
			BeginCommand commands = parseBeginCommand();
			fd = new FunctionDeclaration(id, pl, type, declarations, commands);
		}catch(SyntacticException e){
			System.err.println(e.toString());
			System.exit(0);
		}
		
		return fd;
	}
	
	/**
	 * Parece certo
	 */
	public ProcedureCommand parseProcedureCommand(){
		ProcedureCommand pc = null;
		
		try{
			accept(GrammarSymbols.PROC);
			ID id = new ID(currentToken.getSpelling());
			accept(GrammarSymbols.ID);
			accept(GrammarSymbols.LP);
			
			ParameterList pl = null;
			if (currentToken.getKind() == GrammarSymbols.RP) {
				
				acceptIt();
				accept(GrammarSymbols.SC);
			} else {
				pl = parseParameterList();
				accept(GrammarSymbols.RP);
				accept(GrammarSymbols.SC);
				
			}
			
			
			ArrayList<DeclarationCommand> declarations = new ArrayList<DeclarationCommand>();
			DeclarationCommand dcmmd = null;
			
			
			while(true){
				if(currentToken.getKind() == GrammarSymbols.VAR){
					
					dcmmd = parseLocalVariableDeclaration();
					accept(GrammarSymbols.SC);
				}
				declarations.add(dcmmd);
				
				if(currentToken.getKind() == GrammarSymbols.BEGIN){
					break;
				}
			}
			
			BeginCommand commands = parseBeginCommand();
			pc = new ProcedureCommand(id, pl, declarations, commands);			
		}catch(SyntacticException e){
			System.err.println(e.toString());
			System.exit(0);
		}
		
		return pc;
	}
	
	public Expression parseExpression() {
		Expression exp = null;
		Operator op = null;
		ExpressionA expA = parseExpressionA();
		Tuple aet = null;
		
		try{
			if(currentToken.getKind() == GrammarSymbols.EQ || currentToken.getKind() == GrammarSymbols.MAIORQ ||  
					currentToken.getKind() == GrammarSymbols.MENORQ || currentToken.getKind() == GrammarSymbols.MAIOR ||
					currentToken.getKind() == GrammarSymbols.MENOR || currentToken.getKind() == GrammarSymbols.DIF){
				
				if(currentToken.getKind() == GrammarSymbols.MENOR){
					acceptIt();
					accept(GrammarSymbols.MAIOR);
					op = new Operator("<>");
					ExpressionA expA2 = parseExpressionA();
					aet = new Tuple(op, expA2);
				}else{
					op = new Operator(currentToken.getSpelling());
					acceptIt();
					ExpressionA expA2 = parseExpressionA();
					aet = new Tuple(op, expA2);
				}
			}
			exp = new Expression(expA, aet);
		}catch(SyntacticException e){
			System.err.println(e.toString());
			System.exit(0);
		}
		return exp;
	}
	
	public ExpressionA parseExpressionA() {
		ExpressionA expA = null;
		Tuple tt = null;
		Termo term = null;
		ArrayList<Tuple> tuples = new ArrayList<Tuple>();
		term = parseTermo();
		
		while(currentToken.getKind() == GrammarSymbols.SOM || currentToken.getKind() == GrammarSymbols.SUB){
			Operator op = new Operator(currentToken.getSpelling());
			acceptIt();
			Termo term2 = parseTermo();
			tt  = new Tuple(op, term2);
			tuples.add(tt);
		}
		expA = new ExpressionA(term, tuples);
		return expA;
	}
	
	public Termo parseTermo() {
		Termo term = null;
		Fator fat = null;
		Operator op = null;
		ArrayList<Tuple> tuples = new ArrayList<Tuple>();

		fat = parseFator();
		while(currentToken.getKind() == GrammarSymbols.MULT || currentToken.getKind() == GrammarSymbols.DIV){
			op = new Operator(currentToken.getSpelling());
			acceptIt();
			fat = parseFator();
			Tuple ft = new Tuple(op, fat);
			tuples.add(ft);
		}
		term = new Termo(fat, tuples);
		return term;
	}
	
	public Fator parseFator() {
		Fator fat = null;
		try{
			if(currentToken.getKind() == GrammarSymbols.ID){
				ID id = new ID(currentToken.getSpelling());
				acceptIt();
				fat = new FatorID(id);
				if(currentToken.getKind() == GrammarSymbols.LP){
					ArgumentList al = null;
					FunctionCall fc = null;
					acceptIt();
					if(currentToken.getKind() == GrammarSymbols.RP){
						acceptIt();
					}else{
						al = parseArgumentList();
						accept(GrammarSymbols.RP);
					}
					fc = new FunctionCall(id, al);
					fat = new FatorFunctionCall(fc);					
				}
			}else if (currentToken.getKind() == GrammarSymbols.INTEGER || currentToken.getKind() == GrammarSymbols.NUMBER){
				Numbers n = new Numbers(currentToken.getSpelling());
				acceptIt();
				fat = new FatorInteger(n);
			}else if(currentToken.getKind() == GrammarSymbols.TRUE || currentToken.getKind() == GrammarSymbols.FALSE || currentToken.getKind() == GrammarSymbols.BOOLEAN){
				Logic n = new Logic(currentToken.getSpelling());;
				acceptIt();
				fat = new FatorBoolean(n);
			}else{
				accept(GrammarSymbols.LP);
				Expression exp = parseExpression();
				accept(GrammarSymbols.RP);
				fat = new FactorExpression(exp);
			}
		}catch(SyntacticException e){
			System.err.println(e.toString());
			System.exit(0);
		}
	
		return fat;
	}
	
	public ArgumentList parseArgumentList() throws SyntacticException {
		ArgumentList al = null;
		
			Expression exp;
			ArrayList<Expression> expressions = new ArrayList<Expression>();	
			exp = parseExpression();
			expressions.add(exp);
			
			while(currentToken.getKind() != GrammarSymbols.RP){
				accept(GrammarSymbols.VIRG);
				exp = parseExpression();
				expressions.add(exp);
			}
			al = new ArgumentList(expressions);
			
		return al;
	}
	
	/**
	 * Parece certo
	 */
	public ParameterList parseParameterList() {
		ParameterList pl = null;
		try {
			ArrayList<VariableDeclaration> varDeclarations = new ArrayList<VariableDeclaration>();
			VariableDeclaration vd = parseVariableDeclaration();
			
			varDeclarations.add(vd);
			
			while (currentToken.getKind() != GrammarSymbols.RP) {
				
				accept(GrammarSymbols.VIRG);
				vd = parseVariableDeclaration();
				varDeclarations.add(vd);
			}
			
			
			pl = new ParameterList(varDeclarations);
		} catch (SyntacticException exception) {
			System.err.println(exception.toString());
			System.exit(0);
		}
		return pl;
	
	}
	
	/**
	 * Parece certo
	 */
	private VariableDeclaration parseVariableDeclaration() {
		VariableDeclaration vd = null;
		try {
			accept(GrammarSymbols.VAR);
			ID id = new ID(currentToken.getSpelling());
			accept(GrammarSymbols.ID);
			accept(GrammarSymbols.COLON);
			if (currentToken.getKind() == GrammarSymbols.BOOLEAN || currentToken.getKind() == GrammarSymbols.INTEGER) {
				Type type = null;
				if (currentToken.getKind() == GrammarSymbols.BOOLEAN) {
					type = new TypeBool(currentToken.getSpelling());
				} else if (currentToken.getKind() == GrammarSymbols.INTEGER) {
					type = new TypeInteger(currentToken.getSpelling());
				}
				acceptIt();
				vd = new VariableDeclaration(type, id);
			}
			
		} catch (SyntacticException exception) {
			System.err.println(exception.toString());
			System.exit(0);
		}
		return vd;
	}
	
	
	/**
	 * Parece certo
	 */
	public GlobalVariableDeclaration parseGlobalVariableDeclaration() {
		GlobalVariableDeclaration gvd = null;
		
		try{
			accept(GrammarSymbols.VAR);
			ID id = new ID(currentToken.getSpelling());
			accept(GrammarSymbols.ID);
			accept(GrammarSymbols.COLON);
			if(currentToken.getKind() == GrammarSymbols.BOOLEAN || currentToken.getKind() == GrammarSymbols.INTEGER){
				Type type = null;
				if (currentToken.getKind() == GrammarSymbols.BOOLEAN) {
					type = new TypeBool(currentToken.getSpelling());
				} else if (currentToken.getKind() == GrammarSymbols.INTEGER) {
					type = new TypeInteger(currentToken.getSpelling());
				}
				acceptIt();
				gvd = new GlobalVariableDeclaration(type, id);
			}
		}catch(SyntacticException e){
			System.err.println(e.toString());
			System.exit(0);
		}
		
		return gvd;
	}
	
	/**
	 * Parece certo
	 */
	public LocalVariableDeclaration parseLocalVariableDeclaration() {
		LocalVariableDeclaration lvd = null;
		
		try{
			accept(GrammarSymbols.VAR);
			ID id = new ID(currentToken.getSpelling());
			accept(GrammarSymbols.ID);
			accept(GrammarSymbols.COLON);
			if(currentToken.getKind() == GrammarSymbols.BOOLEAN || currentToken.getKind() == GrammarSymbols.INTEGER){
				Type type = null;
				if (currentToken.getKind() == GrammarSymbols.BOOLEAN) {
					type = new TypeBool(currentToken.getSpelling());
				} else if (currentToken.getKind() == GrammarSymbols.INTEGER) {
					type = new TypeInteger(currentToken.getSpelling());
				}
				acceptIt();
				lvd = new LocalVariableDeclaration(type, id);
			}
		}catch(SyntacticException e){
			System.err.println(e.toString());
			System.exit(0);
		}
		
		return lvd;
	}
	
	
	
	
}
