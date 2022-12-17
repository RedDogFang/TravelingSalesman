package salesmansdilema;

//import java.util.HashSet;
import java.util.Random;

public class MapGenerator {
    
    String[] cityNames = {"Agra","Amsterdam","Antalya","Athens","Atlanta","Auckland","Bali","Bangkok","Barcelona","Beijing","Berlin","Bogotá","Boston","Brussels","Bucharest","Budapest","Buenos_Aires","Cairo","Cancún","Cape_Town","Chennai","Chicago","Copenhagen","Dallas","Delhi","Dubai","Dublin","Edinburgh","Edirne","Florence","Guangzhou","Hà_Nội","Hồ_Chí_Minh","Hong_Kong","Honolulu","Houston","Istanbul","Jakarta","Jerusalem","Johannesburg","Kiev","Kuala_Lumpur","Las_Vegas","Libson","Lima","London","Los_Angeles","Luxor","Macau","Madrid","Manila","Marrakesh","Mecca","Melbourne","Mexico_City","Miami","Milan","Montreal","Moscow","Mumbai","Munich","New_Orleans","New_York","Orlando","Oslo","Paris","Pattaya","Petra","Philadelphia","Phnom_Penh","Phuket","Prague","Punta_Cana","Rio_de_Janeiro","Riyadh","Rome","San_Diego","San_Francisco","Santiago","São_Paulo","Seattle","Seoul","Shanghai","Sharm_el-Sheikh","Shenzhen","Singapore","St._Petersburg","Stockholm","Sydney","Taipei","Tokyo","Toronto","Vancouver","Varna","Venice","Vienna","Warsaw","Washington_D.C.","Wellington","Zürich"};
    
    public Map GenerateMap(int numberOfCities, int worldDimension, int seed){
        int i,j;
        Random r;
        Map map = new Map(numberOfCities, worldDimension);
        
        r = new Random(seed);
        
        for (i = 0; i<numberOfCities; i++){
            if (numberOfCities <= cityNames.length)
                map.cities[i].name = cityNames[i];
            else
                map.cities[i].name = "c_"+i;

            map.cities[i].xPos = r.nextInt(worldDimension);
            map.cities[i].yPos = r.nextInt(worldDimension);
        }
        
        for (i = 0; i < numberOfCities; i++){
            for (j = 0; j < numberOfCities; j++){
                if (j==i)
                    map.cityDistances[i][j]=map.cityDistances[j][i]=0;
                else {
                    int xDelta = map.cities[i].xPos-map.cities[j].xPos;
                    int yDelta = map.cities[i].yPos-map.cities[j].yPos;

                    map.cityDistances[i][j]=map.cityDistances[j][i]=(int)Math.round(Math.sqrt((xDelta*xDelta)+(yDelta*yDelta)));
                }
            }
        }
        
        return map;
    }
}
