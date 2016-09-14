package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JMenuItem;

import model.Move;
import model.ArtificialPlayer;
import model.BoardModel;
import view.Board;
import view.View;

public class Controller {
	private View view = null;
	private BoardModel model = null;
	private ArtificialPlayer ai = null;
	private boolean vsAi = true;
	
	public Controller(View view, BoardModel model){
		this.model = model;
		this.view = view;
		this.view.getBoard().setMoveHistory(this.model);
		ai = new ArtificialPlayer(this.model, 1);	//initialized to circle
		
		addModelListeners();
		addViewListeners();
	}

	public void addModelListeners(){
		Board board = view.getBoard();
		board.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mouseEvent){
				//Get move location move
				int xIndex = board.getClickedXIndex(mouseEvent.getX());
				int yIndex = board.getClickedYIndex(mouseEvent.getY());
				
				//Ignore if invalid
				if(model.checkValid(yIndex*3 + xIndex) && !model.getIsOver()){
					//Update model and get game state
					int gameOver = model.registerMove(xIndex, yIndex);	//x is true, o is false
					
					//Propagate model change to view
					view.registerMove(model.getLatestMove().getIsX(), gameOver);
					view.displayGameState(model.getLatestMove().getIsX(), gameOver);	
					
					//If not over, countermove
					if(gameOver == -1){
						view.toggleCursor();	//also, don't want to toggle cursor if the game's just ended
						if(vsAi){
							makeAImove();
						}
					}
				}
			}	
		});
	}
	
	public void makeAImove(){
		ai.determineMove();
		int cellIndex = ai.determineMove();
		int gameOver = model.registerMove(cellIndex%3, cellIndex/3);
		view.registerMove(model.getLatestMove().getIsX(), gameOver);
		view.executeWorker(gameOver);
	}
	
	public void addViewListeners(){
		view.getNewGameVsAi().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vsAi = true;
				model.initialize();
				view.initialize();
				view.setTitle("Tic Tac Toe (Player vs AI)");
				ai.initialize();
			}
		});
		view.getNewGameVsPlayer().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				vsAi = false;
				model.initialize();
				view.initialize();
				view.setTitle("Tic Tac Toe (Player vs Player)");
				ai.initialize();
				view.getBoard().setProgress(100); //must come after view initialization, which clears arcExtent
			}
		});
	}
	
}
