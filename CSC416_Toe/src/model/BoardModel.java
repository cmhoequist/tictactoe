package model;

import java.util.HashMap;
import java.util.Map;

public class BoardModel {
	Map<Integer, Move> moveMap = new HashMap<>();
	int latest = -1;
	int[] rowSums = new int[3];
	int[] colSums = new int[3];
	int[] diSums = new int[2];
	boolean isX = true;
	boolean isOver = false;
	
	public BoardModel(){
		initialize();
	}
	
	/**
	 * Returns 1 if there is a victor, 0 if there is a tie, or -1 if the game is ongoing
	 * @return
	 */
	public int checkWin(){
		for(int i = 0; i < 3; i++){
			if(Math.abs(rowSums[i]) > 2 || Math.abs(colSums[i]) > 2 || (i < 2 && Math.abs(diSums[i]) > 2)){
				isOver = true;
				return 1; //there's a winner
			}
		}
		if(moveMap.size() < 9){
			return -1; //the game is ongoing
		}
		isOver = true;
		return 0;  //there's a tie
	}
	
	public boolean checkValid(int cellIndex){
		if(moveMap.get(cellIndex) != null){
			return false;
		}
		return true;
	}
	
	public boolean checkValid(int xIndex, int yIndex){
		return checkValid(yIndex*3 + xIndex);
	}
	
	public int registerMove(int cellIndex){
		int yIndex = cellIndex/3;
		int xIndex = cellIndex%3;
		return registerMove(xIndex, yIndex);
	}
	
	public int registerMove(int xIndex, int yIndex){
		latest = yIndex*3 + xIndex;									//remember INDEX vs ROW/COL
		moveMap.put(latest, new Move(xIndex, yIndex, isX));
		
		isX = !isX;
		
		int val = isX ? 1 : -1;
		rowSums[yIndex] += val;	//Every move increments one row & one column sum
		colSums[xIndex] += val;
		if(xIndex==yIndex){		//0,0 1,1 2,2 increment first diagonal (1,1 also increments 2nd diagonal)
			diSums[0] += val;
			if(xIndex==1){
				diSums[1] += val;
			}
		}
		else if(Math.abs(xIndex-yIndex)==2){ //0,2 2,0 increment second diagonal
			diSums[1] += val;
		}
		
		//Check the game state (win, ongoing, tie)
		return checkWin();
	}
	
	public void initialize(){
		for(int i = 0; i < 3; i++){
			rowSums[i] = 0;
			colSums[i] = 0;
			if(i < 2){
				diSums[i] = 0;
			}
		}
		moveMap.clear();
		isX = true;
		isOver = false;
	}
	
	//GETTERS/SETTERS--------------------------------------------------------------------------------------------------
	public Move getLatestMove(){
		return moveMap.get(latest);
	}
	
	public boolean getIsOver(){
		return isOver;
	}
	
	public Map<Integer, Move> getMoves(){
		return moveMap;
	}
	
	/**
	 * Every time you register move, isX toggles to the next turn
	 * @return
	 */
	public boolean getIsX(){
		return isX;
	}

	public int[] getRowSums(){
		return rowSums;
	}
	
	public int[] getColSums(){
		return colSums;
	}
	
	public int[] getDiSums(){
		return diSums;
	}
	
	public Move getElement(int gridIndex){
		return moveMap.get(gridIndex);
	}
}
