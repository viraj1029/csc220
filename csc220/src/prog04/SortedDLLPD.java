package prog04;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class SortedDLLPD extends DLLBasedPD {
  /** Add an entry to the directory.
      @param name The name of the new person
      @param number The number of the new person
  */
 protected void add (String name, String number) {
    // EXERCISE
    // Call find to find where to put the entry.
	  FindOutput fo = find(name);
	  DLLNode entry = new DLLNode(name, number);
	  // For a TEST try using new FindOutput(false, head).

    // What will be the next entry after this entry?
	  // What will be the previous entry after this entry?
    // Two cases:  entry goes last (no next) or not.
	  if (tail == null)
	  {
		  head = entry;
		  tail = entry;
	  }	
	  else if (fo.entry == null)
{
	tail.setNext(entry);
	entry.setPrevious(tail);
	tail = entry;
	tail.setNext(fo.entry);
} 
else if (fo.entry.getPrevious() == null)
{
    // Allocate new entry.
entry.setPrevious(null);
entry.setNext(head);
head.setPrevious(entry);
head = entry;
}
else
{
	DLLNode previous = fo.entry.getPrevious();
	entry.setPrevious(previous);
	previous.setNext(entry);
	entry.setNext(fo.entry);
	fo.entry.setPrevious(entry);
}
    // Set next for the entry.

    // Set previous for next, two cases (next is null or not).

    // Ditto for previous.

    modified = true;
  }

  /** Find an entry in the directory.
      @param name The name to be found
      @return A FindOutput object describing the result.
  */
 protected FindOutput find (String name) {
    // EXERCISE
    // Modify find so it also stops when it gets to an entry after the
    // one you want.
	  int cmp;
	  for (DLLNode entry = head; entry != null; entry = entry.getNext()) {
		  cmp = entry.getKey().compareTo(name);
		  if (cmp == 0)
			  return new FindOutput(true, entry);
		  else if (cmp > 0)
				return new FindOutput(false, entry);
		}
		return new FindOutput(false, null); // Name not found.
	}
}
