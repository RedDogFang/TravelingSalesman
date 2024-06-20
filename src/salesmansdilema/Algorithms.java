package salesmansdilema;

import java.util.Arrays;
import java.util.Random;

public class Algorithms {
	
	// nearestAll sorts all cities by distance from every city  
	int[][] nearestAll = null;
	Map map = null;
	public long numberOfLengthsCalculated = 0;
	
	public Algorithms(Map m) {
		map = m;
    	fillNearest();
    }
	
    // generates a random solution, stores it in currentSolution plus bestSolution as appropriate
	public void Random(TSBase ts) {

		int numberOfCities = ts.numberOfCities;
		
        // create and initialize array to track visits to each city
        boolean[] visited = new boolean[numberOfCities];

        for (int i = 0; i < numberOfCities; i++){
            visited[i]=false;
        }

        Random r = new Random();

        int visitedCityCount = 0;
        int nextCity = 0;
        while (visitedCityCount < numberOfCities){

        	// generate random number based on number of unvisited cities
            int index = r.nextInt(numberOfCities-visitedCityCount);
            
            // find unvisited city based on random number
            for (int i = 0; i < numberOfCities; i++) {
                if (!visited[i]){
                    if (index-- == 0){
                        nextCity = i;
                        break;
                    }
                }
            }

            ts.currentSolution.cities[visitedCityCount]=nextCity;
            visited[nextCity]=true;
            visitedCityCount++;
        }

        ts.currentSolution.length = calculateLength(ts.currentSolution);
        
        if (ts.currentSolution.length<ts.bestSolution.length){
            ts.updateBestSolution(ts.currentSolution);
        }
	}
    
    // this fills in NearestAll with all cities from each city sorted by distance
    private void fillNearest() {
    	
    	int numCities = map.numberOfCities;
    	
    	nearestAll = new int[numCities][numCities];
    	int[] nearestLine = new int[numCities];
    	
    	// go through each city
    	for (int k=0; k<numCities; k++) {
    		
        	// first populate nearestLine with distances from this city
        	for (int i = 0; i<numCities; i++) {
        		nearestLine[i] = map.cityDistances[k][i];
        	}

    		// find the distances in order from this city
	    	for (int j=0; j<numCities; j++) {
		    	int shortestLength = 0x7ffffff0;
		    	int index = -1;
		    	for (int i=0; i<numCities; i++) {
		    		if (nearestLine[i] <= shortestLength) {
		    			shortestLength = nearestLine[i];
		    			index = i;
		    		}
		    	}
	    		
	    		nearestAll[k][j]=index;
	    		nearestLine [index] = 0x7fffffff;
	    	}
    	}
    }
    
    // sets currentSolution to the best nearestNeighbor solution
	public void Nearest(TSBase ts) {
		
		Solution currentSolution = ts.currentSolution;
		int numberOfCities = currentSolution.cities.length;
		
	    boolean[] visited = new boolean[numberOfCities];
	    
	    // start from every city
	    for (int j = 0; j<numberOfCities; j++){

	    	// initialize visited array
	    	for (int i = 0; i < numberOfCities; i++){
	            visited[i]=false;
	        }
	    	
	        visited[j]=true;
	        currentSolution.cities[0] = j;

	        int citiesVisited = 1;
	        int currentCity = j;
	        int nextCityDistance;
	        int nextCity;
	        
	        while (citiesVisited<numberOfCities){
	            nextCityDistance = 0x7fffffff;
	            nextCity = 0;
	
	            // loop through all cities finding the nearest one
	            for (int k = 0; k < numberOfCities; k++){
	
	                //look for nearest remaining city
	                if (visited[k]==false){
	                    if (map.cityDistances[currentCity][k]<nextCityDistance){
	                        nextCityDistance = map.cityDistances[currentCity][k];
	                        nextCity = k;
	                    }
	                }
	            }
	            
	            currentSolution.cities[citiesVisited++]=nextCity;
	            visited[nextCity]=true;
	            currentCity = nextCity;
	        }
	        
	        currentSolution.length = calculateLength(currentSolution);

	        if (currentSolution.length<ts.bestSolution.length){
	            ts.updateBestSolution(currentSolution);
	        }
	    }
	}

	// this needs bestSolution to already be populated
	// it starts with bestSolution and manipulates it by
	// going down line swapping points
    boolean flipSegments(TSBase ts){
    	// set local values for efficiency
		Solution currentSolution = ts.currentSolution;
		int numberOfCities = currentSolution.cities.length;

		boolean flipped = false; //was an improvement found
        boolean keepLooking; // keep going until a pass is completed w/o an improvement
        
        do {
            keepLooking = false;
            ts.bestSolution.copyTo(currentSolution);

            for (int i = 0; i<numberOfCities-2; i++){
                for (int j = i+1; j < numberOfCities-1; j++){

                    // swap
                	int temp = currentSolution.cities[i];
                    currentSolution.cities[i] = currentSolution.cities[j];
                    currentSolution.cities[j] = temp;
                    
                    int newLength = calculateLength(currentSolution);
                    if (newLength < currentSolution.length){
                    	// improvement
                        //System.out.println("flip "+newLength+" "+currentSolution.length);
                    	currentSolution.length = newLength;
                        ts.updateBestSolution(currentSolution);
                        keepLooking = true;
                        flipped = true;
                    }
                    else if (newLength == currentSolution.length) {
                    	
                    }
                    else {
                    	// swap back
                    	temp = currentSolution.cities[i];
                        currentSolution.cities[i] = currentSolution.cities[j];
                        currentSolution.cities[j] = temp;
                    }                    
                }
            }
        } while (keepLooking && !ts.gTimeToStop);
        return flipped;
    }
    
    // remove point ahead, slide near points up into hole, place removed point in near hole
    boolean slidePoints(TSBase ts){
    	// set local values for efficiency
		Solution currentSolution = ts.currentSolution;
		int numberOfCities = currentSolution.cities.length;

		boolean flipped = false; //was an improvement found
        boolean keepLooking; // keep going until a pass is completed w/o an improvement
        
        do {
            keepLooking = false;
            ts.bestSolution.copyTo(currentSolution);
            
            for (int i = 0; i<numberOfCities-2; i++){
                for (int j = i+1; j < numberOfCities-1; j++){

                    // open position in list of points
                	int temp = currentSolution.cities[j];
                	
                	for (int k=j; k>i; k--) {
                		currentSolution.cities[k]=currentSolution.cities[k-1];
                	}
                	currentSolution.cities[i]=temp;
                	
                    int newLength = calculateLength(currentSolution);
                    if (newLength < currentSolution.length){
                    	// improvement
                        //System.out.println("slide "+newLength+" "+currentSolution.length);
                    	currentSolution.length = newLength;
                        ts.updateBestSolution(currentSolution);
                        keepLooking = true;
                        flipped = true;
                    }
                    else if (newLength == currentSolution.length){
                    	
                    }
                    else {
                    	// slide back
                    	                    	
                    	for (int k=i; k<j; k++) {
                    		currentSolution.cities[k]=currentSolution.cities[k+1];
                    	}
                    	currentSolution.cities[j]=temp;
                    }                    
                }
            }
        } while (keepLooking && !ts.gTimeToStop);
        return flipped;
    }

    // remove points where solution crosses itself
    boolean untwist(TSBase ts){
    	// set local values for efficiency
		Solution currentSolution = ts.currentSolution;
		int numberOfCities = currentSolution.cities.length;

		boolean flipped = false; //was an improvement found
        boolean keepLooking; // keep going until a pass is completed w/o an improvement

        do {
            keepLooking = false;
            
            for (int i = 0; i<numberOfCities-2; i++){
                for (int j = numberOfCities-1; j > i; j--){
                	
                	ts.bestSolution.copyTo(currentSolution);
                	
                	for (int k=0; j-k>i+k; k++) {
                    	int temp = currentSolution.cities[i+k];
                		currentSolution.cities[i+k] = currentSolution.cities[j-k];
                		currentSolution.cities[j-k] = temp;

                		int newLength = calculateLength(currentSolution);
                		if (newLength < currentSolution.length){
                			// improvement
                			//System.out.println("untwist "+newLength+" "+currentSolution.length);
                			currentSolution.length = newLength;
                			ts.updateBestSolution(currentSolution);
                			keepLooking = true;
                			flipped = true;
	                    }
                    }                    
                }
            }
        } while (keepLooking && !ts.gTimeToStop);
        return flipped;
    }

    boolean walkPoints(TSBase ts){
    	
    	// set local values for efficiency
		Solution currentSolution = ts.currentSolution;
		int numberOfCities = currentSolution.cities.length;
		
		boolean flipped = false; //was an improvement found
        boolean keepLooking; // keep going until a pass is completed w/o an improvement
        
        do {
        	keepLooking = false;

	        for (int i = 0; i<numberOfCities-2; i++){
	        	
	        	// make sure currentSolution is the best         	
	        	ts.bestSolution.copyTo(currentSolution);
	        	
	        	int walkingCity = currentSolution.cities[i];
	        	
	            for (int j = i; j < numberOfCities-2; j++){
	
                    currentSolution.cities[j] = currentSolution.cities[j+1];
                    currentSolution.cities[j+1] = walkingCity;
                    
	                int newLength = calculateLength(currentSolution);
	                if (newLength < currentSolution.length){
	                	// improvement
	                    //System.out.println("walk "+newLength+" "+currentSolution.length);
	                	currentSolution.length = newLength;
	                    ts.updateBestSolution(currentSolution);

	                    keepLooking = true;
	                    flipped = true;
	                }
	            }
	        }
	    } while (keepLooking && !ts.gTimeToStop);
        return flipped;
    }

    int calculateLength(Solution s) {
		numberOfLengthsCalculated++;

		int numberOfCities = s.cities.length;
		int length = 0;
		int k;
		for (k = 0; k < numberOfCities-1; k++){
			length += map.cityDistances[s.cities[k]][s.cities[k+1]];
		}
		length += map.cityDistances[s.cities[k]][s.cities[0]];
		
		return length;
    }
    
    boolean sprint = true;
    private void printSol(Solution currentSolution) {
    	
        if(sprint){
            int len = currentSolution.cities.length;
            for (int i=0; i<len; i++) {
                System.out.print(" "+currentSolution.cities[i]);
            }
            System.out.println(" ("+currentSolution.length+")");
        }     
        
//        mg.updateGraph(currentSolution);
    }
    
    private void swapCities(Solution currentSolution, int a, int b) {
    	int numberOfCities = currentSolution.cities.length;
    	int length = currentSolution.length;
    	
    	length -= map.cityDistances[currentSolution.cities[a]][currentSolution.cities[(a-1+numberOfCities)%numberOfCities]];
    	length -= map.cityDistances[currentSolution.cities[a]][currentSolution.cities[(a+1)%numberOfCities]];
    	
    	length -= map.cityDistances[currentSolution.cities[b]][currentSolution.cities[(b-1+numberOfCities)%numberOfCities]];
    	length -= map.cityDistances[currentSolution.cities[b]][currentSolution.cities[(b+1)%numberOfCities]];
    	
		int tmp = currentSolution.cities[a];
		currentSolution.cities[a] = currentSolution.cities[b];
		currentSolution.cities[b] = tmp;    	

    	length += map.cityDistances[currentSolution.cities[a]][currentSolution.cities[(a-1+numberOfCities)%numberOfCities]];
    	length += map.cityDistances[currentSolution.cities[a]][currentSolution.cities[(a+1)%numberOfCities]];
    	
    	length += map.cityDistances[currentSolution.cities[b]][currentSolution.cities[(b-1+numberOfCities)%numberOfCities]];
    	length += map.cityDistances[currentSolution.cities[b]][currentSolution.cities[(b+1)%numberOfCities]];
    	
    	currentSolution.length = length;
    }

    // this has been customized to hold the first point as 0 which addresses the complete loop aspect
    public void exhaust(TSBase ts, int minIndex, int maxIndex) {
    	
    	// set local values for efficiency
		Solution currentSolution = ts.currentSolution;
		int numberOfCities = currentSolution.cities.length;
		
        // Print initial string, as only the alterations will be printed later
        printSol(currentSolution);

        int n = maxIndex-minIndex;//currentSolution.cities.length-1; // normal operation does not have -1
        int[] p = new int[n];  // Weight index control array initially all zeros. Of course, same size of the char array.
        int i = 1; //Upper bound index. i.e: if string is "abc" then index i could be at "c"
        while (i < n && !ts.gTimeToStop) {
            if (p[i] < i) { //if the weight index is bigger or the same it means that we have already switched between these i,j (one iteration before).
                int j = ((i % 2) == 0) ? 0 : p[i];//Lower bound index. i.e: if string is "abc" then j index will always be 0.
                swapCities(currentSolution, i+minIndex, j+minIndex);
                // Print current
                // printSol(currentSolution);
	
				numberOfLengthsCalculated++;
                if (currentSolution.length<ts.bestSolution.length)
                    ts.updateBestSolution(currentSolution);

                p[i]++; //Adding 1 to the specific weight that relates to the char array.
                i = 1; //if i was 2 (for example), after the swap we now need to swap for i=1
            }
            else { 
                p[i] = 0;//Weight index will be zero because one iteration before, it was 1 (for example) to indicate that char array a[i] swapped.
                i++;//i index will have the option to go forward in the char array for "longer swaps"
            }
        }
    }

// 	long globalCnt;
// 	// this has been customized to hold the first point as 0 which addresses the complete loop aspect
// 	public void exhaust2(TSBase ts) {
//     	// Map map = ts.map;
// 		globalCnt = 0;

// 		recurseSetup(ts);

// 		numberOfLengthsCalculated=globalCnt;
// 	}

// 	private void recurseSetup(TSBase ts){
// 		// for (int i=0; i<ts.numberOfCities; i++)
// 		int i=0;
// 		{
// 			ts.currentSolution.cities[0] = i;
// 			ts.currentSolution.length = 0;
// 			recurse(ts,1<<i, 0, ts.numberOfCities-1, i, ts.numberOfCities);
// 		}
// 	}

//     private void recurse(TSBase ts, int usedIndices, int runningLen, int level, int lastCity, int numCities){
// 		if(ts.gTimeToStop){
// 			return;
// 		}

// 		if (runningLen >= ts.bestSolution.length){
// 			return;
// 		}

//         if (level == 0){
// 			globalCnt++;
// 			runningLen += ts.map.cityDistances[ts.currentSolution.cities[0]][lastCity];
// 			// System.out.println(""+runningLen+" "+ts.bestSolution.length);
// 			// System.out.println(Arrays.toString(ts.currentSolution.cities));
// 			if (runningLen < ts.bestSolution.length){
// 				ts.currentSolution.length = runningLen;
// 				// System.out.println("updating best from "+ts.currentBestLength()+" to "+runningLen);
// 				ts.updateBestSolution(ts.currentSolution);
// 			}
// 			return;
//         }
// 		int startIndex = 1;
// 		int stopIndex = numCities;
// 		if (level == numCities-1){
// 			stopIndex = numCities-1;
// 		}
// 		if (level == 1){
// 			startIndex = ts.currentSolution.cities[1]+1;
// 		}
// 		for (int i=startIndex; i<stopIndex; i++){
//             int myIndex = 1 << i;
//             if ((usedIndices & myIndex) == 0){
//                 // an empty bit
// 				int len = map.cityDistances[lastCity][i];
// 				ts.currentSolution.cities[numCities-level] = i;                 //cities[cityCount-level]=i;
//                 recurse(ts, usedIndices|myIndex, runningLen+len,level-1, i, numCities);
//             }
//         }
//     }
// }

long gCount;
int gNumCities;
int[] gCities;
// this has been customized to hold the first point as 0 which addresses the complete loop aspect
public void exhaust2(TSBase ts) {
	gCount = 0;

	recurseSetup(ts);
	numberOfLengthsCalculated=gCount;
}

private void recurseSetup(TSBase ts){
	ts.currentSolution.length = 0;
	gNumCities = ts.numberOfCities;
	gCities = ts.currentSolution.cities;
	recurse(ts,1, 0, ts.numberOfCities-1, 0);
}

private void recurse(TSBase ts, int usedIndices, int runningLen, int level, int lastCity){
	if(ts.gTimeToStop){
		return;
	}

	if (runningLen >= ts.bestSolution.length){
		return;
	}

	if (level == 0){
		gCount++;

		runningLen += ts.map.cityDistances[ts.currentSolution.cities[0]][lastCity];
		if (runningLen < ts.bestSolution.length){
			ts.currentSolution.length = runningLen;
			ts.updateBestSolution(ts.currentSolution);
		}
		return;
	}

	int startIndex = 1;
	int stopIndex = gNumCities;
	if (level == gNumCities-1){
		stopIndex = gNumCities-1;
	}
	if (level == 1){
		startIndex = gCities[1]+1;
	}
	for (int i=startIndex; i<stopIndex; i++){
		int myIndex = 1 << i;
		if ((usedIndices & myIndex) == 0){
			// an empty bit
			int len = map.cityDistances[lastCity][i];
			gCities[gNumCities-level] = i;
			recurse(ts, usedIndices|myIndex, runningLen+len,level-1, i);
		}
	}
}
}