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
	private int clickedXIndex = 0, clickedYIndex = 0;
	private int cellWidth = 0, cellHeight = 0;
	
	//Moves
	private List<Move> moveList = new LinkedList<>();
	private class Move{
		private int xIndex = 0, yIndex = 0;
		boolean isX = true;
		
		public Move(int x, int y){
			xIndex = x; 
			yIndex = y;
		}
		
		public int getX(){
			return xIndex;
		}
		
		public int getY(){
			return yIndex;
		}
	}
	
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
		//Redraw grid
		if(repaintAll){	
			int yoffset = cellHeight;
			int xoffset = cellWidth;
			for(int i = 0; i < rows; i++){
				g.drawLine(minCoord, yoffset, maxX, yoffset);	//Draw horizontal lines
				g.drawLine(xoffset, minCoord, xoffset, maxY);	//Draw vertical lines
				yoffset = cellHeight*(i+1);
				xoffset = cellWidth*(i+1);
				g.setColor(Color.BLUE);
				g.drawRect(minCoord, minCoord, width, height);
				g.setColor(Color.BLACK);
			}
			for(Move move : moveList){				//TODO: note that if we somehow have paintMove = true, this will cause problems in a second
				clickedXIndex = move.getX();
				clickedYIndex = move.getY();
				paintMove(g);
			}
		}
		//Paint x in a cell
		if(paintMove){
			paintMove(g);
		}

		//Default behavior 
		repaintAll = true;
		paintMove = false;
	}

	public void paintMove(Graphics g){
		g.setColor(Color.BLUE);
		int baseX = clickedXIndex*cellWidth;
		int baseY = clickedYIndex*cellHeight;
		g.drawLine(minCoord + baseX + 5, minCoord + baseY + 5, baseX + cellWidth - 5, baseY + cellHeight - 5);
		g.drawLine(minCoord + baseX + 5, baseY + cellHeight - 5, baseX + cellWidth - 5,  minCoord + baseY + 5);
	}
	
	public void setSizeConstraints(){
		maxY = getHeight() - exteriorPadding;
		maxX = getWidth() - exteriorPadding;
		height = maxY - minCoord;
		width = maxX - minCoord;
		cellHeight = height/rows;
		cellWidth = width/columns;
	}
	
	public void setClickedRow(int x){
		if(x > width/3 + minCoord){
			if(x > 2*width/3 + minCoord){
				clickedXIndex = 2;
			}
			else{
				clickedXIndex = 1;
			}
		}
		else{
			clickedXIndex = 0;
		}
	}
	
	public void setClickedColumn(int y){
		if(y > height/3 + minCoord){
			if(y > 2*height/3 + minCoord){
				clickedYIndex = 2;
			}
			else{
				clickedYIndex = 1;
			}
		}
		else{
			clickedYIndex = 0;
		}
	}
	
	//LISTENERS----------------------------------------------------------------------------------------------------------------
	public void addMouseListeners(){
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mouseEvent){
				int x = mouseEvent.getX();
				int y = mouseEvent.getY();
				setClickedRow(x);
				setClickedColumn(y);
				moveList.add(new Move(clickedXIndex, clickedYIndex));
				repaintAll = false;
				paintMove = true;
				repaint();
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
