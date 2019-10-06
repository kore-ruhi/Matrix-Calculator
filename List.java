/*
Ruhi Kore

List.java

Description: This file contains the fields and methods used to manipulate a list. The methods are used by Lex.java
and ListClient.java to perform actions like add and delete elements from the list, and use a cursor element to traverse
the list. The List ADT consists of a bi-directional queue (doubly-linked list) and incorporates a cursor for the
purpose of iteration.
*/

public class List
{
	private class Node
	{
		Object data;
		Node prev;
		Node next;

		Node (Object data)
		{
			this.data = data;
			this.prev = null;
			this.next = null;
		}
		public String toString()
		{
			return data.toString();	//data has its own toString() method
		}
	}
	private Node back;
	private Node front;
	private Node cursor;
	private int list_length;
	private int cursor_index;	//when cursor is undefined, should be -1

	List ()
	{
		this.back = null;
		this.front = null;
		this.cursor = null;
		this.list_length = 0;
		this.cursor_index = -1;
	}

	/*
	Returns the number of elements in this List.
	*/
	int length ()
	{
		if (list_length >= 0)
			return this.list_length;
		else
			return 0;
	}

	/*
	If the cursor is defined, returns the index of the cursor element. Otherwise, returns -1.
	*/
	int index ()
	{
		if (this.cursor != null)
			return this.cursor_index;
		else
		{
			this.cursor_index = -1;
			return this.cursor_index;
		}
	}

	/*
	Returns the front element.
	Preconditions:
		length () > 0
	*/
	Object front ()
	{
		if (this.length () < 1)
		{
			System.err.println("ERROR: The length is zero.");//print error message to stderr
			return null;
		}
		else
			return this.front.data;
	}

	/*
	Returns the back element.
	Preconditions:
		length () > 0
	*/
	Object back ()
	{
		if (this.length () < 1)
		{
			System.err.println("ERROR: The length is zero.");//print error message to stderr
			return null;
		}
		else
			return this.back.data;
	}

	/*
	Returns the cursor element.
	Preconditions:
		length () > 0
		index () >= 0
	*/
	Object get ()
	{
		if (this.length () < 1)
			System.err.println("ERROR: The length is zero.");//print error message to stderr
		else if (this.index () < 0)
			System.err.println("ERROR: The index is undefined.1");//print error message to stderr
		else if(cursor != null)
			return this.cursor.data;
		return null;
	}

	/*
	Returns true if and only if this List and L are the same integer sequence.
	The states of the cursors in the two Lists aren't used in determining equality.
	*/
	public boolean equals(Object x)			//changing the signature so it overrides the Object toString() method
	{
		if (this == null)
			return false;
		else if (((List)x) == null)
			return false;
		else if (this.list_length != ((List)x).list_length)
			return false;
		else
		{
			Node temp1 = this.front;
			Node temp2 = ((List)x).front;
			while (temp1 != null && temp2 != null)
			{
				if (!(temp1.data).equals(temp2.data))
					return false;
				temp1 = temp1.next;
				temp2 = temp2.next;
			}
			return true;
		}
	}

	/*
	Deletes the front element. 
	Preconditions: 
		length() > 0
	*/
	void deleteFront ()
	{
		if (this.list_length < 1)
			System.err.println("ERROR: The length is zero.");//print error message to stderr
		else if (this.list_length == 1)
		{
			this.back = this.front = null;
			list_length --;

			this.front = null;
			this.back = null;
			list_length--;
		}
		else
		{
			if (this.cursor != null && this.cursor.equals(this.front))
			{
				this.cursor = null;
				this.cursor_index = -1;
			}
			else if (this.cursor != null)
				this.cursor_index--;
			Node temp = this.front.next;
			this.front = null;
			this.front = temp;
			list_length--;
		}
	}

	/*
	Deletes the back element. 
	Preconditions: 
		length() > 0
	*/
	void deleteBack ()
	{
		if (this.list_length < 1)
			System.err.println("ERROR: The length is zero.");//print error message to stderr
		else if (this.list_length == 1)
		{
			this.back = this.front = null;
			list_length --;
		}
		else
		{
			if (this.cursor != null && this.cursor.equals(this.back))
			{
				this.cursor = null;
				this.cursor_index = -1;
			}
			Node temp = this.back.prev;
			this.back = null;
			this.back = temp;
			list_length--;
		}
	}

	/*
	Deletes cursor element, making cursor undefined.
	Preconditions: 
		length() > 0
		index() >= 0
	*/
	void delete ()
	{
		if (this.list_length < 1)
			System.err.println("ERROR: The length is zero.");//print error message to stderr
		else if (this.index () < 0)
			System.err.println("ERROR: The index is undefined.2");//print error message to stderr
		else if (this.list_length == 1)
			deleteFront ();
		else if (this.cursor.equals(this.front))
			deleteFront ();
		else if (this.cursor.equals(this.back))
			deleteBack ();
		else
		{
			Node temp = this.cursor;
			Node prevTemp = temp.prev;
			Node nextTemp = temp.next;
			temp.prev = null;
			temp.next = null;
			prevTemp.next = nextTemp;
			nextTemp.prev = prevTemp;
			this.list_length--;
			this.cursor = null;
			this.cursor_index = -1;
		}	
	}

	/*
	If List is non-empty, places the cursor under the front element; otherwise does nothing.
	*/
	void moveFront ()
	{
		if (this.length() >= 1)
		{
			this.cursor = this.front;
			cursor_index = 0;
		}
	}

	/*
	If List is non-empty, places the cursor under the back element; otherwise does nothing.
	*/
	void moveBack ()
	{
		if (this.length() >= 1)
		{
			this.cursor = this.back;
			cursor_index = this.length() - 1;
		}
	}

	/*
	If cursor is defined and not at front, moves cursor one step toward front of this List, if cursor is defined and at front, cursor becomes
	undefined, if cursor is undefined does nothing.
	*/
	void movePrev ()
	{
		if (this.length() < 1)
			System.err.println("ERROR: The length is zero.");//print error message to stderr
		else if (this.cursor != null && this.cursor.equals(this.front))
		{
			this.cursor = null;
			this.cursor_index = -1;
		}
		else if (this.cursor != null)
		{
			this.cursor = this.cursor.prev;
			this.cursor_index--;
		}
	}

	/*
	If cursor is defined and not at front, moves cursor one step toward back of this List, if cursor is defined and at back, cursor becomes
	undefined, if cursor is undefined does nothing.
	*/
	void moveNext ()
	{
		//System.out.println ("TRIGGERED");
		if (this.length() < 1)
			System.err.println("ERROR: The length is zero.");//print error message to stderr
		//else if (this.cursor.equals(this.back))
		else if(this.cursor != null && this.cursor.equals(this.back))
		{
			cursor = null;
			cursor_index = -1;
		}
		else if (this.cursor != null)
		{
			cursor = cursor.next;
			cursor_index++;
		}
	}

	/*Insert new element into this List. If List is non-empty, insertion takes place before front element.*/
	void prepend (Object data)
	{
		Node n = new Node (data);
		if (this.length() == 0)
		{
			this.front = n;
			this.back = n;
			list_length = 1;
		}
		else
		{
			n.next = this.front;
			this.front.prev = n;
			this.front = n;
			list_length++;
			if(cursor != null)
				cursor_index++;
		}
	}

	/*
	Insert new element into this List. If List is non-empty, insertion takes place after back element.
	*/
	void append (Object data)
	{
		Node n = new Node (data);
		if (this.length() == 0)
		{
			this.front = this.back = n;
			list_length++;

		}
		else
		{
			n.prev = this.back;
			this.back.next = n;
			this.back = n;
			list_length++;
		}
	}

	/*
	Insert new element before cursor.
	Preconditions:
		length() > 0
		index() >= 0
	*/
	void insertBefore (Object data)
	{
		if (this.length() < 1)
			System.err.println("ERROR: The length is zero.");//print error message to stderr
		else if (this.index () < 0)
			System.err.println("ERROR: The index is undefined.3");//print error message to stderr
		else if ((this.front).equals(this.cursor))
		{
			Node n = new Node (data);
			Node temp = this.front;
			n.next = temp;
			temp.prev = n;
			this.front = null;
			this.front = n;
			list_length++;
			cursor_index++;
		}
		else if ((this.back).equals(this.cursor))
		{
			Node n = new Node (data);
			Node tempPrev = this.cursor.prev;
			this.cursor.prev = n;
			n.next = this.cursor;
			n.prev = tempPrev;
			if (tempPrev == null)
			{
				prepend(data);
			}
			else
			{
				tempPrev.next = n;
				list_length++;
				cursor_index++;
			}
		}
		else
		{
			Node n = new Node (data);
			Node tempPrev = this.cursor.prev;
			tempPrev.next = n;
			n.prev = tempPrev;
			n.next = this.cursor;
			this.cursor.prev = n;
			list_length++;
			cursor_index++;
		}
	}

	/*
	Inserts new element after cursor.
	Preconditions:
		length() > 0
		index() >= 0
	*/
	void insertAfter (Object data)
	{
		if (this.length() < 1)
			System.err.println("ERROR: The length is zero.");//print error message to stderr
		else if (this.index () < 0)
			System.err.println("ERROR: The index is undefined.4");//print error message to stderr
		else if ((this.front).equals(this.cursor))
		{
			Node tempNext = this.cursor.next;
			if (tempNext == null)
			{
				append(data);
			}
			else
			{
				Node n = new Node (data);
				this.cursor.next = n;
				n.prev = this.cursor;
				n.next = tempNext;
				tempNext.prev = n;
				list_length++;
			}
		}
		else if ((this.back).equals(this.cursor))
		{
			Node n = new Node (data);
			this.cursor.next = n;
			n.prev = this.cursor;
			this.back = n;
			this.list_length++;
		}
		else
		{
			Node n = new Node (data);
			Node tempNext = this.cursor.next;
			tempNext.prev = n;
			n.next = tempNext;
			n.prev = this.cursor;
			this.cursor.next = n;
			this.list_length++;
		}
	}

	/*
	Resets this List to its original empty state.
	*/
	void clear()
	{
		Node temp = this.front;
		while (this.length() > 0)
		{
			temp = this.front.next;
			this.front = null;
			this.front = temp;
			this.list_length--;
		}
		this.back = null;
		this.cursor = null;
		cursor_index = -1;
	}

	/*
	Overrides Object's toString method. Returns a String representation of this List consisting of a space separated sequence
	of integers, with front on left.
	*/
	public String toString ()
	{
		Node temp = this.front;
		String finalString = "";
		while (temp != null)
		{
			finalString += temp.toString();
			if (temp.next != null)
				finalString += " ";
			temp = temp.next;
		}
		return finalString;
	}

	/*
	Returns a new List representing the same integer sequence as this List. The cursor in the new list is undefined, regardless of the
	state of the cursor in this List. This List is unchanged.
	*/
	List copy()
	{
		List newList = new List();
		Node temp = this.front;
		while (temp != null)
		{
			newList.append (temp.data);
			temp = temp.next;
		}
		return newList;
	}
}
