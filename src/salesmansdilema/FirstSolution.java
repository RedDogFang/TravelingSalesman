package salesmansdilema;

public class FirstSolution extends TSBase{

    // public FirstSolution(TravelingSalesman ts, String pDescription) {
    //     super(ts, pDescription);
    //     //TODO Auto-generated constructor stub
        
    // }

    public FirstSolution (TravelingSalesman pts){
        super(pts, "FirstSolution");

    }

    @Override
    void solveIt() {
        // TODO Auto-generated method stub
        
        bestSolution.cities[0] = 1;
        bestSolution.cities[1]= 5;
        bestSolution.cities[2] = 4;
        bestSolution.cities[3] = 0;
        bestSolution.cities[4]= 2;
        bestSolution.cities[5] = 3;
        

        

    }
    public static int dist(int x1, int y1, int x2, int y2){
        return (int) Math.round(Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2,2)));
    }
    
}
