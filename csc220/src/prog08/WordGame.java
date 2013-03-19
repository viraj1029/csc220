package prog08;

import prog02.GUI;
import java.util.Scanner;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WordGame {
  private static class Node {
    String word;
    Node previous;
    
    Node (String word) {
      this.word = word;
    }
  }
  
  static GUI ui = new GUI();
  
  public static void main (String[] args) {
    WordGame game = new WordGame();
    game.loadDictionary(ui.getInfo("Enter dictionary file:"));
    
    String start = ui.getInfo("Enter starting word:");
    String target = ui.getInfo("Enter target word:");
    
    String[] commands = { "Computer plays.", "Human plays." };
    int c = ui.getCommand(commands);
    
    if (c == 1)
      game.play(start, target);
    else
      game.solve(start, target);
  }
  
  void play (String start, String target) {
    while (true) {
      ui.sendMessage("Current word: " + start + "\n" +
                     "Target word: " + target);
      String word = ui.getInfo("What is your next word?");
      if (find(word) == null)
        ui.sendMessage(word + " is not in the dictionary.");
      else if (!oneDegree(start, word))
        ui.sendMessage("Sorry, but " + word +
                       " differs by more than one letter from " + start);
      else if (word.equals(target)) {
        ui.sendMessage("You win!");
        return;
      }
      else
        start = word;
    }
  }    
  
  static boolean oneDegree (String snow, String slow) {
    if (snow.length() != slow.length())
      return false;
    int count = 0;
    for (int i = 0; i < snow.length(); i++)
      if (snow.charAt(i) != slow.charAt(i))
        count++;
    return count == 1;
  }
  
  List<Node> nodes = new ArrayList<Node>();
  
  void loadDictionary (String file) {
    try {
      Scanner in = new Scanner(new File(file));
      while (in.hasNextLine()) {
        String word = in.nextLine();
        Node node = new Node(word);
        nodes.add(node);
      }
    } catch (Exception e) {
      ui.sendMessage("Uh oh: " + e);
    }
  }
  
  Node find (String word) {
    for (int i = 0; i < nodes.size(); i++)
      if (word.equals(nodes.get(i).word))
        return nodes.get(i);
    return null;
  }
  
  void clearAllPrevious () {
    for (int i = 0; i < nodes.size(); i++)
      nodes.get(i).previous = null;
  }
  
  void solve (String start, String target) {
    clearAllPrevious();
    
    Queue<Node> queue = new Heap<Node>();
    
    Node startNode = find(start);
    queue.offer(startNode);
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      System.out.println("DEQUEUE: " + node.word);
      System.out.print("ENQUEUE:");
      for (int i = 0; i < nodes.size(); i++) {
        Node next = nodes.get(i);
        if (next != startNode &&
            next.previous == null &&
            oneDegree(node.word, next.word)) {
          next.previous = node;
          queue.offer(next);
          System.out.print(" " + next.word);
          if (next.word.equals(target)) {
            ui.sendMessage("Got to " + target + " from " + node.word);
            String s = node.word + "\n" + target;
            while (node != startNode) {
              node = node.previous;
              s = node.word + "\n" + s;
            }
            ui.sendMessage(s);
            return;
          }
        }
      }
      System.out.println();
    }
  }
   static class compNode implements Comparator<Node>
  {
	   String target = "";
	   compNode (String target)
	   {
		   this.target = target;
	   }

	@Override
	public int compare(Node arg0, Node arg1) { 
		int count0 = 0;
		int prevCount0 = 0;
		int prevCount1 = 0;
		int count1 = 0;
		if (arg0.word.equals(arg1.word))
		return 0;
	    for (int i = 0; i < arg0.word.length(); i++){
	        if (arg0.word.charAt(i) != target.charAt(i))
	          count0++;		
	    }
	    for (int i = 0; i < arg1.word.length(); i++){
	        if (arg1.word.charAt(i) != target.charAt(i))
	          count1++;		
	    }
	    for (Node node = arg0.previous; node.previous != null; node = node.previous ){
	    	prevCount0++;
	    }
	    for (Node node = arg1.previous; node.previous != null; node = node.previous ){
	    	prevCount1++;
	    }
	    if ((prevCount0 + count0) == (prevCount1 + count1))
	    		return 0;
	    else if ((prevCount0 + count0) < (prevCount1 + count1))
	    	return -1;
	    else
	    	return 1;
	}
  	  
  }

}
