package salesmansdilema;

import java.util.Random;

public class SolutionRandom extends TSBase {

    public SolutionRandom (TravelingSalesman pts){
        super(pts,"Random");
    }
    
    @Override 
    void solveIt(){
    	
    	Algorithms algo = new Algorithms(map);
    	
    	while (!gTimeToStop)
    	{
    		algo.Random(this);
        	boolean keepGoing;
        	do {
        		//System.out.println("Top of loop");
        		keepGoing = false;
//        		if (algo.untwist(this)) keepGoing = true;
//        		if (algo.slidePoints(this)) keepGoing = true;
//    	    	if (algo.flipSegments(this)) keepGoing = true;
//    	    	if (algo.walkPoints(this)) keepGoing = true;
        	} while (keepGoing);
    	}
		numberOfLengthsCalculated = algo.numberOfLengthsCalculated;
    }
}


//boolean[] visited = new boolean[numberOfCities];
//
//int i;
//Random r = new Random();
//
////int count = 0;
//while(!gTimeToStop){
//    for (i = 0; i < numberOfCities; i++){
//        visited[i]=false;
//    }
//
//    int visitedCityCount = 0;
//    int distance = 0;
//    int nextCity=0;
//    while (visitedCityCount<numberOfCities && !gTimeToStop){
//
//        int index = r.nextInt(numberOfCities-visitedCityCount);
//        for (i = 0; i < numberOfCities; i++) {
//            if (!visited[i]){
//                if (index-- == 0){
//                    nextCity = i;
//                    break;
//                }
//            }
//        }
//        if (visitedCityCount>0)
//            distance += map.cityDistances[currentSolution.cities[visitedCityCount-1]][currentSolution.cities[visitedCityCount]];
//        if (distance>=bestSolution.length){
//            break;
//        }
//        currentSolution.cities[visitedCityCount]=nextCity;
//        visited[nextCity]=true;
//        visitedCityCount++;
//    }
//    if (visitedCityCount == numberOfCities){
//        distance += map.cityDistances[currentSolution.cities[visitedCityCount-1]][currentSolution.cities[0]];
//        if (distance<bestSolution.length){
//        	currentSolution.length = distance;
//            updateBestSolution(currentSolution);
//        }
//    }
//}
//}    
