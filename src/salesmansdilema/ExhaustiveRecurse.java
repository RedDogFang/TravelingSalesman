package salesmansdilema;

public class ExhaustiveRecurse extends TSBase {
    
    boolean sprint = false;
    
    public ExhaustiveRecurse (TravelingSalesman pts){
        super(pts,"ExhaustiveRecurse");
    }

    MiniGrapher mg = null;
    @Override void solveIt() {
    	
//    	numberOfCities = currentSolution.cities.length;
//    	updateBestSolution(currentSolution);
        RecursePerm(0);

        //System.out.println(description() + " is done "+numberOfCities);
    }
    
    void RecursePerm (int index){

    	if (gTimeToStop) 
            return;
        
        if (index == numberOfCities){
        	int step = map.cityDistances[currentSolution.cities[index-1]][currentSolution.cities[0]];
            currentSolution.length += step;
            numberOfLengthsCalculated++;
            if (currentSolution.length<bestSolution.length)
            {
                updateBestSolution(currentSolution);
                //System.out.println("*"+currentSolution.length);
            }
            
            if(sprint){
                System.out.print("E2 - len: "+currentSolution.length+" indices:");
                int l2=0;
                for (int i=0; i<numberOfCities; i++) {
                    System.out.print(" "+currentSolution.cities[i]);
                    l2+= map.cityDistances[currentSolution.cities[i]][currentSolution.cities[(i+1)%numberOfCities]];
                }
                System.out.println(" ("+l2+")");
            }
            
            currentSolution.length -= step;
            return;
        }
        
        for (int i=0; i<numberOfCities && !gTimeToStop; i++){
            boolean skip = false;
            
            for (int j=0; j<index && !skip; j++){
                if (currentSolution.cities[j]==i){
                    skip = true;
                    break;
                }
            }
            if (skip) continue;

            currentSolution.cities[index]=i;
            if (index>0) 
                currentSolution.length+= map.cityDistances[currentSolution.cities[index-1]][currentSolution.cities[index]];
            else
                currentSolution.length=0;
            
            if (currentSolution.length>=bestSolution.length) {
                if(sprint){
                    System.out.print("E2 - len: "+currentSolution.length+" indices:");
                    int l2=0;
                    for (i=0; i<index; i++) {
                        System.out.print(" "+currentSolution.cities[i]);
                        l2+= map.cityDistances[currentSolution.cities[i]][currentSolution.cities[(i+1)%numberOfCities]];
                    }
                    System.out.println(" ("+l2+") terminated early");
                }
                currentSolution.length-= map.cityDistances[currentSolution.cities[index-1]][currentSolution.cities[index]];
                return;
            }
            
            RecursePerm(index+1);
            if (index>0) 
                currentSolution.length-= map.cityDistances[currentSolution.cities[index-1]][currentSolution.cities[index]];
            else
                currentSolution.length=0;
        }
    }
}
