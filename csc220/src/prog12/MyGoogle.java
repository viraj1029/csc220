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
	private Map<String, Integer> url2Index = new TreeMap<String, Integer>();

	@Override
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub
		Set<Integer> refSet = new HashSet<Integer>();
		List<String> urls = new ArrayList<String>();
		List<String> words = new ArrayList<String>();
		Queue<String> urlQueue = new ArrayDeque<String>();
		if (startingURLs.size() > 0)
			//for (int i = 0; i < startingURLs.size(); i++)
		{//indexURL(startingURLs.get(i));
		for (int i = 0; i < startingURLs.size(); i++) {
			urlQueue.offer(startingURLs.get(i));
			indexURL(startingURLs.get(i));
		}}
		// for (String startURL : startingURLs)
		// urlQueue.offer(startURL);
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
				/*
				 * if (theList.size() < 1) theList.add(url2Index.get(url));
				 */
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
	 * URL as its key and its index in allURLs as its value Adds it to refCounts
	 * list with an initial * value of 0
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

	List<List<Integer>> urlIndexLists = new ArrayList<List<Integer>>();

	private void indexWord(String word) {
		allWords.add(word);
		word2Index.put(word, allWords.size() - 1);
		urlIndexLists.add(new ArrayList<Integer>());
	}

	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub
		// 1. search all webpages containing key words
		// 2. rank the candidate webpages by a priority queue according to
		// refcounts

		Iterator<Integer>[] pageIndexIterators = (Iterator<Integer>[]) new Iterator[keyWords
				.size()];
		int[] currentPageIndices = new int[keyWords.size()];
		for (int i = 0; i < keyWords.size(); i++) {
			pageIndexIterators[i] = urlIndexLists.get(
					word2Index.get(keyWords.get(i))).iterator();
			currentPageIndices[i] = urlIndexLists.get(
					word2Index.get(keyWords.get(i))).get(0);
		}
		// current page index in each list, just "behind" the iterator
		Stack<String> temp = new Stack<String>();
		Comparator comparator = new URLComparator();
		PriorityQueue<Integer> bestPageIndices = new PriorityQueue<Integer>(numResults,
				comparator);
		while (updateSmallest(currentPageIndices, pageIndexIterators)) {
			if (allEqual(currentPageIndices))
				bestPageIndices.offer(currentPageIndices[0]);
		}
		while (!bestPageIndices.isEmpty())
			temp.push(allURLs.get(bestPageIndices.poll()));
		String[] ret = new String[temp.size()];
		int i = 0;
		while (!temp.isEmpty()) {
			ret[i] = temp.pop();
			i++;
		}
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
			
			if (currentPageIndices[i] < currentPageIndices[i + 1])
			{
				smallest = currentPageIndices[i];
				smallestIndex = i;
			}else if (currentPageIndices[i] > currentPageIndices[i+1]){
				smallest = currentPageIndices[i + 1];
				smallestIndex = i+1;
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
		public int compare(Integer o1, Integer o2) {
			int ref1 = refCounts.get(o1);
			int ref2 = refCounts.get(o2);
			return ref1-ref2;
		}

	}
}
