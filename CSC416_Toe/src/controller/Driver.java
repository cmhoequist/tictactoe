package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.BoardModel;
import view.View;

public class Driver {
	public static void main(String[] args) {
		Controller controller = new Controller(new View(), new BoardModel());
	}
}
