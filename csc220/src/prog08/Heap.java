package prog08;

import java.util.*;

public class Heap<E> extends AbstractQueue<E> {
	private List<E> theData = new ArrayList<E>();

	public Heap() {
	}

	/** An optional reference to a Comparator object. */
	Comparator<E> comparator = null;

	/**
	 * Creates a heap-based priority queue with that orders its elements
	 * according to the specified comparator.
	 * 
	 * @param comp
	 *            The comparator used to order this priority queue
	 */
	public Heap(Comparator<E> comp) {
		comparator = comp;
	}

	public int size() {
		return theData.size();
	}

	/**
	 * Compare the items with index i and index j in theData using either a
	 * Comparator object�s compare method or their natural ordering using method
	 * compareTo.
	 * 
	 * @param i
	 *            index of first item in theData
	 * @param j
	 *            index of second item in theData
	 * @return Negative int if first less than second, 0 if first equals second,
	 *         positive int if first > second
	 * @throws ClassCastException
	 *             if items are not Comparable
	 */
	private int compare(int i, int j) {
		if (comparator == null)
			return ((Comparable<E>) theData.get(i)).compareTo(theData.get(j));
		else
			return comparator.compare(theData.get(i), theData.get(j));
	}

	/**
	 * Swap the items with index i and index j in theData.
	 * 
	 * @param i
	 *            index of first item in theData
	 * @param j
	 *            index of second item in theData
	 */
	private void swap(int i, int j) {
		E temp = theData.get(i);
		theData.set(i, theData.get(j));
		theData.set(j, temp);
	}

	/**
	 * Insert an item into the priority queue. pre: theData is in heap order.
	 * post: The item is in the priority queue and theData is in heap order.
	 * 
	 * @param item
	 *            The item to be inserted
	 * @throws NullPointerException
	 *             if the item to be inserted is null.
	 */
	public boolean offer(E item) {
		if (item == null)
			throw new NullPointerException();

		theData.add(item);

		int child = size() - 1;
		int parent = (child - 1) / 2;

		while (child > 0 && compare(child, parent) < 0) {
			swap(parent, child);
			child = parent;
			parent = (child - 1) / 2;
		}

		return true;
	}

	/**
	 * Look at the first item in the queue pre: The ArrayList theData is in heap
	 * order post: The ArrayList theData is in heap order
	 * 
	 * @return The item at the top of the heap
	 * 
	 */
	public E peek() {
		if (theData.size() == 0)
			return null;
		else {
			return theData.get(0);
		}
	}

	/**
	 * Remove an item from the priority queue pre: The ArrayList theData is in
	 * heap order. post: Removed smallest item, theData is in heap order.
	 * 
	 * @return The item with the smallest priority value or null if empty.
	 */
	public E poll() {
		// IMPLEMENT
//System.out.println(theData.size());
		// Return null if queue is empty.
		if (theData.size() == 0)
			return null;

		// Save the top of the heap.

		// If only one item then remove it and you are done.
		if (theData.size() == 1) {
			return theData.remove(0);
		}

		/*
		 * Remove the last item from the ArrayList and place it into the first
		 * position.
		 */
		E temp = theData.get(0);
		theData.set(0, theData.remove(theData.size()-1));
		int parent = 0;
		int leftChild = (2 * parent + 1);
		int rightChild = (2 * parent + 2);
		while ((leftChild < theData.size()   && compare(parent, leftChild) > 0)
				|| (rightChild < theData.size()  && compare(parent,	rightChild) > 0)) {
			if (rightChild < theData.size() && compare(rightChild, leftChild) <= 0){
				swap(parent, rightChild);
				parent = rightChild;
				leftChild = (2 * parent + 1);
				rightChild = (2 * parent + 2);
					}
			

			else {
				swap(parent, leftChild);
				parent = leftChild;
				leftChild = (2 * parent + 1);
				rightChild = (2 * parent + 2);

			}

		}

		/* Use swaps to move that item into the correct position. */

		/* See the lab notes for a hint on this loop. */
		return temp;
	}

	public Iterator<E> iterator() {
		return theData.iterator();
	}

	public String toString() {
		return toString(0, 0);
	}

	private String toString(int root, int indent) {
		if (root >= size())
			return "";
		String ret = toString(2 * root + 2, indent + 2);
		for (int i = 0; i < indent; i++)
			ret = ret + "  ";
		ret = ret + theData.get(root) + "\n";
		ret = ret + toString(2 * root + 1, indent + 2);
		return ret;
	}

	public static void main(String[] args) {
		int[] data = { 3, 1, 4, 1, 5, 9, 2, 6 };
		Heap<Integer> queue = new Heap<Integer>();

		for (int i = 0; i < data.length; i++) {
			queue.offer(data[i]);
			System.out.println(queue);
		}
		for (int i = 0; i < data.length; i++) {
			queue.poll();
			System.out.println(queue);
		}
	}
}
