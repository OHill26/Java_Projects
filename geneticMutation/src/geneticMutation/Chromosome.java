package geneticMutation;
import java.util.ArrayList;

import java.util.Random;
/**
 * class Chromosome - does operations on chromosome objects and arraylists
 * @author OwenHill
 *
 */

public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome>{
	private static Random rng; //going to be used for a random number generator
	
	public Chromosome() { //default constructor for chromosome, supposed to be empty
		
	}
	
	public Chromosome(ArrayList<Item> items) { 
		/** 
		* Adds a copy of each of the items passed in to this Chromosome. Uses a random 
		* number to decide whether each item’s included field is set to true or false.
		*/

		rng = new Random();
		for (int i = 0; i < items.size(); i++) { // iterates through items.size and randomly sets genes to true or false
			if (rng.nextInt(1,11) < 6) {
				items.get(i).setIncluded(true);
			}
			Item newItem = items.get(i);
			this.add(newItem);
		}
	}
	
	public Chromosome crossover(Chromosome other) {
		/**
		 * Creates and returns a new child chromosome by performing the crossover 
		 * operation on this chromosome and the other one that is passed in 
		 */	
			
	
		Chromosome child = new Chromosome();
		
		for (int i = 0; i < this.size(); i++) { // creates a deep copy of chromosome and sets fields to true or false
			Item item = new Item(this.get(i).getName(), this.get(i).getWeight(), this.get(i).getValue());
			rng = new Random();
			if (rng.nextInt(1,11) > 6) {
				item.setIncluded(this.get(i).isIncluded());
			}
			else { 
				item.setIncluded(other.get(i).isIncluded());
			}
			child.add(item);
		}
		return child; //returns new child
		
	}
	
	public void mutate() {
		/**
		 * Performs the mutation operation on this chromosome (i.e. for each item in this
		 * chromosome, use a random number to decide whether or not to flip it’s included field from
		 * true to false or vice versa)
		 */
		
		rng = new Random();
		for (int i = 0; i < this.size(); i++) { // changes 10% of the genes in chromosome to account for genetic mutation
			if (rng.nextInt(1,11) == 1) {
			this.get(i).setIncluded(!this.get(i).isIncluded());
			}
		}
	}
	
	public int getFitness() {
		/**
		 * Returns the fitness of this chromosome. If the sum of all of the included items’ weights
		 * are greater than 10, the fitness is zero. Otherwise, the fitness is equal to the sum of all of the
		 * included items’ values
		 */
		
		int totalValue = 0;
		double totalWeight = 0;
		
		for (int i = 0; i < this.size(); i++) { // gets the most fit out of the population
			if (this.get(i).isIncluded()) {
				totalWeight += this.get(i).getWeight();
				totalValue += this.get(i).getValue();
			}
		}
		if (totalWeight > 10) {
				return 0; // returns 0 if the item weight is over 10 lbs (too heavy to take)
			}
		return totalValue; // returns the values of the most fit chromosomes
	}

	
	
	public int compareTo(Chromosome other) {
		/**
		 * Returns -1 if this chromosome’s fitness is greater than the other’s fitness, +1 if
		 * this chromosome’s fitness is less than the other one’s, and 0 if their fitness is the same
		 */
		
		if (this.getFitness() > other.getFitness()) {
			return -1;
		}
		else if (this.getFitness() < other.getFitness()) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public String toString() {
		/**
		 * Displays the name, weight and value of all items in this chromosome whose included
		 * value is true, followed by the fitness of this chromosome
		 */
		
		String bestFitInfo = "";
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).isIncluded()) {
				bestFitInfo += this.get(i).toString() + "\n"; 
			}
		}
		return bestFitInfo; 
	}
}
