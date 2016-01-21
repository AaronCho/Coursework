class StringAVLNodeXtra extends StringAVLNode {
	private String item;
	public StringAVLNodeXtra(String s) {
		super(s);
		item = s;
	}

	public final static StringAVLNodeXtra upgrade(StringAVLNode n) {
		StringAVLNodeXtra copy = new StringAVLNodeXtra(n.getItem());
		copy.setBalance(n.getBalance());
		copy.setLeft(n.getLeft());
		copy.setRight(n.getRight());
		return copy;
	}
	
    int getDepth() {
        return getDepth(this);
    }
        
    private static int getDepth(StringAVLNode nodeIn) {	
        int depth;
        if (nodeIn == null) depth = 0;
        else {
        	nodeIn = StringAVLNodeXtra.upgrade(nodeIn);
        	depth = 1 + Math.max(getDepth(nodeIn.getLeft()), getDepth(nodeIn.getRight()));
        }
        return depth;
    }
    
    // this will give the first three characters of a String, 
     //precedded by spaces if string is shorter than 3
    String getFirstThreeChar() {
        String rtn = "";
        if (getItem().length() >= 3) rtn = getItem().substring(0,3);
        else if (getItem().length() == 2) rtn = " " + getItem();
        else if (getItem().length() == 1) rtn = "  " + getItem();
        else if (getItem().length() == 0) rtn = "   ";
        return rtn;
    }


}
