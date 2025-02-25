package pongGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * 
 * @author OwenHill 
 * Class Pong - handles most of the logic for the game including scores, ball and paddle logic (called from respective classes), extends JPanel
 *
 */

public class Pong extends JPanel implements KeyListener {
	
	
	
	private int height = 575; //JPanel height
	private int width = 780; //JPanel width
	private BouncePanel panel;
	private Paddle playerOne; //creates new Paddle object
	private Paddle playerTwo; //creates anotehr paddle object for total of two paddles
	private static int playerOneScore; //static so I can call it in MainWindow
	private static int playerTwoScore;
	
	

	
	//default constructor, puts ball and paddles in the frame
	public Pong() {
		panel = new BouncePanel();
		playerOne = new Paddle(10, 200, 115, 15, 8, Color.BLACK);
		playerTwo = new Paddle(755, 200, 115, 15, 8, Color.BLACK);
		this.addKeyListener(this);
		playerOneScore = 0;
		playerTwoScore = 0;

	}
	
	//paints the frame 
	public void paintComponent(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);

		panel.paint(g);
		playerOne.paint(g);
		playerTwo.paint(g);
	}

	//handles the logic of the game
	public void gameLogic() {
		panel.animate(); // makes the ball move and bounce off top and bottom
		
		if (playerOne.checkCollision(panel) || playerTwo.checkCollision(panel)) { // checks if ball hits paddle, if does reverses direction of ball
			panel.reverseX();
			panel.increaseBallSpeed();
		}
		
		if (panel.getCircleX() < 0) { // checks if ball is going out of frame at the left side
			playerTwoScore++;
			MainWindow.updateScore();
			panel = new BouncePanel();
			//player one loses
			
			
			
		}
		if (panel.getCircleX() > 785) { // checks if the ball is is going at of frame on the right side
			//PLayer two loses
			playerOneScore++;
			MainWindow.updateScore();
			panel = new BouncePanel();
			
		}
		
		if (playerOneScore == 10) { // the game ends when a player gets 10 points
			JOptionPane.showMessageDialog(MainWindow.getGameWindow(), "Player one wins!");
			System.exit(0);
		}
		
		if (playerTwoScore == 10) {
			JOptionPane.showMessageDialog(MainWindow.getGameWindow(), "Player two wins!");
			System.exit(0);
		}
	
	
	}
	//getters, static so I can access them from MainWindow
	public static int getPlayerOneScore() {
		return playerOneScore;
	}
	public static int getPlayerTwoScore() {
		return playerTwoScore;
	}
	
	public class upAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			playerOne.moveUpPlayerOne();
			
		}
	}
	
	public class downAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			playerOne.moveDownPlayerOne();
			
		}
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		//not used but needed
	}
	
		
	@Override
	// handles key events ("VK_UP" and "VK_DOWN")
	public void keyPressed(KeyEvent e) {

		
			if (e.getKeyChar() == 'w') {
			playerOne.moveUpPlayerOne();
			}
		
			if (e.getKeyChar() == 's') {
			playerOne.moveDownPlayerOne();
			}
		
			if (e.getKeyCode() == e.VK_UP) {
			playerTwo.moveUpPlayerTwo();
			}

			if (e.getKeyCode() == e.VK_DOWN) {
			playerTwo.moveDownPlayerTwo();
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Not used but needed

	}

	
	
}
