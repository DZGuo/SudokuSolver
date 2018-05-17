import java.lang.Object;
import java.util.Iterator;
import java.util.Stack;

public class Sudoku {
	
	private static SudokuBoard sudokuBoard = new SudokuBoard();
	private static SudokuBoard previousBoard = new SudokuBoard();
	private static Stack<StackElement> stack = new Stack<StackElement>();
	
	private static void initPossible() {
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				for(int k = 0; k < 9; ++k) {
					sudokuBoard.setPosVal(i, j, k, true);
				}
			}
		}
	}
	
	public static void set(int i, int j, int val) {
		sudokuBoard.set(i, j, val);
	}
	
	// prints board
	public static void printBoard() {
		sudokuBoard.print();
	}
	
	// checks row for num
	private static boolean checkRow(int row, int num) {
		for(int j = 0; j < 9; ++j) {
			if(sudokuBoard.get(row,  j) == num) {
				return true;
			}
		}
		return false;
	}
	
	// checks column for num
	private static boolean checkCol(int col, int num) {
		for(int i = 0; i < 9; ++i) {
			if(sudokuBoard.get(i,  col) == num) {
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
				if(num == sudokuBoard.get(i, j)) {
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
				if(sudokuBoard.get(i, j) == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static void updatePosVal(int row, int col) {
		int region = getRegion(row, col);
		for(int num = 0; num < 9; ++num) {
			if(checkRegion(region, num + 1) ||
					checkRow(row, num + 1) ||
					checkCol(col, num + 1)) { 
				sudokuBoard.setPosVal(row, col, num, false);
			} else {
				sudokuBoard.setPosVal(row,  col, num, true);
			}
		}
	}
	
	private static void updateVal(int row, int col) {
		// counter
		int t = 0;
		int val = 0;
		for(int k = 0; k < 9; ++k) {
			if(sudokuBoard.getPosVal(row, col, k)) {
				++t;
				val = k + 1;
			}
		}
		if(t < 0) {
			System.out.println("Unsolveable puzzle");
			System.exit(1);
		} else if(t == 1) {
			sudokuBoard.set(row, col, val);
		}
	}
	
	private static void copyBoard(SudokuBoard original, SudokuBoard clone) {
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				clone.set(i,  j, original.get(i, j));
			}
		}
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				for(int k = 0; k < 9; ++k) {
					clone.setPosVal(i, j, k, original.getPosVal(i, j, k));
				}
			}
		}
	}
	
	private static boolean matchBoard(SudokuBoard pBoard, SudokuBoard sBoard) {
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				if(pBoard.get(i, j) != sBoard.get(i, j)) return false;
			}
		}
		return true;
	}
	
	private static pos findFirst() {
		pos position = new pos();
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				if(sudokuBoard.get(i, j) == 0) {
					position.row = i;
					position.col = j;
					return position;
				}
			}
		}
		return position;
	}
	
	private static Stack<Integer> getVal(pos position) {
		Stack<Integer> vals = new Stack<Integer>();
		for(int k = 0; k < 9; ++k) {
			if(sudokuBoard.getPosVal(position.row, position.col, k)) {
				vals.push(k + 1);
			}
		}
		return vals;
	}
	
	private static boolean matchPosVals(int row1, int col1, int row2, int col2) {
		boolean bool = true;
		for(int i = 0; i < 9; ++i) {
			bool &= (sudokuBoard.getPosVal(row1, row2, i) == sudokuBoard.getPosVal(row2, col2, i));
		}
		return bool;
	}
	
	private static boolean matchRow(int row, int col) {
		boolean bool = false;
		for(int j = col + 1; j < 9; ++j) {
			bool |= matchPosVals(row, col, row, j);
		}
		return bool;
	}
	
	private static boolean matchCol(int row, int col) {
		boolean bool = false;
		for(int i = row + 1; i < 9; ++i) {
			bool |= matchPosVals(row, col, i, col);
		}
		return bool;
	}
	
	private static boolean matchReg(int row, int col) {
		boolean bool = false;
		int region = getRegion(row, col);
		int endRow = ((region / 3) *3) +2;
		int endCol = (((region - 1) % 3) * 3) + 2;
		for(int i = row; i < endRow; ++i) {
			for(int j = col; j < endCol; ++j) {
				if(i != row && j != col) {
					bool |= matchPosVals(row, col, i, j);
				}
			}
		}
		return bool;
	}
	
	private static pos findDuple() {
		pos position = new pos();
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				if((matchRow(i, j)) || 
						(matchCol(i, j)) || 
						(matchReg(i, j))) {
					position.row = i;
					position.col = j;
					System.out.println("Duple found! " + i + " " + j);
					return position;
				}
			}
		}
		return position;
	}
	
	private static pos findTriple() {
		pos position = new pos();
		return position;
	}
	
	private static boolean impossibleBoard() {
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				boolean valid = false;
				for(int k = 0;k < 9; ++k) {
					if(sudokuBoard.get(i, j) != 0) {
						valid = true;
					} else if(sudokuBoard.getPosVal(i, j, k)) {
						valid = true;
					}
				}
				if(!valid) { return true; }
			}
		}
		return false;
	}
	
	public static void solve() {
		initPossible();
		while(!complete()) {
			copyBoard(sudokuBoard, previousBoard);
			for(int i = 0; i < 9; ++i) {
				for(int j = 0; j < 9; ++j) {
					if(sudokuBoard.get(i,j) == 0) {
						updatePosVal(i, j);
						updateVal(i, j);
					}
				}
			}
			if(impossibleBoard()) {
				System.out.println("need to backtrack...");
				System.out.println("values left: " + printVals(stack.peek().values));
				if(stack.empty()) { 
					System.out.println("Board is not solvable");
					System.exit(0); 
				}
				while(stack.peek().values.empty()) {
					System.out.println("popping...");
					stack.pop();
					if(stack.empty()) { 
						System.out.println("Board is not solvable");
						System.exit(0); 
					}
				}
				StackElement element = stack.peek();
				sudokuBoard = element.sudokuBoard;
				sudokuBoard.set(element.position, element.values.pop());
			} else if(matchBoard(previousBoard, sudokuBoard)) {
				System.out.println("Testing output");
				SudokuBoard newBoard = new SudokuBoard();
				copyBoard(previousBoard, newBoard);
				StackElement element = new StackElement();
				element.sudokuBoard = newBoard;
				
				// finding possible starting points
				pos position;
				if(!findDuple().empty()) {
					position = findDuple();
				} else if (!findTriple().empty()){
					position = findTriple();
				} else {
					// we can do this b/c we check with impossibleBoard that there exists
					//	a possible value
					System.out.println("default");
					position = findFirst();
				}
				element.position = position;
				element.values = getVal(position);
				System.out.println("Values: " + printVals(element.values));
				
				stack.push(element);
				sudokuBoard.set(element.position, element.values.pop());
			}
			sudokuBoard.print();
		}
	}
	private static String printVals(Stack<Integer> vals) {
		String s = "";
		Iterator<Integer> iter = vals.iterator();
		while(iter.hasNext()) {
			s = s + " " + iter.next();
		}
		return s;
	}

}
