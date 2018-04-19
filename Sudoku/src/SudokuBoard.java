public class SudokuBoard {
	
	private static int[][] sudokuBoard = new int[9][9];
	private static boolean[][][] possibleValues = new boolean[9][9][9];
	
	private static void initPossible() {
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				for(int k = 0; k < 9; ++k) {
					possibleValues[i][j][k] = true;
				}
			}
		}
	}
	
	public static void set(int i, int j, int val) {
		sudokuBoard[i][j] = val;
	}
	
	// prints board
	public static void printBoard() {
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
	
	// checks row for num
	private static boolean checkRow(int row, int num) {
		for(int j = 0; j < 9; ++j) {
			if(sudokuBoard[row][j] == num) {
				return true;
			}
		}
		return false;
	}
	
	// checks column for num
	private static boolean checkCol(int col, int num) {
		for(int i = 0; i < 9; ++i) {
			if(sudokuBoard[i][col] == num) {
				return true;
			}
		}
		return false;
	}
	
	// checks 3x3 region to see if it contains num
	//	regions are numbered left->right, top->bottom
	private static boolean checkRegion(int region, int num) {
		int startRow = ((region - 1) / 3) * 3;
		int startCol = (((region - 1) % 3)) * 3;
		for(int i = startRow; i < startRow + 3; ++i) {
			for(int j = startCol; j < startCol + 3; ++j) {
				if(num == sudokuBoard[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	// prints the region of row, col
	private static int getRegion(int row, int col) {
		return ((row / 3) * 3) + (col / 3) + 1;
	}
	
	private static boolean complete() {
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				if(sudokuBoard[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static void updatePosVal(int row, int col) {
		int region = getRegion(row, col);
		for(int num = 0; num < 9; ++num) {
			if (!possibleValues[row][col][num]) {} 
			else if(checkRegion(region, num + 1) ||
					checkRow(row, num + 1) ||
					checkCol(col, num + 1)) { 
				possibleValues[row][col][num] = false;
			}
		}
	}
	
	private static void updateVal(int row, int col) {
		int t = 0;
		int val = 0;
		for(int k = 0; k < 9; ++k) {
			if(possibleValues[row][col][k]) {
				++t;
				val = k + 1;
			}
		}
		if(t < 0) {
			System.out.println("Unsolveable puzzle");
			System.exit(1);
		} else if(t == 1) {
			sudokuBoard[row][col] = val;
		}
	}
	
	public static void solve() {
		initPossible();
		while(!complete()) {
			for(int i = 0; i < 9; ++i) {
				for(int j = 0; j < 9; ++j) {
					if(sudokuBoard[i][j] == 0) {
						updatePosVal(i, j);
						updateVal(i, j); 
					}
				}
			}
		}
	}
}
