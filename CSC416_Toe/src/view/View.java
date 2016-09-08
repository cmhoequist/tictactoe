package view;

import javax.swing.*;

public class View extends JFrame{
	
	private Board board = null;
	private JLabel turnLabel = null;
	private JMenuItem newGame = null;
		private String xStr = "'X'";
		private String oStr = "'O'";
		private String winning = " Wins!";
		private String playing = " Playing";
	private boolean gameOver = false;
	
	public View(){
		//Set up board
		board = new Board(3,3);
		
		//Populate frame
		JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("Menu");
				newGame = new JMenuItem("New Game");
				menu.add(newGame);
			menuBar.add(menu);
	
		JPanel header = new JPanel();
			header.setLayout(new BoxLayout(header,BoxLayout.LINE_AXIS));
			turnLabel = new JLabel(xStr + playing);
			header.add(turnLabel);
			
		setJMenuBar(menuBar);
		getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		getContentPane().add(header);
		getContentPane().add(board);
		
		//Finish frame
		setTitle("Tic Tac Toe (Java Implementation)");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void initialize(){
		turnLabel.setText(xStr + playing);
		board.toggleCursor();
		board.repaint();
		gameOver = false;
	}
	
	public void registerMove(boolean isX, boolean isOver){
		if(!gameOver){
			board.registerMove();
			//If we're still playing, we need to pick the opposite indicated by isX - registerMove is called on the model first, which
			//has toggled to the next turn before we've displayed this turn in the view. If we're winning, we don't want to iterate turns.
			String text = !isX ? isOver ? oStr + winning : xStr + playing : isOver ? xStr + winning : oStr + playing;
			turnLabel.setText(text);
			gameOver = isOver;
		}
	}
	
	public Board getBoard(){
		return (Board)board;
	}
	
	public JMenuItem getNewGame(){
		return newGame;
	}
}
