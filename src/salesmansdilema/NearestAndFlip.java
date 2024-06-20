package salesmansdilema;

public class NearestAndFlip extends TSBase {

	Algorithms algo = null;

	public NearestAndFlip (TravelingSalesman pts){
        super(pts,"NearestAndFlip");
    }
    
    @Override void solveIt(){

    	algo = new Algorithms(map);
    	
    	algo.Nearest(this);
    	
    	boolean keepGoing;
    	do {
    		//System.out.println("Top of loop");
    		keepGoing = false;
	    	if (algo.slidePoints(this)) keepGoing = true;
	    	if (algo.flipSegments(this)) keepGoing = true;
	    	if (algo.walkPoints(this)) keepGoing = true;
	    	if (algo.untwist(this)) keepGoing = true;
    	} while (keepGoing);

		numberOfLengthsCalculated = algo.numberOfLengthsCalculated;
    }
}