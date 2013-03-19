package prog02;

import java.io.*;

/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {
	/** Add an entry to the directory.
    @param name The name of the new person
    @param number The number of the new person
	 */
	protected void add(String name, String number) {
		if (size >= theDirectory.length) {
			reallocate();
		}
		FindOutput fo = find(name);
		if(!fo.found)
		{
			for (int i = size; i > fo.index; i--)
			{
				theDirectory[i] = theDirectory[i-1];
			}
		
		theDirectory[fo.index] = new DirectoryEntry(name, number);
		size++;
		}
		
		
	}
	/** Find an entry in the directory.
    @param name The name to be found
    @return A FindOutput object containing the result of the search.
	 */
	protected FindOutput find (String name) {
		int cmp;
		int first = 0;
		int last = size-1;
		int middle = 0;
		while (!(first > last))
		{
			middle = (last+first)/2;
			cmp = name.compareTo(theDirectory[middle].getName());
			if (cmp == 0)
			{
				return new FindOutput(true, middle);
			}
			else if (cmp < 0)
			{
				last = middle-1;
			}
			else if (cmp > 0)
			{
				first = middle+1;
			}
		}
		return new FindOutput(false, first);
	}

	/** Remove an entry from the directory.
    @param name The name of the person to be removed
    @return The current number. If not in directory, null is
            returned
	 */
	public String removeEntry (String name) {
		FindOutput fo = find(name);
		if (!fo.found)
			return null;
		DirectoryEntry entry = theDirectory[fo.index];
		for (int i = fo.index; i < (size-1); i++)
		{
			theDirectory[i] = theDirectory[i+1];
		}
		size--;
		modified = true;
		return entry.getNumber();
	}
	
}
