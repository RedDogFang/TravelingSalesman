package salesmansdilema;

public class NearestNeighbor extends TSBase{

	Algorithms algo = null;
	
    public NearestNeighbor (TravelingSalesman pts){
        super(pts,"NearestNeighbor");
    }
    
    @Override void solveIt(){
    	algo = new Algorithms(map);
    	
    	algo.Nearest(this);
        numberOfLengthsCalculated = algo.numberOfLengthsCalculated;
    }
}