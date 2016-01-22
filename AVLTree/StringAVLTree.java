class StringAVLTree {
	private StringAVLNode root;
 
	// the one and only constructor
	public StringAVLTree() {
		root = null;
	}

	// this is here to make it easier for me to write a test
	// program – you would never do this in real life!
	public StringAVLNode getRoot() {
		return root;
	}
	 
	// Rotate the node to the right
	private static StringAVLNode rotateRight(StringAVLNode t) {
		try {
			
			if (t.getLeft() == null) {
				t.setLeft(t.getLeft().getRight());
			}
			else {
				StringAVLNode temp = t.getLeft();
				StringAVLNode temp2 = t.getLeft().getRight();
				temp.setRight(t);
				t.setLeft(temp2);
				t = temp;
			}
		}
		catch (Exception e) {
			System.out.println("Failed Rotate Right");
		}		
		
		return t;
	}

	// Rotate the node to the left
	private static StringAVLNode rotateLeft(StringAVLNode t) {
		try {
			
			if (t.getRight() == null) {
				t.setRight(t.getRight().getLeft());
			}
			else {
				StringAVLNode temp = t.getRight();
				StringAVLNode temp2 = t.getRight().getLeft();
				
				temp.setLeft(t);
				t.setRight(temp2);
				t = temp;
			}
		}
		catch (Exception e) {
			System.out.println("Failed Rotate Left");
		}		
		
		return t;
	}
	
	// For these next four, be sure not to use any global variables
	// Return the height of the tree – not to be used anywhere in insert or delete
	public int height() {
		return height(root);
	}
	
	private static int height(StringAVLNode pt) {
		int result;
		
		if (pt == null) {
			result = 0;
		}
		else {
			if (height(pt.getLeft()) > height(pt.getRight())) {
				result = height(pt.getLeft()) + 1;
			}
			else {
				result = height(pt.getRight()) + 1;
			}
		}
		
		return result;
	}
	
	// Return the number of leaves in the tree
	public int leafCt() {
		return leafCt(root);
	}
	
	private static int leafCt(StringAVLNode pt) {
		int leafCount;
		
		if (pt == null) {
			leafCount = 0;
		}
		else if (pt.getLeft() == null && pt.getRight() == null) {
			leafCount = 1;
		}
		else {
			leafCount = leafCt(pt.getLeft()) + leafCt(pt.getRight());
		}
		
		return leafCount;
	}
	
	// Return the number of perfectly balanced AVL nodes
	 public int balanced() {
		 return balanced(root);
	 }
	 
	 private static int balanced(StringAVLNode pt) {
		 int balancedCount;
		 
		 if (pt == null) {
			 balancedCount = 0;
		 }
		 else if (pt.getBalance() == 0){
			 balancedCount = 1 + balanced(pt.getLeft()) + balanced(pt.getRight());
		 }
		 else {
			 balancedCount = balanced(pt.getLeft()) + balanced(pt.getRight());
		 }
		 
		 return balancedCount;
	 }
	
	// Return the inorder successor or null if there is none or str is not in the tree
	public String successor(String str) {
		return successor(str, root);
	}
	
	private static String successor(String str, StringAVLNode t) {
		String succ;
		StringAVLNode nextRight;
		StringAVLNode nextLeft;
		
		if (t == null) {
			succ = null;
		}
		else if (str.compareToIgnoreCase(t.getItem()) < 0) {
			if (t.getLeft() == null) {
				succ = null;
			}
			succ = successor(str, t.getLeft());
		}
		else if (str.compareToIgnoreCase(t.getItem()) > 0) {
			if (t.getRight() == null) {
				succ = null;
			}
			succ = successor(str, t.getRight());
		}
		else { //if str is found in the tree
			if (t.getRight() == null) {
				succ = null; 
			}
			else {
				nextRight = t.getRight();
				if (nextRight.getRight() == null && nextRight.getLeft() == null) {
					succ = nextRight.getItem();
				}
				else {
					nextLeft = nextRight.getLeft();
					succ = successor2(nextLeft);
				}
			}
		}
		
		return succ;
	}
	
	//successor2 is used to keep going left in the tree after the first right node.
	private static String successor2(StringAVLNode nextLeft) {
		String succ;
		
		if (nextLeft.getLeft() == null) {
			succ = nextLeft.getItem();
		}
		else {
			succ = successor2(nextLeft.getLeft());
		}
		
		return succ;
	}
	
	public void insert(String str) {
		root = insert(str, root);
	}
	
	private StringAVLNode insert(String str, StringAVLNode t) {
		int oldBalance, newBalance;
		
		if (t == null) { // easiest case – inserted node goes here
			t = new StringAVLNode(str);
		}
		else if (t.getItem() == str) { // already in the tree – do nothing

		}
		else if (str.compareToIgnoreCase(t.getItem()) < 0){ 
			// str is smaller than this node – go left
			// get the old balance of the left child (where the insertion
			// is taking place)
			//oldBalance is the balance of the right/left subtree
			if (t.getLeft() == null) {
				oldBalance = -3;//flag value
				t.setBalance(t.getBalance() - 1);
			}
			else { 
				oldBalance = t.getLeft().getBalance(); 
			}
			
			t.setLeft(insert(str, t.getLeft()));	
			newBalance = t.getLeft().getBalance();	 
				
			if (oldBalance == 0 && newBalance != 0) { // did the height increase?
				t.setBalance(t.getBalance() - 1);
			}

			if (t.getBalance() == -2) { // out of balance – must rotate
				if (t.getLeft().getBalance() == -1) { 
					// single rotation and balance update
					//the return value of rotateRight(t) is the new root after the rotation	
					t = rotateRight(t);  	
					//The new root and its right child always has a balance of 0 after a rotate right.
					t.setBalance(0);
					t.getRight().setBalance(0);
				}
				else { // double rotation and balance update
					   // once you get it right here, basically the
					   // same code can be used in other places including delete
					
					//Check if it is a x insert or a y insert.  Update balances accordingly.
					if (t.getLeft().getRight().getBalance() == -1) {
						t.setLeft(rotateLeft(t.getLeft()));
						t = rotateRight(t);
						t.setBalance(0);
						t.getLeft().setBalance(0);
						t.getRight().setBalance(1);
					}
					else if (t.getLeft().getRight().getBalance() == 1){
						t.setLeft(rotateLeft(t.getLeft()));
						t = rotateRight(t);
						t.setBalance(0);
						t.getLeft().setBalance(-1);
						t.getRight().setBalance(0);
					}
					else {
						//t.getLeft().getRight().getBalance() == 0
						t.setLeft(rotateLeft(t.getLeft()));
						t = rotateRight(t);
						t.setBalance(0);
						t.getLeft().setBalance(0);
						t.getRight().setBalance(0);
					}
				}
			}	
		}
		//Cannot be just else because this allows duplicates in the tree.
		else if (str.compareToIgnoreCase(t.getItem()) > 0){ // str is bigger than this node.  
			if (t.getRight() == null) {
				oldBalance = 3;//flag value
				t.setBalance(t.getBalance() + 1);
			}
			else {
				oldBalance = t.getRight().getBalance(); 
			}
			t.setRight(insert(str, t.getRight()));
			newBalance = t.getRight().getBalance(); 
				
			if (oldBalance == 0 && newBalance != 0) {
				t.setBalance(t.getBalance() + 1);
			}
				
			if (t.getBalance() == 2) {
				if (t.getRight().getBalance() == 1) {
					//the return value of rotateLeft(t) is the new root after the rotation	
					t = rotateLeft(t);  
					t.setBalance(0);
					t.getLeft().setBalance(0);
				}
				else {
					//Check if it is a x insert or a y insert.  Update balances accordingly.
					if (t.getRight().getLeft().getBalance() == -1) {
						t.setRight(rotateRight(t.getRight()));
						t = rotateLeft(t);
						t.setBalance(0);
						t.getLeft().setBalance(0);
						t.getRight().setBalance(1);
					}
					else if (t.getRight().getLeft().getBalance() == 1) {
						t.setRight(rotateRight(t.getRight()));
						t = rotateLeft(t);
						t.setBalance(0);
						t.getLeft().setBalance(-1);
						t.getRight().setBalance(0);
					}
					else {//t.getRight().getLeft().getBalance() == 0
						t.setRight(rotateRight(t.getRight()));
						t = rotateLeft(t);
						t.setBalance(0);
						t.getLeft().setBalance(0);
						t.getRight().setBalance(0);	
					}		
				}
			}
		}
		return t;
	}
		
 	
	public void delete(String str) {
		root = delete(root, str);
	}

	private StringAVLNode delete(StringAVLNode t, String str) {
		int oldBalance, newBalance;
		
		if (t == null){ // Do nothing if it is not in the tree
			
		}
		else if (str.compareToIgnoreCase(t.getItem()) < 0) { // get the old balance.
			
			if (t.getLeft() == null) {
				// still must deal with this special case in case
				// the element to be deleted is not in the tree
				oldBalance = 5;//flag value
			}	
			else {
				oldBalance = t.getLeft().getBalance(); 
				t.setLeft(delete(t.getLeft(), str));
			}
			
			// get the new balance
			if (t.getLeft() == null) {
				newBalance = 1;//flag value
				if (oldBalance != 5) {
					t.setBalance(t.getBalance() + 1);
				}	
			}
			else {
				newBalance = t.getLeft().getBalance();	 
			}
			
			if (oldBalance != 0 && newBalance == 0) { // did the height decrease?
				// correct the balance
				t.setBalance(t.getBalance() + 1);
			}
			
			if (t.getBalance() == 2) {// need to rotate?
			// there are now actually 3 cases because t.getRight.getBalance()
			// could be -1, 0, or 1.
				if (t.getRight().getBalance() == 1) {//single rotation left
					t = rotateLeft(t);  
					t.setBalance(0);
					t.getLeft().setBalance(0);
				}
				else if (t.getRight().getBalance() == 0) { //single rotation left
					if (t.getRight().getLeft() == null && t.getRight().getRight() == null) {
						t = rotateLeft(t);  
						t.setBalance(0);
						t.getLeft().setBalance(0);
					}
					else {
						t = rotateLeft(t);
						t.setBalance(-1);
						t.getLeft().setBalance(1);
					}
				}
				else { //t.getRight().getBalance() == -1, double rotation case
					if (t.getRight().getLeft().getBalance() == -1) {
						t.setRight(rotateRight(t.getRight()));
						t = rotateLeft(t);
						t.setBalance(0);
						t.getLeft().setBalance(0);
						t.getRight().setBalance(1);
					}
					else if (t.getRight().getLeft().getBalance() == 1) {
						t.setRight(rotateRight(t.getRight()));
						t = rotateLeft(t);
						t.setBalance(0);
						t.getLeft().setBalance(-1);
						t.getRight().setBalance(0);
					}
					else {//t.getRight().getLeft().getBalance() == 0
						t.setRight(rotateRight(t.getRight()));
						t = rotateLeft(t);
						t.setBalance(0);
						t.getLeft().setBalance(0);
						t.getRight().setBalance(0);	
					}
				}
			}
		}
		else if (str.compareToIgnoreCase(t.getItem()) > 0) {
			
			if (t.getRight() == null) {
				// still must deal with this special case in case
				// the element to be deleted is not in the tree
				oldBalance = 5;//flag value
			}	
			else {
				oldBalance = t.getRight().getBalance();  
				t.setRight(delete(t.getRight(), str));
			}
			
			// get the new balance
			if (t.getRight() == null) {
				newBalance = -3;//flag value	
				if (oldBalance != 5) {
					t.setBalance(t.getBalance() - 1);
				}
			}
			else {
				newBalance = t.getRight().getBalance();	 	
			}
			
			if (oldBalance != 0 && newBalance == 0) { // did the height decrease?
				// correct the balance
				t.setBalance(t.getBalance() - 1);
			}
			
			if (t.getBalance() == -2) {// need to rotate?
			// there are now actually 3 cases because t.getLeft.getBalance()
			// could be -1, 0, or 1.
				if (t.getLeft().getBalance() == -1) {//single rotation right
					t = rotateRight(t);  
					t.setBalance(0);
					t.getRight().setBalance(0);
				}
				else if (t.getLeft().getBalance() == 0) {//single rotation right
					if (t.getLeft().getLeft() == null && t.getLeft().getRight() == null) {
						t = rotateRight(t);  
						t.setBalance(0);
						t.getRight().setBalance(0);
					}
					else {
						t = rotateRight(t);
						t.setBalance(1);
						t.getRight().setBalance(-1);
					}
				}
				else { //t.getLeft().getBalance() == 1, double rotation case
					//Check if it is a x insert or a y insert.  Update balances accordingly.
					if (t.getLeft().getRight().getBalance() == -1) {
						t.setLeft(rotateLeft(t.getLeft()));
						t = rotateRight(t);
						t.setBalance(0);
						t.getLeft().setBalance(0);
						t.getRight().setBalance(1);
					}
					else if (t.getLeft().getRight().getBalance() == 1){
						t.setLeft(rotateLeft(t.getLeft()));
						t = rotateRight(t);
						t.setBalance(0);
						t.getLeft().setBalance(-1);
						t.getRight().setBalance(0);
					}
					else {//t.getLeft().getRight().getBalance() == 0
						t.setLeft(rotateLeft(t.getLeft()));
						t = rotateRight(t);
						t.setBalance(0);
						t.getLeft().setBalance(0);
						t.getRight().setBalance(0);
					}
				}
			}	
		}
		else { // t is the node to be deleted
			if (t.getLeft() == null) { // one of the easy cases
				//Replace t with right subtree
				t = t.getRight();
				
			}
			else if (t.getRight() == null) { // the other easy case
				//Replace t with left subtree
				t = t.getLeft();
			}
			else {
				// get the old balance
				// this is getLeft because we are using the inorder predecessor for replace
				
				//nullPrev is to check for the special case when prev is null and 
				//update the balance of the replaced node properly.
				boolean nullPrev = false;
				int tBalance = 0;
				if (t.getLeft() == null) {
					oldBalance = 5;//flag value
				}	
				else {
					oldBalance = t.getLeft().getBalance(); 
				}
				
				if (t.getLeft().getRight() == null) {
					nullPrev = true;
					tBalance = t.getBalance();
				}
				
				// find the replacement node and move it up									
				t = replace(t, null, t.getLeft()); 
									
				// get the new balance
				if (t.getLeft() == null) {
					newBalance = 3;//flag value
					t.setBalance(t.getBalance() + 1);
				}
				else {
					newBalance = t.getLeft().getBalance(); 
				}
				
				// just like before, see if height decrease and if so
			    // check to see if only need to change balance values or rotate
																			
				if (oldBalance != 0 && newBalance == 0) { //if height decreased, correct the balance
					t.setBalance(t.getBalance() + 1); 
				}
				
				if (nullPrev) {
					t.setBalance(tBalance + 1);
				}
				
				if (t.getBalance() == 2) {// need to rotate?
					// there are now actually 3 cases because t.getRight.getBalance()
					// could be -1, 0, or 1.
						if (t.getRight().getBalance() == 1) {//single rotation left
							t = rotateLeft(t);  
							t.setBalance(0);
							t.getLeft().setBalance(0);
						}
						else if (t.getRight().getBalance() == 0) { //single rotation left
							if (t.getRight().getLeft() == null && t.getRight().getRight() == null) {
								t = rotateLeft(t);  
								t.setBalance(0);
								t.getLeft().setBalance(0);
							}
							else {
								t = rotateLeft(t);
								t.setBalance(-1);
								t.getLeft().setBalance(1);
							}
						}
						else { //t.getRight().getBalance() == -1, double rotation case
							if (t.getRight().getLeft().getBalance() == -1) {
								t.setRight(rotateRight(t.getRight()));
								t = rotateLeft(t);
								t.setBalance(0);
								t.getLeft().setBalance(0);
								t.getRight().setBalance(1);
							}
							else if (t.getRight().getLeft().getBalance() == 1) {
								t.setRight(rotateRight(t.getRight()));
								t = rotateLeft(t);
								t.setBalance(0);
								t.getLeft().setBalance(-1);
								t.getRight().setBalance(0);
							}
							else {//t.getRight().getLeft().getBalance() == 0
								t.setRight(rotateRight(t.getRight()));
								t = rotateLeft(t);
								t.setBalance(0);
								t.getLeft().setBalance(0);
								t.getRight().setBalance(0);	
							}
						}
				}
			}
		}
		
		return t;
	}
	
	// The code to find and replace the node being deleted must be recursive
    // so that we have easy access to the nodes that might have balance changes
	
	private StringAVLNode replace(StringAVLNode t, StringAVLNode prev, StringAVLNode replacement) {
		int oldBalance, newBalance;
		if (replacement.getRight() == null) { 
			// at the node that will replace the deleted node
			// move the replacement node – Recall there is no setVal
			if (prev != null) { 
				prev.setRight(replacement.getLeft());
				replacement.setLeft(t.getLeft());
				replacement.setRight(t.getRight());
				
				if (replacement.getBalance() != t.getBalance()) {
					replacement.setBalance(t.getBalance());
				}
			}
			
			replacement.setRight(t.getRight());
			t = replacement;
		}
		else {
			// find the old balance
			if (replacement.getRight() == null) {
				oldBalance = 5;
			}
			else {
				oldBalance = replacement.getRight().getBalance(); 
			}
			t = replace(t, replacement, replacement.getRight());
			
			// find the new balance
			if (replacement.getRight() == null) {
				newBalance = 3;//flag value
				replacement.setBalance(replacement.getBalance() - 1);
			}
			else {
				newBalance = replacement.getRight().getBalance(); 
			}
			
			// update balance and rotate if needed
						
			if (oldBalance != 0 && newBalance == 0) { //if height decreased, correct the balance
				replacement.setBalance(replacement.getBalance() - 1);
			}
			
			
			if (replacement.getBalance() == -2) {// need to rotate?
				// there are now actually 3 cases because replacement.getLeft.getBalance()
				// could be -1, 0, or 1.
					if (replacement.getLeft().getBalance() == -1) {//single rotation right
						replacement = rotateRight(replacement);  
						replacement.setBalance(0);
						replacement.getRight().setBalance(0);
						prev.setRight(replacement);
					}
					else if (replacement.getLeft().getBalance() == 0) {//single rotation right
						if (replacement.getLeft().getLeft() == null 
								&& replacement.getLeft().getRight() == null) {
							replacement = rotateRight(replacement);  
							replacement.setBalance(0);
							replacement.getRight().setBalance(0);
							prev.setRight(replacement);
						}
						else {
							replacement = rotateRight(replacement); 
							replacement.setBalance(1);
							replacement.getRight().setBalance(-1);
							prev.setRight(replacement);
						}
					}
					else {  //replacement.getLeft().getBalance() == 1, double rotation case
						//Check if it is a x insert or a y insert.  Update balances accordingly.
						if (replacement.getLeft().getRight().getBalance() == -1) {
							replacement.setLeft(rotateLeft(replacement.getLeft()));
							replacement = rotateRight(replacement);
							replacement.setBalance(0);
							replacement.getLeft().setBalance(0);
							replacement.getRight().setBalance(1);
							prev.setRight(replacement);
						}
						else if (replacement.getLeft().getRight().getBalance() == 1){
							replacement.setLeft(rotateLeft(replacement.getLeft()));
							replacement = rotateRight(replacement);
							replacement.setBalance(0);
							replacement.getLeft().setBalance(-1);
							replacement.getRight().setBalance(0);
							prev.setRight(replacement);
						}
						else {//t.getLeft().getRight().getBalance() == 0
							replacement.setLeft(rotateLeft(replacement.getLeft()));
							replacement = rotateRight(replacement);
							replacement.setBalance(0);
							replacement.getLeft().setBalance(0);
							replacement.getRight().setBalance(0);
							prev.setRight(replacement);
						}
					}
			}
			
	}
			return t;
		
			
}
	
	public String toString() {		
		String s = new String();
		s = paren_out1(getRoot())+bal_out1(getRoot())+String.valueOf(this.leafCt())+" "+
		    String.valueOf(this.height())+" "+String.valueOf(this.balanced());
		return s;
	}
	
	public String paren_out1(StringAVLNode t) {
		String s;
		if (t == null) {
			s = new String();
		} else {
			s="(" + paren_out1(t.getLeft())+t.getItem()+paren_out1(t.getRight())+")";
		}
		return s;
	}

	public String bal_out1(StringAVLNode t) {
		String s;
		if (t == null) {
			s = new String();
		} else {
			s="("+bal_out1(t.getLeft())+t.getBalance()+bal_out1(t.getRight())+")";
		}
		return s;
	}
	
	// who are you? Put your name here!
	public static String myName() {
		return "Aaron Cho";
	}
} // end of StringAVLTree class
