package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.MoveGrid;
import view.GameView;

public class Driver {
	
	public static void main(String[] args) {
		Controller controller = new Controller(new GameView(), new MoveGrid());
	}

	
	
}
