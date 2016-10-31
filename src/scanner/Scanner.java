package scanner;

import compiler.Properties;

import compiler.Compiler;

import parser.GrammarSymbols;
import util.Arquivo;

/**
 * Scanner class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Scanner {
	private Arquivo file;					// The file object that will be used to read the source code
	private char currentChar;				// The last char read from the source code
	private int currentKind;				// The kind of the current token
	private StringBuffer currentSpelling;	// Buffer to append characters read from file
	private int line, column;				// Current line and column in the source file
	
	/**
	 * Default constructor
	 */
	public Scanner() {
		this.file = new Arquivo(Properties.sourceCodeLocation);		
		this.line = 0;
		this.column = 0;
		this.currentChar = this.file.readChar();
	}
	
	/**
	 * Returns the next token
	 * @return
	 */ //TODO
	public Token getNextToken() {
		currentSpelling = new StringBuffer("");			// Initializes the string buffer
		
		try{
			while(isSeparator(currentChar)){				// Ignores separators
				scanSeparator();
			}
			currentSpelling = new StringBuffer("");		// Clears the string buffer
			currentKind = scanToken();					// Scans the next token
		}catch(LexicalException e){
			System.err.println(e.toString());
			System.exit(0);
		}
		// Creates and returns a token for the lexema identified
		return new Token(currentKind, currentSpelling.toString(), line, column);
	}
	
	/**
	 * Returns if a character is a separator
	 * @param c
	 * @return
	 */
	private boolean isSeparator(char c) {
		if ( c == '#' || c == ' ' || c == '\n' || c == '\t' ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Reads (and ignores) a separator
	 * @throws LexicalException
	 */ //TODO
	private void scanSeparator() throws LexicalException {
		if(currentChar == '#'){ 										// If it is a comment line
			getNextChar();												// Gets next char
			while (isGraphic(currentChar) || currentChar == '\t'){		// Reads characters while they are graphics or '\t'
				getNextChar();
			}
		
			if(currentChar == '\n'){									// A command line should finish with a \n
				getNextChar();
			}else{
				throw new LexicalException("Character not expected.", currentChar, line, column);
			}
		}else{
			getNextChar();
		}
	}
	
	/**
	 * Gets the next char
	 */
	private void getNextChar() {
		this.currentSpelling.append(this.currentChar);	// Appends the current char to the string buffer
		this.currentChar = this.file.readChar();		// Reads the next one
		this.incrementLineColumn();						// Increments the line and column
	}
	
	/**
	 * Increments line and column
	 */
	private void incrementLineColumn() {
		if ( this.currentChar == '\n' ) {		// If the char read is a '\n', increments the line variable and assigns 0 to the column
			this.line++;
			this.column = 0;
		} else {								// If the char read is not a '\n'
			if ( this.currentChar == '\t' ) {	// If it is a '\t', increments the column by 4
				this.column = this.column + 4;
			} else {							// If it is not a '\t', increments the column by 1
				this.column++;
			}
		}
	}
	
	/**
	 * Returns if a char is a digit (between 0 and 9)
	 * @param c
	 * @return
	 */
	private boolean isDigit(char c) {
		if ( c >= '0' && c <= '9' ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns if a char is a letter (between a and z or between A and Z)
	 * @param c
	 * @return
	 */
	private boolean isLetter(char c) {
		if ( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns if a char is a graphic (any ASCII visible character)
	 * @param c
	 * @return
	 */
	private boolean isGraphic(char c) {
		if ( c >= ' ' && c <= '~' ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Scans the next token
	 * Simulates the DFA that recognizes the language described by the lexical grammar
	 * @return
	 * @throws LexicalException
	 */ //TODO
	private int scanToken() throws LexicalException {
		int state = 0;								// The initial automata state is 0
		
			while(true){							// While loop to simulate the automata
				switch (state) {
				case 0:
					if(isDigit(currentChar)){
						state = 1;
						getNextChar();
					}else if(isLetter(currentChar)){
						state = 2;
						getNextChar();							
					}else if(currentChar == '='){
						state = 3;
						getNextChar();
					}else if(currentChar == '<'){
						state = 4;
						getNextChar();
					}else if(currentChar == '>'){
						state = 6;
						getNextChar();
					}else if (currentChar == ':'){
						state = 9;
						getNextChar();
					}else if (currentChar == '+'){
						state = 11;
						getNextChar();
					}else if (currentChar == '-'){
						state = 12;
						getNextChar();
					}else if (currentChar == '*'){
						state = 13;
						getNextChar();
					}else if (currentChar == '/'){
						state = 14;
						getNextChar();
					}else if (currentChar == '('){
						state = 15;
						getNextChar();
					}else if (currentChar == ')'){
						state = 16;
						getNextChar();
					}else if (currentChar == ';'){
						state = 17;
						getNextChar();
					}else if (currentChar == ','){
						state = 18;
						getNextChar();
					}else if(currentChar == '.'){
						state = 19;
						getNextChar();
					}else if (currentChar == '\000'){
						state = 20;
					}else{
						state = 21;
					}
					break;
				case 1:
					while(isDigit(currentChar)){
						getNextChar();
					}
					return GrammarSymbols.NUMBER;
				case 2:
					while(isDigit(currentChar) || isLetter(currentChar)){
						getNextChar();
					}
					
					String spelling = currentSpelling.toString();
					
					if(spelling.equals("integer")){
						return GrammarSymbols.INTEGER;
					}else if (spelling.equals("false")) {
						return GrammarSymbols.FALSE;
					} else if (spelling.equals("true")) {
						return GrammarSymbols.TRUE;
					} else if (spelling.equals("boolean")) {
						return GrammarSymbols.BOOLEAN;
					} else if (spelling.equals("if")) {
						return GrammarSymbols.IF;
					} else if (spelling.equals("then")) {
						return GrammarSymbols.THEN;
					} else if (spelling.equals("else")) {
						return GrammarSymbols.ELSE;
					} else if (spelling.equals("while")) {
						return GrammarSymbols.WHILE;
					} else if (spelling.equals("writeln")){
						return GrammarSymbols.WRITELN;
					}else if(spelling.equals("function")){
						return GrammarSymbols.FUNC;
					}else if(spelling.equals("procedure")){
						return GrammarSymbols.PROC; 			//Retorna valor da função void
					}else if(spelling.equals("begin")){
						return GrammarSymbols.BEGIN;
					}else if(spelling.equals("end")){
						return GrammarSymbols.END;
					}else if(spelling.equals("endif")){
						return GrammarSymbols.ENDIF; 			//Endif para indicar o final de um IF. Sugerido pelo professor
					}else if(spelling.equals("return")){
						return GrammarSymbols.RETURN;
					}else if(spelling.equals("break")){
						return GrammarSymbols.BREAK;
					}else if(spelling.equals("continue")){
						return GrammarSymbols.CONTINUE;
					}else if(spelling.equals("program") == true){
						return GrammarSymbols.PROGAM;
					}else if(spelling.equals("var")){
						return GrammarSymbols.VAR;
					}else if(spelling.equals("do")){
						return GrammarSymbols.DO;
					}else{
						return GrammarSymbols.ID;
					}
				case 3:
					return GrammarSymbols.EQ;
				case 4:
					if(currentChar == '='){
						state = 5;
						getNextChar();
					}else{
						return GrammarSymbols.MENOR;
					}
				case 5:
					return GrammarSymbols.MENORQ;
				case 6:
					if(currentChar == '='){
						state = 7;
						getNextChar();
					}else if(currentChar == '>'){
						state = 8;
						getNextChar();
					}else{
						return GrammarSymbols.MAIOR;
					}
				case 7: 
					return GrammarSymbols.MAIORQ;
				case 8:
					return GrammarSymbols.DIF;
				case 9:
					if(currentChar == '='){
						state = 10;
						getNextChar();
					}else{
						return GrammarSymbols.COLON;
					}
					break;
				case 10:
					return GrammarSymbols.ASG;
				case 11:
					return GrammarSymbols.SOM;
				case 12:
					return GrammarSymbols.SUB;
				case 13:
					return GrammarSymbols.MULT;
				case 14:
					return GrammarSymbols.DIV;
				case 15:
					return GrammarSymbols.LP;
				case 16:
					return GrammarSymbols.RP;
				case 17:
					return GrammarSymbols.SC;
				case 18:
					return GrammarSymbols.VIRG;
				case 19:
					return GrammarSymbols.DOT;
				case 20: 
					return GrammarSymbols.EOT;
				case 21:
					throw new LexicalException("Character is not expected", currentChar, line, column);
				}
			
		}
	}
	
	
}
