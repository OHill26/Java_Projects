package geneticMutation;
/**
 * Item class - takes in data read from a file and stores that information in the item class
 * @author OwenHill
 *
 */
public class Item {
	private final String name; //label for an item
	private final double weight; //weight for an item
	private final int value; //value for an item
	private boolean included; //indicates whether the item should be included or not
	
	public Item(String name, double weight, int value) { //initializes the item's fields that are passed in
		this.name = name;
		this.weight = weight;
		this.value = value;
		included = false;
	}
	
	public Item(Item other) { //initializes this items field to be the same as the other item field
		this.name = other.name;
		this.weight = other.weight;
		this.value = other.value;
		included = false;
	}
	//getters
	public String getName() {
		return this.name;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public boolean isIncluded() {
		return this.included;
	}
	//setter for if the item is included 
	public void setIncluded(boolean included) {
		this.included = included;
	}
	//displays item in string format
	public String toString() {
		return this.name + " (" + this.weight + " lbs, $" + this.value + ")\n";
	}
}
