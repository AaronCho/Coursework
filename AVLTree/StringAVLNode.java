class StringAVLNode {
	private String item;
	private int balance;
	private StringAVLNode left, right;
	
	public StringAVLNode(String str) {
		item = str;
		left = null;
		right = null;
		balance = 0;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int bal) {
		this.balance = bal;
	}

	public String getItem() {
		return item;
	}

	public StringAVLNode getLeft() {
		return left;
	}

	public void setLeft(StringAVLNode pt) {
		this.left = pt;
	}
	
	public StringAVLNode getRight() {
		return right;
	}

	public void setRight(StringAVLNode pt) {
		this.right = pt;
	}
} // StringAVLNode
