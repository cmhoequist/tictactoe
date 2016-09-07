package view;

import javax.swing.*;

public class GameView extends JFrame{
	JPanel board = null;
	
	public GameView(){
		//Set up board
		board = new Board(3,3);
		
		//Populate frame
		getContentPane().add(board);
		
		//Finish frame
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public JPanel getBoard(){
		return board;
	}
}
