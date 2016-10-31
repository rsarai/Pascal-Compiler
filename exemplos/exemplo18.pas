program Expression;

var a: integer;
var b: integer;

begin
	if (a + b <> a * (a + b) ) then
			begin
				writeln(verdade);	
			end
		else
			writeln(falso);
		endif;
end