public class MatrixTest{
	
	
	public static void main( String [] args){
        
		  Matrix A , B , C;

 		  A = new Matrix(10);
          B = new Matrix(15);
          A.changeEntry(1, 1, 1);
          B.changeEntry(1, 1, 1);
          if (A.equals(B)) 
          	System.out.println("Failed--1"); //part 1 of this test
          else
            System.out.println("Passed--1"); //part 1 of this test

          B = new Matrix(10);
          A.changeEntry(1, 1, 1);
          A.changeEntry(1, 3, 1);
          B.changeEntry(1, 1, 1);
          B.changeEntry(1, 3, 1);
          if (!A.equals(B))          	
           System.out.println("Failed--2"); //part 2 of this test
          else
            System.out.println("Passed--2"); //part 2 of this test
          
          A.changeEntry(1, 3, 0);
          if (A.equals(B))           	
           System.out.println("Failed--3"); //part 3 of this test
          else
            System.out.println("Passed--3"); //part 3 of this test
        
         
          A.changeEntry(1, 1, 0);
          B.makeZero();
          A.changeEntry(10, 10, 10);
          B.changeEntry(10, 10, 10);
          if (!A.equals(B))
           System.out.println("Failed--4"); //part 4 of this test
          else
            System.out.println("Passed--4"); //part 4 of this test
         
          A = new Matrix(100);
          B = new Matrix(100);
          int valcount = 1;
          for (int j = 1; j <= 100; j++) {
            for (int k = 1; k <= 100; k++) {
              // hint: this is 1-10000 left-to-right row-by-row
              A.changeEntry(j, k, valcount++);
            }
            B.changeEntry(j, j, 1); // hint: this is the identity matrix
          }
          C = A.scalarMult(2);
          if (!C.equals(A.add(A)))
          	System.out.println("Failed--5"); //part 5 of this test
          else
            System.out.println("Passed--5"); //part 5 of this test
          
          C = A.scalarMult(-2);
          if (!C.equals(A.sub(A).sub(A).sub(A)))
          	System.out.println("Failed--6"); //part 6 of this test
          else
            System.out.println("Passed--6"); //part 6 of this test
          C = A.mult(B);


		if ( C.equals(A)){
			System.out.println("-----Passed-----");
		//	System.out.println(C.getNNZ());
		//	System.out.println( A );
		//	System.out.println( B );
		//	System.out.println( C );


		}
		else{
			System.out.println("-----Failed-----");
		//	System.out.println(C.getNNZ());
		//	System.out.println( A );
		//	System.out.println( B );

		}

	}

}