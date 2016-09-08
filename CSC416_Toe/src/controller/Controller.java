package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JMenuItem;

import model.Move;
import model.BoardModel;
import view.Board;
import view.View;

public class Controller {
	private View view = null;
	private BoardModel model = null;
	
	public Controller(View view, BoardModel model){
		this.model = model;
		this.view = view;
		this.view.getBoard().setMoveHistory(this.model);
		
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
					boolean gameOver = model.registerMove(xIndex, yIndex);
					view.registerMove(model.getLatestMove().getIsX(), gameOver);
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
