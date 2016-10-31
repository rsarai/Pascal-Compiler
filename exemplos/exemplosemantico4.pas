program AA;
var dinheiro:integer;
var mulhergravida:boolean;

procedure main();
begin dinheiro:=100; end;

procedure beber();
begin
	while(dinheiro > 0) do
	begin
		dinheiro := dinheiro - 1;
		if(dinheiro = 20) then
			mulhergravida := true;
		endif;
		if(mulhergravida = true) then
			break;
		endif;
	end
end;

function procurarAA():boolean;
begin
return true;
end;

begin 
main();
beber();
procurarAA();
end