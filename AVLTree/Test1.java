public class Test1 {

	public static void main(String[] args) {
		System.out.println("Instructions:");
		System.out.println("Press 'enter' after typing something.");
		System.out.println("Type a string (up to 3 non-whitespace characters long).");
		System.out.println("Type a '+' sign to start inserting.");
		System.out.println("Type a '-' sign to start deleting.");
		System.out.println("Type a '/' sign to toggle modes.");
		System.out.println("Type a '*' sign to end the program.");		
		System.out.println("For best results, please have the console use the full screen.");
		StringAVLTreeExtra t = new StringAVLTreeExtra();	
		Scanner input = new Scanner(System.in);
		String s = new String();
		boolean amInserting = true; //false if deleting
		String[] msg = {"Entered insertion mode.","Entered deletion mode."};
		do {
			s = input.next();
			if (s.equals("+")) {// enable insertion
				amInserting = true;
				System.out.println(msg[0]);
			}
			else if (s.equals("-")) {// enable deletion
				amInserting = false;
				System.out.println(msg[1]);
			}
			else if (s.equals("/")) {//toggle Modes
				amInserting = !amInserting;
				int n = amInserting?0:1;
				System.out.println(msg[n]);
			}
			else {
				if (amInserting) {
					t.insert(s);
				}
				else { 
					t.delete(s);
				}
				
				if (!t.isEmpty())
					System.out.println(t);
					System.out.println("balanced() returns:  " + t.balanced());
					System.out.println("height()   returns:  " + t.height());
					System.out.println("leafCt()   returns:  " + t.leafCt());

					//uncomment for testing successor.
					System.out.print("Find successor of: ");
					String a = input.next();
					System.out.println("successor("+a+") returns: " + t.successor(a));
				}
		} while (!s.equals("*"));
	}
}
