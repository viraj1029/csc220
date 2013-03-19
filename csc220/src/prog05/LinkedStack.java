package prog05;

import java.util.EmptyStackException;

/** Class to implement interface StackInt<E> as a linked list.
 */

public class LinkedStack < E >
    implements StackInt < E > {

  /** A Node is the building block for a single-linked list. */
  private static class Node < E > {
    // Data Fields
    /** The reference to the item. */
    private E item;

    /** The reference to the next node. */
    private Node next;

    // Constructors
    /** Creates a new node with a null next field.
        @param item The item stored
     */
    private Node (E item) {
      this.item = item;
      next = null;
    }

    /** Creates a new node that references the next node.
        @param item The item stored
        @param next The next node referenced by new node (can be null)
     */
    private Node (E item, Node<E> next) {
      this.item = item;
      this.next = next;
    }
  } //end class Node

  // Data Fields
  /** The reference to the first stack node. */
  private Node<E> head = null;

  /** Insert a new item on top of the stack.
      post: The new item is the top item on the stack.
            All other items are one position lower.
      @param obj The item to be inserted
      @return The item that was inserted
   */
  public E push (E obj) {
    head = new Node<E>(obj, head);
    return obj;
  }

  /** Remove and return the top item on the stack.
      pre: The stack is not empty.
      post: The top item on the stack has been
            removed and the stack is one item smaller.
      @return The top item on the stack
      @throws EmptyStackException if the stack is empty
   */
  public E pop () {
    if (empty())
      throw new EmptyStackException();
    E obj;
    obj = head.item;
    head = head.next;
    return obj;


    // EXERCISE
  }

@Override
public E peek() {
    if (empty())
        throw new EmptyStackException();
	E obj = head.item;
	return obj;
}

@Override
public boolean empty() {
if (head == null)
	return true;
			return false;
}

  // EXERCISE
}
