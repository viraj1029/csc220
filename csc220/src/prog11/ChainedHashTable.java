package prog11;

import java.util.*;

public class ChainedHashTable<K, V> extends AbstractMap<K, V> {
	private static class Entry<K, V> implements Map.Entry<K, V> {
		K key;
		V value;
		Entry<K, V> next;

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V value) {
			return this.value = value;
		}

		Entry(K key, V value, Entry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	private final static int DEFAULT_CAPACITY = 3;
	private Entry<K, V>[] table = new Entry[DEFAULT_CAPACITY];
	private int size;

	private int hashIndex(Object key) {
		int index = key.hashCode() % table.length;
		if (index < 0)
			index += table.length;
		return index;
	}

	private Entry<K, V> find(Object key) {
		int index = hashIndex(key);
		for (Entry<K, V> node = table[index]; node != null; node = node.next)
			if (key.equals(node.key))
				return node;
		return null;
	}

	public boolean containsKey(Object key) {
		return find(key) != null;
	}

	public V get(Object key) {
		Entry<K, V> node = find(key);
		if (node == null)
			return null;
		return node.value;
	}

	public V put(K key, V value) {
		Entry<K, V> node = find(key);
		if (node != null) {
			V old = node.value;
			node.value = value;
			return old;
		}
		if (size == table.length)
			rehash(2 * table.length);
		int index = hashIndex(key);
		table[index] = new Entry<K, V>(key, value, table[index]);
		size++;
		return null;
	}

	public V remove(Object key) {
		// Get the index for the key.
		int index = hashIndex(key);
		// Deal with the case that the linked list at that index is empty.
		if (table[index] == null)
			return null;

		// Deal with the case that the key belongs to the first
		// element in that list.
		if (table[index].key.equals(key)) {
			Entry<K, V> temp = table[index];
			table[index] = temp.next;
			size--;
			return temp.value;
		}

		// Deal with the case that the key belongs to some other
		// element in that list.

		for (Entry<K, V> node = table[index]; node != null; node = node.next) {
			if (node.next.key.equals(key)) {
				Entry<K, V> temp = node.next;
				node.next = node.next.next;
				return temp.value;
			}
		}

		// Return null otherwise.
		return null;
	}

	private void rehash(int newCapacity) {
		// IMPLEMENT
		Entry<K, V> oldTable[] = table;
		table = new Entry[newCapacity];
		Entry<K, V> entry;
		for (int i = 0; i < oldTable.length; i++) {
			entry = oldTable[i];
			while (entry != null) {
				put(entry.key, entry.value);
				entry = entry.next;
			}
		}

	}

	private Iterator<Map.Entry<K, V>> entryIterator() {
		return new EntryIterator();
	}

	private class EntryIterator implements Iterator<Map.Entry<K, V>> {
		// EXERCISE
		boolean isHead = true;
		int i = 0;
		Entry<K, V> node = table[i];

		public boolean hasNext() {
			for (int j = i; j < table.length; j++) {
				if (table[j] != null)
					return true;
			}
			return false;
		}

		public Map.Entry<K, V> next() {
			// EXERCISE
			// Increment Node:
			// If we're supposed to be at the head of the list, get the first
			// entry at that index
			if (isHead) {
				node = table[i];
			} else {
				// Otherwise, get the next node in the list
				node = node.next;
			}
			// Skip over the null nodes until you find one that isn't null. If
			// you're in this loop, hasNext() is true so there will be a non
			// null node.
			while (node == null) {
				i++;
				node = table[i];
				isHead = true;
			}
			// If we're at the last node on a list; return and get ready to go
			// to the next index.
			if (node.next == null) {
				i++;
				isHead = true;
				return node;
			} else {
				// Otherwise, we continue going down the list.
				isHead = false;
			}

			return node;
		}

		public void remove() {
		}
	}

	public Set<Map.Entry<K, V>> entrySet() {
		return new EntrySet();
	}

	private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
		public int size() {
			return size;
		}

		public Iterator<Map.Entry<K, V>> iterator() {
			return entryIterator();
		}

		public void remove() {
		}
	}

	public String toString() {
		String ret = "------------------------------\n";
		for (int i = 0; i < table.length; i++) {
			ret = ret + i + ":";
			for (Entry<K, V> node = table[i]; node != null; node = node.next)
				ret = ret + " " + node.key + " " + node.value;
			ret = ret + "\n";
		}
		return ret;
	}

	public static void main(String[] args) {
		ChainedHashTable<String, Integer> table = new ChainedHashTable<String, Integer>();

		table.put("Brad", 46);
		System.out.println(table);
		table.put("Hal", 10);
		System.out.println(table);
		table.put("Kyle", 6);
		System.out.println(table);
		table.put("Lisa", 43);
		System.out.println(table);
		table.put("Lynne", 43);
		System.out.println(table);
		table.put("Victor", 46);
		System.out.println(table);
		table.put("Zoe", 6);
		System.out.println(table);
		table.put("Zoran", 76);
		System.out.println(table);

		for (String key : table.keySet())
			System.out.print(key + " ");
		System.out.println();

		table.remove("Zoe");
		System.out.println(table);
		table.remove("Kyle");
		System.out.println(table);
		table.remove("Brad");
		System.out.println(table);
		table.remove("Zoran");
		System.out.println(table);
		table.remove("Lisa");
		System.out.println(table);
	}
}
