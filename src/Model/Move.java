package Model;


public class Move {
	private String name;
	private int  probability;
	
	
	public Move(String name, int probability) {
		this.name = name;
		this.probability = probability;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getProbability() {
		return probability;
	}


	public void setProbability(int probability) {
		this.probability = probability;
	}


	
	
	

}
