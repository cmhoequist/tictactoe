package model;

import java.util.ArrayList;
import java.util.List;

public class MoveGrid {
	List<Move> moveList = new ArrayList<>();
	int[] rowSums = new int[3];
	int[] colSums = new int[3];
	int[] diSums = new int[2];
	boolean isX = true;
	
	public MoveGrid(){
		//Initialize sums
		for(int i = 0; i < 3; i++){
			rowSums[i] = 0;
			colSums[i] = 0;
			if(i < 2){
				diSums[i] = 0;
			}
		}
	}
	
	public boolean checkWin(){
		for(int i = 0; i < 3; i++){
			if(Math.abs(rowSums[i]) > 2 || Math.abs(colSums[i]) > 2 || (i < 2 && diSums[i] > 2)){
				return true;
			}
		}
		return false;
	}
	
	public boolean registerMove(int xIndex, int yIndex){
		moveList.add(new Move(xIndex, yIndex, isX));
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
		if(moveList.size() < 9){
			return checkWin();
		}
		return true;
	}
	
	public Move getLatestMove(){
		return moveList.get(moveList.size()-1);
	}
	
	public List<Move> getMoves(){
		return moveList;
	}
	
	public boolean getIsX(){
		return isX;
	}
}
