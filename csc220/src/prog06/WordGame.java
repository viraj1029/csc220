package prog06;

import prog02.GUI;
import prog02.UserInterface;
import java.util.*;
import java.io.*;

public class WordGame {
	static UserInterface ui = new GUI();
	static Scanner in;
	List<Node> nodes = new ArrayList<Node>();

	public static void main(String[] args) {
		WordGame game = new WordGame();
		String s = ui.getInfo("Please enter the File Name:");
		game.loadDictionary(s);
		Node found = null;
		String startWord = "";
		String targetWord = "";
		do {
			startWord = ui.getInfo("Enter starting word");
			targetWord = ui.getInfo("Enter target word");
			found = game.find(targetWord);
			if (found == null)
				ui.sendMessage("That word is not in the dictionary");
		} while (found == null);

		String[] commands = { "Human plays.", "Computer plays." };
		int c = ui.getCommand(commands);
		if (c == 0)
			game.play(startWord, targetWord);
		else
			game.solve(startWord, targetWord);

	}

	void loadDictionary(String fileName) {
		try {
			// Create a Scanner for the file.
			Scanner in = new Scanner(new File(fileName));
			Node currentNode = null;
			// Read each name and number and add the entry to the array.
			while (in.hasNextLine()) {
				String word = in.nextLine();
				currentNode = new Node(word, null);
				// Add an entry for this name and number.
				nodes.add(currentNode);
			}
			// Close the file.
			in.close();
		} catch (FileNotFoundException ex) {
			// Do nothing: no data to load.
			return;
		} catch (Exception ex) {
			System.err.println("Load of directory failed.");
			ex.printStackTrace();
			System.exit(1);
		}
	}

	void play(String current, String target) {
		while (true) {
			ui.sendMessage("Current: " + current + "\nTarget: " + target);
			String s = ui.getInfo("Enter the next word");
			if (oneDegree(s, current)) {
				current = s;
			} else {
				ui.sendMessage("Invalid word");
			}
			if (current.equals(target)) {
				ui.sendMessage("You win!");
				return;
			}

		}
	}

	static boolean oneDegree(String firstWord, String secondWord) {
		if (firstWord.length() != secondWord.length())
			return false;
		int count = 0;
		for (int i = 0; i < firstWord.length(); i++)
			count += firstWord.charAt(i) != secondWord.charAt(i) ? 1 : 0;
		return count == 1;

	}

	Node find(String theWord) {
		Node help;
		Iterator<Node> i = nodes.iterator();
		while (i.hasNext()) {
			help = i.next();
			if (help.word.equals(theWord)) {
				return help;
			}
		}
		return null;
	}

	void solve(String startWord, String targetWord) {
		Queue<Node> nodeQueue = new ArrayQueue<Node>();
		Node startNode = find(startWord);
		Node nextNode;
		nodeQueue.offer(startNode);
		/*
		 * { startNode = new Node(helpNode.word, null);
		 * nodeQueue.offer(startNode); } }
		 */
		while (!nodeQueue.isEmpty()) {
			Node currentNode = nodeQueue.poll();
			for (Node theNode : nodes) {
				if (!theNode.word.equals(startNode.word)
						&& theNode.previous == null
						&& oneDegree(theNode.word, currentNode.word)) {

					nextNode = new Node(theNode.word, currentNode);
					nodeQueue.offer(nextNode);
					if (nextNode.word.equals(targetWord)) {
						ui.sendMessage("You win!");
						String s = "";
						Node printNode = nextNode;
						while (printNode != null) {
							s = (printNode.word + " " + s);
							printNode = printNode.previous;
							/*
							 * for (Node resultNode : nodeQueue){ { s += (" " +
							 * resultNode.word); }
							 */

						}
						ui.sendMessage(s);

						return;

					}

					// for (Node nextNode : nodeQueue)

				}
			}
		}

	}

	public static class Node {
		String word;
		Node previous;

		public Node(String word, Node previous) {
			this.word = word;
			this.previous = previous;
		}

		public Node(Node theNode) {
			this.previous = theNode.previous;
			this.word = theNode.word;
		}

		public Node getPrevious() {
			return this.previous;
		}

		public String getWord() {
			return this.word;
		}

	}

}
