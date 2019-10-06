import java.io.*;
import java.util.Scanner;

public class Sparse {
	public static void main(String args[]) throws FileNotFoundException {
		if (args.length != 2)
			System.out.println("Incorrect argument length");
		

		PrintWriter outFile = new PrintWriter(args[1]);
		Scanner s = new Scanner(new File(args[0]));
		int size = s.nextInt(); 
		int aInput = s.nextInt(); 
		int bInput = s.nextInt();

		Matrix A = new Matrix (size); 
		Matrix B = new Matrix (size); 
		s.nextLine(); 
		
		for (int i = 0; i < aInput; i++) {
			int rowInput = s.nextInt(); 
			int colInput = s.nextInt();
			double valInput = s.nextDouble(); 
			A.changeEntry(rowInput, colInput, valInput);
		}
		s.nextLine(); 
		for(int i = 0; i < bInput; i++) {
			int rowInput = s.nextInt();
			int colInput = s.nextInt(); 
			double valInput = s.nextDouble(); 
			B.changeEntry(rowInput, colInput, valInput);
		}
	 
		outFile.println("A has " + aInput + " non-zero entries:");
		outFile.println(A);
		outFile.println("B has " + bInput + " non-zero entries:");
		outFile.println(B);
		outFile.println("(1.5)*A =");
		outFile.println(A.scalarMult(1.5));
		outFile.println("A+B =");
		outFile.println(A.add(B));
		outFile.println("A+A =");
		outFile.println(A.add(A));
		outFile.println("B-A =");
		outFile.println(B.sub(A));
		outFile.println("A-A =");
		outFile.println(A.sub(A));
		outFile.println("Transpose(A) = ");
		outFile.println(A.transpose());
		outFile.println("A*B =");
		outFile.println(A.mult(B));
		outFile.println("B*B =");
		outFile.println(B.mult(B));

		s.close(); 
		outFile.close();
	}
}