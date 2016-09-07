package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import model.Move;
import model.MoveGrid;
import view.Board;
import view.GameView;

public class Controller {
	private Board board = null;
	private MoveGrid model = null;
	
	public Controller(GameView view, MoveGrid model){
		this.board = (Board)view.getBoard();
		this.model = model;
		board.setMoveHistory(model);
		
		addModelListeners();
	}

	public void addModelListeners(){
		board.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mouseEvent){
				//Draw move
				int x = mouseEvent.getX();
				int y = mouseEvent.getY();
				boolean gameOver = model.registerMove(board.getClickedXIndex(x), board.getClickedYIndex(y));
				board.registerMove();
				if(gameOver){
					System.out.println("WINNER");
				}
			}	
		});
	}
	
}
