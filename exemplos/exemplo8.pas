program funcTestParameter;
	function teste(var a:integer):integer;
		begin
			return a;
		end;
	var b:integer;
	begin
		b:=teste(4);
	end;