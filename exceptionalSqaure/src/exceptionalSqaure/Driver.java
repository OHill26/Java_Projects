package exceptionalSqaure;

import java.util.Scanner;

public class Driver {

	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		double sideLength;
		System.out.print("Enter the length of the square's side: ");
		Square mySquare;
		
		try {
			sideLength = scanner.nextDouble();
			mySquare = new Square(sideLength);
			System.out.println("Square with " + mySquare.toString());
			System.out.println("The perimeter of the square is " + mySquare.getPerimeter());
			System.out.println("The area of the square is " + mySquare.getArea());
			
			if (sideLength < 0) {
				throw new NegativeLengthException(sideLength);
			}
			
			if (Double.isNaN(sideLength)) {
				throw new Exception("Error: you must enter a number");
			}
		}
		catch(NegativeLengthException e) {
			System.out.println(e.getMessage());
		}
		
		
		catch (Exception e) {
			System.out.println("Error: you must enter a number");
		}

	}

}
