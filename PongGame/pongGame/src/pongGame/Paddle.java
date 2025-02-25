package pongGame;
import javax.swing.*;
import java.awt.*;

/**
 * 
 * @author OwenHill
 * Paddle Class - creates paddles and handles movement
 *
 */
public class Paddle extends Component {
	private int height; //paddle height
	private int width; // paddle width
	private int speed; // speed paddle can move at when keys are pressed
	private int paddleX; 
	private int paddleY;
	private Color color;
	
	//constructor for the paddles
	public Paddle(int paddleX, int paddleY, int height, int width, int speed, Color color) {
		this.paddleX = paddleX;
		this.paddleY = paddleY;
		this.height = height;
		this.width = width;
		this.speed = speed;
		this.color = color;
		
	}
	
	//paints paddle objects
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(paddleX, paddleY, width, height);
		
	}
	
	/** 
	 *  the following  movePlayer methods will change the paddle movements, called in the key 
	 *  listener event (includes redundant code technically but I kept it for ease of reading the code)
	 */
	
	public int moveUpPlayerOne() {
		
		if(paddleY > 0 && BouncePanel.getDeltaX() < 0) {
			return paddleY -= speed;
		}
		else {
			return 0;
		}
		
	}
	
	
	public int moveDownPlayerOne() {
		if(paddleY < 450 && BouncePanel.getDeltaX() < 0) {
			return paddleY += speed;
		}
		else {
			return 0;
		}
		
	}
	public int moveUpPlayerTwo() {
		
		if(paddleY > 0 && BouncePanel.getDeltaX() > 0) {
			return paddleY -= speed;
		}
		else {
			return 0;
		}
	}
	
	public int moveDownPlayerTwo() {
		if(paddleY < 450 && BouncePanel.getDeltaX() > 0) {
			return paddleY += speed;
		}
		else {
			return 0;
		}
	}
	
	
	//returns a boolean value if the ball hits the paddle
	public boolean checkCollision(BouncePanel b) {
		int rightSide = paddleX + width;
		int bottomSide = paddleY + height;
		
		//checks if it hits between x value of the paddle
		if (b.getCircleX() > (paddleX - b.getSize()) && b.getCircleX() < rightSide) {
			//checks if the ball hits the y value of the ball
			if (b.getCircleY() > paddleY && b.getCircleY() < bottomSide) {
				
				return true;
			}
		}
		return false;
			
	}
	
	
}
