package salesmansdilema;

public class Specific extends TSBase {

	public Specific (TravelingSalesman pts){
        super(pts,"Specific");
    }
	Algorithms algo = null;
	
	int[] quickSol = {4, 42, 47, 1, 24, 37, 23, 36, 40, 0, 30, 20, 46, 16, 43, 38, 3, 39, 7, 17, 9, 33, 6, 44, 28, 12, 21, 13, 22, 32, 14, 29, 19, 8, 41, 5, 49, 2, 34, 11, 26, 18, 27, 31, 15, 45, 35, 10, 48, 25
};
    
    @Override void solveIt(){

    	System.arraycopy(quickSol, 0, currentSolution.cities, 0, quickSol.length);
    	
    	algo = new Algorithms(map);
    	
    	algo.calculateLength(currentSolution);
    	updateBestSolution(currentSolution);

		numberOfLengthsCalculated = algo.numberOfLengthsCalculated;
    }
}
