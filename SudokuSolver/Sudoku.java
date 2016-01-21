class Sudoku {
	private int board[][];
	
	public Sudoku() {
		
	}
	
	// Construct a new sudoku puzzle from a string
	public Sudoku(String s[]) {
		board = new int[9][9];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				board[row][col] = (int)(s[row].charAt(col + col/3)) - '0';
			}
		}	
	}
	
	//Copy constructor
	public Sudoku(Sudoku p) {
		board = new int[9][9];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				board[row][col] = p.board[row][col];
			}
		}
	}
	
	public String toString() {
	  String boardString = "";
		
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				if (col == 2 || col == 5) {
					boardString += String.valueOf(board[row][col] + " | ");
				}
				else if (col == 8){
					boardString += String.valueOf(board[row][col] + "\n");
				}
				else {
					boardString += String.valueOf(board[row][col]);
				}
				
				if (((row == 2 && col == 8) || (row == 5 && col == 8))) {
					boardString += ("---------------\n");
				}
			}
		}
		
		return boardString;
	}
	
	// for easy checking of your answers
	public String toString2() {
		String result = new String();
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				result = result + String.valueOf(board[row][col]);
			}
		}
	
		return result;
	}
	
	// create rotated sudoku puzzle – used by test programs
    public void rotate() {
    	int[][] temp = new int[9][9];
    	int row, col;
    
    	for (row = 0; row < 9; row++) {
    		for (col = 0; col < 9; col++) {
    			temp[col][8-row] = board[row][col];
    		}
    	}
    	for (row = 0; row < 9; row++) {
    		for (col = 0; col < 9; col++) {
    			board[row][col] = temp[row][col];
    		}
    	}
	
	}
    
	// Does the current board satisfy all the sudoku rules?
	public boolean isValid() {
		boolean isValidResult = true;
		//A counter array that counts the recurrence of a digit in a row, column, or a box. 
		int counterArray[] = new int[10];
				
		for (int digit = 0; digit < counterArray.length; digit++) {
			counterArray[digit] = 0;
		}
		
		//Check all rows for duplicates
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				//Loop through the counterArray and increment the counter for the digit. 
				//Exclude 0s because there can be multiple 0s.   
				for (int digit = 1; digit < counterArray.length; digit++) {
					if (board[row][col] == digit) {
						counterArray[digit]++;
					}
				}
			}
			
			//At this point, 1 row has been traversed.  
			//Check counters 1-9. If counter is greater than 1, isValidResult is false.
			//Reset all of the counters to 0.
			for (int digit = 1; digit < counterArray.length; digit++) {
				if (counterArray[digit] > 1) {
					isValidResult = false;
				}
				counterArray[digit] = 0;
			}
		}
		
		//Check all columns for duplicates
		for (int col = 0; col < board.length; col++) {
			for (int row = 0; row < board.length; row++) {
				//Loop through the counterArray and increment the counter for the digit. 
				//Exclude 0s because there can be multiple 0s.   
				for (int digit = 1; digit < counterArray.length; digit++) {
					if (board[row][col] == digit) {
						counterArray[digit]++;
					}
				}
			}
			
			//At this point, 1 column has been traversed.  
			//Check counters 1-9. If a counter is greater than 1, isValidResult is false.
			//Reset all of the counters to 0.
			for (int digit = 1; digit < counterArray.length; digit++) {
				if (counterArray[digit] > 1) {
					isValidResult = false;
				}
				counterArray[digit] = 0;
			}
		}
		
		//Check all boxes for duplicates
		for (int row = 0; row < 7; row += 3) {
			for (int col = 0; col < 7; col += 3) {
				//Traverse within a box
				for (int boxRow = row; boxRow < row + 3; boxRow++) {
					for (int boxCol = col; boxCol < col + 3; boxCol++) {
						//Increment the counter for the digit.
						for (int digit = 1; digit < counterArray.length; digit++) {
							if (board[boxRow][boxCol] == digit) {
								counterArray[digit]++;
							}
						}
					}
				}
				
				//At this point, 1 box has been traversed.  
				//Check counters 1-9. If a counter is greater than 1, isValidResult is false.
				//Reset all of the counters to 0.
				for (int digit = 1; digit < counterArray.length; digit++) {
					if (counterArray[digit] > 1) {
						isValidResult = false;
					}
					counterArray[digit] = 0;
				}
			}
		}
		
		return isValidResult;
	}
	
	// Is this a solved sudoku?
	public boolean isComplete() {
		boolean isCompleteResult = true;
		
		if (!isValid()) {
			isCompleteResult = false;
		}
		
		//Traverse the board and if a 0 is found, isCompleteResult is changed to false.
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				if (board[row][col] == 0) {
					isCompleteResult = false;
				}
			}
		}
		return isCompleteResult;
	}
	
	// return true if val appears in the row of the puzzle
	private boolean doesRowContain(int row, int val) {
		boolean rowContains = false;
		
		for (int col = 0; col < board.length; col++) {
			if (board[row][col] == val)
				rowContains = true;
		}
		
		return rowContains;
	}
	
	// return true if val appears in the col (column) of the puzzle
	private boolean doesColContain(int col, int val) {
		boolean colContains = false;
		
		for (int row = 0; row < board.length; row++) {
			if (board[row][col] == val)
				colContains = true;
		}
		
		return colContains;
	}
	
	// return true if val appears in the 3 x 3 box
	private boolean doesBoxContain(int row, int col, int val) {
		boolean boxContains = false;
		
		for (int boxRow = (row / 3) * 3; boxRow < ((row / 3) * 3) + 3; boxRow++) {
			for (int boxCol = (col / 3) * 3; boxCol < ((col / 3) * 3) + 3; boxCol++) {
				if (board[boxRow][boxCol] == val) {
					boxContains = true;
				}
			}
		}
		
		return boxContains;
	}
	
	// return n if n is the only possible value for this spot
	// return 0 otherwise
	private int fillSpot(Spot sq) { 
		int fillSpotResult = 0;
		int counterArray[] = new int [10];
		int spotRow = sq.getRow();
		int spotCol = sq.getCol();
		int greaterThanZeroTotal = 0;
		
		for (int digit = 0; digit < counterArray.length; digit++) {
			counterArray[digit] = 0;
		}
		
		
		//Traverse the row of the spot.  
		//Increment counters according to the digits encountered.
		for (int col = 0; col < board.length; col++) {
			for (int digit = 1; digit < counterArray.length; digit++) {
				if (board[spotRow][col] == digit) {
					counterArray[digit]++;
				}
			}
		}
		
		//Traverse the col of the spot.
		//Increment counters according to the digits encountered.
		for (int row = 0; row < board.length; row++) {
			for (int digit = 1; digit < counterArray.length; digit++) {
				if (board[row][spotCol] == digit) {
					counterArray[digit]++;
				}
			}
		}
		
		//Traverse the box of the spot. 
		//Increment counters according to the digits encountered.
		for (int row = (spotRow / 3) * 3; row < ((spotRow / 3) * 3) + 3; row++) {
			for (int col = (spotCol / 3) * 3; col < ((spotCol / 3) * 3) + 3; col++) {
				for (int digit = 1; digit < counterArray.length; digit++) {
					if (board[row][col] == digit) {
						counterArray[digit]++;
					}
				}
			}
		}
		
		//Loop through the counterArray. If the digit counter is greater than 0, 
		//increment the greaterThanZeroTotal variable. 
		for (int digit = 1; digit < counterArray.length; digit++) {
			if (counterArray[digit] > 0) {
				greaterThanZeroTotal++;
			}
		}
		
		//If there are exactly 8 digit counters that have more than 0 count
		//(meaning there is only 1 digit counter that is 0, excluding the digit 0), 
		//loop through the counterArray and find the digit with the 0 count.  
		//Return that digit in the fillSpotResult variable.
		if (greaterThanZeroTotal == 8) {
			for (int digit = 1; digit < counterArray.length; digit++) {
				if (counterArray[digit] == 0 && board[spotRow][spotCol] == 0) {
					fillSpotResult = digit;
				}
			}
		}
		return fillSpotResult;
	}
	
	// return a valid spot if only one possibility for val in row
	// return null otherwise
	private Spot rowFill(int row, int val) {
		Spot spot = new Spot(0,0);
		int possibilityArray[] = new int [9];
		int possibilityCounter = 0;
		
		for (int index = 0; index < possibilityArray.length; index++) {
			possibilityArray[index] = 0;
		}
		
		//If the row does not contain the value
		if (!doesRowContain(row, val)) {
			for (int col = 0; col < board.length; col++) {
				//If the col and box does not contain the value, increment the possibility array.
				if (!doesColContain(col, val) && !doesBoxContain(row, col, val) 
						&& board[row][col] == 0) {
					possibilityArray[col]++;
					possibilityCounter++;
				}	
			}
			
			//If there is only 1 possibility, set the spot.
			if (possibilityCounter == 1) {
				for (int index = 0; index < possibilityArray.length; index++) {
					if (possibilityArray[index] > 0) {
						spot.setRow(row);
						spot.setCol(index);
					}
				}
			}
			else {
				spot = null;
			}
		}
		else {
			spot = null;
		}
		
		return spot;
	}
	
	// return a valid spot if only one possibility for val in col
	// return null otherwise
	private Spot colFill(int col, int val) {
		Spot spot = new Spot(0,0);
		int possibilityArray[] = new int [9];
		int possibilityCounter = 0;
		
		for (int index = 0; index < possibilityArray.length; index++) {
			possibilityArray[index] = 0;
		}
		
		//If the col does not contain the value
		if (!doesColContain(col, val)) {
			for (int row = 0; row < board.length; row++) {
				//If the row and box does not contain the value, increment the possibility array.
				if (!doesRowContain(row, val) && !doesBoxContain(row, col, val) 
						&& board[row][col] == 0) {
					possibilityArray[row]++;
					possibilityCounter++;
				}	
			}
			
			//If there is only 1 possibility, set the spot.
			if (possibilityCounter == 1) {
				for (int index = 0; index < possibilityArray.length; index++) {
					if (possibilityArray[index] > 0) {
						spot.setRow(index);
						spot.setCol(col);
					}
				}
			}
			else {
				spot = null;
			}
		}
		else {
			spot = null;
		}
		
		return spot;
	}
	
	// return a valid spot if only one possibility for val in the box
	// return null otherwise
	private Spot boxFill(int rowbox, int colbox, int val) {
		Spot spot = new Spot(0,0);
		int possibilityArrayRow[] = new int [9];
		int possibilityArrayCol[] = new int [9];
		int possibilityCounter1 = 0;
		int possibilityCounter2 = 0;
		
		for (int index = 0; index < possibilityArrayRow.length; index++) {
			possibilityArrayRow[index] = 0;
		}
		
		for (int index = 0; index < possibilityArrayCol.length; index++) {
			possibilityArrayCol[index] = 0;
		}
		
		//If the box does not contain the value
		if (!doesBoxContain(rowbox, colbox, val)) {
			for (int row = (rowbox / 3) * 3; row < ((rowbox / 3) * 3) + 3; row++) {
				for (int col = (colbox / 3) * 3; col < ((colbox / 3) * 3) + 3; col++) {
					//If the row and col does not contain the value, increment the possibility array.
					if (!doesRowContain(row, val) && !doesColContain(col, val) 
							&& board[row][col] == 0) {
						possibilityArrayRow[row]++;
						possibilityCounter1++;	
						
						possibilityArrayCol[col]++;
						possibilityCounter2++;
					}
				}
			}
			
			//If there is only 1 possibility, set the spot.
			if (possibilityCounter1 == 1 && possibilityCounter2 == 1) {
				for (int index = 0; index < possibilityArrayRow.length; index++) {
					if (possibilityArrayRow[index] > 0) {
						spot.setRow(index);	
					}
				}
				for (int index = 0; index < possibilityArrayCol.length; index++) {
					if (possibilityArrayCol[index] > 0) {
						spot.setCol(index);	
					}
				}
			}
			else {
				spot = null;
			}
		}
		else {
			spot = null;
		}
		
		return spot;
	}
	
	public void solve() {
		boolean isStuck = false;
		Spot spot;
		
		while (!isComplete() && !isStuck) {
			isStuck = true;
			
			//Check all possible rowFills.
			for (int row = 0; row < board.length; row++) {
				for (int val = 1; val < 10; val++) {
					spot = rowFill(row, val);
				
					if (spot != null) {
						board[spot.getRow()][spot.getCol()] = val;
						isStuck = false;
					}					
				}
			}
			
			//Check all possible colFills.
			for (int col = 0; col < board.length; col++) {
				for (int val = 1; val < 10; val++) {
					spot = colFill(col, val);
					
					if (spot != null) {
						board[spot.getRow()][spot.getCol()] = val;
						isStuck = false;
					}
				}
			}
			
			//Check all possible boxFills.
			for (int row = 0; row < 7; row += 3) {
				for (int col = 0; col < 7; col += 3) {
					for (int val = 1; val < 10; val++) {
						spot = boxFill(row, col, val);
						
						if (spot != null) {
							board[spot.getRow()][spot.getCol()] = val;
							isStuck = false;
						}
					}
				}
			}
			
			//Check all possible fillSpots.
			for (int row = 0; row < board.length; row++) {
				for (int col = 0; col < board.length; col++) {
					spot = new Spot(row,col);
					
					if (fillSpot(spot) != 0) {
						board[row][col] = fillSpot(spot);
						isStuck = false;
					}
				}
			}
		}
	}
	
	// who are you? Put your name here!
	public static String myName() {
		return "Aaron Cho";
	}
}
