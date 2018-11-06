package dataCell;

public class WeightWord {
	
	private String word;
	
	private double weight;
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return word+":"+weight;
	}
}
