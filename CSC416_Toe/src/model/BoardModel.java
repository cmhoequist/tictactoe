package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardModel {
	Map<Integer, Move> moveMap = new HashMap<>();
	int latest = -1;
	int[] rowSums = new int[3];
	int[] colSums = new int[3];
	int[] diSums = new int[2];
	boolean isX = true;
	
	public BoardModel(){
		//Initialize sums
		initialize();
	}
	
	public boolean checkWin(){
		for(int i = 0; i < 3; i++){
			if(Math.abs(rowSums[i]) > 2 || Math.abs(colSums[i]) > 2 || (i < 2 && Math.abs(diSums[i]) > 2)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkValid(int xIndex, int yIndex){
		if(moveMap.get(xIndex*3 + yIndex) != null){
			return false;
		}
		return true;
	}
	
	public boolean registerMove(int xIndex, int yIndex){
		latest = xIndex*3 + yIndex;
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
		
		//Check whether the game has ended in victory or in a tie
		if(moveMap.size() < 9){
			return checkWin();
		}
		return true;
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
	}
	
	public Move getLatestMove(){
		return moveMap.get(latest);
	}
	
	public Map<Integer, Move> getMoves(){
		return moveMap;
	}
	
	public boolean getIsX(){
		return isX;
	}
}
