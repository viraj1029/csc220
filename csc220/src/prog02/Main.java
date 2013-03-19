package prog02;

/**
 * 
 * @author vjm
 */
public class Main {

	/**
	 * Processes user's commands on a phone directory.
	 * 
	 * @param fn
	 *            The file containing the phone directory.
	 * @param ui
	 *            The UserInterface object to use to talk to the user.
	 * @param pd
	 *            The PhoneDirectory object to use to process the phone
	 *            directory.
	 */
	public static void processCommands(String fn, UserInterface ui,
			PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = { "Add/Change Entry", "Look Up Entry",
				"Remove Entry", "Save Directory", "Exit" };

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case 0:
				name = ui.getInfo("Enter name:");
				if(name == null || name.equals("")){
					break;
				}
				else {
				//do nothing	
				}
				number  = ui.getInfo("Enter number:");
				if(number == null){
					break;
				}
				else {
				}
				oldNumber= pd.addOrChangeEntry(name, number);
				if (oldNumber == null){
					ui.sendMessage(String.format("%s was added to the directory\n New number: %s",name, number));
				}
				else{
					ui.sendMessage(String.format("Number for %s was changed\nOld Number: %s\nNew Number: %s",name,oldNumber,number));
				}
				// implement
				break;
			case 1:
				name = ui.getInfo("Enter name:");
				if(name == null || name.equals("")){
					break;
				}
					else{
					}
				number = pd.lookupEntry(name);
				if (number == null){
					ui.sendMessage(String.format("%s is not listed in the directory",name));
					break;
				}
				else{
				}
				ui.sendMessage(String.format("The Number for %s is %s",name,number));
				// implement
				break;
			case 2:
				name = ui.getInfo("Enter name");
				if(name == null || name.equals("")){
					break;
				}
				else{
				}
				number = pd.removeEntry(name);
				if (number == null){
					ui.sendMessage(String.format("%s is not in the database",name));
					break;
				}
				else{
				}
				ui.sendMessage(String.format("%s has been removed from the database",name));
				break;
			case 3:
				pd.save();
				break;
			case 4:
				// implement
				return;
			}
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI();
		processCommands(fn, ui, pd);
	}
}
