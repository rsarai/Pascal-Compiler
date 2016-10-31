package scanner;

/**
 * Token class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Token {

	// The token kind
	private int kind;
	// The token spelling
	private String spelling;
	// The line and column that the token was found
	private int line, column;
	
	/**
	 * Default constructor
	 * @param kind
	 * @param spelling
	 * @param line
	 * @param column
	 */
	public Token(int kind, String spelling, int line, int column) {
		this.kind = kind;
		this.spelling = spelling;
		this.line = line;
		this.column = column;
	}

	/**
	 * Returns token kind
	 * @return
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * Returns token spelling
	 * @return
	 */
	public String getSpelling() {
		return spelling;
	}

	/**
	 * Returns the line where the token was found
	 * @return
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Returns the column where the token was found
	 * @return
	 */	
	public int getColumn() {
		return column;
	}
	
	public String toString(){
		String type = null;
		
		switch (kind) {
		case 0:
			type = "ID";
			break;
		case 1:
			type = "BEGIN";
			break;
		case 2:
			type = "END";
			break;
		case 3:
			type = "DOT";
			break;
		case 4:
			type = "SC";
			break;
		case 5:
			type = "COLON";
			break;
		case 6: 
			type = "VAR";
			break;
		case 7: 
			type = "SOM";
			break;
		case 8: 
			type = "SUB";
			break;
		case 9: 
			type = "DIV";
			break;
		case 10: 
			type = "MULT";
			break;
		case 11: 
			type = "COMP";
			break;
		case 12: 
			type = "ASG";
			break;
		case 13: 
			type = "EQ";
			break;
		case 14: 
			type = "MAIORQ";
			break;
		case 15: 
			type = "MENORQ";
			break;
		case 16: 
			type = "MAIOR";
			break;
		case 17: 
			type = "MENOR";
			break;
		case 18: 
			type = "DIF";
			break;
		case 19: 
			type = "LP";
			break;
		case 20: 
			type = "RP";
			break;
		case 21: 
			type = "IF";
			break;
		case 22: 
			type = "ELSE";
			break;
		case 23: 
			type = "THEN";
			break;
		case 24: 
			type = "LETTER";
			break;
		case 25: 
			type = "RETURN";
			break;
		case 26: 
			type = "WHILE";
			break;
		case 27: 
			type = "INTEGER";
			break;
		case 28: 
			type = "BOOLEAN";
			break;
		case 29: 
			type = "CONTINUE";
			break;
		case 30: 
			type = "BREAK";
			break;
		case 31: 
			type = "TRUE";
			break;
		case 32: 
			type = "FALSE";
			break;
		case 33: 
			type = "ENDIF";
			break;
		case 34: 
			type = "VIRG";
			break;
		case 35: 
			type = "WRITELN";
			break;
		case 36:
			type = "PROC";
			break;
		case 37: 
			type = "FUNC";
			break;
		case 38:
			type = "DO";
			break;
		case 39:
			type = "PROGRAM";
			break;
		case 40:
			type = "NUMBER";
			break;
		case 1000: 
			type = "EOT";
			break;
		}
		
		return "Token: " + type + ", " + spelling.toString() + " (" + getLine() + ":" + getColumn() + ")";
	}
	
}
