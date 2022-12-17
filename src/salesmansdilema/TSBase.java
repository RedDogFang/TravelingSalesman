package salesmansdilema;

public abstract class TSBase implements TSP, Runnable{
    boolean gTimeToStop;
    Solution bestSolution, currentSolution;
    TravelingSalesman ts;
    int numberOfCities;
    Map map;
    String theDesc;
    boolean allDone = false;
        
    public String description(){
        return theDesc;
    }

    public Boolean getAllDone(){
        return allDone;
    }

    public TSBase (TravelingSalesman ts, String pDescription){
        this.ts = ts;
        theDesc = pDescription;
    }
    
    public void stop(){
        gTimeToStop = true;
    }
    
    public void run() {
        
        gTimeToStop = false;
        map = ts.getMap();
        numberOfCities = map.numberOfCities;
        bestSolution = new Solution(numberOfCities);

        initializeSolution(bestSolution);
        currentSolution = new Solution(bestSolution);

        solveIt();
        
        if (!gTimeToStop)
            System.out.println(theDesc+" completed len: "+bestSolution.length);
        else
        	System.out.println(theDesc+" terminated (exceeded timeout)");
        allDone = true;
    }
    
    void initializeSolution(Solution solution)
    {
        solution.length = 0;
        solution.cities[0] = 0;
        int i;
        for (i=1; i<solution.cities.length; i++){
            solution.cities[i] = i;
            solution.length += map.cityDistances[i-1][i];
        }
        solution.length += map.cityDistances[i-1][0];
    }
    
    public int currentBestLength() {
    	return bestSolution.length;
    }
    
    public Solution uploadBestSolution()
    {
    	//System.out.println("uploadBestSolution");
    	return new Solution(bestSolution);
    }
    
    public void uploadBestSolution(Solution dst)
    {
    	//System.out.println("uploadBestSolution");
    	bestSolution.copyTo(dst);
    }
    
    void updateBestSolution(Solution betterSolution)
    {
        if (!gTimeToStop) {
        	if (betterSolution.length < bestSolution.length)
        		betterSolution.copyTo(bestSolution);
        }
    }

    abstract void solveIt();
}
