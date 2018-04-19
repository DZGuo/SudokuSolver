import java.util.Scanner;

public class Solver {
	
	public SudokuBoard sudokuBoard = new SudokuBoard();
	
	public static void main(String [] args)	{
		Scanner reader = new Scanner(System.in);
		int in;
		System.out.println("Please enter the 81 numbers on the board. Use 0 to denote empty spaces");
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				in = reader.nextInt();
				SudokuBoard.set(i,  j, in);
			}
		}
		System.out.println("Initial Board:");
		SudokuBoard.printBoard();
		SudokuBoard.solve();
		SudokuBoard.printBoard();
		reader.close();
	}
}
