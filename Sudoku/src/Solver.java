import java.util.Scanner;

public class Solver {
	
	public Sudoku sudokuBoard = new Sudoku();
	
	public static void main(String [] args)	{
		Scanner reader = new Scanner(System.in);
		int in;
		System.out.println("Please enter the 81 numbers on the board. Use 0 to denote empty spaces");
		for(int i = 0; i < 9; ++i) {
			for(int j = 0; j < 9; ++j) {
				in = reader.nextInt();
				Sudoku.set(i,  j, in);
			}
		}
		System.out.println("Initial Board:");
		Sudoku.printBoard();
		Sudoku.solve();
		System.out.println("Solved Board:");
		Sudoku.printBoard();
		reader.close();
	}
}
