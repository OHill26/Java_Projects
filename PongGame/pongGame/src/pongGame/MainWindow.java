package pongGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Class MainWindow - contains creating of JFrame, additions to JFrame and a timer
 * @author Owen Hill
 *
 */
public class MainWindow {
	
	
	static JFrame gameWindow = new JFrame();
	
	
	//getter used for labels in other classes
	public static JFrame getGameWindow() {
		return gameWindow;
	}
	//setter
	public static void setGameWindow(JFrame gameWindow) { 
		MainWindow.gameWindow = gameWindow;
	}
	
	static JLabel scoreKeeper = new JLabel(); // label for score keeping
	
	
	public static void updateScore() { // called in Pong class to update score 
		scoreKeeper.setText("Player One score - " + Pong.getPlayerOneScore() + " Player Two score - " + Pong.getPlayerTwoScore());
	}
	
	//Main method
	public static void main(String[] args) {
		
		JPanel instructionsPopUp = new JPanel();
		JLabel instructions = new JLabel();
		scoreKeeper.setBounds(0, 0, 0, 0); // sets label middle top of screen
		scoreKeeper.setText("Player One score - " + Pong.getPlayerOneScore() + " Player Two score - " + Pong.getPlayerTwoScore());

		
		int height = 800; //window height
		int width = 600; //window width
		
		gameWindow.setSize(height, width);
		gameWindow.setTitle("Pong");
		gameWindow.setResizable(false);
		
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Pong myGame = new Pong(); // creates pong class that handles most game logic
		
		//I dont know HTML, TA helped me with this - text for instructions
		instructions.setText("<html><h1>Instructions</h1><br></br>This is a player vs player game<br></br> Player one controls the left paddle 'w' "
				+ "for moving up and 's' for down<br></br> Player two controls the right paddle with up and down arrow"
				+ "keys<br></br>The first player to get to ten points wins the game<br></br>Everytime the ball is hit the ball will speed up<br></br>Goodluck!<br></br></html>");
		
		JOptionPane.showMessageDialog(gameWindow, instructions);
		
	
		gameWindow.add(myGame); // adds pong class to JFrame
		myGame.add(scoreKeeper); //adds JLabel to Frame
		myGame.addKeyListener(myGame);
		myGame.setFocusable(true); // lets me add a keyListener to a JPanel
		
		Timer timer = new Timer(33, new ActionListener() { // repaints the ball every 33 milliseconds, could not get ball to move without this timer
			@Override										// lines 65 - 74 I got from https://kevinsguides.com/guides/code/java/javaprojs/simple-2d-pong, I took nothing else
			public void actionPerformed(ActionEvent e) {
				
				myGame.gameLogic();
				
				myGame.repaint();
			}
		});
		timer.start();
		
		gameWindow.setVisible(true);
	}
	
	
}
