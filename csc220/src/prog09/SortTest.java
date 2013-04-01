package prog09;

import java.util.Random;

public class SortTest<E extends Comparable<E>> {
	public void test(Sorter<E> sorter, E[] array) {
		E[] copy = array.clone();
		long start, end;
		double time = 0.0;
		start = System.currentTimeMillis();
		sorter.sort(copy);
		end = System.currentTimeMillis();

		time = (double)(end-start);
		
		
			
		if (array.length < 100){
			System.out.println(sorter);
		for (int i = 0; i < copy.length; i++)
			System.out.print(copy[i] + " ");}
		System.out.println();
		System.out.println("Took " + time + " milliseconds");
	}

	public static void main(String[] args) {
		Integer[] array = { 3, 1, 4, 1, 5, 9, 2, 6 };

		if (args.length > 0) {
			// Print out command line argument if there is one.
			System.out.println("args[0] = " + args[0]);

			// Create a random object to call random.nextInt() on.
			Random random = new Random(0);

			// Make array.length equal to args[0] and fill it with random
			Integer[] randArray = new Integer[Integer.parseInt(args[0])];
			// integers:
			for (int i = 0; i < randArray.length; i++) {
				randArray[i] = random.nextInt();
			}
			SortTest<Integer> tester = new SortTest<Integer>();
			//tester.test(new InsertionSort<Integer>(), randArray);
			/*1000: 19 ms
			 * 10000: 93 ms
			 * 100000: 7808 ms
			 * 1000000: too long
			 */
			//tester.test(new HeapSort<Integer>(), randArray);
			/*
			 * 1000: 3 ms
			 * 10000: 18 ms
			 * 100000: 52 ms
			 * 1000000: 690 ms
			 * 10000000: 13122 ms
			 * 100000000: out of memory error
			 */
			//tester.test(new QuickSort<Integer>(), randArray);
			/*
			 * 1000: ~ 1 ms
			 * 10000: 21 ms
			 * 100000: 40 ms
			 * 1000000: 337 ms
			 * 10000000: 5185 ms
			 * 100000000: out of memory error
			 */
			tester.test(new MergeSort<Integer>(), randArray);
			/*
			 * 1000: ~ 1 ms
			 * 10000: 21 ms
			 * 100000: 199 ms
			 * 1000000: 560 ms
			 * 10000000: 5358 ms
			 * 100000000: out of memory error
			 */

		} else {

			SortTest<Integer> tester = new SortTest<Integer>();
			tester.test(new InsertionSort<Integer>(), array);
			tester.test(new HeapSort<Integer>(), array);
			tester.test(new QuickSort<Integer>(), array);
			tester.test(new MergeSort<Integer>(), array);
		}
	}
}

class InsertionSort<E extends Comparable<E>> implements Sorter<E> {
	public void sort(E[] array) {
		for (int n = 0; n < array.length; n++) {
			E data = array[n];
			int i = n;
			// while array[i-1] > data
			while (i > 0 && array[i - 1].compareTo(data) > 0) {
				// move array[i-1] to array[i] and
				array[i] = array[i - 1];
				// decrement i
				i--;

			}

			array[i] = data;
		}
	}
}

class HeapSort<E extends Comparable<E>> implements Sorter<E> {

	private E[] array;

	private void swap(int i, int j) {
		E data = array[i];
		array[i] = array[j];
		array[j] = data;
	}

	public void sort(E[] array) {
		this.array = array;

		for (int i = parent(array.length - 1); i >= 0; i--)
			swapDown(i, array.length - 1);

		for (int n = array.length - 1; n >= 0; n--) {
			swap(0, n);
			swapDown(0, n - 1);
		}
	}

	public void swapDown(int root, int end) {
		// Calculate the left child of root.
		int leftChild = left(root);
		int rightChild;
		int parent = root;
		int biggerChild;
		// while the left child is still in the array
		while (leftChild <= end) {
			rightChild = right(parent);
			if (rightChild <= end) {
				if (array[leftChild].compareTo(array[rightChild]) < 0) {
					biggerChild = rightChild;
				} else {
					biggerChild = leftChild;
				}

			} else {
				biggerChild = leftChild;
			}
			if (array[parent].compareTo(array[biggerChild]) >= 0) {

				return;
			}
			swap(parent, biggerChild);
			parent = biggerChild;
			leftChild = left(parent);
		}
		// calculate the right child
		// if the right child is in the array and
		// it is bigger than than the left child
		// bigger child is right child
		// else
		// bigger child is left child
		// if the root is not less than the bigger child
		// return
		// swap the root with the bigger child
		// update root and calculate left child
	}

	private int left(int i) {
		return 2 * i + 1;
	}

	private int right(int i) {
		return 2 * i + 2;
	}

	private int parent(int i) {
		return (i - 1) / 2;
	}
}

class QuickSort<E extends Comparable<E>> implements Sorter<E> {

	private E[] array;

	private void swap(int i, int j) {
		E data = array[i];
		array[i] = array[j];
		array[j] = data;
	}

	public void sort(E[] array) {
		this.array = array;
		sort(0, array.length - 1);
	}

	private void sort(int left, int right) {
		if (left >= right)
			return;

		swap(left, (left + right) / 2);

		E pivot = array[left];
		int a = left + 1;
		int b = right;
		while (a <= b) {
			if (array[a].compareTo(pivot) <= 0) {
				a++;
			} else if (array[b].compareTo(pivot) > 0) {
				b--;
			}
			// Move a forward if array[a] <= pivot
			// Move b backward if array[b] > pivot
			// Otherwise swap array[a] and array[b]
			else {
				swap(a, b);
			}
		}

		swap(left, b);

		sort(left, b - 1);
		sort(b + 1, right);
	}
}

class MergeSort<E extends Comparable<E>> implements Sorter<E> {

	private E[] array, array2;

	public void sort(E[] array) {
		this.array = array;
		array2 = array.clone();
		sort(0, array.length - 1);
	}

	private void sort(int left, int right) {
		if (left >= right)
			return;

		int middle = (left + right) / 2;
		sort(left, middle);
		sort(middle + 1, right);

		int i = left;
		int a = left;
		int b = middle + 1;
		while (a <= middle || b <= right) {
			// If both a <= middle and b <= right
			if (a <= middle && b <= right) {
				if (array[a].compareTo(array[b]) <= 0) {
					array2[i++] = array[a++];
				} else {
					array2[i++] = array[b++];
				}
			}

			else if (b <= right) {
				array2[i++] = array[b++];
			} else if (a <= middle) {
				array2[i++] = array[a++];
			}
			// copy the smaller of array[a] or array[b] to array2[i]
			// Otherwise just copy the remaining elements to array2
		}

		System.arraycopy(array2, left, array, left, right - left + 1);
	}
}
