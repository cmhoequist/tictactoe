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
		if(willWin()){
			System.out.println("w,l: " + winningMove + ", " + losingMove);
		}
		
		//TODO: algorithm
		return 0;
	}
	
	public boolean willWin(){
		for(int i = 0; i < 3; i++){
			int rowSum = model.getRowSums()[i];
			int colSum = model.getColSums()[i];
			int diSum = -1;
			if(i < 2){
				diSum = model.getDiSums()[i];
			}
			System.out.println("SUMS: " + rowSum + ", " + colSum + ", " + diSum);
			if(Math.abs(rowSum) == 2){
				int move = 0;
				for(int j = 0; j < 3; j++){
					if(model.getElement(i*3 + j) == null){
						move = i*3+j;
					}
				}
				if(rowSum < 0 && polarity < 0 || rowSum > 0 && polarity > 0){
					winningMove = move;
				}
				else{
					losingMove = move;
				}
				return true;
			}
			else if(Math.abs(colSum) == 2){
				int move = -1;
				for(int j = 0; j < 3; j++){
					if(model.getElement(j*3 + i) == null){
						move = j*3+i;
					}
				}
				if(colSum < 0 && polarity < 0 || colSum > 0 && polarity > 0){
					winningMove = move;
				}
				else{
					losingMove = move;
				}
				return true;
			}
			else if(Math.abs(diSum) == 2){
				int move = -1;
				if(i==0){
					for(int j = 0; j < 3; j++){
						if(model.getElement(j*3 + j) == null){
							move = j*3+j;
						}
					}
				}
				else{
					for(int j = 0; j < 3; j++){
						if(model.getElement((j+1)*2) == null){
							move = (j+1)*2;
						}
					}
				}
				if(diSum < 0 && polarity < 0 || diSum > 0 && polarity > 0){
					winningMove = move;
				}
				else{
					losingMove = move;
				}
				return true;
			}
		}
		return false;
	}
}
