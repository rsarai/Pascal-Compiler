program naoSeiOQueTestar;
var lugar:integer;

procedure main(var hawaii:boolean, var california:boolean, var fiji:boolean);
begin
	if(hawaii = true) then
		lugar := 1;
	else
		if(california = true) then
			lugar := 2;
		else
			if(fiji = true) then
				lugar := 3;
			endif;
		endif;
	endif;
end;
begin
surfar(true,false,false);
end