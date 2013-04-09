package prog11;

import java.util.*;

public class OpenHashTable<K, V> extends AbstractMap<K, V> {
	private static class Node<K, V> implements Map.Entry<K, V> {
		K key;
		V value;

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V value) {
			return this.value = value;
		}

		Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private final static int DEFAULT_CAPACITY = 5;
	private Node<K, V>[] table = new Node[DEFAULT_CAPACITY];
	private Node<K, V> DELETED = new Node<K, V>(null, null);
	private int size;
	private int nonNull = 0;

	private int hashIndex(Object key) {
		int index = key.hashCode() % table.length;
		if (index < 0)
			index += table.length;
		return index;
	}

	// Linear probe sequence: start at hashIndex(key) and increment,
	// but roll around to zero at the end of the table.

	// Return the index of the Node with key if it is in the probe
	// sequence.

	// If it is not there, return the index where the Node with key
	// should be inserted. If there is a deleted Node in the probe
	// sequence, return the index of the *first* deleted Node in the
	// sequence.

	// Otherwise return the index of the first null in the probe
	// sequence.
	private int find(Object key) {
		int index = hashIndex(key);
		if (table[index] == null)
			return index;
		int firstDeleted = -1;
		while (table[index] != null
				&& (table[index] == DELETED || !table[index].key.equals(key))) {
			if (firstDeleted == -1 && table[index] == DELETED)

			{
				firstDeleted = index;
			}
			index = (index + 1) % table.length;
		}
		if (firstDeleted != -1 && table[index] == null) {
			return firstDeleted;
		}

		return index;
	}

	public boolean containsKey(Object key) {
		Node<K, V> node = table[find(key)];
		return node != null && node != DELETED;
	}

	public V get(Object key) {
		Node<K, V> node = table[find(key)];
		if (node == null || node == DELETED)
			return null;
		return node.value;
	}

	public V put(K key, V value) {
		System.out.println("put " + key + " " + value);
		int index = find(key);
		Node<K, V> node = table[index];
		if (node == null || node == DELETED) {
			table[index] = new Node<K, V>(key, value);
			nonNull++;
			size++;
			if (nonNull >= table.length / 2)
				rehash(4 * size);
			return null;
		}
		V old = node.value;
		node.value = value;
		return old;
	}

	public V remove(Object key) {
		System.out.println("remove " + key);
		int index = find(key);
		Node<K, V> node = table[index];
		if (node == null || null == DELETED)
			return null;
		table[index] = DELETED;
		nonNull++;
		size--;
		return node.value;
	}

	private void rehash(int newCapacity) {
		// IMPLEMENT
		Node<K, V>[] oldTable = table;
		nonNull = 0;
		table = new Node[newCapacity];
		for (int i = 0; i < oldTable.length; i++) {
			if (oldTable[i] != null && oldTable[i] != DELETED) {
				put(oldTable[i].getKey(), oldTable[i].getValue());
			}

		}
		System.out.println(nonNull);

	}

	private Iterator<Map.Entry<K, V>> entryIterator() {
		return new EntryIterator();
	}

	private class EntryIterator implements Iterator<Map.Entry<K, V>> {
		// EXERCISE
		int i = 0;
		Node<K, V> item;
		int k = 0;
		public boolean hasNext() {

			/*
			 * while (i < table.length) { if (table[i] != null || table[i] !=
			 * DELETED) return true; } i++; return false;
			 */
int j = i;
			// while (k < table.length) {
			while (j < table.length) {
				if (k < nonNull*2) {
					if (table[j] != null && table[k] != DELETED) {
						k++;
						return true;
					}
				}
				// k++;
				j++;
			}
			// return false;
			return false;
		}

		public Map.Entry<K, V> next() {
			// EXERCISE
			if (!hasNext())
				throw new NoSuchElementException();

			while (i < table.length) {
				if (table[i] != null) {
					if (table[i] != DELETED) {
						item = table[i];
						i++;
						return item;
					}
				}

				i++;
			}
			return item;

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
			ret = ret + i + ": ";
			Node<K, V> node = table[i];
			if (node == null)
				ret = ret + "null\n";
			else if (node == DELETED)
				ret = ret + "DELETED\n";
			else
				ret = ret + node.key + " " + node.value + "\n";
		}
		return ret;
	}

	public static void main(String[] args) {
		OpenHashTable<String, Integer> table = new OpenHashTable<String, Integer>();

		table.put("Brad", 46);
		// System.out.println(table);
		table.put("Hal", 10);
		// System.out.println(table);
		table.put("Kyle", 6);
		// System.out.println(table);
		table.put("Lisa", 43);
		// System.out.println(table);
		table.put("Lynne", 43);
		// System.out.println(table);
		table.put("Victor", 46);
		// System.out.println(table);
		table.put("Zoe", 6);
		// System.out.println(table);
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
		table.remove("Hal");
		System.out.println(table);
		table.remove("Lynne");
		System.out.println(table);

		table.put("Ant", 3);
		System.out.println(table);
		table.remove("Ant");
		System.out.println(table);
		table.put("Bug", 1);
		System.out.println(table);
		table.remove("Bug");
		System.out.println(table);
		table.put("Cat", 4);
		System.out.println(table);
		table.remove("Cat");
		System.out.println(table);
		table.put("Dog", 1);
		System.out.println(table);
		table.remove("Dog");
		System.out.println(table);
		table.put("Eel", 5);
		System.out.println(table);
		table.remove("Eel");
		System.out.println(table);
		table.put("Fox", 9);
		System.out.println(table);
		table.remove("Fox");
		System.out.println(table);
		table.put("Gnu", 2);
		System.out.println(table);
		table.remove("Gnu");
		System.out.println(table);

		table.put("Hen", 2);
		System.out.println(table);
		table.remove("Hen");
		System.out.println(table);
		table.put("Jay", 2);
		System.out.println(table);
		table.remove("Jay");
		System.out.println(table);
		table.put("Owl", 2);
		System.out.println(table);
		table.remove("Owl");
		System.out.println(table);
		table.put("Pig", 2);
		System.out.println(table);
		table.remove("Pig");
		System.out.println(table);
		table.put("Rat", 2);
		System.out.println(table);
		table.remove("Rat");
		System.out.println(table);
		table.put("Yak", 2);
		System.out.println(table);
		table.remove("Yak");
		System.out.println(table);
	}
}
