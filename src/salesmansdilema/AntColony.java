package salesmansdilema;

import java.util.Random;

public class AntColony extends TSBase {
    
    public AntColony(TravelingSalesman ts, String pDescription) {
		super(ts, pDescription);
		// TODO Auto-generated constructor stub
	}

	public String description() {return "Ant Colony";}
    
    public int[] solveIt(int[][] cityDistances){
        
        int numberOfCities = cityDistances[0].length;
        
        int[] solution = new int[numberOfCities];
        int[] bestSolution = new int[numberOfCities+1];
        int bestDistance = 0x3fffffff;

        boolean[] visited = new boolean[numberOfCities];
        float[][] weights = new float[numberOfCities][numberOfCities];
        float[] startWeights = new float[numberOfCities];
        
        int guesses = 200000;//100*numberOfCities*numberOfCities;
        int i,j;
            
        for (i=0; i<numberOfCities; i++) {
            startWeights[i] = 1;
            for (j=0; j<numberOfCities; j++) {
                weights[i][j] = 1;
            }
        }
        Random r = new Random();
        int count = 0;

        for (j = 0; j<guesses; j++){
            for (i = 0; i < numberOfCities; i++){
                visited[i]=false;
            }
            
            // start in random city
            float sumOfStartWeights = 0;
            for (i = 0; i < numberOfCities; i++) 
                sumOfStartWeights += startWeights[i];
            
            float startGuess = sumOfStartWeights*r.nextFloat();
            
            for (i = 0; i < numberOfCities; i++){
                if (startGuess <= startWeights[i]){
                    visited[i] = true;
                    solution[0] = i;
                    break;
                }
                startGuess -= startWeights[i];
            }

            int visitedCityCount = 1;
            
            while (visitedCityCount<numberOfCities){

                int sumOfWeights=0;
                for (i = 0; i < numberOfCities; i++) {
                    if (!visited[i]){
                        sumOfWeights += weights[i][solution[visitedCityCount-1]];
                    }
                }
 
                float weightedGuess = sumOfWeights*r.nextFloat();
                
                for (i = 0; i < numberOfCities; i++) {
                    if (!visited[i]){
                        if (weightedGuess <= weights[i][solution[visitedCityCount-1]]){
                            solution[visitedCityCount]=i;
                            visited[i]=true;
                            break;
                        }
                        weightedGuess -= weights[i][solution[visitedCityCount-1]];
                    }
                }

                visitedCityCount++;
            }

            int distance = 0;
            for (i=1; i<numberOfCities; i++){
                distance += cityDistances[solution[i-1]][solution[i]];
            }
            distance += cityDistances[solution[0]][solution[numberOfCities-1]];
            
            count++;
            if (distance<bestDistance){
                System.arraycopy(solution, 0, bestSolution, 0, numberOfCities);

                bestSolution[numberOfCities] = solution[0];
                bestDistance = distance;
                j = 0; // reset the guess counter
                System.out.println(""+count+": "+bestDistance);
            }

            float weight = (float)1.0/(float)distance;
            startWeights[solution[0]] += weight;
            for (i = 1; i < numberOfCities; i++) {
                weights[solution[i-1]][solution[i]] += weight;
            }
        }
        System.out.println(""+count+": "+bestDistance);
        return bestSolution;
    }

	@Override
	void solveIt() {
		// TODO Auto-generated method stub
		
	}    
}
