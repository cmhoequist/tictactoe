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
		//TODO: algorithm
		return 0;
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
						return willWinHelper(rowSum, move);
					}
				}
			}
			else if(Math.abs(colSum) == 2){
				int move = -1;
				for(int j = 0; j < 3; j++){
					if(model.getElement(j*3 + i) == null){
						move = j*3+i;
						return willWinHelper(colSum, move);
					}
				}
			}
			else if(Math.abs(diSum) == 2){
				int move = -1;
				if(i==0){									//Check descending diagonal
					for(int j = 0; j < 3; j++){
						if(model.getElement(j*3 + j) == null){
							move = j*3+j;
							return willWinHelper(diSum, move);
						}
					}
				}
				else{										//Check ascending diagonal
					for(int j = 0; j < 3; j++){
						if(model.getElement((j+1)*2) == null){
							move = (j+1)*2;
							return willWinHelper(diSum, move);
						}
					}
				}
			}
		}
		return false;
	}
	
	
	private boolean willWinHelper(int sum, int move){
		if(sum < 0 && polarity < 0 || sum > 0 && polarity > 0){
			winningMove = move;
		}
		else{
			losingMove = move;
		}
		if(model.getElement(move)==null){
			
		}
		
		if(!model.checkValid(winningMove)){
			winningMove = -1;
		}
		if(!model.checkValid(losingMove)){
			losingMove = -1;
		}
		System.out.println((sum < 0 && polarity < 0 ? "X: " : "O: ") + "w,l: " + winningMove + ", " + losingMove);
		return true;
	}
}
