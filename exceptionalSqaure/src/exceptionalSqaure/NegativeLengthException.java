package exceptionalSqaure;

public class NegativeLengthException extends Exception {
	public NegativeLengthException(double userLength) {
		super("Negative length: " + userLength);
	}
}
