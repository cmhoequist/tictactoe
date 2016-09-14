package model;

import view.Board;

public class ArtificialPlayer {
	BoardModel model = null;
	int winningMove = -1;
	int losingMove = -1;
	int polarity = 0;
	
	//Polarity tells it whether its win condition is - or + (which maps to one of the symbols, X or O).
	public ArtificialPlayer(BoardModel model, int polarity){
		this.model = model;
		this.polarity = polarity;
	}
	
	public void initialize(){
		winningMove = -1;
		losingMove = -1;
	}
	
	public int determineMove(){
		checkWinConditions();
		if(winningMove < 0){
			if(losingMove < 0){
				for(int i = 0; i < 9; i++){	
					if(model.checkValid(i)){
						return i;
					}
				}
				return -1; //if there are no valid moves and we're still asked to determine move, an error has occurred
			}
			return losingMove;
		}
		return winningMove;
	}
	
	public boolean checkWinConditions(){
		for(int i = 0; i < 3; i++){	//i is the yIndex here: the row. j will be the xIndex (column).
			int rowSum = model.getRowSums()[i];
			int colSum = model.getColSums()[i];
			int diSum = 0;
			if(i < 2){
				diSum = model.getDiSums()[i];
			}
			if(Math.abs(rowSum) == 2){
				for(int j = 0; j < 3; j++){
					if(model.getElement(i*3 + j) == null){
						if(setCalculatedMove(rowSum, i*3+j)){	//Only return if it's a win; otherwise keep looking. (Prioritize wins over losses).
							return true;
						}
						break;
					}
				}
			}
			if(Math.abs(colSum) == 2){
				for(int j = 0; j < 3; j++){
					if(model.checkValid(j*3 + i)){
						if(setCalculatedMove(colSum, j*3+i)){
							return true;
						}
						break;
					}
				}
			}
			if(Math.abs(diSum) == 2){
				if(i==0){									//Check descending diagonal
					for(int j = 0; j < 3; j++){
						if(model.getElement(j*3 + j) == null){
							if(setCalculatedMove(diSum, j*3+j)){
								return true;
							}
							break;
						}
					}
				}
				else{										//Check ascending diagonal
					for(int j = 0; j < 3; j++){
						if(model.getElement((j+1)*2) == null){
							if(setCalculatedMove(diSum, (j+1)*2)){
								return true;
							}
							break;
						}
					}
				}
			}
		}
		validateCalculatedMoves();
		return false;
	}
	
	
	/**
	 * Returns true if the move is winning, false if it losing.
	 * @param sum
	 * @param move
	 * @return
	 */
	private boolean setCalculatedMove(int sum, int move){
		boolean wins = false;
		if(sum < 0 && polarity < 0 || sum > 0 && polarity > 0){
			winningMove = move;
			wins = true;
		}
		else{
			losingMove = move;
		}
		validateCalculatedMoves();
		return wins;
	}
	
	/**
	 * winningMove or losingMove may become invalid as players continue to 
	 * populate the board. This function checks for validity and updates the stored moves.
	 */
	private void validateCalculatedMoves(){
		if(!model.checkValid(winningMove)){
			winningMove = -1;
		}
		if(!model.checkValid(losingMove)){
			losingMove = -1;
		}
	}
}
