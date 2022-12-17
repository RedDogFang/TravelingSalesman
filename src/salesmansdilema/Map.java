package salesmansdilema;

import java.io.Serializable;

public class Map  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	City[] cities;
    int[][] cityDistances;
    int numberOfCities;
    int worldDimension;
    
    Map(int pNumberOfCities, int pWorldDimension){
        numberOfCities = pNumberOfCities;
        worldDimension = pWorldDimension;
        cities = new City[pNumberOfCities];
        for (int i = 0; i < numberOfCities; i++){
            String cityName = "c_"+i;
            cities[i]= new City(cityName,0,0,numberOfCities);
        }
        
        cityDistances = new int[pNumberOfCities][pNumberOfCities];
    }
    
    Map copy(){
        Map newMap = new Map(numberOfCities,worldDimension);
        
        for (int i=0;i<numberOfCities; i++)
            System.arraycopy(cityDistances[i], 0, newMap.cityDistances[i], 0, numberOfCities);
        for (int i=0;i<numberOfCities; i++) {
        	newMap.cities[i].xPos = cities[i].xPos;
        	newMap.cities[i].yPos = cities[i].yPos;
        }
        	
        newMap.numberOfCities = numberOfCities;
        newMap.worldDimension = worldDimension;
        return newMap;
    }
}

