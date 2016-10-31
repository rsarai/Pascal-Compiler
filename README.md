# Pascal-Compiler
Final Project for the course of Compilers at University of Pernambuco 


# Gramática Léxica

letter -> [a-zA-Z]
Digit -> [0-9]
boolean -> [True-False]
integer -> Digit+
ID -> letter (letter|digit)*
Comp -> = | >= | <= | >  | < | <>
Type -> integer | boolean
Token ->  ID | begin | end | . | ; | = | >= | <= | >  | < | <>| + | - | / | * | , | # | := | Comp | ( | ) | if | else | then | letter | return | while | integer | boolean | continue | break | true | false | /n | “ | “ | Type | endif | procedure |  writeln | : | var | program | EOT 


# Gramática sintática

Program ::= program id; (VariableDeclaration; | FunctionDec;| ProcedureCmd;)* BeginCommand

Command ::=	VariableDeclaration;
	| ifCmd
		| whileCmd
		| continue; | break;
		| writeln(expression);
		| returnCmd;
		| ID ( ((ArgumentList?);)    | (:= expression;)    )

FunctionDec ::= function ID(parameterList) : Type; VariableDeclaration;* BeginCommand

ProcedureCmd ::= procedure ID(parameterList); VariableDeclaration;* BeginCommand 

BeginCommand ::= begin Command* end


ifCmd ::= if (expression ) then (BeginCommand || Command*) (endif; ||  (else (BeginCommand || Command*)endif;)     )  

whileCmd ::= while (expression) do BeginCommand;

returnCmd ::= return (expression)?;

expression ::= expressionAdd ( (= | >= | <= | >  | < | <>) expressionAdd)?

expressionAdd ::= termo ( (+ | - ) termo)* 

termo ::= fator ( ( * | /  ) fator)*

fator ::=  ID(ArgumentList?)?  //lista de parâmetros
		| integer
		| (expression)
		| boolean

ParameterList ::= (VariableDeclaration)(, VariableDeclaration)*

ArgumentList ::= (expression) (, expression)*

VariableDeclaration ::= var ID: Type


# Observações
Os comentários de linha serão iniciados por “#”

# REGRAS IMPLEMENTADAS NO PROJETO 
1.Todos os identificadores precisam ser declarados antes de serem utilizados.
2. Não pode haver mais de um identificador (global | local) com o mesmo spelling.
3. Ao chamar uma função os tipos dos argumentos devem ser iguais ao dos parâmetros.
4.Toda função com retorno diferente de void, precisa ter um ou mais return deste tipo.
5 Ao retornar um valor, a função em questão deve ter como retorno o mesmo tipo.
6 Break e continue podem ser utilizados somente dentro do escopo de um while.
7. Todos os operadores devem ser aplicados a operandos do mesmo tipo. Operadores +, -, * e / devem ser aplicados a operandos int 
Operadores +, -, * e / retornam o tipo dos seus operandos.
Operadores == e != devem ser aplicados a operandos int, ou boolean.
Operadores &gt;, &lt;, &gt;=, &lt;= devem ser aplicados a operandos int
Operadores ==, !=, &gt;, &lt;, &gt;=, &lt;= retornam valor de tipo boolean.
8. Em A = B, o tipo de B precisa ser igual ao tipo de A e A é uma variável.
9.Todo código deve ter uma função principal (ponto de entrada).
10. Toda declaração de variável deve ser iniciada pela palavra reservada “var” sendo global ou localmente.

