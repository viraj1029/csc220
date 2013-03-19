package prog05;

import java.util.EmptyStackException;

import prog02.DirectoryEntry;

public class ArrayStack < E >
    implements StackInt < E > {

  // Data Fields
  /** Storage for stack. */
  E[] theItems;
  /** Number of items in the stack. */
  private int size = 0; // Initially empty stack.
  private static final int INITIAL_CAPACITY = 2;

  /** Construct an empty stack with the default
      initial capacity.
   */
  public ArrayStack () {
    theItems = (E[])new Object[INITIAL_CAPACITY];
  }

  /** Insert a new item on top of the stack.
      post: The new item is the top item on the stack.
            All other items are one position lower.
      @param obj The item to be inserted
      @return The item that was inserted
   */
  public E push (E obj) {
    if (this.size == this.theItems.length)
      reallocate();

    this.theItems[size] = obj;
    this.size++;
    return obj;
  }

  /** Remove and return the top item on the stack.
      pre: The stack is not empty.
      post: The top item on the stack has been
            removed and the stack is one item smaller.
      @return The top item on the stack
      @throws EmptyStackException if the stack is empty
   */
  public E pop() {
    if (this.empty())
      throw new EmptyStackException();
    E obj = this.theItems[size-1];
    this.theItems[size-1] = null;
    this.size--;
    return obj;
    

    // EXERCISE
  }
public void reallocate() {
	E[] newItems  =
			(E[])new Object[2 * theItems.length];
	System.arraycopy(theItems, 0, newItems, 0,
			theItems.length);
	theItems = newItems;
}
public E peek()
{
    if (this.empty())
        throw new EmptyStackException();
   
	return (E)this.theItems[size-1];
}
  // EXERCISE

@Override
public boolean empty() {
	if (this.size == 0)
	return true;
	return false;
}
}
