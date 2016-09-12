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
				//Draw move
				int xIndex = board.getClickedXIndex(mouseEvent.getX());
				int yIndex = board.getClickedYIndex(mouseEvent.getY());
				if(model.checkValid(xIndex, yIndex)){
					System.out.println("x,y: " + xIndex + ", " + yIndex);
					int gameOver = model.registerMove(xIndex, yIndex);
					view.registerMove(model.getLatestMove().getIsX(), gameOver);
					ai.determineMove();
				}
			}	
		});
	}
	
	public void addViewListeners(){
		JMenuItem newGame = view.getNewGame();
		newGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.initialize();
				view.initialize();
			}
		});
	}
	
}
