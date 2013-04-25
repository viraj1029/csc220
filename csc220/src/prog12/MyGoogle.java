package prog12;

import java.util.*;

public class MyGoogle implements Google {
	/**
	 * A list containing the reference counts for a given website. The index in
	 * the list corresponds to the same index in allURLs
	 */
	private List<Integer> refCounts = new ArrayList<Integer>();
	/**
	 * All of the URLs we have found
	 */
	private List<String> allURLs = new ArrayList<String>();
	/**
	 * A TreeMap which will return a URL's index in the allURLs list
	 */
	private Map<String, Integer> url2Index = new TreeMap<String, Integer>();

	@Override
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub
		Set<Integer> refSet = new HashSet<Integer>();
		List<String> urls = new ArrayList<String>();
		List<String> words = new ArrayList<String>();
		Queue<String> urlQueue = new ArrayDeque<String>();
		if (startingURLs.size() > 0) {
			for (int i = 0; i < startingURLs.size(); i++) {
				urlQueue.offer(startingURLs.get(i));
				indexURL(startingURLs.get(i));
			}
		}

		int count = 0;
		while (!urlQueue.isEmpty() && count++ < 100) {
			refSet.clear();
			String url = urlQueue.poll();
			System.out.println(url);
			browser.loadPage(url);
			urls = browser.getURLs();
			words = browser.getWords();
			for (int i = 0; i < urls.size(); i++) {
				String item = urls.get(i);
				if (url2Index.get(item) == null) {
					urlQueue.offer(item);
					indexURL(item);
				}
				int index = url2Index.get(item);
				if (refSet.contains(index) == false) {
					int temp = refCounts.get(index);
					temp++;
					refCounts.set(index, temp);
					refSet.add(index);
				}

			}
			for (int i = 0; i < words.size(); i++) {
				String item = words.get(i);

				if (word2Index.get(item) == null) {
					indexWord(item);
				}
				int index = word2Index.get(item);
				List<Integer> theList = urlIndexLists.get(index);
				if (theList.isEmpty()
						|| theList.get(theList.size() - 1) != url2Index
								.get(url))
					theList.add(url2Index.get(url));
			}
		}

		System.out.println("allURLs: \n " + allURLs);
		System.out.println("url2Index: \n" + url2Index);
		System.out.println("refCounts: \n" + refCounts);
		System.out.println("allWords \n" + allWords);
		System.out.println("word2Index: \n" + word2Index);
		System.out.println("urlIndexLists \n" + urlIndexLists);
	}

	/**
	 * Indexes a URL Adds it to our allURLs list. Puts it in url2Index with the
	 * URL as its key and its index in allURLs as its value. Adds it to refCounts
	 * list with an initial value of 0
	 * 
	 * @param url
	 *            the URL of the web page we're indexing
	 */
	private void indexURL(String url) {
		allURLs.add(url);
		url2Index.put(url, allURLs.size() - 1);
		refCounts.add(0);
	}

	/**
	 * A list of all the words found.
	 */
	List<String> allWords = new ArrayList<String>();
	/**
	 * A HashMap that maps our words as a key to their index in allWords as a
	 * value
	 */
	Map<String, Integer> word2Index = new HashMap<String, Integer>();
/**
 * An ArrayList which contains ArrayLists of all the URL indices a given word is found on.
 */
	List<List<Integer>> urlIndexLists = new ArrayList<List<Integer>>();

	/**
	 * Indexes a word
	 * Adds it to the allWords list.
	 * maps it in the word2Index HashMap.
	 * adds a new ArrayList for the word in urlIndexLists.
	 * @param word the word to be indexed
	 */
	private void indexWord(String word) {
		allWords.add(word);
		word2Index.put(word, allWords.size() - 1);
		urlIndexLists.add(new ArrayList<Integer>());
	}

	@Override
	/**
	 * search all webpages containing key words
	 * rank the candidate webpages by a priority queue according to refcounts
	 * @param keyWords a list of keyWords to search
	 * @param numResults the number of results to be displayed
	 * @return A String array of URLs, sorted by priority.
	 */
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub


		Iterator<Integer>[] pageIndexIterators = (Iterator<Integer>[]) new Iterator[keyWords
				.size()];
		int[] currentPageIndices = new int[keyWords.size()];
		//Initialize the current page indices
		//and their Iterators
		for (int i = 0; i < keyWords.size(); i++) {
			pageIndexIterators[i] = urlIndexLists.get(
					word2Index.get(keyWords.get(i))).iterator();
			currentPageIndices[i] = urlIndexLists.get(
					word2Index.get(keyWords.get(i))).get(0);
		}
		Stack<String> temp = new Stack<String>();
		Comparator<Integer> comparator = new URLComparator();
		PriorityQueue<Integer> bestPageIndices = new PriorityQueue<Integer>(
				numResults, comparator);
		//While there is a smallest to be updated
		while (updateSmallest(currentPageIndices, pageIndexIterators)) {
			if (allEqual(currentPageIndices))
				//If a word is found on all pages, add it to the queue
				bestPageIndices.offer(currentPageIndices[0]);
		}
		//Pages are in reverse order in the Priority Queue
		//So push them onto a stack to re-order them
		while (!bestPageIndices.isEmpty())
		temp.push(allURLs.get(bestPageIndices.poll()));
		String[] ret = new String[temp.size()];
		int i = 0;
		//Pop the strings into a String array
		while (!temp.isEmpty()) {
			ret[i] = temp.pop();
			i++;
		}
		//return the filled String array
		return ret;
	}

	/**
	 * Look through currentPageIndices for all the smallest elements. For each
	 * smallest element currentPageIndices[i], load the next element from
	 * pageIndexIterators[i]. If pageIndexIterators[i] does not have a next
	 * element, return false. Otherwise, return true.
	 * 
	 * @param currentPageIndices
	 *            array of current page indices
	 * @param pageIndexIterators
	 *            array of iterators with next page indices
	 * @return true if all minimum page indices updates, false otherwise
	 */
	private boolean updateSmallest(int[] currentPageIndices,
			Iterator<Integer>[] pageIndexIterators) {
		int smallest = Integer.MAX_VALUE;
		int smallestIndex = -1;

		for (int i = 0; i < currentPageIndices.length - 1; i++) {

			if (currentPageIndices[i] < currentPageIndices[i + 1]) {
				smallest = currentPageIndices[i];
				smallestIndex = i;
			} else if (currentPageIndices[i] > currentPageIndices[i + 1]) {
				smallest = currentPageIndices[i + 1];
				smallestIndex = i + 1;
			}
		}
		for (int k = 0; k < currentPageIndices.length; k++) {
			if (!pageIndexIterators[k].hasNext())
				return false;
			else if (currentPageIndices[k] <= smallest || smallestIndex == -1)
				currentPageIndices[k] = pageIndexIterators[k].next();
		}
		return true;

	}

	/**
	 * Check if all elements in an array are equal.
	 * 
	 * @param array
	 *            an array of numbers
	 * @return true if all are equal, false otherwise
	 */
	private boolean allEqual(int[] array) {
		int result = 0;
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i] != array[i + 1])
				return false;
		}
		return true;
	}

	public class URLComparator implements Comparator<Integer> {

		@Override
		/**
		 * Compares the reference counts of two URLs
		 * @param o1 the index of the first URL
		 * @param o2 the index of the second URL
		 */
		public int compare(Integer o1, Integer o2) {
			int ref1 = refCounts.get(o1);
			int ref2 = refCounts.get(o2);
			return ref1 - ref2;
		}

	}
}
