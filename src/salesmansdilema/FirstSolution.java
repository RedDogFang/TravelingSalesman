package salesmansdilema;
import java.util.ArrayList;

public class FirstSolution extends TSBase {
    public FirstSolution(TravelingSalesman ts) {
        super(ts, "pDescription");
        //TODO Auto-generated constructor stub
        
    }

    @Override void solveIt() {
        ArrayList<Integer> visitedPlaces = new ArrayList<>();  
        int currentCity = 0;
        int count = 1;
        int lowest = Integer.MAX_VALUE;
        int[] solution = new int[ts.map.cities.length];
        solution[0] = currentCity;
        while(count < ts.map.cityDistances.length) {
            for(int i = 0; i < ts.map.cityDistances.length; i++) {
                if(ts.map.cityDistances[currentCity][i] < lowest && currentCity != i && !visitedPlaces.contains(i)){
                    lowest = ts.map.cityDistances[currentCity][i]; 
                    currentCity = i;
                }
            }
            System.out.println(currentCity);
            visitedPlaces.add(currentCity);
            solution[count] = currentCity;
            lowest = Integer.MAX_VALUE;
            count++;
        }
        Solution beSolution = new Solution(count+1, solution);
        super.updateBestSolution(beSolution);
    }
}
