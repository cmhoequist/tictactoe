package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

public class Board extends JPanel implements ComponentListener{
	int exteriorPadding = 5;
	int gridScaling = 100;
	int rows = 0;
	int columns = 0;
	Dimension size = null;
	
	
	public Board(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		size = new Dimension(gridScaling*columns + 2*exteriorPadding, gridScaling*rows + 2*exteriorPadding);
	}
	
	@Override
	public void paintComponent(Graphics g){
		int yoffset = this.getHeight()/rows;
		int xoffset = this.getWidth()/columns;
		for(int i = 0; i < rows; i++){
			g.drawLine(0, yoffset, this.getWidth(), yoffset);
			g.drawLine(xoffset, 0, xoffset, this.getHeight());
			yoffset = this.getHeight()/rows*(i+1);
			xoffset = this.getWidth()/columns*(i+1);
		}
	}

	
	//LISTENER----------------------------------------------------------------------------------------------------------------
	@Override
	public void componentResized(ComponentEvent arg0) {
		
	}
	@Override
	public void componentHidden(ComponentEvent arg0) {
	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
	}


	
	
	
}
