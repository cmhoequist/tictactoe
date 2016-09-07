package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JPanel;

import model.Move;
import model.MoveGrid;

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
	//Move image data
	private MoveGrid moves = null;
	
	public Board(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		size = new Dimension(gridScaling*columns + 2*exteriorPadding, gridScaling*rows + 2*exteriorPadding);
		setPreferredSize(size);
		addComponentListener(this);
		setSizeConstraints();
	}
	
	@Override
	public void paintComponent(Graphics g){
		//Redraw grid
		if(repaintAll){	
			int yoffset = minCoord + cellHeight;
			int xoffset = minCoord + cellWidth;
			for(int i = 0; i < rows; i++){
				g.drawLine(minCoord, yoffset, maxX, yoffset);	//Draw horizontal lines
				g.drawLine(xoffset, minCoord, xoffset, maxY);	//Draw vertical lines
				yoffset = minCoord + cellHeight*(i+1);
				xoffset = minCoord + cellWidth*(i+1);
				g.setColor(Color.BLUE);
				g.drawRect(minCoord, minCoord, width, height);
				g.setColor(Color.BLACK);
			}
			for(Move move : moves.getMoves()){				//TODO: note that if we somehow have paintMove = true, this will cause problems in a second
				paintMove(g, move);
			}
		}
		//Paint latest move
		if(paintMove){
			paintMove(g, moves.getLatestMove());
		}

		//Default behavior 
		repaintAll = true;
		paintMove = false;
	}

	public void paintMove(Graphics g, Move move){	
		g.setColor(Color.BLUE);
		int baseX = minCoord + move.getX()*cellWidth;
		int baseY = minCoord + move.getY()*cellHeight;
		if(move.getIsX()){
			g.drawLine(baseX, baseY, baseX + cellWidth, baseY + cellHeight);
			g.drawLine(baseX, baseY + cellHeight, baseX + cellWidth, baseY);
		}
		else{
			g.drawOval(baseX, baseY, cellWidth, cellHeight);
		}
		
	}
	
	//SETTERS/GETTERS----------------------------------------------------------------------------------------
	public void setMoveHistory(MoveGrid grid){
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
		repaintAll = false;
		paintMove = true;
		repaint();
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
