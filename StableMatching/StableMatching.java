import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Project1 {

	public static void main(String[] args) throws IOException{
		  final BufferedReader reader = new BufferedReader(new FileReader(new File("src/stablematchinginput.txt")));
	      String line;
	      
	      final int n = Integer.parseInt(reader.readLine());
	      
	      int[][] pref = new int[n * 2][n];
	      int[][] menPref = new int[n][n];
	      int[][] womenPref = new int[n][n];
	      
	      int prefCounter = 0;
	      
	      //Store preferences from file to pref array
	      while ((line = reader.readLine()) != null) {
	    	  final String[] positionArgs = line.split(" ");
	    	  
	    	  if (positionArgs[0].equals("") || line.isEmpty())
    			  ;
	    	  else {
	    		  for (int i = 0; i < positionArgs.length; i++) {
		    		  if (positionArgs[i].equals(""))
		    			  ;
		    		  else
		    			  pref[prefCounter][i] = Integer.parseInt(positionArgs[i]);
		    	  } 
	    		  prefCounter++;
	    	  }
	      }  
	      
	      //Transfer preferences from pref array to menPref array and womenPref array
	      for (int i = 0; i < n; i++) {
	    	  for (int j = 0; j < n; j++) {
	    		  menPref[i][j] = pref[i][j];
	    	  }
	      }
	      
	      for (int i = n; i < n * 2; i++) {
	    	  for (int j = 0; j < n; j++) {
	    		  womenPref[i - n][j] = pref[i][j];
	    	  }
	      }
	      
	      /*//Uncomment to see if pref array is correct
	      System.out.println("Pref Array:");
	      for (int i = 0; i < n * 2; i++) {
	    	  for (int j = 0; j < n; j++) {
	    		  System.out.print(pref[i][j]);
	    	  }
	    	  System.out.println();
	      }
	      
	      //Uncomment to see if menPref array is correct
	      System.out.println("menPref Array:");
	      for (int i = 0; i < n; i++) {
	    	  for (int j = 0; j < n; j++) {
	    		  System.out.print(menPref[i][j]);
	    	  }
	    	  System.out.println();
	      }
	      
	      //Uncomment to see if womenPref array is correct
	      System.out.println("womenPref Array:");
	      for (int i = 0; i < n; i++) {
	    	  for (int j = 0; j < n; j++) {
	    		  System.out.print(womenPref[i][j]);
	    	  }
	    	  System.out.println();
	      }*/
	      
	      //call either the bruteForce algorithm or the galeShapley algorithm (or both)
	      //bruteForce(n, menPref, womenPref);
	      galeShapley(n, menPref, womenPref);
	}
	
	public static void bruteForce(int n, int[][] menPref, int[][] womenPref) {
		double startNano = System.nanoTime();
		int[] males = new int[n];
		
		//initialize males array
		for (int i = 0; i < n; i++) {
			males[i] = i;
		}
		
		//generate all permutations of integers of n.
		//keep males in order, and have them match up to each permutation of females.
		//compare the males with all permutations of females and look for stable solution
		
		int[] num = new int[n];
		for (int i = 0; i < n; i++) {
			num[i] = i;
		}
		
		List<List<Integer>> permResult = permute(num);
		
		//compare male array with all permutations of permResult and look for stable solution
		int factorialN = factorial(n);
		for (int i = 0; i < factorialN; i++) {
			int[] stableMatchResult = stableMatching(n, males, permResult.get(i), menPref, womenPref); 
			
			if (stableMatchResult[0] == 1) {
				//the matching is stable, put into matchups array, and print results.
				//get stableMatchResult into two arrays to make printing easier.
				int[] stableMales = new int[n];
				int[] stableFemales = new int[n];
				
				int counter = 0;
				for (int x = 1; x < n + 1; x++) {
					stableMales[counter] = stableMatchResult[x];
					counter++;
				}
				
				counter = 0;
				for (int x = n + 1; x < stableMatchResult.length; x++) {
					stableFemales[counter] = stableMatchResult[x];
					counter++;
				}
				
				System.out.print("[");
				
				for (int x = 0; x < n; x++) {
					System.out.print("(" + stableMales[x] + "," + stableFemales[x] + ")");
				}
				
				System.out.println("]");			
			}
		}
		
		double endNano = System.nanoTime();
	      
	    System.out.println("Brute Force took " + RoundTo2Decimals((double)((endNano - startNano) / 1000000)) + "ms");
	}
	
	public static void galeShapley(int n, int[][] menPref, int[][] womenPref) {
		double startNano = System.nanoTime();
		String[] males = new String[n];
		String[] females = new String[n];
		String[][] menPrefString = new String[n][n];
		String[][] womenPrefString = new String[n][n];
		for (int i = 0; i < n; i++) {
			males[i] = Integer.toString(i);
			females[i] = Integer.toString(i);
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				menPrefString[i][j] = Integer.toString(menPref[i][j]);
				womenPrefString[i][j] = Integer.toString(womenPref[i][j]);
			}
		}
		
		GaleShapley gs = new GaleShapley(males, females, menPrefString, womenPrefString);
		
		double endNano = System.nanoTime();
	      
	    System.out.println("GaleShapley took " + RoundTo2Decimals((double)((endNano - startNano) / 1000000)) + "ms");
	}
	
	public static double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
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
	
	public static int factorial(int f) {
		
	    return ((f == 0) ? 1 : f * factorial(f - 1)); 
	} 
	
	public static int[] stableMatching(int n, int[] males, List<Integer> perm, int[][] menPref, int[][] womenPref) {
		int[] stablematch = new int[(n * 2) + 1];
		
		stablematch[0] = 1;  //stable
		
		for (int i = 0; i < n; i++) {
			//check menPref for current woman i.  if there are any preferred over current woman i, 
			//check womenPref if current man i is higher than a'.
			int currentWomanIndex = 0;
			
			for (int x = 0; x < n; x++) {
				if (menPref[i][x] == perm.get(i)) { //current woman i found
					currentWomanIndex = x;
				}
			}
			
			for (int currentPreferredWomanIndex = 0; currentPreferredWomanIndex < currentWomanIndex; currentPreferredWomanIndex++) {
				//menPref[i][a] are the women currently preferred over woman i.
				//Use this as the row of womenPref array.
			
				int currentManIndex = 0;
				int otherGuyIndex = 0;
				
				for (int y = 0; y < n; y++) {
					if (womenPref[currentWomanIndex][y] == males[i]) {
						currentManIndex = y;
					}
				}
				
				//search for index of current other guy by finding index of preferred woman.
				//start finding the currentPreferredWoman in the menPref array. This is menPref[i][currentPreferredWomanIndex].
				int currentPreferredWoman = menPref[i][currentPreferredWomanIndex];
				int currentOtherGuyIndex = 0;
				for (int m = 0; m < n; m++) {
					if (perm.get(m) == currentPreferredWoman) {
						currentOtherGuyIndex = m;
					}
				}
				
				int currentOtherGuy = males[currentOtherGuyIndex];
				
				for (int y = 0; y < n; y++) {
					if (womenPref[currentWomanIndex][y] == currentOtherGuy) {
						otherGuyIndex = y;
					}
				}
				
				//compare position of currentManIndex and otherGuyIndex.  If currentManIndex is lower,
				//than otherGuyIndex, there is an instability.
				if (currentManIndex < otherGuyIndex) {
					stablematch[0] = 0; //not stable
				}
			}
		}
		
		//fill stablematch array with males[] values from index 1 to n + 1.
		//fill stablematch array with perm values from index n + 2 to the end.
		int counter = 0;
		for (int i = 1; i < n + 1; i++) {
			stablematch[i] = males[counter];
			counter++;
		}
		
		counter = 0;
		for (int i = n + 1; i < stablematch.length; i++) {
			stablematch[i] = perm.get(counter);
			counter++;
		}
		
		return stablematch;
	}
}

class GaleShapley //Source: http://www.sanfoundry.com/java-program-gale-shapley-algorithm/
{
    private int N, engagedCount;
    private String[][] menPref;
    private String[][] womenPref;
    private String[] men;
    private String[] women;
    private String[] womenPartner;
    private boolean[] menEngaged;
 
    /** Constructor **/
    public GaleShapley(String[] m, String[] w, String[][] mp, String[][] wp)
    {
        N = mp.length;
        engagedCount = 0;
        men = m;
        women = w;
        menPref = mp;
        womenPref = wp;
        menEngaged = new boolean[N];
        womenPartner = new String[N];
        calcMatches();
    }
    /** function to calculate all matches **/
    public void calcMatches()
    {
        while (engagedCount < N)
        {
            int free;
            for (free = 0; free < N; free++)
                if (!menEngaged[free])
                    break;
 
            for (int i = 0; i < N && !menEngaged[free]; i++)
            {
                int index = womenIndexOf(menPref[free][i]);
                if (womenPartner[index] == null)
                {
                    womenPartner[index] = men[free];
                    menEngaged[free] = true;
                    engagedCount++;
                }
                else
                {
                    String currentPartner = womenPartner[index];
                    if (morePreference(currentPartner, men[free], index))
                    {
                        womenPartner[index] = men[free];
                        menEngaged[free] = true;
                        menEngaged[menIndexOf(currentPartner)] = false;
                    }
                }
            }            
        }
        printCouples();
    }
    /** function to check if women prefers new partner over old assigned partner **/
    private boolean morePreference(String curPartner, String newPartner, int index)
    {
        for (int i = 0; i < N; i++)
        {
            if (womenPref[index][i].equals(newPartner))
                return true;
            if (womenPref[index][i].equals(curPartner))
                return false;
        }
        return false;
    }
    /** get men index **/
    private int menIndexOf(String str)
    {
        for (int i = 0; i < N; i++)
            if (men[i].equals(str))
                return i;
        return -1;
    }
    /** get women index **/
    private int womenIndexOf(String str)
    {
        for (int i = 0; i < N; i++)
            if (women[i].equals(str))
                return i;
        return -1;
    }
    /** print couples **/
    public void printCouples()
    {       
        System.out.print("[");
        
        int counter = 0;
        while (counter < N) {
        	 for (int i = 0; i < N; i++) {
        		 if (Integer.parseInt(womenPartner[i]) == counter) {
        			 System.out.print("(" + counter + "," + i + ")");
        			 if (counter < N - 1)
        				 System.out.print(",");
        			 counter++;
        		 }
             }
        }
        
        System.out.println("]");
    }
    /** main function **/
    public static void main(String[] args) 
    {
        System.out.println("Gale Shapley Marriage Algorithm\n");
        /** list of men **/
        String[] m = {"M1", "M2", "M3", "M4", "M5"};
        /** list of women **/
        String[] w = {"W1", "W2", "W3", "W4", "W5"};
 
        /** men preference **/
        String[][] mp = {{"W5", "W2", "W3", "W4", "W1"}, 
                         {"W2", "W5", "W1", "W3", "W4"}, 
                         {"W4", "W3", "W2", "W1", "W5"}, 
                         {"W1", "W2", "W3", "W4", "W5"},
                         {"W5", "W2", "W3", "W4", "W1"}};
        /** women preference **/                      
        String[][] wp = {{"M5", "M3", "M4", "M1", "M2"}, 
                         {"M1", "M2", "M3", "M5", "M4"}, 
                         {"M4", "M5", "M3", "M2", "M1"},
                         {"M5", "M2", "M1", "M4", "M3"}, 
                         {"M2", "M1", "M4", "M3", "M5"}};
 
        GaleShapley gs = new GaleShapley(m, w, mp, wp);                        
    }
}
