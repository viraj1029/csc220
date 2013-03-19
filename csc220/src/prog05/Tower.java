package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Tower {
  static UserInterface ui = new GUI();

  static public void main (String[] args) {
    int n = getInt("How many disks?");
    if (n <= 0)
      return;
    Tower tower = new Tower(n);

    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    if (c == 0)
      tower.play();
    else
      tower.solve();
  }

  /** Get an integer from the user using prompt as the request.
   *  Return 0 if user cancels or enters an empty string.  */
  static int getInt (String prompt) {
    while (true) {
      String number = ui.getInfo(prompt);
      if (number == null || number.length() == 0)
        return 0;
      try {
        return Integer.parseInt(number);
      } catch (Exception e) {
        ui.sendMessage(number + " is not a number.  Try again.");
      }
    }
  }

  int nDisks;
  StackInt<Integer>[] pegs = (StackInt<Integer>[]) new ArrayStack[3];

  Tower (int nDisks) {
    this.nDisks = nDisks;
    for (int i = 0; i < pegs.length; i++)
      pegs[i] = new ArrayStack<Integer>();
    for (int index = nDisks; index > 0; index--)
    	pegs[0].push(index);
    // EXERCISE: Initialize game with pile of nDisks disks on peg 'a'
    // which is pegs[0].
  }

  static String[] fromChoices = { "FROM a", "FROM b", "FROM c" };
  static String[] toChoices = { "TO a", "TO b", "TO c" };

  void play () {
    while (!(pegs[0].empty() && pegs[1].empty())) {
      displayPegs();
      int from = ui.getCommand(fromChoices);
      System.out.println(from);
      int to = ui.getCommand(toChoices);
      move(from, to);
    }

    ui.sendMessage("You win!");
  }

  String stackToString (StackInt<Integer> peg) {
  StackInt<Integer> helper = new ArrayStack<Integer>();
String s = "";
while (!peg.empty())
helper.push(peg.pop());
 while (!helper.empty())
 {
	 
	 s = (s + " " + helper.peek());	 
	 peg.push(helper.pop());
 }
 
    // String to append items to.

    // EXERCISE:  append the items in peg to s from bottom to top.*/

    return s;
  }

  void displayPegs () {
    String s = "";
    for (int i = 0; i < pegs.length; i++) {
      char abc = (char) ('a' + i);
      s = s + abc + ": " + stackToString(pegs[i]);
      if (i < pegs.length-1)
        s = s + "\n";
    }
    ui.sendMessage(s);
  }

  void move (int from, int to) {
Integer disk;
if (!pegs[from].empty()){
disk = new Integer(pegs[from].peek());
if (!pegs[to].empty() && disk > pegs[to].peek())
		ui.sendMessage("Cannot place " + disk + "on top of " + pegs[to].peek());
else
	
	pegs[to].push(pegs[from].pop());

// Don't do illegal moves.  Send a warning message instead, like:
    // Cannot place 2 on top of 1.
}
else
	ui.sendMessage("Cannot take disks from empty peg");
  }

  static String[] pegNames = { "a", "b", "c" };

  class Goal {
    int howMany, fromPeg, toPeg;

    Goal (int howMany, int from, int to) {
      this.howMany = howMany;
      this.fromPeg = from;
      this.toPeg = to;
    }

    public String toString () {
      return ("Move " + howMany + " disk(s) from " +
              pegNames[fromPeg] + " to " + pegNames[toPeg] + ".");
    }
  }

  // EXERCISE:  display contents of goal stack
  void displayGoals (StackInt<Goal> goals) {
	  String s = "";
	  StackInt<Goal> helps = new ArrayStack<Goal>();
	  while (!goals.empty())
		  helps.push(goals.pop());
		   while (!helps.empty())
		   {
		  	 
		  	 s = (helps.peek() + "\n" + s);	 
		  	 goals.push(helps.pop());
		   }	  
		   ui.sendMessage(s);
	  
  }

  void solve () {
	  	StackInt<Goal> goals = new ArrayStack<Goal>();
	  	StackInt<Integer> helper = new ArrayStack<Integer>();
	  	while (!pegs[0].empty())
	  	helper.push(pegs[0].pop());	  	  	
      	Goal theGoal = new Goal(helper.peek(), 0, 2);    
  		goals.push(theGoal);
  		while (!helper.empty())
  			pegs[0].push(helper.pop());
      	displayPegs();
  		while(!goals.empty())
  		{
  		displayGoals(goals);
  		if (goals.peek().howMany == 1)
  		{
  			move(goals.peek().fromPeg, goals.peek().toPeg);
  			displayPegs();
  		goals.pop();
  		}
  		else
  		{
  			theGoal = goals.pop();
  		  	goals.push(new Goal(theGoal.howMany-1, 3-(theGoal.toPeg+theGoal.fromPeg), theGoal.toPeg));
  		  	goals.push(new Goal(theGoal.howMany-(theGoal.howMany-1), theGoal.fromPeg, theGoal.toPeg));
  		  	goals.push(new Goal(theGoal.howMany-1, theGoal.fromPeg, 3-(theGoal.toPeg+theGoal.fromPeg)));


  		}
  		}
  		
  	

}        
    		
    	

  }        