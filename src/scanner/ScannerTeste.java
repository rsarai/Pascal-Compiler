package scanner;

import parser.GrammarSymbols;

public class ScannerTeste {

	/**
	* @version 2016-april-23
	* @course Compiladores
	* @author Rebeca Sarai
	* @email rsas@ecomp.poli.br
	*/
	public static void main(String[] args) {
		Scanner scan = new Scanner();
		Token a;
		do {
			a = scan.getNextToken();
			System.out.println(a);
		} while (a.getKind()!= GrammarSymbols.EOT);

	}

}
