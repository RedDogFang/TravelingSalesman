package salesmansdilema;

import java.util.ArrayList;
import java.util.Random;

class TSTracker {
    TSP tsp;
    Solution tsSol;
    Thread thrd;
    boolean done = false;

    TSTracker(int numCities, TSP pTSP){
        tsp = pTSP;
        tsSol = new Solution(numCities);
        thrd = new Thread((Runnable) tsp);
    }
}

public class TravelingSalesman {
    int numCities = 13;
    Map map;
    Grapher graph = null;
    int seed = 0;
    int worldDimension = 100;
        
    ArrayList<TSTracker> tst2 = new ArrayList<TSTracker>();
    ArrayList<Solution> allSols = new ArrayList<Solution>();
    
    public TravelingSalesman() throws InterruptedException {
    	//System.out.println("TravelingSalesman constructor");
        MapGenerator mapGenerator = new MapGenerator();
        map = mapGenerator.GenerateMap(numCities, worldDimension, seed);
        start(map);
    }
    
    public Map getMap(){
            return(map.copy());
    }
    
    private void dumpAllSolutions(ArrayList<Solution> all) {
    	for(Solution s: all) {
       		System.out.println(s.description+" "+s.description+" length:"+s.length +" time:"+s.timeStamp);

        	for (int j=0; j<s.cities.length; j++) {
   	    		System.out.println(map.cities[s.cities[j]].xPos+" "+map.cities[s.cities[j]].yPos+" ");
        	}
        	System.out.println();
        	System.out.println();
    	}
    }
    
    private void dumpRawGraphData(ArrayList<TSTracker> tst) {
    	
        for (TSTracker t: tst){
    		System.out.print(t.tsp.description()+" "+t.tsp.description()+" ");
    	}

    	System.out.println();
    	for (int j=0; j<map.numberOfCities; j++) {
            for (TSTracker t: tst2){
	    		System.out.print(map.cities[t.tsSol.cities[j]].xPos+" "+map.cities[t.tsSol.cities[j]].yPos+" ");
	    	}
	    	System.out.println();
    	}
    	System.out.println();
    }
    
    public void verifyAndPrintSolution(TSTracker tsTracker) {
                
        String name = tsTracker.tsp.description();
        Solution solution = tsTracker.tsSol;
        int totalDistance = 0;
        int i;
        
        System.out.print(name+" ");
        
        if (solution.cities.length != numCities) {
            System.out.println("solution array is wrong length ("+solution.cities.length+") expected " + (numCities));
            return;
        }
        
        for (i = 0; i < numCities; i++){
            if (solution.cities[i] >= numCities || solution.cities[i] < 0){
                System.out.println("At least one invalid entry in solution array: index "+i+" is "+solution.cities[i]);
                return;
            }
        }
        
        boolean[] verifier = new boolean[numCities];
        
        for (i = 0; i<numCities; i++)
            verifier[i] = false;
        
        for (i = 0; i<numCities; i++){
            if (verifier[solution.cities[i]] == true){
                System.out.println("city visited twice: "+i);
                //return;
            }
            verifier[solution.cities[i]] = true;
        }

        for (i = 0; i < numCities; i++){
            if (verifier[i] == false){
                System.out.println("city never visited: "+i);
                return;
            }
        }

        for (i = 0; i < numCities; i++){
            totalDistance += map.cityDistances[solution.cities[i]][solution.cities[(i+1)%numCities]];
        }
        
        System.out.print("len: "+totalDistance+" indices:");

        for (i=0; i<numCities; i++) {
            System.out.print(" "+solution.cities[i]);
        }
        System.out.println();
    }
    
    public void start(Map map) {

    	this.map = map;
        numCities = map.numberOfCities;
        tst2.add(new TSTracker(numCities, new Test(this)));
        // tst2.add(new TSTracker(numCities, new SolutionRandom(this)));
        // tst2.add(new TSTracker(numCities, new NearestNeighbor(this)));
        // tst2.add(new TSTracker(numCities, new NearestAndFlip(this)));
        // tst2.add(new TSTracker(numCities, new Exhaustive(this)));
        // tst2.add(new TSTracker(numCities, new ExhaustiveRecurse(this)));
//        tst2.add(new TSTracker(numCities, new Specific(this)));
        
        graph = new Grapher(tst2,map);
        
        for (TSTracker t: tst2){
            t.thrd.start();
        }
    	long maxDuration = 10000; // millisecs
        long maxSleepTime = 1000; // millisecs
        long start = System.currentTimeMillis();
        long end = start+maxDuration;
        long now = System.currentTimeMillis();
        long updateDuration = 1000;
        long updateTime = now+updateDuration;
        while (now<end){
            if (isAllDone()){
                System.out.println("all are done, stopping");
                break;
            }
            long millisToMaxDuration = end-now;
            long sleepTime = Math.min(maxSleepTime,millisToMaxDuration);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            now = System.currentTimeMillis();
            if (now>updateTime){
                System.out.print(" "+((end-now)/1000)+"...");
                updateTime=now+updateDuration;
            }
            getLatest();
        }

        stop();

    }
    
    public void stop() {
               for (TSTracker t: tst2){
            t.tsp.stop();
        }
    
        for (TSTracker t: tst2){
            verifyAndPrintSolution(t);
        }
        
    //    dumpRawGraphData(tst2);
        
       dumpAllSolutions(allSols);
        
       System.out.println("Seed: "+seed);
   	
    }
    
    Solution bestSolution = null;
    
    public Solution getLatest() {
    	boolean improvedBest = false;
    	
        for (TSTracker t: tst2){
        	if (!t.done) {
        		
            	if(t.tsp.currentBestLength()<t.tsSol.length) {
            		t.tsp.uploadBestSolution(t.tsSol);
            		//allSols.add(new Solution(t.tsSol));
            		if (!t.tsp.getAllDone())
            			System.out.println(t.tsp.description()+": "+t.tsSol.length);
            		
            		if (bestSolution==null) {
            			bestSolution = new Solution(t.tsSol.cities.length);
            			t.tsSol.copyTo(bestSolution);
            			improvedBest = true;
            		}
            		else if (t.tsSol.length < bestSolution.length) {
            			t.tsSol.copyTo(bestSolution);
            			improvedBest = true;
            		}
            	}
            	t.done = t.tsp.getAllDone();
        	}

            if (graph != null)
            	graph.updateGraph();
        }    	
        if (improvedBest) {
        	//System.out.println("improved "+bestSolution.length);
        	bestSolution.description += bestSolution.length;
        	return bestSolution;
        }
        else {
        	return null;
        }
    }
    
    public boolean isAllDone() {
        for (TSTracker t: tst2){
        	if (!t.done) {
        		return false;
            }    	
        }
        return true;
    }
}