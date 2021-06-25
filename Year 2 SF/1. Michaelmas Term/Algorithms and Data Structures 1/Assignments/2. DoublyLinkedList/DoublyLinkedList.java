import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

//import DoublyLinkedList.DLLNode;

// -------------------------------------------------------------------------
/**
 *  This class contains the methods of Doubly Linked List.
 *
 *  @author  John Wesley Kommala
 *  @version 09/10/18 11:13:22
 */

/**
 * Class DoublyLinkedList: implements a *generic* Doubly Linked List.
 * 
 * @param <T> This is a type parameter. T is used as a class name in the
 *            definition of this class.
 *
 *            When creating a new DoublyLinkedList, T should be instantiated
 *            with an actual class name that extends the class Comparable. Such
 *            classes include String and Integer.
 *
 *            For example to create a new DoublyLinkedList class containing
 *            String data: DoublyLinkedList<String> myStringList = new
 *            DoublyLinkedList<String>();
 *
 *            The class offers a toString() method which returns a
 *            comma-separated sting of all elements in the data structure.
 * 
 *            This is a bare minimum class you would need to completely
 *            implement. You can add additional methods to support your code.
 *            Each method will need to be tested by your jUnit tests -- for
 *            simplicity in jUnit testing introduce only public methods.
 */
class DoublyLinkedList<T extends Comparable<T>> {

	/**
	 * private class DLLNode: implements a *generic* Doubly Linked List node.
	 */
	private class DLLNode {
		public final T data; // this field should never be updated. It gets its value once from the constructor DLLNode.
		public DLLNode next;
		public DLLNode prev;

		/**
		 * Constructor
		 * 
		 * @param theData  : data of type T, to be stored in the node
		 * @param prevNode : the previous Node in the Doubly Linked List
		 * @param nextNode : the next Node in the Doubly Linked List
		 * @return DLLNode
		 */
		public DLLNode(T theData, DLLNode prevNode, DLLNode nextNode) {
			data = theData;
			prev = prevNode;
			next = nextNode;
		}
	}

	// Fields head and tail point to the first and last nodes of the list.
	private DLLNode head, tail;
	private int size;

	/**
	 * Constructor of an empty DLL
	 * 
	 * @return DoublyLinkedList
	 */
	public DoublyLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Tests if the doubly linked list is empty
	 * 
	 * @return true if list is empty, and false otherwise
	 *
	 *         Worst-case asymptotic running time cost: Theta(1)
	 *
	 *         Justification: This function just checks if the doubly linked list is
	 *         empty by checking if the head (i.e the first element) exists or not
	 *         by comparing it to null.
	 */
	public boolean isEmpty() {
		return (head == null);
	}

	
	/**
	 * Inserts an element in the doubly linked list
	 * 
	 * @param pos  : The integer location at which the new data should be inserted
	 *             in the list. We assume that the first position in the list is 0
	 *             (zero). If pos is less than 0 then add to the head of the list.
	 *             If pos is greater or equal to the size of the list then add the
	 *             element at the end of the list.
	 * @param data : The new data of class T that needs to be added to the list
	 * @return none
	 *
	 *         Worst-case asymptotic running time cost: Theta(N)
	 *
	 *         Justification: So the function starts with an if-else statement paired with
	 *         isEmpty() as the condition which has Theta(1) running cost, then if we lpok
	 *         at the nested if-else statements, we can assume that the cost of operations 
	 *         happening to be Theta(1). But when you look at the nested else statement, a
	 *         for loop is being used which has a Worst-case asymptotic running time cost 
	 *         of Theta(N). All the operations happening other than for loop have cost of
	 *         Theta(1). Hence, the total  Worst-case asymptotic running time cost of this 
	 *         function is Theta(N).
	 */
	public void insertBefore(int pos, T data) {
		if (!isEmpty()) {
			if (pos <= 0) {
				// add to head
				head.prev = new DLLNode(data, null, head);
				head = head.prev;
			} else if (pos >= size) {
				// add to the end of the list
				tail.next = new DLLNode(data, tail, null);
				tail = tail.next;
			} else {
				// add data at pos
				DLLNode presentNode = head;
				for (int i = 0; i < pos; i++) {
					presentNode = presentNode.next;
				}
				DLLNode currentNode = presentNode;
				DLLNode prevNode = presentNode.prev;
				DLLNode newNode = new DLLNode(data, prevNode, currentNode);
				prevNode.next = newNode;
				currentNode.prev = newNode;
			}

		} else head = tail = new DLLNode(data, null, null);
		size++;
	}

	/**
	 * Inserts new element as the head
	 *
	 * @param data : The new data of class T that needs to be added to the list
	 * @return none
	 *         <p>
	 *         Worst-case asymptotic running time cost: Theta(1)
	 *         <p>
	 *         Justification: This function uses isEmpty which costs Theta(1) in a if statement.
	 *         If we assume the cost of constructor to be Theta(1) and the cost of if - else block is Theta(1)
	 *         as we know. Hence, the total cost is Theta(1)

	 */
	public void addHead(T data) {
		if (isEmpty()) {
			head = new DLLNode(data, null, null);
			tail = head;
		} else {
			head.prev = new DLLNode(data, null, head);
			head = head.prev;
		}
		size++;
	}

	/**
	 * Inserts an element to the tail of the list
	 *
	 * @param data : The new data of class T that needs to be added to the list
	 * @return none
	 *         <p>
	 *         Worst-case asymptotic running time cost: Theta(1)
	 *         <p>
	 *         Justification:  This function uses isEmpty which costs Theta(1) in a if statement.
	 *         If we assume the cost of constructor to be Theta(1) and the cost of if - else block is Theta(1)
	 *         as we know. Hence, the total cost is Theta(1).
	 * 
	 */
	public void addTail(T data) {
		if (isEmpty()) {
			head = new DLLNode(data, null, null);
			tail = head;
		} else {
			tail.next = new DLLNode(data, tail, null);
			tail = tail.next;
		}
		size++;
	}

	/**
	 * 
	 * Returns the data stored at a particular position
	 * 
	 * @param pos : the position
	 * 
	 * @return the data at pos, if pos is within the bounds of the list, and null
	 * otherwise.
	 *
	 * Worst-case asymptotic running time cost: Theta(N)
	 *
	 * Justification: This function starts with an if-else statement paired with
	 *         isEmpty() and checking if the pos is in our bounds as the conditions which 
	 *         have Theta(1) running costs, then if we look at the nested if-else statements, 
	 *         we can assume that the cost of operations happening to be Theta(1). But when 
	 *         you look at the nested else statement, a for loop is being used which has a 
	 *         Worst-case asymptotic running time cost of Theta(N). All the operations 
	 *         happening other than for loop have cost of Theta(1). 
	 *         Hence, the total  Worst-case asymptotic running time cost of this function is Theta(N).
	 */
	public T get(int pos) {
		if ((!isEmpty()) && pos < size && pos >= 0) {
			if (pos == 0)
				return getHead();
			else if (pos == size - 1)
				return getTail();
			else {
				DLLNode node = head;
				for (int i = 0; i < pos; i++) {
					node = node.next;
				}
				return node.data;
			}
		} else
			return null;
	}

	/**
	 * 
	 * Returns the data stored at head
	 * 
	 * @param none
	 * @return the data at head, if head is within the bounds of the list, and null otherwise.
	 *
	 * Worst-case asymptotic running time cost: Theta(1)
	 *
	 * Justification: This function uses isEmpty which costs Theta(1) in a if statement.
	 * If we assume the cost of constructor to be Theta(1) and the cost of if - else block is Theta(1)
	 * as we know. Hence, the total cost is Theta(1)
	 *
	 */
	public T getHead() {
		return (!isEmpty()) ? head.data : null;
	}

	/**
	 * Returns the data stored at tail
	 *
	 * @param none
	 * @return the data at head, if head is within the bounds of the list, and null otherwise.
	 *
	 * Worst-case asymptotic running time cost: Theta(1)
	 * 
	 * Justification: This function uses isEmpty which costs Theta(1) in a if statement.
	 * If we assume the cost of constructor to be Theta(1) and the cost of if - else block is Theta(1)
	 * as we know. Hence, the total cost is Theta(1).
	 */
	public T getTail() {
		return (!isEmpty()) ? tail.data : null;
	}

	/**
	 * Deletes the element of the list at position pos. First element in the list
	 * has position 0. If pos points outside the elements of the list then no
	 * modification happens to the list.
	 * 
	 * @param pos : the position to delete in the list.
	 * @return true : on successful deletion, false : list has not been modified.
	 *
	 * Worst-case asymptotic running time cost: Theta(N)
	 *
	 * Justification: This function starts with an if-else statement paired with
	 *         isEmpty() and checking if the pos is in our bounds as the conditions which 
	 *         have Theta(1) running costs, then if we look at the nested if-else statements, 
	 *         we can assume that the cost of operations happening to be Theta(1). But when 
	 *         you look at the nested else statement, a for loop is being used which has a 
	 *         Worst-case asymptotic running time cost of Theta(N). All the operations 
	 *         happening other than for loop have cost of Theta(1). 
	 *         Hence, the total  Worst-case asymptotic running time cost of this function is Theta(N).
	 */	

	public boolean deleteAt(int pos) {
		if ((!isEmpty()) && pos < size && pos >= 0) {
			if (pos == 0) {
				// removing from the start of the list
				return deleteHead();
			}

			else if (pos == (size - 1)) {
				// removing from the end of the list
				return deleteTail();
			} else {
				// delete data at pos
				DLLNode currentNode = head;
				for (int i = 0; i < pos; i++) {
					currentNode = currentNode.next;
				}

				DLLNode prevNode = currentNode.prev;
				DLLNode nextNode = currentNode.next;

				currentNode.prev = null;
				prevNode.next = nextNode;
				currentNode.next = null;
				nextNode.prev = prevNode;
			}
			size--;
			return true;
		} else
			return false;
	}

	/**
	 * Deletes the head of the DLL
	 *
	 * @return true : on successful deletion, false : list has not been modified.
	 * 
	 *         Worst-case asymptotic running time cost: Theta(1)
	 *         
	 *         Justification: This function uses isEmpty which costs Theta(1) in a if statement.
	 *         If we assume the cost of constructor to be Theta(1) and the cost of if - else block is Theta(1)
	 *         as we know. Hence, the total cost is Theta(1).
	 */
	public boolean deleteHead() {
		if (!isEmpty()) {
			if (size == 1) {
				head = null;
				tail = null;
			} else {
				head = head.next;
				head.prev = null;
			}
			size--;
			return true;
		}
		return false;
	}

	/**
	 * Deletes the tail of the list
	 *
	 * @return true : on successful deletion, false : list has not been modified.
	 * 
	 *         Worst-case asymptotic running time cost: Theta(1)
	 *         
	 *         Justification: This function uses isEmpty which costs Theta(1) in a if statement.
	 *         If we assume the cost of constructor to be Theta(1) and the cost of if - else block is Theta(1)
	 *         as we know. Hence, the total cost is Theta(1).
	 */
	public boolean deleteTail() {
		if (!isEmpty()) {
			if (size == 1) {
				head = null;
				tail = null;
			} else {
				tail = tail.prev;
				tail.next = null;
			}
			size--;
			return true;
		}
		return false;
	}


	/**
	 * Reverses the list. If the list contains "A", "B", "C", "D" before the method
	 * is called Then it should contain "D", "C", "B", "A" after it returns.
	 *
	 * Worst-case asymptotic running time cost: Theta(N)
	 *
	 * Justification: This function starts with an if-else statement paired with isEmpty()
	 *  which has a running cost of Theta(1), This has a nested while loop which iterates 
	 *  through all N elements in the list thus the cost of this  will be( N * Theta(1)) = Theta(N). 
	 *  Looking at all the other operations, they have Theta(1) as their running time.
	 *  Hence, the Worst-case asymptotic running time cost will be Theta(N).
	 
	 */
	public void reverse() {
		if (!isEmpty()) {
			DLLNode current = head;
			DLLNode nextNode;

			while (current != null) {
				nextNode = current.next;
				current.next = current.prev;
				current.prev = nextNode;
				current = nextNode;
			}

			current = head;
			head = tail;
			tail = current;
		}
	}

	/**
	 * Removes all duplicate elements from the list. The method should remove the
	 * _least_number_ of elements to make all elements unique. If the list contains
	 * "A", "B", "C", "B", "D", "A" before the method is called Then it should
	 * contain "A", "B", "C", "D" after it returns. The relative order of elements
	 * in the resulting list should be the same as the starting list.
	 *
	 * Worst-case asymptotic running time cost: Theta(N^2)
	 *
	 * Justification: This function starts with an if-else statement paired with isEmpty()
	 *  which has a running cost of Theta(1), This has a nested while loop which iterates 
	 *  through all N elements in the list thus the cost of this  will be( N * Theta(1)) = Theta(N). 
	 *  As there is an if statement in it which uses has() which has a worst-case running time of 
	 *  Theta(N), looking at all the other operations, they have Theta(1) as their running time.
	 *  Considering the while loop paired with has() it would bring the total cost to 
	 *  (N*Theta(N)=Theta(N^2) times. Hence, the Worst-case asymptotic running time cost will be Theta(N ^ 2).
	 */
	public void makeUnique() {
		if (!isEmpty()) {
			DLLNode current = head;
			DoublyLinkedList<T> uniqueDLL = new DoublyLinkedList<T>();
			while (current != null) {
				if (!uniqueDLL.has(current.data))
					uniqueDLL.addTail(current.data);
				current = current.next;
			}
			size = uniqueDLL.size;
			head = uniqueDLL.head;
			tail = uniqueDLL.tail;

		}
	}

	/**
	   * This method checks if the doubly linked list has a specific element
	   *
	   * @return true if element is present: false in all other cases.
	   * 
	   *     Worst-case asymptotic running time cost: Theta(N)
	   *     
	   *     Justification: This function starts with an if-else statement paired with
	   *         isEmpty() as the condition which has Theta(1) running cost, a nested-for loop
	   *         is being used which has Worst-case asymptotic running time cost of Theta(N). 
	   *         it has a nested if statement which has running cost of Theta(1). All the operations 
	   *         happening other than for loop have cost of Theta(1) here.
	   *         Hence, the total  Worst-case asymptotic running time cost of this function is Theta(N).
	   */
	
	public boolean has(T data) {
		if (!isEmpty()) {
			DLLNode current = head;
			for (int i = 0; i < size; i++) {
				if (current.data.equals(data))
					return true;
				current = current.next;
			}
		}
		return false;
	}

	/*---------------------- STACK API 
	 * If only the push and pop methods are called the data structure should behave like a stack.
	 */

	/**
	 * This method adds an element to the data structure. How exactly this will be
	 * represented in the Doubly Linked List is up to the programmer.
	 * 
	 * @param item : the item to push on the stack
	 *
	 *             Worst-case asymptotic running time cost: Theta(1)
	 *
	 *             Justification: As this uses addTail function, therefore it
	 *             has the same Worst-case asymptotic running time cost
	 */
	public void push(T item) {
		addTail(item);
	}

	/**
	 * This method returns and removes the element that was most recently added by
	 * the push method.
	 * 
	 * @return the last item inserted with a push; or null when the list is empty.
	 *
	 *         Worst-case asymptotic running time cost: Theta(1)
	 *
	 *         Justification: As this uses getTail() and deleteTail() which have
	 *         the same running cost of Theta(1). Hence by simplification, the
	 *         total Worst-case asymptotic running time cost would be Theta(1).
	 */
	public T pop() {
		T lastElement = getTail();
		deleteTail();
		return lastElement;
	}

	/*----------------------- QUEUE API
	 * If only the enqueue and dequeue methods are called the data structure should behave like a FIFO queue.
	 */

	/**
	 * This method adds an element to the data structure. How exactly this will be
	 * represented in the Doubly Linked List is up to the programmer.
	 * 
	 * @param item : the item to be enqueued to the stack
	 *
	 *             Worst-case asymptotic running time cost: Theta(N)
	 *
	 *             Justification: As this uses push(), therefore it
	 *             has the same Worst-case asymptotic running time cost
	 */
	public void enqueue(T item) {
		push(item);
	}

	/**
	 * This method returns and removes the element that was least recently added by
	 * the enqueue method.
	 * 
	 * @return the earliest item inserted with an enqueue; or null when the list is
	 *         empty.
	 *
	 *         Worst-case asymptotic running time cost: Theta(1)
	 *
	 *         Justification: As this uses getHead() and deleteHead() which have
	 *         the same running cost of Theta(1). Hence by simplification, the
	 *         total Worst-case asymptotic running time cost would be Theta(1).
	 *         
	 */
	public T dequeue() {
		T first = getHead();
		deleteHead();
		return first;
	}

	/**
	 * @return a string with the elements of the list as a comma-separated list,
	 *         from beginning to end
	 *
	 *         Worst-case asymptotic running time cost: Theta(n)
	 *
	 *         Justification: We know from the Java documentation that
	 *         StringBuilder's append() method runs in Theta(1) asymptotic time. We
	 *         assume all other method calls here (e.g., the iterator methods above,
	 *         and the toString method) will execute in Theta(1) time. Thus, every
	 *         one iteration of the for-loop will have cost Theta(1). Suppose the
	 *         doubly-linked list has 'n' elements. The for-loop will always iterate
	 *         over all n elements of the list, and therefore the total cost of this
	 *         method will be n*Theta(1) = Theta(n).
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		boolean isFirst = true;

		// iterate over the list, starting from the head
		for (DLLNode iter = head; iter != null; iter = iter.next) {
			if (!isFirst) {
				s.append(",");
			} else {
				isFirst = false;
			}
			s.append(iter.data.toString());
		}

		return s.toString();
	}

}
