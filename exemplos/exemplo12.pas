program testBreak;
var a:integer;
var ab: integer;
begin
	a:=10;
	ab:=20;
	while(ab > a )do
	begin
		if(a=ab) then
			break;
		else
		ab:=ab-1;
		endif;
	end
end