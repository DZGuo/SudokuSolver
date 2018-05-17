
public class SudokuBoard {
	private int[][] sudokuBoard = new int[9][9];
	private boolean[][][] possibleValues = new boolean[9][9][9];
	
	public void set(int i, int j, int val) {
		sudokuBoard[i][j] = val;
	}
	
	public void set(pos p, int val) {
		sudokuBoard[p.row][p.col] = val;
	}
	
	public int get(int i, int j) {
		return sudokuBoard[i][j];
	}
	
	public int get(pos p) {
		return sudokuBoard[p.row][p.col];
	}
	
	public void setPosVal(int i, int j, int k, boolean bool) {
		possibleValues[i][j][k] = bool;
	}
	
	public boolean getPosVal(int i, int j, int k) {
		return possibleValues[i][j][k];
	}
	
	public void print() {
		System.out.println(" " + String.format("%0" + 17 + "d", 0).replace("0","-") + " ");
		for(int i = 0; i < 9; ++i) {
			System.out.print("|");
			for(int j = 0; j < 9; ++j) {
				if (sudokuBoard[i][j] != 0) {
					System.out.print(sudokuBoard[i][j]);
				} else {
					System.out.print(" ");
				}
				// vertical separators
				if(j % 3 == 2) {
					System.out.print("|");
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("\n");
			
			// horizontal separators
			if(i % 3 == 2 && i != 8) {
				System.out.println("|" + String.format("%0" + 17 + "d", 0).replace("0","-") + "|");
			} else if (i == 8) {
				System.out.println(" " + String.format("%0" + 17 + "d", 0).replace("0","-") + " ");
			}
		}
	}
}
