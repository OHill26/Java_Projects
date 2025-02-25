package packageDeliverySimulation;
import java.util.Scanner;
import java.util.*;
import java.io.*;

public class Simulation {
	
	public static ArrayList<Train> myTrains = new ArrayList<Train>(); //hold all trains
	public static ArrayList<Truck> truckList = new ArrayList<Truck>(); //holds all trucks
	public static Scanner scanner = new Scanner(System.in); //universal scanner so I didn't need to retype in each method
	public static int PERCENT_BY_DRONE; //see method for initialization
	public static final double OFFSET = 15.0; // how often a truck initially leaves
	public static final double crossing = 100.0; //a truck takes 100 minutes to get to crossing
	public static ArrayList<Truck> waitList = new ArrayList<Truck>(); // going to hold trucks that have to wait
	public static PriorityQueue<Event> eventQueue = new PriorityQueue<>(); // holds all events
	

	public static void main(String[] args) throws FileNotFoundException {
		
		int numPackages = setPackageNum(); // number of packages
		int numDrones = numDrones(setDronePercent(), numPackages); //number of Drones
		int numTrucks = numTrucks(numDrones, numPackages); // number of trucks
		
		createTrucks(numTrucks);
		readTrainInfo();
		packageSimulationDelivery();
		
		// Stat methods
		individualTruckTime();
		truckTotalTime();
		printDroneAverageTime();
		printDroneTotalTime(numDrones);
		printTotalTime();
		
		
		
	}
	
	// Adds all trucks and trains to priorityQueue
	public static void addEventsToPriorityQueue() {
		for (int i = 0; i < myTrains.size(); i++) { //Cycle through trains, put them in sorted queue
			eventQueue.offer(myTrains.get(i));
		}
		for (int i = 0; i < truckList.size(); i++) { //Cycle through trucks, put them in sorted queue
			eventQueue.offer(truckList.get(i)); 
		}
	}
	
	//Call to start the simulation	
	public static void packageSimulationDelivery() {
		addEventsToPriorityQueue(); // adds all truck and train events into a sorted PriorityQueue
		double simClock; //used to keep track of clock
		Boolean isTrain = false; //True if train is blocking the intersection
		
		while(!eventQueue.isEmpty()) { //cycle through all sorted events
			Event e = eventQueue.poll();
			simClock = e.getTime();
			
			if (e instanceof Train) { //found a train object in queue
				Train train = ((Train) e);
				
				if (train.getIsBlocking()) { // checks if train is blocking
					train.setNotBlocking(); //update for next train event
					isTrain = true; //sets current event to blocking
					System.out.println(simClock + ": Train arrives at crossing");
					eventQueue.offer(train); //add train back into queue
				}
				
				else { //Train is not blocking
					System.out.println(simClock + ": Train leaves crossing");
					if (!waitList.isEmpty()) { //check if any trucks are waiting
						isTrain = false; // No train is blocking anymore
						updateTruckInfo(eventQueue, waitList.remove(0), train.getEndTime() + 1, "crossing");  // removes truck from waitList and updates truck info
					}
				}
				
			}
			
			else { // Found a truck object instead of a train object
				Truck truck = ((Truck) e);
				
				if (truck.getEvent().equals("beginning")) { //checks what stage the truck is at, "beginning" is default
					System.out.println(simClock + ": Truck #" + truck.getTruckNum() + " begins journey");
					updateTruckInfo(eventQueue, truck, truck.getTime() + 100, "at crossing"); //updates for next truck event (truck reaches crossing)
				}
				
				else if (truck.getEvent().equals("at crossing")) { // truck reaches crossing
					if (isTrain || (!waitList.isEmpty())) { //checks if train is blocking or there is a truck waiting
						waitList.add(truck); //adds truck to waitlist since train is blocking or truck is waiting
						System.out.println(simClock + ": Truck #" + truck.getTruckNum() + " arrives at crossing"); //updates truck for next event
						

					}

					else { //No train is blocking and no truck is waiting at the crossing
						updateTruckInfo(eventQueue, truck, truck.getTime(), "crossing"); //updates the truck to cross the crossing
						
					}
				}
				
				else if (truck.getEvent().equals("crossing")) { // Checks if Truck is crossing
					System.out.println(simClock + ": Truck #" + truck.getTruckNum() + " crosses crossing"); //Print event
					
					if (waitList.isEmpty()) {
						updateTruckInfo(eventQueue, truck, truck.getTime() + 900, "done"); //Updates truck info after crossing
					}
					else {
						updateTruckInfo(eventQueue, waitList.remove(0),  simClock + 1, "crossing");  //updates next truck to cross
						updateTruckInfo(eventQueue, truck, simClock + 900, "done"); //updates current truck to finish journey
					}
				}
				
				else if (truck.getEvent().equals("done")) {
					System.out.println(simClock + ": Truck #" + truck.getTruckNum() + " completes journey");
					truck.setTotalTime(simClock); //passes the time each truck finishes for stats calculations
				}
			}
		}
		
	}
	
	
	//creates number of trucks necessary
	public static void createTrucks(int numberOfTrucks) {
		double startTime = 0.0;
		
		
		
		for (int i = 0; i < numberOfTrucks; i++) { //creates all trucks, passes in truck num and startTime
			truckList.add(new Truck(i, startTime));
			startTime += OFFSET;
		}
	}
	
	//set and get number of packages
	public static int setPackageNum() {
		int numPackages;
		System.out.println("How many Packages?");
		numPackages = scanner.nextInt(); 
		while (numPackages <= 0) { //Can only put in a number greater than zero for packages
			System.out.println("Must be greater than 0");
			numPackages = scanner.nextInt();
		}
		
		return numPackages;
	}
	
	//asks user for package by drone percentage
	public static int setDronePercent() {
		System.out.println("Percent by drone?");
		PERCENT_BY_DRONE = scanner.nextInt();
		
		while (PERCENT_BY_DRONE < 0 || PERCENT_BY_DRONE > 100) {
			System.out.println("Must be a number between 0 and 100");
			PERCENT_BY_DRONE = scanner.nextInt();
		}
		
		return PERCENT_BY_DRONE;
		
		
	}
	
	// total number of drones needed
	public static int numDrones(int dronePercent, int numPackages) {
		int numDrones;
		double dronePercentConv = (dronePercent / 100.0); // converts dronePercent to a double rounded to two decimal places
		
		numDrones = (int) (dronePercentConv * numPackages); //multiply dronePercent and number of packages for number of drones needed
		
		return numDrones; 
	}
	
	//gets total number of Trucks needed
	public static int numTrucks(int numDrones, int numPackages) {
		double numTrucks;
		double leftOver = numPackages - numDrones;
	
		numTrucks = Math.ceil((leftOver / 10.0)); //take ceiling function to avoid float rounding errors for total number of trucks
		
		return (int)numTrucks;
	}
	
	//Returns the time it takes for a drone to deliver a package (same for all drones)
	public static void printDroneAverageTime() {
		int distance = 30000; //hard coded distance, always going to be 30,000 units
		int distancePerMin = 500; //A drone travels 500units per minute
		System.out.println("\nDRONE AVERAGE TIME: " + distance / distancePerMin + " minutes");
	}
	
	//Total time for all drones
	public static void printDroneTotalTime(int numDrones) {
		double droneTotal = 60.0 + ((numDrones - 1.0) * 3.0);
		System.out.println("DRONE TOTAL TIME: " + droneTotal + " minutes"); // 60 is the minutes a drone takes to deliver a package, 3 is how often a drone departs
	}
	
	//reads in train schedule
	public static void readTrainInfo() throws FileNotFoundException {
		
		File trainSchedule = new File("Train_schedule.txt");
		Scanner inFS = new Scanner(trainSchedule);
		
		while(inFS.hasNext()) { //reads through file creates string, splits string, change string to double and pass it in to train
			String myString = inFS.nextLine(); //
			String[] splitString = myString.split(" "); //Splits a single train's begin and end time into separate strings
			String arrive = splitString[0];
			String leave = splitString[1];
			
			myTrains.add(new Train(Double.parseDouble(arrive), Double.parseDouble(leave) + Double.parseDouble(arrive)));  //Parse times read in as string to a double when creating new object
			
		}
		inFS.close();
	}
	
	//updates truck info 
	public static void updateTruckInfo(PriorityQueue<Event> pq, Truck truck, double setTime, String event) {
		truck.setEvent(event);
		truck.setTime(setTime);
		pq.offer(truck);
	}
	
	//prints trip time for each truck
	public static void individualTruckTime() {
		double allTruckMinutes = 0.0;
		
		System.out.print("\nSTATS\n---\n");
		for (int i = 0; i < truckList.size(); i++) { //iterates through all trucks
			System.out.println("Truck #" + i + " total trip time: " + truckList.get(i).getTotalTime() + " minutes"); //prints total time it took per truck
			allTruckMinutes += truckList.get(i).getTotalTime();
		}
		truckAVGTime(allTruckMinutes); // prints average truck time and passes in the sum of all individual truck times 
	}
	
	//Prints average time takes for the trucks to finish
	public static void truckAVGTime(double allMinutes) {
		double meanTime = (allMinutes / (double)truckList.size()); // sets meanTime to total minutes / number of trucks
		meanTime = Math.round(meanTime * 100.0) / 100.0; //rounds minutes to two decimal places
		System.out.print("\nTRUCK AVG TRIP TIME: " + meanTime + " minutes\n");
	}
	
	//Prints total time took for all trucks to finish
	public static void truckTotalTime() {
		System.out.println("TOTAL TRUCK TIME: " + truckList.get(truckList.size()-1).getTime() + " minutes");
	}
	
	//Prints total time everything took to finish
	public static void printTotalTime() {
		System.out.println("\nTOTAL TIME: " + truckList.get(truckList.size()-1).getTime() + " minutes");
	}
	

}
