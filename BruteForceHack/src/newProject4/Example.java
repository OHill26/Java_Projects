
import java.util.ArrayList;

import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;
import java.nio.file.Files;
import java.nio.file.Path;


/*
 * Class Example - Driver for brute force password cracker
 * @author Owen Hill
 * 
 */

//3 threads took 2 hours 10 minutes, 4 threads took 1 hour 50 minutes
public class Example {
	static int numThreads = 4; // number of threads working on decrypting - TO CHANGE
	static String fileName = "ProtectedZip.5"; //Name of file trying to crack - TO CHANGE
	static ArrayList<String> threeLetterCombos = threeLetter(); //Holds all three letter password combinations
	static ArrayList<String> fiveLetterCombos = fiveLetter(); //Holds all five letter password combinations
	static boolean isThreaded = true; //Change to true to run part two, false to run part 1
	public static volatile boolean isFound = true; // Tells other threads to stop if password is found
	
	 public static void main(String[] args) {
		 if (isThreaded == false) { // Runs part one if false, else runs part two
			 partOneSim(); 
		 }
		 else {
			 partTwoSim();
		 } 		 
	 }
	 
	 public static void partTwoSim() { //Runs part Two
		int block = (fiveLetterCombos.size() / numThreads); // Used to create subLists of original ArrayList of passwords
		int extraPasswords = (fiveLetterCombos.size() % numThreads);
		int start = 0; 
		ArrayList<MyThreads> threads = new ArrayList<>();
		
		for (int i = 0; i < numThreads; i++) { //Iterates over numThreads to create and run threads necessary
			ArrayList<String> newList = new ArrayList<>();
			if (i == (numThreads - 1)) { //Checks if on last iteration to add Remaining passwords if Integer division causes rounding errors
				newList.addAll(fiveLetterCombos.subList(start, ((start + block) - 1) + extraPasswords)); //Creates subList to pass into Thread class
				MyThreads cracker = new MyThreads(i, newList); //Creates thread
				threads.add(cracker); //Adds threads to arrayList
				start += block;
				cracker.start();
			}
			else {
			newList.addAll(fiveLetterCombos.subList(start, (start + block) - 1));
			MyThreads cracker = new MyThreads(i, newList);
			threads.add(cracker);
			start += block;
			cracker.start(); //Starts the threads
			}
		}
		try {
			for (int i = 0; i < threads.size(); i++) { //Joins all the threads
				threads.get(i).join();
			}
		}
		catch (Exception s) {
			s.printStackTrace();
		}
		System.out.println("Time it took in milliseconds " + System.currentTimeMillis()); //Prints time it takes to find password
		
	 }
	 
	 public static void partOneSim() { //Runs part one sim
		 for (int i = 0; i < threeLetterCombos.size(); i++) {  //Iterates over ArrayList that holds all the password combinations
			 try { //Supplied Code given to us
					ZipFile zipFile = new ZipFile("protected3.zip");
					zipFile.setPassword(threeLetterCombos.get(i)); //Tries every password from ArrayList until Break statement is reached
					zipFile.extractAll("contents");
					System.out.println("Successfully cracked!");
					System.out.println("Time it took in milliseconds " + System.currentTimeMillis()); //Time it took for none threaded part
					System.out.println("Password is: " + threeLetterCombos.get(i)); //Prints found password
					break; //Exit if password is found
				} catch (ZipException ze) {
					System.out.println("Incorrect password :(");
				} catch (Exception e){
					e.printStackTrace();
				}
		 }
	 }
	 
	 public static ArrayList<String> threeLetter() { // creates all three letter combinations
		 ArrayList<String> combs = new ArrayList<>(); //Going to hold all three letter combinations
		 for (char i = 97; i < 123; i++) {  // iterates over characters 'a' - 'z' 
			 for (char j = 97; j < 123; j++) {
				 for (char k = 97; k < 123; k++) {
					 combs.add("" + i + j + k); // Adds all combos to ArrayList
					 
				 }
			 }
		 }
		 return combs;
	 }
	 
	 public static ArrayList<String> fiveLetter() { // creates all five letter combinations, same logic as method above
		 ArrayList<String> moreCombos = new ArrayList<>();
		 for (char i = 97; i < 123; i++) {
			 for (char j = 97; j < 123; j++) {
				 for (char k = 97; k < 123; k++) {
					 for (char l = 97; l < 123; l++) {
						 for (char m = 97; m < 123; m++) {
							 moreCombos.add("" + i + j + k + l + m);
						 }
					 }
				 }
			 }
		 }
		 return moreCombos;
	 }
	 
	
}
