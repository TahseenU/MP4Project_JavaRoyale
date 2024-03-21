public class HealthBox{
    private int x;
    private int y;
    
    public HealthBox (){
        x = (int) (Math.random () * 1321);
        y = (int) (Math.random () * 721);
    }

    public int getX (){
        return x;
    }

    public int getY (){
        return y;
    }
}