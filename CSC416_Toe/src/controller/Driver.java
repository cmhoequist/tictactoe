package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import model.BoardModel;
import view.View;

public class Driver {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){	//invokelater schedules on the EDT; swing code should execute on the EDT
			@Override
			public void run() {
				View view = new View();
				BoardModel model = new BoardModel();
				Controller controller = new Controller(view, model);
			}
		});	
	}
}
