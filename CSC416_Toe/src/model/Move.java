package model;

public class Move {
	private int xIndex = 0, yIndex = 0;
	boolean isX = true;
	
	public Move(int x, int y, boolean xBool){
		xIndex = x; 
		yIndex = y;
		isX = xBool;
	}
	
	public int getX(){
		return xIndex;
	}
	
	public int getY(){
		return yIndex;
	}
	
	public boolean getIsX(){
		return isX;
	}
	
	public int getIndex(){
		return yIndex*3 + xIndex;
	}
}
