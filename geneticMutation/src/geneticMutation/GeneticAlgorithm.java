package geneticMutation;
import java.io.*;
/**
 * class GeneticAlgorithm - reads in data and stores the data into an object to act as a chromosome
 * @author OwenHill
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GeneticAlgorithm {
	
	public static ArrayList<Item> readData(String fileName) throws FileNotFoundException{
		/**
		 * Reads in a data file with the format shown below and creates and returns an ArrayList
		 * of Item objects.
		 */
		
		ArrayList<Item> items = new ArrayList<>();
		Item  item; 
		String itemName;
		String itemWeight;
		String itemValue;
		File file = new File(fileName);
		Scanner inFS = new Scanner(file);
	
		while (inFS.hasNextLine()) { //scans through a file until there are no lines to read
			String input = inFS.nextLine();
			String[] parts = input.split(",");
			itemName = parts[0].trim();
			itemWeight = parts[1].trim();
			itemValue = parts[2].trim();
			
			item = new Item(itemName, Double.parseDouble(itemWeight), Integer.parseInt(itemValue));
			items.add(item);
		}
		inFS.close();
		return items; // returns an arraylist of the items read in
	}
	
	public static ArrayList<Chromosome> initializePopulation (ArrayList<Item> items, int populationSize) {
		/**
		 * Creates and returns an ArrayList of populationSize Chromosome objects that
		 * each contain the items, with their included field randomly set to true or false
		 */
		
		ArrayList<Chromosome> myPopulation = new ArrayList<>();
		
		for (int i = 0; i < populationSize; i++) { // creates copies of the arraylist of data read in populationSize times
		Chromosome c = new Chromosome(items);
		myPopulation.add(c);
		}
		
		return myPopulation; // returns the copies of the arrayList into one new arrayList
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		/**
		 * 1. Create a set of ten random individuals to serve as the initial population
		 * 2. Add each of the individuals in the current population to the next generation
		 * 3. Randomly pair off the individuals and perform crossover to create a child and add it to the next generation as well.
		 * 4. Randomly choose ten percent of the individuals in the next generation and expose them to mutation
		 * 5. Sort the individuals in the next generation according to their fitness
		 * 6. Clear out the current population and add the top ten of the next generation back into the population
		 * 7. Repeat steps 2 through 6 twenty times
		 * 8. Sort the population and display the fittest individual to the console
		 * 
		 */
		
		int population = 10; // 10 is the initial population size
		
		ArrayList<Item> items = readData("items.txt");
		ArrayList<Chromosome> chromosomes = initializePopulation(items, population);
		ArrayList<Chromosome> nextGeneration = new ArrayList<Chromosome>();
		
		for (int i = 0; i < 20; i++) {  // creates twenty generations
			
			for (Chromosome oldGen: chromosomes) { // adds old generation into new generation
				nextGeneration.add(oldGen);
			}
			
			Collections.shuffle(nextGeneration); // randomizes the nextGeneration ArrayList
			
			for(int j = 0; j < chromosomes.size(); j += 2) { // iterates through chromosomes and creates a child based on two random parents paired together
				Chromosome parentOne = chromosomes.get(j);
				Chromosome parentTwo = chromosomes.get(j + 1);
				Chromosome child = parentOne.crossover(parentTwo);
				nextGeneration.add(child);
			}
			
			Collections.shuffle(nextGeneration); // randomizes the new child chromosomes added to nextGeneration
			
			for (int k = 0; k < nextGeneration.size(); k++) { // randomly mutates 10% of the chromosomes
				nextGeneration.get(k).mutate();
			}
			
			Collections.sort(nextGeneration); // sorts the chromosomes into the most fit
			chromosomes.clear(); // clears the old gen 
			
			for (int l = 0; l < population; l++) { // iterates through the population and adds the nextGen to replace the OldGen
				chromosomes.add(nextGeneration.get(l));
			}
		}
		
		Collections.sort(chromosomes); // sorts chromosomes into most fit
		System.out.println(chromosomes.get(0)); //returns the most fit chromosome
		
		
	}
}
