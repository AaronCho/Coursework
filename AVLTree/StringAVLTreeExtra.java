class StringAVLTreeExtra extends StringAVLTree {

	public StringAVLTreeExtra() {
		super();
	}

	public boolean isEmpty(){
		return getRoot() == null;
	}

    String[][] makeGrid(int depth) {    
        int height = (int) (2 * (Math.pow(2, depth - 1) -1 + depth));
        int width = (int) Math.pow(2, depth) -1;
        String[][] rtn = new String[height][width];
            
        return rtn;
    }
    
     private String[][] subGrid(StringAVLNode subRt, int depth) {
   		StringAVLNodeXtra subRoot=StringAVLNodeXtra.upgrade(subRt);
   
        int height = (int)(2 * (Math.pow(2, depth-1) -1 + depth));
            
        int width = (int)Math.pow(2, depth) -1;
        int down = (int) (2 * (Math.pow(2, depth - 2)) + 2);
        String[][] grid = new String[height][width];
            
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = "   ";
            }
        }
            
        grid[0][width / 2] = subRoot.getFirstThreeChar();
        
        if (subRoot.getBalance() >= 1)
            grid[1][width / 2] = " +" + subRoot.getBalance();
        else if (subRoot.getBalance() == 0)
            grid[1][width / 2] = "  0";
        else if (subRoot.getBalance() <= -1)
            grid[1][width / 2] = " " + subRoot.getBalance();
            
        int numOfDash = (int) Math.pow(2, depth - 2);
        if (subRoot.getLeft() != null) {
            for (int i = 0;i < numOfDash; i++) {
                grid[2 * (numOfDash - i)][i + width / 4 + 1] = "/  ";
            }
        }
            
        if (subRoot.getRight() != null) {
            for (int i = 0; i < numOfDash; i++) {
                grid[2 *(i + 1)][i + width / 2 + 1] = " \\ ";
            }
        }
        if (subRoot.getLeft() != null) {
            String[][] tempLF = subGrid(subRoot.getLeft(), depth - 1);        
            for (int row = 0; row < tempLF.length; row++) {
                for (int col = 0; col < tempLF[0].length; col++) {
                    grid[row+down][col] = tempLF[row][col];
                }
            }
        }
        if (subRoot.getRight()!=null) {
            String[][] tempRT = subGrid(subRoot.getRight(), depth - 1);   
            for (int row = 0; row < tempRT.length; row++) {
                for (int col = 0; col < tempRT[0].length; col++) {
                    grid[row + down][col + 1 + width / 2] = tempRT[row][col];
                }
            }
        }
        return grid;
    }
     public String toString() {
    	StringAVLNodeXtra gR=StringAVLNodeXtra.upgrade(getRoot());
        String rtn = "";
        String[][] grid = subGrid(gR, gR.getDepth());

            
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                rtn += grid[row][col];
            }
            rtn += "\n";
        }
        return rtn;
    }

}
