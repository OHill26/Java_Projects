package exceptionalSqaure;
import java.util.Scanner;

public class Square {
	private double side;
	
	public Square (double userSide) throws NegativeLengthException{
		side = userSide;
		if (userSide < 0.0) {
			throw new NegativeLengthException(userSide);
		}
	}
	
	public String toString() {
		return "Side = " + side;
	}
	
	public double getPerimeter() {
		return (side + side + side + side);
	}
	
	public double getArea() {
		return side * side;
	}
	
}
