package salesmansdilema;

public class Exhaustive extends TSBase {
    
    boolean sprint = false;
    
    public Exhaustive (TravelingSalesman pts){
        super(pts,"Exhaustive");
    }

    MiniGrapher mg = null;
    @Override void solveIt() {
    	
    	Algorithms algo = new Algorithms(map);
    	algo.exhaust(this,1,numberOfCities);
        numberOfLengthsCalculated = algo.numberOfLengthsCalculated;
//    	mg = new MiniGrapher(map);
//    	System.out.println(description() + " is done");
    }
}
