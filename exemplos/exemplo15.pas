program NandoReis;
function main(var acustico:integer) : integer;
var andar:integer;
var all:integer;
var star:integer;
var azul:integer;
begin 
	all:=1;
	star:=2;
	azul:=3;
	andar:=acustico;
	return azul; #comentar essa linha para testar regra 4
end;
begin
	main(12);
	if(1 = 12) then
		writeln(1);
	endif;
end