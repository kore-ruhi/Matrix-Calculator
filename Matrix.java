public class Matrix
{
	List [] matrix;

	private class MatrixEntry
	{
		int columnIndex;
		double value;
		MatrixEntry previous;
		MatrixEntry next;

		MatrixEntry (int colIndex, double val)
		{
			this.columnIndex = colIndex;
			this.value = val;
		}

		public boolean equals(Object x)
		{
			if (x instanceof MatrixEntry)				//we need to make sure that Object x is a MatrixEntry object, we test that using instanceof
			{
				MatrixEntry temp = (MatrixEntry) x;
				if (this.columnIndex == temp.columnIndex && this.value == temp.value)	//two entries are equal if their column indices and values are equal
					return true;
				return false;
			}
			return false;
		}

		public String toString()
		{
			String temp = "(" + columnIndex + ", " + value + ")";
			return temp;
		}

		public int getColumnIndex()
		{
			return columnIndex;
		}

		public double getValue()
		{
			return value;
		}

		public void setColumnIndex(int num)
		{
			columnIndex = num;
		}

		public void setValue(double num)
		{
			value = num;
		}
	}

	//---------------------------------------------------------- BEGIN CONSTRUCTOR ---------------------------------------------------------------
    /*
	Matrix Constructor

	Description: Makes a new n x n zero Matrix.
		
	Preconditions:
		n >= 1
	*/
	Matrix(int n)
	{
		if (n < 1)
			System.err.println("ERROR: Can't create a matrix of size less than 1.\n");//print error message to stderr
		else
		{
			matrix = new List [n];
			for(int x = 0; x < n; x++)
	        	matrix[x] = new List();
	    }
	}
	//---------------------------------------------------------- END CONSTRUCTOR -----------------------------------------------------------------

	//---------------------------------------------------------- BEGIN ACCESS FUNCTIONS ----------------------------------------------------------
	/*
	Function: getSize()

	Description: Returns n, the number of rows and columns of this Matrix
	*/
	int getSize()
	{
		int size = matrix.length;
		return size;
	}

	/*
	Function: getNNZ()

	Description: Returns the number of non-zero entries in this Matrix
	*/
	int getNNZ()
	{
		List temp;
		int numEntries = 0;
		for (int i = 0; i < getSize(); i++)
		{
			temp = matrix[i];
			numEntries += temp.length();
			//System.out.println("length:" + temp.length());
		}
		return numEntries;
	}

	/*
	Function: equals()

	Description: Overrides Object's equals() method
	*/
	public boolean equals(List L)
	{
		if (this.length() != L.length())
			return false;

		Node temp = this.front;

		Node tempCursor = L.front;

		for(int i = 0; i < this.length(); i++)
		{
			if(!temp.data().equals(tempCursor.data())) 
				return false;
			this.moveNext();
			L.moveNext();
		}
		return true;
	}

	//---------------------------------------------------------- END ACCESS FUNCTIONS ------------------------------------------------------------

	//---------------------------------------------------------- BEGIN MANIPULATION PROCEDURES ---------------------------------------------------

	/*
	Function: makeZero()

	Description: Sets this Matrix to the zero state
	*/
	void makeZero()
	{
		for (int i = 0; i < getSize(); i++)
			matrix[i].clear();
	}

	/*
	Function: copy()

	Description: Returns a new Matrix having the same entries as this Matrix
	*/
	Matrix copy()
	{
		Matrix copy = new Matrix(this.matrix.length);
		for (int i = 0; i < this.matrix.length; i++)
			copy.matrix[i] = this.matrix[i].copy();
		return copy;
	}

	/*
	Function: changeEntry

	Description: Changes ith row, jth column of this Matrix to x
		
	Preconditions:
		1 <= i <= getSize()
		1 <= j <= getSize()
	*/
	void changeEntry(int i, int j, double x)
	{
		if (i > getSize())
			System.err.println("ERROR: i is greater than the length of the array.\n");//print error message to stderr
		else if (j > getSize())
			System.err.println("ERROR: j is greater than the length of the array.\n");//print error message to stderr
		else
		{	
			List l = this.matrix[i - 1];
			MatrixEntry m = null;
			MatrixEntry temp = null;
			l.moveFront();
			//EMPTY LIST
			if (l.length() == 0)
			{
				if (x != 0)
					l.append(new MatrixEntry (j, x));
			}
			//NON-ZERO BECOMES ZERO (DELETE)
			else if (x == 0)
			{
				boolean elementFound = false;
				int len = l.length();
				int num = 0;
				while (num < len && elementFound == false)
				{
					temp = (MatrixEntry)l.get();
					if (temp.getColumnIndex() == j)
					{
						elementFound = true;
						m = temp;
					}
					else
					{
						l.moveNext();
						num++;
					}
				}
				//System.out.println("Hhihi" + this.getNNZ());
				if (m != null)
				{
					l.delete();
					//System.out.println("2Hhihi" + this.getNNZ());
					return;
				}
			}
			//GENERAL CASE
			else
			{
				boolean elementFound = false;
				int len = l.length();
				int num = 0;
				while (num < len && elementFound == false)
				{
					temp = (MatrixEntry)l.get();
					if (temp.getColumnIndex() == j)
					{
						elementFound = true;
						m = (MatrixEntry)l.get();
					}
					else
						l.moveNext();
					num++;
				}
				if (m != null)
					m.setValue(x);
				//ZERO BECOMES NON-ZERO (INSERT)
				if (num == len && x != 0)
				{
					l.moveFront();
					num = 0;
					temp = (MatrixEntry)l.get();
					while (temp != null && temp.getColumnIndex() < j)
					{
						temp = (MatrixEntry)l.get();
						l.moveNext();
						temp = temp.next;
						num++;
					}
					if (num == len)
						l.append(new MatrixEntry(j, x));
					else
						l.insertBefore(new MatrixEntry(j, x));
				}
			}
		}
	} 

	/*
	Function: scalarMult(double x)

	Description: Returns a new Matrix that is the scalar product of this Matrix with x
	*/
	Matrix scalarMult(double x)
	{
		Matrix M = new Matrix (this.getSize());
		MatrixEntry temp = null;

		if (x == 0)
			return M;
		else
		{
			for (int i = 0; i < this.matrix.length; i++)
			{
				List l = this.matrix[i];
				List mL = M.matrix[i];
				l.moveFront();
				mL.moveFront();
				while (l.index() != -1)
				{
					temp = (MatrixEntry)l.get();
					mL.append(new MatrixEntry (temp.getColumnIndex(), x * temp.getValue()));
					l.moveNext();
				}
			}
		}
		return M;
	}
	
	/*
	Function: add(Matrix M)

	Description: Returns a new Matrix that is the sum of this Matrix with M
		
	Preconditions:
		getSize() == M.getSize()
	*/
	Matrix add(Matrix M)
	{
		Matrix MCopy = new Matrix (this.getSize());
		if (this.equals(M))
			MCopy = this.copy();
		else
			MCopy = M;
		if (this.getSize() != MCopy.getSize())
		{
			System.err.println("ERROR: The passed in matrix isn't the same size as this matrix.\n");//print error message to stderr
			return null;
		}
		Matrix Sum = new Matrix (this.getSize());
		for (int i = 0; i < this.matrix.length; i++)
		{
			List L1 = this.matrix[i];
			List L2 = MCopy.matrix[i];
			L1.moveFront();
			L2.moveFront();
			while (L1.index() != -1 || L2.index() != -1)
			{
				double addend1 = 0;
				double addend2 = 0; 
				int col1 = -1;
				int col2 = -1;
				if (L1.index() != -1)
				{
					addend1 = ((MatrixEntry)(L1.get())).getValue();
					//System.out.println ("addend1: " + addend1);
					col1 = ((MatrixEntry)(L1.get())).getColumnIndex();
				}
				if (L2.index() != -1)
				{
					addend2 = ((MatrixEntry)(L2.get())).getValue();
					col2 = ((MatrixEntry)(L2.get())).getColumnIndex();
				}
				if (L1.index() != -1 && L2.index() != -1 && col1 == col2)
				{
					//System.out.println ("addend1: " + addend1);
					if (addend1 + addend2 != 0)
						(Sum.matrix[i]).append(new MatrixEntry (col1, addend1 + addend2));
					L1.moveNext();
					L2.moveNext();
				}
				else if ((L1.index() != -1 && L2.index() == -1) || (L1.index() != -1 && L2.index() != -1 && col1 < col2))
				{
					if (addend1 != 0)
						(Sum.matrix[i]).append(new MatrixEntry (col1, addend1));
					L1.moveNext();
				}
				else if ((L1.index() == -1 && L2.index() != -1) || ((L1.index() != -1 && L2.index() != -1) && col1 > col2))
				{
					if (addend2 != 0)
						(Sum.matrix[i]).append(new MatrixEntry (col2, addend2));
					L2.moveNext();
				}
				//System.out.println ("addend1: " + addend1);
				//System.out.println ("L1 index: " + L1.index());
			}
		}
		return Sum;
	}

	/*
	Function: sub(Matrix M)

	Description: Returns a new Matrix that is the difference of this Matrix with M
		
	Preconditions:
		getSize() == M.getSize()
	*/
	Matrix sub(Matrix M)
 	{
 		Matrix MCopy = new Matrix (this.getSize());
	 	if (this.equals(M))
				MCopy = this.copy();
			else
				MCopy = M;
	 	if (this.getSize() != MCopy.getSize())
		{
			System.err.println("ERROR: The passed in matrix isn't the same size as this matrix.\n");//print error message to stderr
			return null;
		}
		Matrix Difference = new Matrix (this.getSize());
		for (int i = 0; i < this.matrix.length; i++)
		{
			List L1 = this.matrix[i];
			List L2 = MCopy.matrix[i];
			L1.moveFront();
			L2.moveFront();
			while (L1.index() != -1 || L2.index() != -1)
			{
				double term1 = 0;
				double term2 = 0; 
				int col1 = -1;
				int col2 = -1;
				if (L1.index() != -1)
				{
					term1 = ((MatrixEntry)(L1.get())).getValue();
					col1 = ((MatrixEntry)(L1.get())).getColumnIndex();
				}
				if (L2.index() != -1)
				{
					term2 = ((MatrixEntry)(L2.get())).getValue();
					col2 = ((MatrixEntry)(L2.get())).getColumnIndex();
				}

				if (L1.index() != -1 && L2.index() != -1 && col1 == col2)
				{
					if (term1 - term1 != 0)
						(Difference.matrix[i]).append(new MatrixEntry (col1, term1 - term2));
					L1.moveNext();
					L2.moveNext();
				}
				else if ((L1.index() != -1 && L2.index() == -1) || (L1.index() != -1 && L2.index() != -1 && col1 < col2))
				{
					if (term1 != 0)
						(Difference.matrix[i]).append(new MatrixEntry (col1, term1));
					L1.moveNext();
				}
				else if ((L1.index() == -1 && L2.index() != -1) || ((L1.index() != -1 && L2.index() != -1) && col1 > col2))
				{
					if (term2 != 0)
						(Difference.matrix[i]).append(new MatrixEntry (col2, term2));
					L2.moveNext();
				}				
			}
		}
		return Difference;
	}
	/*
	Function: transpose()

	Description: Returns a new Matrix that is the transpose of this Matrix
	*/
	Matrix transpose()
	{
		Matrix Transpose = new Matrix (this.getSize());
		MatrixEntry tempEntry = null;

		for (int i = 0; i < this.getSize(); i++)
		{
			List temp = this.matrix[i];
			if (temp.length() > 0)
			{
				temp.moveFront();
				while (temp.index() != -1)
				{
					tempEntry = (MatrixEntry)temp.get();
					Transpose.changeEntry(tempEntry.getColumnIndex(), i + 1, tempEntry.getValue());
            		temp.moveNext();
				}
			}
		}
		return Transpose;
	}

	/*
	Function: mult(Matrix M)

	Description: Returns a new Matrix that is the product of this Matrix with M

	Preconditions:
		getSize() == M.getSize()
	*/
	/*Matrix mult(Matrix M)
	{
		Matrix Product = new Matrix (this.getSize());
		if (this.getSize() != M.getSize())
		{
			System.err.println("ERROR: The passed in matrix isn't the same size as this matrix.\n");//print error message to stderr
			return Product;
		}
		Matrix Transpose = M.transpose();
		//System.out.println(Transpose);
		for (int i = 0; i < this.getSize(); i++)
		{
			List L1 = this.matrix[i];
			if(L1.length() == 0) 
				continue;
			for (int j = 0; j < Transpose.getSize(); j++)
			{
				//System.out.println ("j: " + j);
				List L2 = Transpose.matrix[j];
				//System.out.println ("L2: " + L2);
				if(L2.length() != 0)
					Product.changeEntry(i + 1, j + 1, dot(L1, L2));
			}
		}
		return Product;
	} 

	Matrix mult(Matrix M)
	{
		double dotResult = 0;
		Matrix Product = new Matrix (this.getSize());
		if (this.getSize() != M.getSize())
		{
			System.err.println("ERROR: The passed in matrix isn't the same size as this matrix.\n");//print error message to stderr
			return Product;
		}
		Matrix Transpose = M.transpose();
		//System.out.println("Transpose: " + Transpose);
		int numColumns = 0;
		//System.out.println(Transpose);
		for (int i = 0; i < this.getSize(); i++)
		{
			List L1 = this.matrix[i];

			for (int j = 0; j < Transpose.matrix.length; j++)
			{
				//System.out.println ("j: " + j);
				List L2 = Transpose.matrix[j];
				if (L2.length() > 0)
				{
					System.out.println ("L2: " + L2);
					dotResult = dot(L1, L2);
					Product.matrix[i].moveFront();
					if (dotResult != 0)
					{
						System.out.println ("dotResult: " + dotResult);
						Product.matrix[i].append(new MatrixEntry(j + 1, dotResult));
					}
				}
			}
		}
		System.out.println (Product);
		return Product;
	}*/

	Matrix mult(Matrix M){

		Matrix colMat = M.transpose();
		int len;

		if( colMat.matrix.length > this.matrix.length){
			len = colMat.matrix.length;
		}
 
		else{
			len = this.matrix.length;
		}

		Matrix multMat = new Matrix(len);
		double tempDot;
		int colCount;

		for( int i = 0; i < len ; i++){

			colCount = colMat.matrix.length;
			
			for( int k = 0 ; k < colCount ; k++){
			
				tempDot = dot( matrix[i] , colMat.matrix[k]);

				if( tempDot != 0){
					multMat.matrix[i].append( new MatrixEntry ( k+1 , tempDot)) ;
				}
			}
		}

		return multMat;

	}


	/*
	Function: dot (List P, List Q)

	Description: Calculates the dot product of two lists

	Preconditions:
		P and Q != NULL
	*/

	/*private static double dot (List P, List Q)
	{
		if (P == null || Q == null)
		{
			System.err.println("ERROR: NULL method parameter(s).\n");//print error message to stderr
			return 0;
		}
		double answer = 0;
		P.moveFront();
		Q.moveFront();
		MatrixEntry temp1 = null;
		MatrixEntry temp2 = null;
		while (P.index() != -1 && Q.index() != -1)
		{
			temp1 = (MatrixEntry) P.get();
			temp2 = (MatrixEntry) Q.get();
			if (temp1.getColumnIndex() == temp2.getColumnIndex())
			{
				answer += (temp1.getValue() * temp2.getValue());
				//System.out.println ("answer: " + answer);
				P.moveNext();
				Q.moveNext();
				//System.out.println (P.index());
				//System.out.println (Q.index());
			}
			else if (temp1.getColumnIndex() < temp2.getColumnIndex())
				P.moveNext();
			else if (temp1.getColumnIndex() > temp2.getColumnIndex())
				Q.moveNext();
		}
		//System.out.println (answer);
		return answer;
	} */

	private static double dot(List P, List Q){

		double sum = 0;

		if( P.equals(Q)){
			Q = P.copy();
		}

		P.moveFront();
		Q.moveFront();

		while( P.index() != -1 || Q.index() != -1){

			if( P.index() != -1 && Q.index() != -1){

				if( ((MatrixEntry)P.get()).getColumnIndex() == ((MatrixEntry)Q.get()).getColumnIndex()){

					sum += ((MatrixEntry)P.get()).getValue() * ((MatrixEntry)Q.get()).getValue();

					P.moveNext();
					Q.moveNext();
				}

				else if ( ((MatrixEntry)P.get()).getColumnIndex() > ((MatrixEntry)Q.get()).getColumnIndex() ) {

					sum += 0;

					Q.moveNext();
				}

				else{

					sum += 0;

					P.moveNext();
				}
			}

			else if( P.index() == -1 && Q.index() != -1){
					
					sum += 0;

					Q.moveNext();

			}	

			else{
					
					sum += 0;

					P.moveNext();

			}	

		}

		return sum;
	}

	//---------------------------------------------------------- END MANIPULATION PROCEDURES -----------------------------------------------------

	//---------------------------------------------------------- BEGIN OTHER FUNCTIONS -----------------------------------------------------------
	
	/*
	Function: toString()

	Description: Overrides Object's toString() method
	*/
	public String toString()
	{
		/*List L;
		String toPrint = "";
		MatrixEntry temp = null;
		for (int i = 1; i <= getSize(); i++)
		{
			L = this.matrix[i - 1];
			if (L.length() > 0)
			{
				toPrint += (i + ": ");
				L.moveFront();
				while (L.index() != -1)
				{
					temp = (MatrixEntry)L.get();
					toPrint += ("(" + temp.getColumnIndex() + " " + temp.getValue() + ")");
					L.moveNext();
				}
			}
			//System.out.println("");
		}
		return toPrint; */
      String out = "";
      for(int i = 0; i < getSize(); i++) {
         if(matrix[i].length() > 0)
            out += ((i + 1) + ": " + matrix[i] + "\n"); 
      }
      return out;
	}
}