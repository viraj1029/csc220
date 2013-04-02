package prog10;
import java.util.*;

public class BTree<K extends Comparable<K>, V>
  extends AbstractMap<K, V> implements Map<K, V> {
  
  /**
   * At the bottom of the tree, this is an data entry in the directory list.
   * It is called a leaf and contains the V value in valueOrList.
   * Inside the tree, this is a list entry.
   * Its valueOrList points to list the next level down.
   */
  private class Entry<K extends Comparable<K>, V>
    implements Map.Entry<K, V> {
    private K key;
    private Object valueOrList;

    private Entry (K key, V value) {
      this.key = key;
      valueOrList = value;
    }

    private Entry (ArrayList<Entry <K, V>> list) {
      key = list.get(0).key;
      valueOrList = list;
    }

    public K getKey () {
      return key;
    }

    private boolean bottom () {
      return !(valueOrList != null &&
               valueOrList instanceof ArrayList &&
	       getList().size() > 0 &&
               getList().get(0) instanceof Entry);
    }

    public V getValue () {
      return (V) valueOrList;
    }

    public V setValue (V value) {
      V old = getValue();
      valueOrList = value;
      return old;
    }

    public ArrayList<Entry<K, V>> getList () {
      return (ArrayList<Entry<K, V>>) valueOrList;
    }

    public String toString () {
      return "" + getKey() + " " + getValue();
    }
  }

  // (4) means give it a capacity (length) of 4.  size is set to 0.
  private ArrayList<Entry<K, V>> list = new ArrayList<Entry<K, V>>(4);

  /**
   * Find the index of the entry that contains key.  Remember that if
   * key comes before list.get(i).key, then i is too big.  Start with
   * the last index and work backwards.  Return -1 if the key is
   * definitely not there.
   */
  private int findIndex (K key, ArrayList<Entry<K, V>> list) {
    int i = list.size() - 1;

    // EXERCISE
    
while (i>= 0 && key.compareTo(list.get(i).getKey()) < 0)
	i--;
    return i;
  }

  /**
   * Recursively find the leaf entry for the key in the tree.
   * Return null if not there.
   */
  private Entry<K, V> find (K key, ArrayList<Entry<K, V>> list) {
    // If the list is empty, done.
    if (list.size() == 0)
      return null;

    // Find the index i which should contain the key's entry.
    int i = findIndex(key, list);
    if (i < 0)
    	return null;
    // If the list entries are leaves (contain data not lists):
    if (list.get(0).bottom()) {
      // EXERCISE
    	if (key.compareTo(list.get(i).key) == 0)
    	
      // Check if entry i is the one we want.  Be careful!
    	return list.get(i);
    	else
    		return null;
      // If it is, return it.  If not, return null.

    }

    // List contains lists.

    // Recursively find the key in the list at i.
    return find(key, list.get(i).getList());
  }

  public boolean containsKey (K key) {
    return find(key, list) != null;
  }

  public V get (Object obj) {
    K key = (K) obj;
    return find(key, list).getValue();
  }

  /**
   * Split off a right hand list for a list containing four elements.
   * Original list contains [0] and [1]
   * Returned list contains what used to be [2] and [3].
   * As always, new list capacity should be 4.
   */
  private ArrayList<Entry<K, V>> split (ArrayList<Entry<K, V>> list) {
    ArrayList<Entry<K, V>> right = new ArrayList<Entry<K, V>>(4);
    right.add(list.get(2));
    right.add(list.get(3));
    list.remove(3);
    list.remove(2);
    return right;
  }

  public V put (K key, V value) {
    Entry<K, V> entry = find(key, list);
    if (entry != null) {
      V old = entry.getValue();
      entry.setValue(value);
      return old;
    }

    insert(key, value, list);

    // Look at this!!
    if (list.size() == 4) {
        ArrayList<Entry<K, V>> left = list;
        ArrayList<Entry<K, V>> right = split(list);
        list = new ArrayList<Entry<K, V>>(4);
        list.add(new Entry<K, V>(left));
        list.add(new Entry<K, V>(right));
    }

    return null;
  }
      
  /**
   * Recursively insert an entry with key and value into list.
   * Precondition:  entry must not be there.
   */
  private void insert (K key, V value, ArrayList<Entry<K, V>> list) {
    int i = findIndex(key, list);

    // If we are at the bottom level:
    if (list.size() == 0 || list.get(0).bottom()) {
      // EXERCISE
    	Entry<K, V> newEntry = new Entry<K, V>(key, value);
    	list.add(i+1, newEntry);
      // Create a new Entry and insert it.  Look at the API for List.
      // You don't need a loop!
      // Remember:  this is an ordinary list of entries (leaf not internal).
      // What index do you want to insert the new Entry?
      // (Hint: it's not i, but it is close to it!)


      return;
    }
    if (i < 0)
    {
    	list.add(0, new Entry<K, V>(key, value));
    }

    // EXERCISE
    // If key comes before all the keys in the list, what will i equal?
    // In which sublist should the new key go?
    // So what should you set i equal to in this case?


    Entry<K, V> entry = list.get(i);
    ArrayList<Entry<K, V>> sublist = entry.getList();

    insert(key, value, sublist);
   // entry.key = sublist.get(0).key;

    if (sublist.size() < 4)
      return;

    ArrayList<Entry<K, V>> rightlist = split(sublist);

    // EXERCISE
    // Create a new Entry with rightlist.
    Entry<K, V> rightEntry = new Entry<K,V>(rightlist);
    // Insert it into list. What index?  (Hint: to the right of index i!)
list.add(i+1, rightEntry);

  }
      
  public V remove (K key) {
    Entry<K, V> entry = find(key, list);

    if (entry == null)
      return null;

    remove(key, list);

    // EXERCISE
    // If the list has only one element and that element is not a
    if (list.size() == 1 && !list.get(0).bottom())
    	list = list.get(0).getList();
   // entry = list.get(0);
    // leaf, then throw away the list and that entry and set list
    // equal to that entry's list.


    return entry.getValue();
  }

  /**
   * Recursively remove the entry with key from the list.
   * Precondition:  entry must be there.
   */
  private void remove (K key, ArrayList<Entry<K, V>> list) {
    // EXERCISE
    // Use findIndex to find the index to remove from.
int index = findIndex(key, list);


    // If the key comes before everyone, it is not there.  (Poor Aaron.)
if (index < 0)
	return;

// If the Entry at that index is a leaf, then it must be the one
// we want to remove.  Remove it using the remove method of
// ArrayList.  Then you are done.


if (list.get(index).bottom())
{
	list.remove(index);
	return;
}
	

    // Otherwise, get the Entry at that index, which is an internal entry.
Entry<K, V> entry = list.get(index);

    // And get its list, call it the sublist.
ArrayList<Entry<K,V>> sublist = entry.getList();
    // Recursively remove the entry with the key from that sublist.
remove(key, sublist);
entry.key = sublist.get(0).key;

    // Make sure to update the key field of the entry because the
    // sublist may have a new key at index 0 now.


    // If the size of the sublist is still 2 or 3, then we are done.
if (sublist.size() == 2 || sublist.size() == 3)
{
	return;
	}

// Othewise, we need to merge the sublist with its left or right
// neighbor in list.  To keep things simple, merge it with the one
// on the right, with index+1, but if the index is the last index,
// that's not going to work.  So in that case, decrement the index
// and make the entry and sublist be the ones which belong to that
// one.

	if (index == sublist.size())
	{
		index--;
		sublist = list.get(index).getList();

	}
	ArrayList<Entry<K,V>> rightList = list.get(index+1).getList();

		sublist.addAll(rightList);
  
		System.out.println("removing " + list.get(index+1));
		list.remove(index+1);






    // Add all the elements of the sublist to the right to the current
    // sublist.  You can just use the addAll method of ArrayList.


    // Remove the entry to the right from list.

    // If the size of the sublist is less than 4, then we are done.
if (sublist.size() < 4)
	return;
else
{
        ArrayList<Entry<K, V>> left = sublist;
        ArrayList<Entry<K, V>> right = split(sublist);
        list = new ArrayList<Entry<K, V>>(4);
        list.add(new Entry<K, V>(left));
        list.add(new Entry<K, V>(right));
}


    // Otherwise, split the sublist.  Look at insert to see how this
    // is done.



  }

  // Required to implement Map<K, V>.  Ignore.
  public Set<Map.Entry<K,V>> entrySet () { return null; }

  public String toString () {
    return "-------------------------\n" + toString(list, 0);
  }
  
  private String toString (ArrayList<Entry<K, V>> list, int indent) {
    String ret = "";
    for (int i = 0; i < list.size(); i++) {
      for (int j = 0; j < indent; j++)
        ret = ret + "    ";
      ret = ret + list.get(i).key;
      if (list.get(i).bottom())
        ret = ret + " " + list.get(i).getValue() + "\n";
      else
        ret = ret + "\n" + toString(list.get(i).getList(), indent+4);
    }
    return ret;
  }

  public static void main (String[] args) {
    BTree<String, Integer> tree = new BTree<String, Integer>();
    
    tree.put("Brad", 46);
    System.out.println(tree);
    tree.put("Hal", 10);
    System.out.println(tree);
    tree.put("Kyle", 6);
    System.out.println(tree);
    tree.put("Lisa", 43);
    System.out.println(tree);
    tree.put("Lynne", 43);
    System.out.println(tree);
    tree.put("Victor", 46);
    System.out.println(tree);
    tree.put("Zoe", 6);
    System.out.println(tree);
    tree.put("Zoran", 76);
    System.out.println(tree);
System.out.println("REMOVING ZOE");
    tree.remove("Zoe");
System.out.println(tree);
System.out.println("REMOVING KYLE");

tree.remove("Kyle");
    System.out.println(tree);
    System.out.println("REMOVING BRAD");

    tree.remove("Brad");
    System.out.println(tree);
    tree.remove("Zoran");
    System.out.println(tree);
    tree.remove("Lisa");
    System.out.println(tree);
  }
}