package view;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class View extends JFrame{
	//Critical view elements
	private Board board = null;
	private JProgressBar turnProgress = null;
	private JMenuItem newGameVsAi = null;
	private JMenuItem newGameVsPlayer = null;
	
	//Game state descriptors
	private String xStr = "'X'";
	private String oStr = "'O'";
	private String winning = " Wins!";
	private String playing = " Playing";
	
	//View painting fields
	private SwingWorker<Void, Integer> worker = null;
	private Color xColor = new Color(0, 128, 255);
	private Color oColor = new Color(255, 128, 0);
	private Color defaultProgressForeground = null;
	
	public View(){	
		//Populate frame
		JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("Menu");
				newGameVsAi = new JMenuItem("New Game (vs AI)");
				newGameVsPlayer = new JMenuItem("New Game (PvP)");
				menu.add(newGameVsAi);
				menu.add(newGameVsPlayer);
			menuBar.add(menu);
		JPanel header = new JPanel();
			header.setLayout(new BoxLayout(header,BoxLayout.LINE_AXIS));
			turnProgress = new JProgressBar();
				turnProgress.setValue(0);
				turnProgress.setStringPainted(true);
				turnProgress.setString(xStr + playing);
				defaultProgressForeground = turnProgress.getForeground();
			header.add(turnProgress);
		board = new Board(3,3, xColor, oColor);
			
		setJMenuBar(menuBar);
		getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		getContentPane().add(header);
		getContentPane().add(board);

		//Finish frame
		setTitle("Tic Tac Toe (Player vs AI)");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void initialize(){
		turnProgress.setForeground(defaultProgressForeground);
		turnProgress.setString(xStr + playing);
		board.setCursorToX();
		board.setProgress(0);
		turnProgress.setValue(100);
		board.repaint();
	}
	
	public void registerMove(boolean isX, int isOver){
		//Catch attempted inputs after game ends
		board.registerMove();
	}
	
	public Board getBoard(){
		return (Board)board;
	}
	
	public JMenuItem getNewGameVsAi(){
		return newGameVsAi;
	}
	
	public JMenuItem getNewGameVsPlayer(){
		return newGameVsPlayer;
	}
	
	public void toggleCursor(){
		board.toggleCursor();
	}
	
	public void executeWorker(int isOver){
		//Set up swingworker - not reusable
		worker = new SwingWorker<Void, Integer>(){
			@Override
			protected Void doInBackground() throws Exception {
				turnProgress.setString(oStr + playing);
				for(int i = 0; i < 100; i++){
					setProgress(i+1);
					Thread.sleep(4);
				}
				return null;
			}
			
			@Override
			protected void done(){
				displayGameState(false, isOver);	//false: this worker always works on the circle's turn. It's never x.
				board.setProgress(0);
				board.toggleCursor();
			}
		};
		worker.addPropertyChangeListener(new PropertyChangeListener(){
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if ("progress".equals(event.getPropertyName())) {
                    int value = (int) event.getNewValue();
                    turnProgress.setValue(value);
                    board.setProgress(value);
                }
			}
		});
		worker.execute();
	}
	
	/**
	 * Returns true if the game is ongoing, false if it is now over.
	 * @param isX
	 * @param isOver
	 * @return
	 */
	public boolean displayGameState(boolean isX, int isOver){
		//If we're still playing, we need to pick the opposite indicated by isX - registerMove is called on the model first, which
		//has toggled to the next turn before we've displayed this turn in the view. If we're winning, we don't want to iterate turns.
		if(isOver == -1){
			turnProgress.setString(!isX ? (xStr + playing) : (oStr + playing));
			return true;
		}
		else if(isOver == 0){
			turnProgress.setString("Game ends in TIE");
		}
		else{
			if(isX){
				turnProgress.setString(xStr + winning);
				turnProgress.setForeground(xColor);	
			}
			else{
				turnProgress.setString(oStr + winning);
				turnProgress.setForeground(oColor);
			}
		}
		return false;
	}
}
