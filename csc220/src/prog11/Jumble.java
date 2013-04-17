package prog11;

import java.io.File;
import java.util.Scanner;

import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;
import prog08.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;

public class Jumble {
	/**
	 * Sort the letters in a word.
	 * 
	 * @param word
	 *            Input word to be sorted, like "computer".
	 * @return Sorted version of word, like "cemoptru".
	 */
	public static String sort(String word) {
		char[] sorted = new char[word.length()];

		for (int i = 0; i < word.length(); i++)
			sorted[i] = word.charAt(i);

		Arrays.sort(sorted);

		return new String(sorted, 0, word.length());
	}

	public static void main(String[] args) {
		UserInterface ui = new GUI();
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		Scanner in = null;
		do {
			try {
				in = new Scanner(new File(ui.getInfo("Enter word file.")));
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Try again.");
			}
		} while (in == null);

		int n = 0;
		while (in.hasNextLine()) {
			ArrayList<String> wordList = new ArrayList<String>();
			String word = in.nextLine();
			if (n++ % 1000 == 0)
				System.out.println(word + " sorted is " + sort(word));
			if (map.get(sort(word)) == null) {
				wordList.add(word);
				map.put(sort(word), wordList);
			} else
				map.get(sort(word)).add(word);

			// EXERCISE: Insert an entry for word into map.
			// What is the key? What is the value?

		}
		// ((SkipList<String, String>) map).draw();

		while (true) {
			String jumble = ui.getInfo("Enter jumble.");
			if (jumble == null)
				return;
			// EXERCISE: Look up the jumble in the map.
			// What key do you use?
			ArrayList<String> word = null;
			word = map.get(sort(jumble));
			if (word == null)
				ui.sendMessage("No match for " + jumble);
			else
				ui.sendMessage(jumble + " unjumbled is " + word);
			String sorted = ui.getInfo("Enter clue letters");
			int length = Integer.parseInt(ui
					.getInfo("Enter the length of the first word"));
			sorted = sort(sorted);
			for (String sorted1 : map.keySet()) {
				// If we find a word that is cthe same length as our target word
				if (sorted1.length() == length) {
					// Create a new empty StringBuilder object
					StringBuilder sb = new StringBuilder("");
					int i = 0;
					int j = 0;
					// Loop through all the letters in our clue
					while (i < sorted.length()) {
						// j has to be less than sorted1.length, else we'll get
						// an indexOutOfBounds error
						if (j < sorted1.length()) {
							// if the character at this position equals the
							// character at a given position in j
							if (sorted.charAt(i) == sorted1.charAt(j)) {
							
								// increment j. This works because both words
								// are sorted, so if we find a letter at a
								// positions j&i, we know all of the next
								// letters will be greater than it in both
								// words.
								j++;
							} else {
								sb.append(sorted.charAt(i));

							}
						}
						// if we've matched every letter in sorted1 (j >=
						// sorted1.length) add the rest of the letters to the
						// StringBuilder object
						else {
							sb.append(sorted.charAt(i));
						}
						i++;
					

						

					}
					String sorted2 = sb.toString();
					// If our left over letters and our matched letters are the
					// same size as sorted AND SORTED2 IS IN THE DICTIONARY
					if (sorted.length() == sorted1.length() + sb.length()
							&& map.get(sorted2) != null) {
						System.out.println(sorted.length() + " "
								+ sorted1.length() + " " + sb.length());
						// tell the user what we found
						ui.sendMessage(map.get(sorted1) + " "
								+ map.get(sorted2));
					}

				}
			}

		}
	}
}
