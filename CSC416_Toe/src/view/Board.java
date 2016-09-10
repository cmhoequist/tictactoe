package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Move;
import model.BoardModel;

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
	private boolean repaintAll = true;
	private boolean paintMove = false;
	private int cellWidth = 0, cellHeight = 0;
	private Color xColor = new Color(0, 128, 255);
	private Color oColor = new Color(255, 128, 0);
	
	//Move image data
	private BoardModel moves = null;
	private Cursor xCursor = null;
	private Cursor oCursor = null;
	
	public Board(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		size = new Dimension(gridScaling*columns + 2*exteriorPadding, gridScaling*rows + 2*exteriorPadding);
		setPreferredSize(size);
		addComponentListener(this);
		setSizeConstraints();
		
		//Set cursor
		try {
			Image xPNG = ImageIO.read(new File("C:\\Users\\Moritz\\Desktop\\Coding\\Java\\resources\\X.png"));
			Image oPNG = ImageIO.read(new File("C:\\Users\\Moritz\\Desktop\\Coding\\Java\\resources\\O.png"));
			xCursor = Toolkit.getDefaultToolkit().createCustomCursor(xPNG, new Point(this.getX(), this.getY()), "xCursor");
			oCursor = Toolkit.getDefaultToolkit().createCustomCursor(oPNG, new Point(this.getX(), this.getY()), "oCursor");
			this.setCursor(xCursor);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		//Note that when the parent is redrawn, its children are too - gating a full repaint for the sake of efficiency
		//risks not painting the background if a parent repaint triggers unexpectedly
		int yoffset = minCoord + cellHeight;
		int xoffset = minCoord + cellWidth;
		for(int i = 0; i < rows; i++){
			g2d.setStroke(new BasicStroke(4));
			g.drawLine(minCoord, yoffset, maxX, yoffset);	//Draw horizontal lines
			g.drawLine(xoffset, minCoord, xoffset, maxY);	//Draw vertical lines
			yoffset = minCoord + cellHeight*(i+1);
			xoffset = minCoord + cellWidth*(i+1);
		}
		for(Move move : moves.getMoves().values()){				//TODO: note that if we somehow have paintMove = true, this will cause problems in a second
			paintMove(g, move);
		}
	}

	public void paintMove(Graphics g, Move move){	
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	//Smoothness of drawing
		
		double cellCover = 0.6;
		int shrinkWidth = new Double(cellWidth*cellCover).intValue();
		int shrinkHeight = new Double(cellHeight*cellCover).intValue();
		int baseX = minCoord + move.getX()*cellWidth + (cellWidth - shrinkWidth)/2;
		int baseY = minCoord + move.getY()*cellHeight + (cellHeight - shrinkHeight)/2;
		
		if(move.getIsX()){
			g.setColor(xColor);
			g.drawLine(baseX, baseY, baseX + shrinkWidth, baseY + shrinkHeight);
			g.drawLine(baseX, baseY + shrinkHeight, baseX + shrinkWidth, baseY);
		}
		else{
			g.setColor(oColor);
			g.drawOval(baseX, baseY, shrinkWidth, shrinkHeight);
		}
	}
	
	//SETTERS/GETTERS----------------------------------------------------------------------------------------
	public void setMoveHistory(BoardModel grid){
		moves = grid;
	}
	
	
	public void setSizeConstraints(){
		maxY = getHeight() - exteriorPadding;
		maxX = getWidth() - exteriorPadding;
		height = maxY - minCoord;
		width = maxX - minCoord;
		cellHeight = height/rows;
		cellWidth = width/columns;
	}
	
	public int getClickedYIndex(int y){
		if(y > height/3 + minCoord){
			if(y > 2*height/3 + minCoord){
				return 2;
			}
			else{
				return 1;
			}
		}
		else{
			return 0;
		}
	}
	
	public int getClickedXIndex(int x){
		if(x > width/3 + minCoord){
			if(x > 2*width/3 + minCoord){
				return 2;
			}
			else{
				return 1;
			}
		}
		else{
			return 0;
		}
	}
	
	public void registerMove(){
		paintMove = true;
		toggleCursor();
		repaint();
	}
	
	public void toggleCursor(){
		if(moves.getIsX() && this.getCursor().getName().equals("oCursor")){
			this.setCursor(xCursor);
		}
		else if(!moves.getIsX() && this.getCursor().getName().equals("xCursor")){
			this.setCursor(oCursor);
		}
	}
	
	//LISTENERS----------------------------------------------------------------------------------------------------------------
	
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
