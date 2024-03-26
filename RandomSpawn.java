public class RandomSpawn{
    private int x;
    private int y;
    
    public RandomSpawn (int xLim, int yLim){
        x = (int) (Math.random () * xLim);
        y = (int) (Math.random () * yLim);
    }

    public int getX (){
        return x;
    }

    public int getY (){
        return y;
    }
}