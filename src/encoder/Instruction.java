package encoder;

public class Instruction {
	private String instruction;
	
	public Instruction(String i){
		instruction = i;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	public String toString(){
		return instruction + "\n";
	}
}
