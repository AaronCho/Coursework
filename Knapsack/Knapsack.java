import java.text.DecimalFormat;
import java.util.*;


public class Project3 {

	public static void main(String[] args) {
		
		/*//Test Case #1
	    int n = 6;
	    int[] weights = {2,4,3,4,4,1}; 
	    int W = 10;
	    int[] values = {1,2,3,3,3,6};*/

	    /*//Test Case #2  
	    int n = 10;
	    int[] weights = {3,4,2,5,6,1,2,7,8,2};
	    int W = 12;
	    int[] values = {6,7,4,3,2,6,8,7,9,6};*/

	    /*//Test Case #3
	    int n = 20;
	    int[] weights = {2,3,4,2,6,5,3,7,2,4,3,1,5,6,2,1,1,3,4,3}; 
	    int W = 18;
	    int[] values = {2,3,4,1,2,5,3,2,4,6,2,2,1,3,4,5,6,2,1,9};*/

	    //Test Case #4
	    int n = 25;
	    int[] weights = {9,16,12,8,7,14,7,8,9,14,15,18,20,2,4,5,10,11,3,17,15,18,15,9,7};
	    int W = 100;
	    int[] values = {1,7,3,4,5,5,3,4,6,2,6,6,4,2,1,1,2,2,4,5,4,3,2,1,3};
		
		/*
		int n = 6;
	    int[] weights = {2,4,3,4,4,1}; 
	    int W = n * 1000;
	    int[] values = {1,2,3,3,3,6};*/

	    System.out.println("Test Case #");
	    System.out.println("\tNumber of Items = " + n);
	    System.out.println("\tWeight Capacity of Knapsack: " + W);
	    
	    bruteForce(n, W, weights, values);
	    dynamicProgramming(n, W, weights, values);
	}
	
	public static void bruteForce(int n, int W, int[] weights, int[] values) {
		int maxValue = 0;
		double startNano = System.nanoTime();
		
		Set<Integer> mySet = new HashSet<Integer>();
		Set<Set<Integer>> solutionSet = new HashSet<Set<Integer>>();
		for (int i = 0; i < n; i++) {
			mySet.add(i);
		}
		
		for (Set<Integer> s : powerSet(mySet)) { // for each set in the powerset
			int totalWeight = 0;
			int totalValue = 0; 
			
			for (int index : s) { //for each integer in the index set
				totalWeight += weights[index];
				totalValue += values[index];
			}
			
			if (totalValue > maxValue && totalWeight < W) {
				maxValue = totalValue;
			}
		}
		
		//loop through the powerset again to find the other optimal solutions
		for (Set<Integer> s : powerSet(mySet)) { // for each set in the powerset
			int totalWeight = 0;
			int totalValue = 0; 
			
			for (int index : s) { //for each integer in the index set
				totalWeight += weights[index];
				totalValue += values[index];
			}
			
			if (totalValue == maxValue && totalWeight < W) {//add the optimal set to solution set.
				solutionSet.add(s);
			}
		
		}
		
		System.out.println("\nBrute Force Optimal Solutions (List all solutions):");
		
		for (Set<Integer> s : solutionSet) {
			Set<Integer> convertedSet = new HashSet<Integer>();
			int value = 0; 
			int weight = 0;
			
			for (int index : s) { //for each integer in the index set
				convertedSet.add(index + 1); 
				weight += weights[index];
				value += values[index];
			}
			
			
			System.out.println("\tSubset of Items = " + convertedSet + "; Subset Weight = " + weight + "; Subset Value = " + value + ";");
		}
		
		double endNano = System.nanoTime();
	      
	    System.out.println("\tRuntime (ms) = " + RoundTo2Decimals((double)((endNano - startNano) / 1000000)) + "ms");
	}
	
	public static void dynamicProgramming(int n, int W, int[] weights, int[] values) {
		double startNano = System.nanoTime();
		int OPT[][] = new int[n][W];
		
		//initialize all values to -1
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < W; j++) {
				OPT[i][j] = -1;
			}
		}
		
		//OPT(k, 0) = 0 and OPT(0, w) = 0
		for (int i = 0; i < n; i++) {
			OPT[i][0] = 0;
		}
		
		for (int j = 0; j < W; j++) {
			OPT[0][j] = 0;
		}
		
		//fill in OPT matrix
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < W; j++) {
				if (weights[i] > j) {
					OPT[i][j] = OPT[i - 1][j];
				}
				else {
					OPT[i][j] = Math.max(OPT[i - 1][j], values[i] + OPT[i - 1][j - weights[i]]);
				}
			}
		}
		
		
		//Find the optimal subset
		int subsetWeight = 0;
		Set<Integer> optimalSet = new HashSet<Integer>();
		optimalSet = findSolution(n - 1, W - 1, weights, values, OPT);
		
		System.out.println("\nDynamic Programming Optimal Solution (List one solution): ");
		System.out.print("\tSubset of Items = [");
		
		for (int value : optimalSet) {
			subsetWeight += weights[value];
			System.out.print(", ");
			System.out.print(value + 1); //instructions say to index items from 1 to n.
			
		}
		
		System.out.print("]; Subset Weight = " + subsetWeight + "; Subset Value = " + OPT[n - 1][W - 1] + ";");
		
		double endNano = System.nanoTime();
	      
	    System.out.println("\n\tRuntime (ms) = " + RoundTo2Decimals((double)((endNano - startNano) / 1000000)) + "ms");
	    
	    	/*//Only for Test Case 1: print the OPT matrix
	  		for (int i = 0; i < n; i++) {
	  			for (int j = 0; j < W; j++) {
	  				System.out.print("|" + OPT[i][j] + "| ");
	  			}
	  			
	  			System.out.println();
	  		}*/
	}
	
	public static Set<Integer> findSolution(int k, int w, int[] weights, int[] values, int[][] OPT) {
		Set<Integer> solutionSet = new HashSet<Integer>();
		
		if (k == 0 || w == 0) {
			
		}
		else if (weights[k] > w) {
			return findSolution(k - 1, w, weights, values, OPT);
		}
		else if (OPT[k - 1][w] >= values[k] + OPT[k - 1][w - weights[k]]) {
			return findSolution(k - 1, w, weights, values, OPT);
		}
		else {
			solutionSet = findSolution(k - 1, w - weights[k], weights, values, OPT);
			solutionSet.add(k);
		}
		return solutionSet;
	}
	
	//Subset Algorithm source: http://stackoverflow.com/questions/1670862/obtaining-a-powerset-of-a-set-in-java
	public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
	    Set<Set<T>> sets = new HashSet<Set<T>>();
	    if (originalSet.isEmpty()) {
	    	sets.add(new HashSet<T>());
	    	return sets;
	    }
	    List<T> list = new ArrayList<T>(originalSet);
	    T head = list.get(0);
	    Set<T> rest = new HashSet<T>(list.subList(1, list.size())); 
	    for (Set<T> set : powerSet(rest)) {
	    	Set<T> newSet = new HashSet<T>();
	    	newSet.add(head);
	    	newSet.addAll(set);
	    	sets.add(newSet);
	    	sets.add(set);
	    }		
	    return sets;
	}
	
	public static double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
	}
}
