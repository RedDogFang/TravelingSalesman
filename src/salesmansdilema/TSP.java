package salesmansdilema;

interface TSP{
    public String description();// = "default";
    public void stop();
    public int currentBestLength();
    public Solution uploadBestSolution();    
    public void uploadBestSolution(Solution dst);
    public Boolean getAllDone();
}

class Solution {
	private static final long serialVersionUID = 1L;
	
	String description="";
	int timeStamp = 0;
    int length;
    int[] cities;

    Solution (){
        length = 0x7fffffff;
    }
    
    Solution (int numberOfCities){
        length = 0x7fffffff;
        cities = new int[numberOfCities];
    }
    
    Solution (int length, int[] path){
        this.length = length;
        cities = new int[path.length];
        System.arraycopy(path, 0, cities, 0, path.length);
    }
    
    Solution (Solution s){
    	this.description = s.description;
    	this.timeStamp = s.timeStamp;
        this.length = s.length;
        cities = new int[s.cities.length];
        System.arraycopy(s.cities, 0, cities, 0, s.cities.length);
    }
    
    void copyTo(Solution dst){
        System.arraycopy(cities, 0, dst.cities, 0, dst.cities.length);
        dst.length = length;    
        dst.timeStamp = timeStamp;
        dst.description = description;
    }
}

