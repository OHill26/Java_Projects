

import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/*
 * @author Owen Hill
 * MyThreads class - contains thread logic
 */

public class MyThreads extends Thread {
	
	private int threadNum; //keep track of each thread
	private ArrayList<String> passwords; //ArrayList to hold passwords
	
	
	
	//constructor
	public MyThreads(int threadNum, ArrayList<String> passwords) {
		this.threadNum = threadNum; //Gives each thread a threadNumber
		this.passwords = passwords; //Gives each thread an ArrayList of unique passwords to try
	}
	
	@Override
	public void run() { //Thread run method
		try {
			Files.copy(Path.of("protected5.zip"), Path.of("protected5" + "-" + threadNum + ".zip")); //Copies file for each thread to try passwords on
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
			for (int i = 0; i < passwords.size() && Example.isFound; i++) { // loop to try all five letter combinations to crack the password, runs until a thread finds password
				 try { //Same logic as non-threaded password cracker (Simulation of part one)
					ZipFile zipFile = new ZipFile("protected5" + "-" + threadNum + ".zip"); 
					zipFile.setPassword(passwords.get(i)); // gets combination, tries it on the password
					zipFile.extractAll("contents-" + threadNum );
					System.out.println("Successfully cracked!");	
					System.out.println("Password is: " + passwords.get(i));
					Example.isFound = false;
					break;
				} catch (ZipException ze) {
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		 try { //Deletes copies of created files after threads terminate
			 Files.delete(Path.of("protected5" + "-" + threadNum + ".zip"));
			 Files.delete(Path.of("contents-" + threadNum + "/message.txt"));
			 Files.delete(Path.of("contents-" + threadNum));
			
		 }
		 catch (Exception ze){
			 ze.printStackTrace();
		 }
	}

}
