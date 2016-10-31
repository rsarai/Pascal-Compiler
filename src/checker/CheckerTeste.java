package checker;

import parser.Parser;

public class CheckerTeste {
	public static void main(String[] args) {
		Parser parser = new Parser();
		Checker test = new Checker();
		test.check(parser.parseProgram());
		System.out.println("Sucesso!");
	}
}
