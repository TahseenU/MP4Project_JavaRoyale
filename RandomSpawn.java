public class RandomSpawn{
    private int x;
    private int y;
    
    public RandomSpawn (int xLim, int yLim){
        x = (int) (Math.random () * xLim) + 20;
        y = (int) (Math.random () * yLim) + 40;
    }

    public int getX (){
        return x;
    }

    public int getY (){
        return y;
    }
}