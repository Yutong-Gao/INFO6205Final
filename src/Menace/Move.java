package Menace;


public class Move {
	private int name;
	private int  probability;
	
	
	public Move(int name, int probability) {
		this.name = name;
		this.probability = probability;
	}


	public int getName() {
		return name;
	}


	public void setName(int name) {
		this.name = name;
	}


	public int getProbability() {
		return probability;
	}


	public void setProbability(int probability) {
		this.probability = probability;
	}


	@Override
	public String toString() {
		return "Move [name=" + name + "]";
	}


	
	
	

}
