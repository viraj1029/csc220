package prog12;

import java.util.List;

public interface Browser {
	/**Loads a given page and parses the contents of that page.
	 * 
	 * @param the URL of the page as a String.
	 * @return true if the page loads, false if the page does not exist.
	 */
    boolean loadPage (String url);
    /**Gets a list of all the words in a given webpage.
     * 
     * @return A list of words from a webpage.
     */
    List<String> getWords ();
    /**Gets a list of all the URLs on a webpage
     * 
     * @return A list of all the URLs from a webpage.
     */
    List<String> getURLs ();
}

