package salesmansdilema;

public class Test extends TSBase {
    public Test (TravelingSalesman pts){
        super(pts,"Test");
    }

    @Override void 
    solveIt(){
        double startTime = System.currentTimeMillis();
        Algorithms algo = new Algorithms(map);
    	algo.exhaust2(this);
        numberOfLengthsCalculated = algo.numberOfLengthsCalculated;
        double endTime = System.currentTimeMillis();
        double duration = (endTime-startTime)/1000.00;
        long factorial = 1;
        for (long i=2; i<=ts.numCities; i++){
            factorial *= i;
        }
        long totalSmart = factorial/(2*ts.numCities);
        System.out.println("duration:"+duration+", factorial:"+factorial+", smart:"+totalSmart+", crafty:"+numberOfLengthsCalculated);
        System.out.println("factorial efficiency:"+(double)factorial/duration+", smart efficiency:"+(double)totalSmart/duration+", crafty efficiency:"+(double)numberOfLengthsCalculated/duration);

    }
}
