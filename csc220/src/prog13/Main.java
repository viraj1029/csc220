package prog13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import prog02.GUI;
import prog02.UserInterface;

public class Main {
	static Map<String, Integer> allWords = new HashMap<String, Integer>();

	public static void main(String args[]) throws FileNotFoundException {

		UserInterface ui = new GUI();
		int numResults = Integer.parseInt(ui
				.getInfo("How many results would you like?"));
		Comparator<String> comparator = new Main.compareWords();
		PriorityQueue<String> commonWords = new PriorityQueue<String>(
				numResults, comparator);
		String fileName = ui.getInfo("Enter the File Name");
		Scanner scn = new Scanner(new File(fileName));
		while (scn.hasNext()) {
			String word = scn.next();
			if (allWords.get(word) == null)
				allWords.put(word, 1);
			else {
				int i = allWords.get(word);
				i++;
				allWords.put(word, i);
			}

		}
		for (String word : allWords.keySet()) {
			if (commonWords.size() < numResults)
				commonWords.offer(word);
			else if (allWords.get(commonWords.peek()) < allWords.get(word)) {
				commonWords.poll();
				commonWords.offer(word);
			}
		}
		Stack<String> temp = new Stack<String>();

		while (!commonWords.isEmpty()) {
			temp.push(commonWords.poll());
			
		}
while (!temp.isEmpty())
{
	
String word = temp.pop();
	System.out.println(word + " " + allWords.get(word));
}	}

	public static class compareWords implements Comparator<String> {
		@Override
		public int compare(String arg0, String arg1) {
			int num1 = allWords.get(arg0);
			int num2 = allWords.get(arg1);
			return num1 - num2;

		}

	}
}
