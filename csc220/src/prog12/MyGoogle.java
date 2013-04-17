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
	/**
	 * @param browser A browser object to gather from
	 * @param startingURLs a List of URLs to start gathering from
	 * Gathers relevant information from a webpage
	 * Indexes all of the words on that webpage
	 * Indexes all of the links on that webpage
	 * Updates the reference count for links on that webpage (max 1)
	 * Updates urlIndexList with a list of each webpage on which a given word appears
	 */
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub
		Set<Integer> refSet = new HashSet<Integer>();
		List<String> urls = new ArrayList<String>();
		List<String> words = new ArrayList<String>();
		Queue<String> urlQueue = new ArrayDeque<String>();
		if (startingURLs.size() > 0)
			indexURL(startingURLs.get(0));
		for (int i = 0; i < startingURLs.size(); i++) {
			urlQueue.offer(startingURLs.get(i));
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
				if (refSet.contains(index) == false)
				{					
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
				if (theList.size() < 1)
					theList.add(url2Index.get(url));
				if (theList.get(theList.size() - 1) != url2Index.get(url))
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
		return null;
	}

}
