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
	
	public int determineMove(){
		willWin();
		if(winningMove < 0){
			if(losingMove < 0){
				for(int i = 0; i < 9; i++){	
					if(model.checkValid(i)){
						System.out.println("MOVE (nonterminating): " + i);
						return i;
					}
				}
				return -1; //if there are no valid moves and we're still asked to determine move, an error has occurred
			}
			System.out.println("MOVE:" + losingMove);	
			return losingMove;
		}
		System.out.println("MOVE:" + winningMove);
		return winningMove;
	}
	
	public boolean willWin(){
		for(int i = 0; i < 3; i++){
			int rowSum = model.getRowSums()[i];
			int colSum = model.getColSums()[i];
			int diSum = 0;
			if(i < 2){
				diSum = model.getDiSums()[i];
			}
			if(Math.abs(rowSum) == 2){
				int move = 0;
				for(int j = 0; j < 3; j++){
					if(model.getElement(i*3 + j) == null){
						move = i*3+j;
						if(willWinHelper(rowSum, move)){	//Only return if it's a win; otherwise keep looking
							return true;
						}
						break;
					}
				}
			}
			if(Math.abs(colSum) == 2){
				int move = -1;
				for(int j = 0; j < 3; j++){
					if(model.getElement(j*3 + i) == null){
						move = j*3+i;
						if(willWinHelper(colSum, move)){
							return true;
						}
						break;
					}
				}
			}
			if(Math.abs(diSum) == 2){
				int move = -1;
				if(i==0){									//Check descending diagonal
					for(int j = 0; j < 3; j++){
						if(model.getElement(j*3 + j) == null){
							move = j*3+j;
							if(willWinHelper(diSum, move)){
								return true;
							}
							break;
						}
					}
				}
				else{										//Check ascending diagonal
					for(int j = 0; j < 3; j++){
						if(model.getElement((j+1)*2) == null){
							move = (j+1)*2;
							if(willWinHelper(diSum, move)){
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
	
	
	private boolean willWinHelper(int sum, int move){
		boolean wins = false;
		if(sum < 0 && polarity < 0 || sum > 0 && polarity > 0){
			winningMove = move;
			wins = true;
		}
		else{
			losingMove = move;
		}
		validateCalculatedMoves();
		System.out.println((sum < 0 && polarity < 0 ? "X: " : "O: ") + "w,l: " + winningMove + ", " + losingMove);
		return wins;
	}
	
	private void validateCalculatedMoves(){
		if(!model.checkValid(winningMove)){
			winningMove = -1;
		}
		if(!model.checkValid(losingMove)){
			losingMove = -1;
		}
	}
}
