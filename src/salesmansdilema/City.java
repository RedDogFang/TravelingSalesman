package salesmansdilema;

public class City {
    
    String name;
    int xPos;
    int yPos;
    
    City(String pName, int pXPos, int pYPos, int cities){
        name = pName;
        xPos = pXPos;
        yPos = pYPos;
    }
    
    public void copy(City dst){
        dst.name = name;
        dst.xPos = xPos;
        dst.yPos = yPos;
    }
}
