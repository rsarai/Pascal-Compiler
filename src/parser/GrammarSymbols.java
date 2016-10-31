package parser;


/**
 * This class contains codes for each grammar terminal
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class GrammarSymbols {

	// Language terminals (starts from 0)
	public static final int ID = 0, BEGIN = 1, END = 2, DOT = 3, SC = 4,
			SOM = 7, SUB = 8, DIV = 9, MULT = 10, COM = 11, ASG = 12, EQ = 13
			, MAIORQ = 14, MENORQ = 15, MAIOR = 16 , MENOR = 17, DIF = 18, LP = 19, RP = 20
			, IF = 21 , ELSE = 22, THEN = 23, LETTER = 24, RETURN = 25, WHILE = 26, INTEGER = 27
			, BOOLEAN = 28, CONTINUE = 29, BREAK = 30, TRUE = 31, FALSE = 32, ENDIF = 33, VIRG = 34
			, WRITELN = 35, PROC = 36, FUNC = 37, COLON = 5, PROGAM = 39, DO =38, VAR = 6, NUMBER=40;
	//TODO
	public static final int EOT = 1000;
	
}
