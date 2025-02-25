package pongGame;
import java.awt.*;
import javax.swing.*;

public class BouncePanel {
	
	private int circleX; // x position of the ball
	private int circleY; //y position of the ball
	private static int deltaX; // how fast it moves left to right, static so I can call from Paddle class
	private static int deltaY; // how fast the ball moves up and down, called from Paddle class
	private int circleSize; 
	private int circleSpeed; // used to increase speed of ball
	private Color color; // color of the ball
	
	// constructor method
	public BouncePanel() {
		circleX = 380;
		circleY = 290;
		deltaX = 3;
		deltaY = 3;
		circleSpeed = 3;
		color = Color.BLUE;
		circleSize = 15;
	}
	public void increaseBallSpeed() { // increases ball speed every time ball bounces off a paddle
		circleSpeed++;
		if (deltaX > 0) { //increments speed of ball, checks direction of ball
			deltaX = circleSpeed;
		}
		
		else if (deltaX < 0) { //checks direction of ball and adjust accordingly
			deltaX = circleSpeed * -1;
		}
		
		if (deltaY > 0) {
			deltaY = circleSpeed;
		}
		else if (deltaY < 0) {
			deltaY = circleSpeed * -1;
		}
	}
	//getter methods, I have no setters to reduce unnecessary code and to reduce program size
	public int getSize() {
		return circleSize;
	}
	
	public int getCircleX() {
		return circleX;
	}
	
	public int getCircleY() {
		return circleY;
	}
	
	public static int getDeltaX() {
		return deltaX;
	}
	
	
	public static int getDeltaY() {
		return deltaY;
	}
	
	//creates the ball
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(circleX, circleY, circleSize, circleSize);
	}
	
	// makes the ball move
	public void animate() { //I hard coded the values for height and width 
		circleX += deltaX;
		circleY += deltaY;
		
		//bounces off right
//		if (circleX >= 780 - circleSize && deltaX > 0) { 
//			circleX = 780 - circleSize;
//			deltaX *= -1;
//		}
		
		//ball bounces off bottom
		if (circleY >= 565 - circleSize) {
			deltaY *= -1;
		}
		//ball bounces off left
//		if (circleX <= 0) { 
//			deltaX *= -1;
//		}
		//ball bounces off top
		if (circleY <= 0) {
			deltaY *= -1;
		}
	}
	
	//reverses the direction of the ball, used for the paddles
	public void reverseX() {
		deltaX *= -1;
	}

	
	
	

}
