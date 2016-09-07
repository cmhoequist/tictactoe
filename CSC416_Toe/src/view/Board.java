package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Board extends JPanel implements ComponentListener{
	//Size constraints
	private int exteriorPadding = 5;
	private int gridScaling = 100;
	private Dimension size = null;
	private int rows = 0;
	private int columns = 0;
	//Painting constraints
	private int minCoord = 5;
	private int maxY = 0, maxX = 0;
	private int height = 0, width = 0;
	
	public Board(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		size = new Dimension(gridScaling*columns + 2*exteriorPadding, gridScaling*rows + 2*exteriorPadding);
		setPreferredSize(size);
		addComponentListener(this);
		addMouseListeners();
		setSizeConstraints();
	}
	
	@Override
	public void paintComponent(Graphics g){
		int yoffset = height/rows;
		int xoffset = width/columns;
		for(int i = 0; i < rows; i++){
			g.drawLine(minCoord, yoffset, maxX, yoffset);	//Draw horizontal lines
			g.drawLine(xoffset, minCoord, xoffset, maxY);	//Draw vertical lines
			yoffset = height/rows*(i+1);
			xoffset = width/columns*(i+1);
			g.setColor(Color.BLUE);
			g.drawRect(minCoord, minCoord, width, height);
			g.setColor(Color.BLACK);
		}
	}

	public void setSizeConstraints(){
		maxY = getHeight() - exteriorPadding;
		maxX = getWidth() - exteriorPadding;
		height = maxY - minCoord;
		width = maxX - minCoord;
	}
	
	public int getRow(){
		//TODO
		return 0;
	}
	
	public int getColumn(){
		//TODO
		return 0;
	}
	
	//LISTENERS----------------------------------------------------------------------------------------------------------------
	public void addMouseListeners(){
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mouseEvent){
				
			}
		});
	}
	
	@Override
	public void componentResized(ComponentEvent arg0) {
		setSizeConstraints();
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
