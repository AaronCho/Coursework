import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Project2 {

	public static void main(String[] args) throws IOException{
		final BufferedReader reader = new BufferedReader(new FileReader(new File("src/tspinput.txt")));
	    String line;
	      
	    final int n = Integer.parseInt(reader.readLine());
	    double[] xloc = new double[n];
	    double[] yloc = new double[n];
	    String[] cityNames = new String[n];
	    double[][] distanceMatrix = new double[n][n];
	    
	    while ((line = reader.readLine()) != null) { //Read in values
	    	final String[] positionArgs = line.split(" ");
	    	
	    	xloc[Integer.parseInt(positionArgs[0])] = Double.parseDouble(positionArgs[1]);
	    	yloc[Integer.parseInt(positionArgs[0])] = Double.parseDouble(positionArgs[2]);
	    	cityNames[Integer.parseInt(positionArgs[0])] = positionArgs[3];	
	    }
		
	    //initialize distance matrix 
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				distanceMatrix[i][j] = -1;
			}
		}
		
		//fill in distance matrix
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				//the distance from the i index to the j index
				distanceMatrix[i][j] = Math.sqrt(Math.pow(xloc[j] - xloc[i], 2) + Math.pow(yloc[j] - yloc[i],2));
			}
		}
	    
	    //print test data
	    System.out.println("Test Data:");
	    System.out.println("n = " + n);
	    
	    for (int i = 0; i < n; i++) {
	    	System.out.println("City " + i + ": " + xloc[i] + " " + yloc[i] + " " + cityNames[i]);
	    }
	    
	    //print distance matrix
	    System.out.println("\nDistance Matrix:");
	    
	    for (int i = 0; i < n; i++) {
	    	for (int j = 0; j < n; j++) {
	    		System.out.print("|" + distanceMatrix[i][j] + "|" + " ");
	    	}
	    	
	    	System.out.println();
	    }
	    
	    System.out.println();
	    
	    //bruteForce(n, xloc, yloc, cityNames);
	    greedy(n, xloc, yloc, cityNames);
	    mst(n, xloc, yloc, cityNames);
		
	}

	public static void bruteForce(int n, double[] xloc, double[] yloc, String[] cityNames) {
		int[] permArray = new int[n - 1]; //permarray is the indices from 1 to n-1.  
		double min = 301 * n;
		Object[] minIndices = new Object[n];
		
		//only from 1 to n - 1, because we always start at city 0.
		for (int i = 0; i < permArray.length; i++) {
			permArray[i] = i + 1;
		}
		
		List<List<Integer>> permResult = permute(permArray);
		
		//For every permutation (outer loop), find the distances between each x-y pair and add them.
		for (int i = 0; i < n; i++) {
			List<Integer> currentPermutationList = permResult.get(i);
			Object indices[] = currentPermutationList.toArray();
			
			double d = 0;
			double a = 0;
	
			//add permuted distances to running total:
			for (int j = 0; j < n; j++) {
				if (j == 0) { //add distance from city 0 to first city to running total:
					a = Math.sqrt(Math.pow(xloc[(int)indices[j]] - xloc[0], 2) + Math.pow(yloc[(int)indices[j]] - yloc[0], 2));
					d += a;
				}
				
				if (j > 0 && j < n - 1) {
					a = Math.sqrt(Math.pow(xloc[(int)indices[j]] - xloc[(int)indices[j - 1]], 2) + Math.pow(yloc[(int)indices[j]] - yloc[(int)indices[j - 1]], 2));
					d += a;
				}
				
				if (j == n - 1) { //add distance from last city to city 0 to running total:
					a = Math.sqrt(Math.pow(xloc[0] - xloc[(int)indices[j - 1]], 2) + Math.pow(yloc[0] - yloc[(int)indices[j - 1]], 2));
					d += a;
				}
			}
			
			//Store results
			if (d < min) {
				min = d;
				minIndices = indices;
			}
		}
		
		//print results
		System.out.println("Optimal Tour: " + n + " Cities");
		System.out.println("Min Cost " + min);
		System.out.print("0 ");
		
		for (int i = 0; i < n - 1; i++) {
			System.out.print((int)minIndices[i] + " ");
		}
		
		System.out.print("0\n");
		System.out.print(cityNames[0] + " ");
		
		for (int i = 0; i < n - 1; i++) {
			System.out.print(cityNames[(int)minIndices[i]] + " ");
		}
		
		System.out.print(cityNames[0] + " ");
		
	}
	
	public static void greedy(int n, double[] xloc, double[] yloc, String[] cityNames) {
		boolean[] visited = new boolean[n];
		boolean allCitiesVisited = false;
		int[] indices = new int[n];
		double[] distances = new double[n];
		
		//initialize arrays
		for (int i = 0; i < n; i++) {
			visited[i] = false;
			indices[i] = -1;
			distances[i] = -1;
		}
		
		int currentIndex = 0;
		int tempCurrentIndex = 0;
		int indicesDistancesIndex = 0;
		
		while (!allCitiesVisited) {
			visited[currentIndex] = true;
			tempCurrentIndex = currentIndex;
			
			//find the min distance from all the distances from current index to all unvisited locations.
			double min = 301 * n;
			for (int i = 0; i < n; i++) {
				if (!visited[i]) {
					//find distance from current index to i
					double d = Math.sqrt(Math.pow(xloc[i] - xloc[currentIndex], 2) + Math.pow(yloc[i] - yloc[currentIndex],2));
					
					if (d < min) {
						min = d;
						tempCurrentIndex = i;
					}
				}
			}
			
			//update indices and distances arrays
			indices[indicesDistancesIndex] = tempCurrentIndex;
			distances[indicesDistancesIndex] = min;
			
			//check to see if all cities are visited
			int visitedCounter = 0;
			for (int i = 0; i < n; i++) {
				if (visited[i]) {
					visitedCounter++;
				}
			}
			
			if (visitedCounter == n) {
				allCitiesVisited = true;
			}
			
			currentIndex = tempCurrentIndex;
			indicesDistancesIndex++;
		}
		
		//find distance from indices[n-2] to 0.  Add result to distances[n-1].
		distances[n - 1] = Math.sqrt(Math.pow(xloc[0] - xloc[indices[n - 2]], 2) + Math.pow(yloc[0] - yloc[indices[n - 2]],2));
		indices[n - 1] = 0;
		
		//find min cost
		double minCost = 0;
		for (int i = 0; i < distances.length; i++) {
			minCost += distances[i];
		}
		
		//print results
		System.out.println("\n\nGreedy Tour: " + n + " Cities");
		System.out.println("Min Cost " + minCost);
		System.out.print("0 ");
		
		for (int i = 0; i < n; i++) {
			System.out.print(indices[i] + " ");
		}
		
		System.out.println("");
		
		System.out.print(cityNames[0] + " ");
		
		for (int i = 0; i < n; i++) {
			System.out.print(cityNames[indices[i]] + " ");
		}
	}

	public static void mst(int n, double[] xloc, double[] yloc, String[] cityNames) {
		boolean[] visited = new boolean[n];
		double[][] distanceMatrix = new double[n][n];
		int[][] adjacencyMatrix = new int[n][n]; //the MST
		boolean allCitiesVisited = false;
		int[] indices = new int[n];
		double[] distances = new double[n];
		
			//initialize arrays
			for (int i = 0; i < n; i++) {
				visited[i] = false;
				indices[i] = -1;
				distances[i] = 0;
			}
			
			//initialize distance matrix and adjacency matrix
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					distanceMatrix[i][j] = -1;
					adjacencyMatrix[i][j] = 0;
				}
			}
			
			//fill in distance matrix
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					//the distance from the i index to the j index
					distanceMatrix[i][j] = Math.sqrt(Math.pow(xloc[j] - xloc[i], 2) + Math.pow(yloc[j] - yloc[i],2));
				}
			}
			
			int destinationIndex = 0;
			int currentIndex = 0;
			
			//generate MST
			while (!allCitiesVisited) {
				
				visited[currentIndex] = true;
				double min = 301 * n;
				
				//check distance matrix for destination index
				for (int x = 0; x < n; x++) {		
					if (distanceMatrix[currentIndex][x] < min && distanceMatrix[currentIndex][x] != 0 && !visited[x]) {
						min = distanceMatrix[currentIndex][x];
						destinationIndex = x;
					}					
				}
				
				//Update adjacency matrix
				adjacencyMatrix[currentIndex][destinationIndex] = 1;
				
				distances[currentIndex] = min;
				visited[destinationIndex] = true;
				
				//check to see if all cities are visited
				int visitedCounter = 0;
				for (int i = 0; i < n; i++) {
					if (visited[i]) {
						visitedCounter++;
					}
				}
				
				if (visitedCounter == n) {
					allCitiesVisited = true;
				}
				
				currentIndex = destinationIndex;
			}
			
			Stack stack = new Stack();
			
			//Perform DFS to generate Tour order
			int counter = 0;
			int currentI = 0;
			int nextIndex = 0;
			
			while (counter < n) {
				for (int i = 0; i < n; i++) {
					if (adjacencyMatrix[currentI][i] == 1) {
						stack.push(i);
						nextIndex = i;
					}
				}
				
				currentI = nextIndex;	
				counter++;
			}
			
			//store contents of stack into indices array
			while (!stack.isEmpty()) {
				for (int i = 0; i < n; i++) {
					if (indices[i] == -1) {
						if (!stack.isEmpty()) {
							indices[i] = (int)stack.peek();
							stack.pop();
						}
					}
				}
			}
			
			//find min cost
			double minCost = 0;
			for (int i = 0; i < distances.length; i++) {
				minCost += distances[i];
			}
			
			minCost += distanceMatrix[indices[n - 2]][0];
			
			//print results
			System.out.println("\n\nMST Tour: " + n + " Cities");
			System.out.println("Min Cost " + minCost);
			System.out.print("0 ");
			
			for (int i = n - 2; i > -1; i--) {
				System.out.print(indices[i] + " ");
			}
			
			System.out.print("0 \n");
			
			System.out.print(cityNames[0] + " ");
			
			for (int i = n - 2; i > -1; i--) {
				System.out.print(cityNames[indices[i]] + " ");
			}
			
			System.out.print(cityNames[0] + " ");
			
			
	}
	
	//Permutation Algorithm source: http://codereview.stackexchange.com/questions/68716/permutations-of-any-given-numbers
	public static List<List<Integer>> permute(int[] num) {
	    List<List<Integer>> res=new ArrayList<List<Integer>>();
	    res.add(new ArrayList<Integer>()); // Add an empty list
	    for(int number = 0; number < num.length; number++)
	    {
	        List<List<Integer>> curr = new ArrayList<List<Integer>>();
	        for(List<Integer> nestedL : res)
	        {
	            for(int j = 0; j < nestedL.size() + 1 ;j++)
	            {
	                nestedL.add(j,num[number]);
	                List<Integer> temp = new ArrayList(nestedL);
	                curr.add(temp);
	                nestedL.remove(j);
	            }
	        }
	        res = new ArrayList<List<Integer>>(curr);
	    }
	    return res;
	}
}
